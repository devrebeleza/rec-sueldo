package suel.widget;



import suel.data.ConceptosData;
import suel.data.ConceptosRecord;
import suel.shared.ManejoErrores;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.PopupPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;


public class SueldoForm extends Canvas {

  final ListGrid conceptosGrid = new ListGrid();
  final VLayout layoutPrincipal = new VLayout();
  final HLayout layoutSuperior = new HLayout();
  final VLayout layoutEste = new VLayout();
  final VLayout layoutOeste = new VLayout();
  final HLayout layoutSeparador = new HLayout();
  final HLayout layoutTotal = new HLayout();
  final HLayout layoutInferior = new HLayout();
  final IButton calcularSueldoButton = new IButton("CalcularSueldo ");
  private final TextItem textHaberes = new TextItem();
  private final TextItem textRetenciones = new TextItem();
  private final TextItem textTotal = new TextItem();
  double redondeo = 0;
  double totalConceptosNoRetenibles;
  double valorHora = 0;
  double porcentajeAntiguedad = 1.5;
  
  final DynamicForm formTotales = new DynamicForm();  

  
  public SueldoForm() {
  super();
       
  GWT.log("init ContextAreaListGrid()...", null);
  conceptosGrid.setWidth("649px");
  
// cuadrilla que contiene los conceptos y los calculos
  conceptosGrid.setWidth100(); 
  conceptosGrid.setHeight100();
//  conceptosGrid.addStyleName("crm-Grid-Name");
  conceptosGrid.setShowAllRecords(true);  
  conceptosGrid.setSelectionType(SelectionStyle.SIMPLE);  
  conceptosGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX); 

  
  ListGridField descripcionField = new ListGridField("concepto", "Descripcion de los Conceptos");  
  descripcionField.setCanEdit(false);
  ListGridField unidadesField = new ListGridField("unidades", "Unidades"); 
  unidadesField.setCanEdit(true);
  
  ListGridField haberesField = new ListGridField("haberes", "Haberes");
  haberesField.setCanEdit(true);
  
  ListGridField retencionesField = new ListGridField("retenciones", "Retenciones");
  retencionesField.setCanEdit(true);
  
  conceptosGrid.setFields( descripcionField, unidadesField, haberesField, retencionesField);  
  
  conceptosGrid.setData(ConceptosData.getRecords());  
 
  conceptosGrid.setEditEvent(ListGridEditEvent.CLICK);  
  conceptosGrid.setEditByCell(true);  

  layoutSuperior.addMember(conceptosGrid);
  layoutSuperior.setWidth100();
  layoutSuperior.setHeight100();
  
  
  formTotales.setAutoFocus(true);  
  formTotales.setNumCols(3);
  formTotales.setWidth(300); 
  
  
// boton para ejecutar el calculo  
  calcularSueldoButton.setWidth(150);
  calcularSueldoButton.addClickHandler(new ClickHandler() {
    public void onClick(ClickEvent event) {
      CalcularSueldo();  
    }
  });  
  
  
//  text para mostrar los resultados totales
  textHaberes.setWidth("100px");  
  textHaberes.setTitle("Total Haberes");  
  textHaberes.setDefaultValue("0");
  
  
  textRetenciones.setWidth("100px");
  textRetenciones.setTitle("Total Retenciones"); 
  textRetenciones.setDefaultValue("0");
  
  textTotal.setWidth("100px");
  textTotal.setTitle("Total Neto");
  textTotal.setDefaultValue("0");
  
  formTotales.setFields(textHaberes, textRetenciones, textTotal);
  formTotales.setAlign(Alignment.CENTER);
  
//  lo uso como separador entre el layout izquierdo y el form de la derecha
  layoutSeparador.setWidth(30);
  
// contiene el boton de calcular sueldo
  layoutOeste.setWidth(65);
  layoutOeste.setHeight(75);
  layoutOeste.addMember(calcularSueldoButton);
  layoutOeste.addMember(calcularSueldoButton);
  layoutOeste.setAlign(Alignment.CENTER);
    
  
  layoutInferior.addMember(layoutOeste);
  layoutInferior.addMember(layoutSeparador);
  layoutInferior.addMember(formTotales);

  layoutInferior.setAlign(VerticalAlignment.CENTER);
  layoutInferior.setWidth100();
  layoutInferior.setHeight(125);
  
  layoutPrincipal.setWidth100();
  layoutPrincipal.setHeight100();
   
  layoutPrincipal.addMember(layoutSuperior);
  
