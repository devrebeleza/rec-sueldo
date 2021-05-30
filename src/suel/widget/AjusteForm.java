package suel.widget;



import suel.data.ConceptosAjusteData;
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


public class AjusteForm extends Canvas {

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
  
  final DynamicForm formTotales = new DynamicForm();  

  
  public AjusteForm() {
  super();
       
  GWT.log("init ContextAreaListGrid()...", null);
 
//  cuadrilla que contiene todos los conceptos y calculos
  conceptosGrid.setWidth100();
//  conceptosGrid.setHeight(224); 
//  conceptosGrid.setHeight100(); 
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
  
  conceptosGrid.setData(ConceptosAjusteData.getRecords());  

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
  
  this.addChild(layoutPrincipal);  

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
    System.out.println("double");
    System.out.println(basico);
    
    
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
      
      for (numRegistro = 1; numRegistro < conceptosGrid.getRecords().length; numRegistro++){
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
                                       
           if (concepto.equalsIgnoreCase("Boletera")){
             haberes = CalcularBoletera(basico);
           }           
           if (concepto.equalsIgnoreCase("Antiguedad")){
             
             haberes = CalcularAntiguedad(unidades, basico);
           }
           
           if (concepto.equalsIgnoreCase("Presentismo Proporcional")){
//             haberes = CalcularPresentismo(basico);
             haberes = registroCampos.getHaberesDouble();
           }
           
           if (concepto.equalsIgnoreCase("Horas Normales")){
//             haberes = CalcularHorasNormales(unidades, basico);
               haberes = registroCampos.getHaberesDouble();
           }           

           if (concepto.equalsIgnoreCase("Horas Extras 50%")){
//             haberes = CalcularHoras50(unidades, basico);
               haberes = registroCampos.getHaberesDouble();
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
           
          
           sumaHaberes = sumaHaberes + haberes;
           
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
             retenciones = CalcularRetencionPorcentaje(11,haberes);  
           }
           
           if (concepto.equalsIgnoreCase("Ley 19032 3%")){
             retenciones = CalcularRetencionPorcentaje(3, haberes);
           } 
           
           if (concepto.equalsIgnoreCase("Obra Social 3%")){
              retenciones = CalcularRetencionPorcentaje(3, haberes);
           } 
           
           if (concepto.equalsIgnoreCase("Cuota Sindical 1.5%")){
             retenciones = CalcularRetencionPorcentaje(1.5, haberes);
            
           } 
          
           if (concepto.equalsIgnoreCase("Cuota Social 1%")){
             retenciones = CalcularRetencionPorcentaje(1, haberes);
           } 
          
           if (concepto.equalsIgnoreCase("Dto Anticipo Vacaciones")){
//             retenciones = CalcularDescuentoAnticipoVac(basico);
               retenciones = registroCampos.getRetencionesDouble();
           } 
           
           if (concepto.equalsIgnoreCase("Viáticos")){
//           haberes = CalcularViaticos(basico);
             conceptoNoRetenible = registroCampos.getHaberesDouble();
           
         } 
         
         if (concepto.equalsIgnoreCase("Anticipo Vacaciones")){
//           haberes = CalcularAnticipoVacaciones(basico);
             conceptoNoRetenible = registroCampos.getHaberesDouble();             
         }            
                  
           if (concepto.equalsIgnoreCase("Redondeo")){
             double totalHaberes = haberes + totalConceptosNoRetenibles;
             conceptoNoRetenible = CalcularRedondeo(totalHaberes, sumaRetenciones);
             conceptosGrid.setEditValue(numRegistro,3,conceptoNoRetenible);
           }     
                                           
           totalConceptosNoRetenibles = totalConceptosNoRetenibles + conceptoNoRetenible;
           sumaRetenciones = sumaRetenciones + retenciones;
           
           if(retenciones != 0){
             conceptosGrid.setEditValue(numRegistro,4,Redondear(retenciones,2));  
           }
//    redibujo la cuadrilla         
           conceptosGrid.redraw();
 
          }
      }
      
      return Redondear(sumaRetenciones,2);
  }
  
  
  private double CalcularBoletera(double basico) { 
    return 51;
  }


  private double CalcularAnticipoVacaciones(double basico) {
    double resultado;
//    resultado = ;
    return 0;
  }

  private double CalcularHorasNormales(double unidades, double basico) {
    double resultado;
//    resultado = unidades *;
    return 0;
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

  private double CalcularHoras50(double horas, double basico) {
    double resultado;
//    resultado = horas *;
    return 0;
  }

  private double CalcularPresentismo(double basico) {
    double resultado;
//    resultado = basico *;
    return 0;
  }

  private double CalcularAntiguedad(double años, double basico) {
  
    double resultado = 0;
    switch ((int) años){
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
            break;
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
            break;
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
            resultado = 656.04; //basico *
            break;
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
            break;
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
            break;
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
            break;
      case 30:
    }
  return resultado;
}
  
 public static double Redondear(double numero, int numeroDecimales){
  long mult = (long) Math.pow(10, numeroDecimales);
  double resultado = (Math.round(numero*mult))/(double)mult;
  return resultado;
 }
}