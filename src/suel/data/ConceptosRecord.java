package suel.data;

import suel.shared.ManejoErrores;

import com.google.gwt.user.client.ui.PopupPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.grid.ListGridRecord;  
  
  
public class ConceptosRecord extends ListGridRecord {  
  
    public ConceptosRecord() {  
    }  
  
    public ConceptosRecord(String tipo, String conceptoName, String unidades, String haberes, String retenciones) {
        setTipo(tipo); 
        setConceptoName(conceptoName);
        setUnidades(unidades);
        setHaberes(haberes);
        setRetenciones(retenciones);       
    }  
  
    // sets 
    
    public void setRetenciones(String retenciones) {
      setAttribute("retenciones", retenciones);  
    }
    
    public void setRetencionesDouble(double retenciones) {
      setAttribute("retenciones", retenciones);  
    }

    public void setHaberes(String haberes) {
      setAttribute("haberes", haberes);        
    }
    
    public void setHaberesDouble(Double haberes) {
      String haber = haberes.toString();
      setAttribute("haberes", haber);        
    }
    

    public void setUnidades(String unidades) {
      setAttribute("unidades", unidades);        
    }

    public void setUnidadesDouble(double unidades) {
      setAttribute("unidades", unidades);        
    }

    
    public void setConceptoName(String conceptoName) {
      setAttribute("concepto", conceptoName);  
    }

    public void setTipo(String tipo) {
      setAttribute("tipo", tipo);  
    }

    
    // gets
    
    public String getRetenciones() {
      return getAttribute("retenciones");  
    }

    public double getRetencionesDouble() {
      String valorAtributo = getAttribute("retenciones");
      if (valorAtributo != null){
          try{ 
              double retorno = Double.parseDouble(valorAtributo);
              return retorno;
          }catch(NumberFormatException e){ 
           
            String nameConcepto = this.getConceptoName();
            String mensajeError = " Ingrese valores validos en la columna Retenciones <br />Para el Concepto \"" + nameConcepto + "\" ";
            
            new ManejoErrores().MensajeError(mensajeError);

            return 0; 
          }  
      }
      return 0;
    }
    
    public String getHaberes() {
      return getAttribute("haberes");        
    }
    
    public double getHaberesDouble() {
      String valorAtributo = getAttribute("haberes");
      if (valorAtributo != null){
        try{ 
          double retorno = Double.parseDouble(valorAtributo);
          return retorno;
         }catch(NumberFormatException e){ 
           
           String nameConcepto = this.getConceptoName();
           String mensajeError = " Ingrese valores validos en la columna Haberes <br />Para el Concepto \"" + nameConcepto + "\" ";
           
//           new ManejoErrores().MensajeError(mensajeError); 
           
          return 0;
        }
      }
      return 0;
    }

    public String getUnidades() {
      return getAttribute("unidades");
    }
    
    public double getUnidadesDouble() {
      String valorAtributo = getAttribute("unidades");
      if (valorAtributo != null){
        try{ 
          double retorno = Double.parseDouble(valorAtributo);
          return retorno;
         }catch(NumberFormatException e){ 
           
             String nameConcepto = this.getConceptoName();
             String mensajeError = "  Ingrese valores validos en la columna Unidades <br />Para el Concepto \"" + nameConcepto + "\" ";
             
             new ManejoErrores().MensajeError(mensajeError);
//           Window ventanaError=new Window();
//          
//           ventanaError.setTitle("Error");
//           ventanaError.setWidth(300);  
//           ventanaError.setHeight(85); 
//           ventanaError.setIsModal(true);
//           ventanaError.setShowModalMask(true);
//           ventanaError.centerInPage();
//           String nameConcepto = this.getConceptoName();
//           Label label = new Label( "<br />   Ingrese valores validos en la columna Unidades <br />Para el Concepto \"" + nameConcepto + "\" ");  
//           
//           label.setHeight100();  
//           label.setPadding(5);  
//           label.setValign(VerticalAlignment.TOP);  
//           ventanaError.addItem(label);
//          
//           ventanaError.show();
           
           
          return 0;
        }
      }
      return 0;
    }

    public String getConceptoName() {
      return getAttribute("concepto");  
    }

    public String getTipo() {
      return getAttribute("tipo");  
    }
   
}  