  layoutPrincipal.addMember(layoutInferior);
  
//  this.addChild(nb);
  this.addChild(layoutPrincipal);
 
//  this.addChild(selectedCountriesGrid);  

  this.draw();      
  
  }
  
  
  public void CalcularSueldo(){
    
    double totalHaberes = calcularHaberes();
    
    if (totalHaberes != 0){
        double totalRetenciones = calcularRetenciones(totalHaberes);
        
        double haberRedondeo = totalHaberes + totalConceptosNoRetenibles;
        textHaberes.setValue(Redondear(haberRedondeo,2));
        textRetenciones.setValue(totalRetenciones);
    
        double calculoIntermedio = haberRedondeo - totalRetenciones;
    
        textTotal.setValue(Redondear(calculoIntermedio,2));
    }
  }
  
  public double calcularHaberes(){
    int numRegistro;
    ConceptosRecord registroCampos = (ConceptosRecord) conceptosGrid.getRecord(0);
    ListGridRecord[] registrosSeleccionados = conceptosGrid.getSelectedRecords();
    
  
    
    double basico = registroCampos.getHaberesDouble();
    
    
    double sumaHaberes = basico;   
     
    if (basico != 0){
 
      int regSelect;
      int cantRegSeleccionados = registrosSeleccionados.length;
//      guardo todos los nombres de los conceptos que han sido seleccionados
      String[] conceptosSeleccionados = new String[cantRegSeleccionados];
      for (regSelect = 0; regSelect < cantRegSeleccionados; regSelect ++){
        
            ConceptosRecord conceptoRecordSelec = (ConceptosRecord) registrosSeleccionados[regSelect];
        
            conceptosSeleccionados[regSelect] = conceptoRecordSelec.getConceptoName(); 

            
      }
      
      for (numRegistro = 1; numRegistro < conceptosGrid.getRecords().length - 1; numRegistro++){
         registroCampos = (ConceptosRecord) conceptosGrid.getRecord(numRegistro);

         String concepto = registroCampos.getConceptoName();

         double unidades = registroCampos.getUnidadesDouble();         
        
         int contador = 0;
         boolean registroSeleccionado = false;
         double haberes = 0;        
//         verifico que la fila ha sido seleccionada.
         while ((contador < cantRegSeleccionados) && (!registroSeleccionado) ){
           if(conceptosSeleccionados[contador].equals(concepto)){
             registroSeleccionado = true;
           }           
           contador++;
         };
         
         if(registroSeleccionado){
           
        	 
           if (concepto.equalsIgnoreCase("Días de Enfermedad")){
//               haberes = CalcularDiasdeEnfermedad(basico); 
               haberes = registroCampos.getHaberesDouble();  
           }
        	 
           if (concepto.equalsIgnoreCase("Boletera")){
//               haberes = CalcularBoletera(basico); dejo que el importe de la boletera se cargue a mano
               haberes = registroCampos.getHaberesDouble();  
           }           
           
           
           if (concepto.equalsIgnoreCase("Presentismo Proporcional")){
//             haberes = CalcularPresentismo(basico);
               haberes = registroCampos.getHaberesDouble();
           }
           
           if (concepto.equalsIgnoreCase("Antiguedad")){
             
               haberes = CalcularAntiguedad(unidades, sumaHaberes);
               System.out.println("AntiguedaCalcularValorHora(sumaHaberes);d: " + sumaHaberes);
           }
           
           if (concepto.equalsIgnoreCase("Horas Normales")){
               CalcularValorHora(sumaHaberes);
               haberes = CalcularHorasNormales(unidades);
//               haberes = registroCampos.getHaberesDouble();
           }           

           if (concepto.equalsIgnoreCase("Horas Extras 50%")){
             if ( valorHora == 0){
               CalcularValorHora(sumaHaberes);
             }
               haberes = CalcularHoras50(unidades);
//               haberes = registroCampos.getHaberesDouble();
           }
           
           if (concepto.equalsIgnoreCase("Horas Extras 100%")){
             if ( valorHora == 0){
               CalcularValorHora(sumaHaberes);
             }
               haberes = CalcularHoras100(unidades);
//               haberes = registroCampos.getHaberesDouble();
           }
           
           if (concepto.equalsIgnoreCase("Bonos Nocturnos")){
//             haberes = CalcularBonosNocturnos(unidades, basico); 
               haberes = registroCampos.getHaberesDouble();
           }
           
           if (concepto.equalsIgnoreCase("Vacaciones")){
//             haberes = CalcularVacaciones(basico);
             haberes = registroCampos.getHaberesDouble();
           }
           
           if (concepto.equalsIgnoreCase("Descuento Vacaciones")){
//             haberes = CalcularDescVacaciones(unidades, basico);
             haberes = registroCampos.getHaberesDouble();
             
           }           
           
          
           sumaHaberes = sumaHaberes + Redondear(haberes,2);
           
           if(haberes != 0){
             conceptosGrid.setEditValue(numRegistro,3,Redondear(haberes,2));
           }

//    redibujo la cuadrilla
           conceptosGrid.redraw();

          }
      }
      
      return sumaHaberes;
      
     }else{
       
//       PopupPanel ventanaError =  new PopupPanel(true, true);
//       ventanaError.setTitle(" Mensaje de error ");
//       ventanaError.setWidget(new Label("Debe ingresar un importe valido en el sueldo básico"));
//       ventanaError.setPixelSize(350,50);
//       ventanaError.setAnimationEnabled(true);
//       ventanaError.setGlassEnabled(false);
//       ventanaError.showRelativeTo(calcularSueldoButton);
//       
      
       String mensajeError = "Debe ingresar un importe válido en el sueldo básico";
       
       new ManejoErrores().MensajeErrorSc(mensajeError);
    }
    return 0;
  }
  
  
  


  public double calcularRetenciones(double haberes){
    
    int numRegistro;
    ConceptosRecord registroCampos = (ConceptosRecord) conceptosGrid.getRecord(0);
    ListGridRecord[] registrosSeleccionados = conceptosGrid.getSelectedRecords();
    
    
    double sumaRetenciones = 0;
    totalConceptosNoRetenibles = 0;
 
      int regSelect;
      int cantRegSeleccionados = registrosSeleccionados.length;
//      guardo todos los nombres de los conceptos que han sido seleccionados
      String[] conceptosSeleccionados = new String[cantRegSeleccionados];
      for (regSelect = 0; regSelect < cantRegSeleccionados; regSelect ++){
        
            ConceptosRecord conceptoRecordSelec = (ConceptosRecord) registrosSeleccionados[regSelect];
        
            conceptosSeleccionados[regSelect] = conceptoRecordSelec.getConceptoName(); 
      }
      
      for (numRegistro = 1; numRegistro < conceptosGrid.getRecords().length; numRegistro++){
         registroCampos = (ConceptosRecord) conceptosGrid.getRecord(numRegistro);

         String concepto = registroCampos.getConceptoName();
    
        
         int contador = 0;
         boolean registroSeleccionado = false;
         double retenciones = 0;
         double conceptoNoRetenible = 0;
//         verifico que la fila ha sido seleccionada.
         while ((contador < cantRegSeleccionados) && (!registroSeleccionado) ){
           if(conceptosSeleccionados[contador].equals(concepto)){
             registroSeleccionado = true;
           }           
           contador++;
         };
         
         if(registroSeleccionado){
                              
           if (concepto.equalsIgnoreCase("Jubilacion 11%")){
             //retenciones = CalcularRetencionPorcentaje(11,haberes);
        	   double unidades = Double.parseDouble(registroCampos.getUnidades());
        	   retenciones = CalcularRetencionPorcentaje(unidades,haberes);
           }
           
           if (concepto.equalsIgnoreCase("Ley 19032 3%")){
//             retenciones = CalcularRetencionPorcentaje(3, haberes);
        	   double unidades = Double.parseDouble(registroCampos.getUnidades());
        	   retenciones = CalcularRetencionPorcentaje(unidades,haberes);
           } 
           
           if (concepto.equalsIgnoreCase("Obra Social 3%")){
//              retenciones = CalcularRetencionPorcentaje(3, haberes);
        	   double unidades = Double.parseDouble(registroCampos.getUnidades());
        	   retenciones = CalcularRetencionPorcentaje(unidades,haberes);
           } 
           
           if (concepto.equalsIgnoreCase("Cuota Sindical 1.5%")){
//             retenciones = CalcularRetencionPorcentaje(1.5, haberes);
        	   double unidades = Double.parseDouble(registroCampos.getUnidades());
        	   retenciones = CalcularRetencionPorcentaje(unidades,haberes);
            
           } 
          
           if (concepto.equalsIgnoreCase("Cuota Social 1%")){
//             retenciones = CalcularRetencionPorcentaje(1, haberes);
        	   double unidades = Double.parseDouble(registroCampos.getUnidades());
        	   retenciones = CalcularRetencionPorcentaje(unidades,haberes);
           } 
           
           if (concepto.equalsIgnoreCase("Anticipo Vacaciones")){
//             haberes = CalcularAnticipoVacaciones(basico);
               conceptoNoRetenible = registroCampos.getHaberesDouble();             
           }            
                    
          
           if (concepto.equalsIgnoreCase("Embargo Judicial")){
        	   retenciones = registroCampos.getRetencionesDouble();  
               
             }
           
           if (concepto.equalsIgnoreCase("Cuota Alimentaria")){
        	   retenciones = registroCampos.getRetencionesDouble();
        	   System.out.println("Cuota Alimentaria: " + retenciones);
               
             }
           
          
          
           if (concepto.equalsIgnoreCase("Dto Anticipo Vacaciones")){
//             retenciones = CalcularDescuentoAnticipoVac(basico);
        	   retenciones = registroCampos.getRetencionesDouble();
        	   System.out.println("Dto anticipo Vacaciones: " + retenciones);
           } 
           
           if (concepto.equalsIgnoreCase("Viáticos")){
//           haberes = CalcularViaticos(basico);
             conceptoNoRetenible = registroCampos.getHaberesDouble();
           } 
         
  
           if (concepto.equalsIgnoreCase("Redondeo")){
             double totalHaberes = haberes + totalConceptosNoRetenibles;
             conceptoNoRetenible = CalcularRedondeo(totalHaberes, sumaRetenciones);
             conceptosGrid.setEditValue(numRegistro,3,conceptoNoRetenible);
           }
      
           
                                           
           totalConceptosNoRetenibles = totalConceptosNoRetenibles + conceptoNoRetenible;
           System.out.println("totalConceptosNoRetenibles: " + totalConceptosNoRetenibles);
           sumaRetenciones = sumaRetenciones + retenciones;
           System.out.println("sumaRetenciones: " + sumaRetenciones);
           
           if(retenciones != 0){
             conceptosGrid.setEditValue(numRegistro,4,Redondear(retenciones,2));  
           }
//    redibujo la cuadrilla         
           conceptosGrid.redraw();
 
          }
      }
      
      return Redondear(sumaRetenciones,2);
  }
  
  private void CalcularValorHora(double sumaHaberes) {
    valorHora = sumaHaberes / 192;  
  }
  
  private double CalcularBoletera(double basico) { 
    return 51;
  }


  private double CalcularAnticipoVacaciones(double basico) {
    double resultado;
//    resultado = ;
    return 0;
  }

  private double CalcularHorasNormales(double unidades) {
    double resultado;
    resultado = unidades * valorHora;
    return resultado;
  }

  private double CalcularRedondeo(double sumaHaberes, double sumaRetenciones) {
   
    double resultado;
    double resultadoResta = (sumaHaberes - sumaRetenciones);
       
    long parteEntera = (long)resultadoResta;
    double parteDecimal = resultadoResta - (double)parteEntera;
    
    resultado = 1 - parteDecimal;
    
    return Redondear(resultado,2);
  }

  private double CalcularViaticos(double basico) {
    // TODO Auto-generated method stub
    return 0;
  }

  private double CalcularDescuentoAnticipoVac(double basico) {
    
    return 0;
  }

  private double CalcularRetencionPorcentaje(double porcentaje, double haberes) {
    double resultado;
    resultado = (haberes * porcentaje )/100;
    return resultado;
  }

  private double CalcularDescVacaciones(double unidades, double basico) {
    // TODO Auto-generated method stub
    return 0;
  }

  private double CalcularVacaciones(double basico) {
    // TODO Auto-generated method stub
    return 0;
  }

  private double CalcularBonosNocturnos(double unidades, double basico) {
    double resultado;
//    resultado = unidades * ;
    return 0;
  }

  private double CalcularHoras50(double horas) {
    double resultado;
    double costoHora50 = (valorHora * 1.5);
    resultado = horas * costoHora50;
    return resultado;
  }
  
  private double CalcularHoras100(double horas) {
    double resultado;
    double costoHora100 = (valorHora * 2);
    resultado = horas * costoHora100;
    return resultado;
  }

  private double CalcularPresentismo(double basico) {
    double resultado;
//    resultado = basico *;
    return 0;
  }

  private double CalcularAntiguedad(double años, double basico) {
  
    double resultado = 0;
    double costoAntiguedad = (basico * porcentajeAntiguedad) /100;
    resultado = Redondear(costoAntiguedad,2) * años;
    return resultado;
}
  
 public static double Redondear(double numero, int numeroDecimales){
  long mult = (long) Math.pow(10, numeroDecimales);
  double resultado = (Math.round(numero*mult))/(double)mult;
  return resultado;
 }
}