package suel.shared;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;

public class ManejoErrores {

  public ManejoErrores(){
    
  }
  public void MensajeError(String mensaje){
    
  Window ventanaError=new Window();
  
  ventanaError.setTitle("Error");
  ventanaError.setWidth(300);  
  ventanaError.setHeight(85); 
  ventanaError.setIsModal(true);
  ventanaError.setShowModalMask(true);
  ventanaError.centerInPage();
 
  Label label = new Label("<br /> " + mensaje);  
  
  label.setHeight100();  
  label.setPadding(5);  
  label.setValign(VerticalAlignment.TOP);  
  ventanaError.addItem(label);
 
  ventanaError.show();  
  }
  
  public void MensajeErrorSc(String mensaje){
    
    Window ventanaError=new Window();
    
    ventanaError.setTitle("Error");
    ventanaError.setWidth(300);  
    ventanaError.setHeight(85); 
    ventanaError.setIsModal(true);
    ventanaError.setShowModalMask(true);
    ventanaError.centerInPage();
   
    Label label = new Label("<br /> " + mensaje);  
    
    label.setHeight100();  
    label.setPadding(5);  
    label.setValign(VerticalAlignment.TOP);  
    ventanaError.addItem(label);
   
//    ventanaError.show();
//    SC.say(mensaje);
    SC.say("Mensaje", mensaje);
    
    }
}
