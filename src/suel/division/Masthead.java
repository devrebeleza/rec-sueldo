package suel.division;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

public class Masthead extends HLayout {

  private static final int MASTHEAD_HEIGHT = 60;
    

  public Masthead() {
    super();
    
    GWT.log("init Masthead()...", null);
  
    // inicializao el plano contenedor
    this.addStyleName("crm-Masthead");     
    this.setHeight(MASTHEAD_HEIGHT);
//    this.setBackgroundColor("#C3D9FF");  
  
 // initialise the Logo image
    Img logo = new Img("user_logob.png", 45, 45); 
    logo.setSize("40px", "40px");
    logo.addStyleName("crm-Masthead-Logo"); 
    
    // inicializo el label cabecera 
    Label name = new Label();  
    name.setWidth("155px");
    name.addStyleName("crm-MastHead-Name");  
    name.setContents("Sueldo al Día"); 
      
    // inicializo el contenedor del lado izquierdo
    HLayout westLayout = new HLayout();
    westLayout.setHeight(MASTHEAD_HEIGHT);  
    westLayout.setWidth("60%");
    westLayout.addMember(logo);
    westLayout.addMember(name);
//    westLayout.setMembers(logo, name);
    
    // inicializo la etiqueta de firmado de usuario
    Label signedInUser = new Label();  
    signedInUser.setWidth("151px");
    signedInUser.addStyleName("crm-MastHead-SignedInUser");
    signedInUser.setAlign(Alignment.RIGHT);
    signedInUser.setContents("<b>Contacto    </b><br />rgarcia.inf@gmail.com    <br />");   
    
    // inicializo el contenedor derecho
    HLayout eastLayout = new HLayout();
    eastLayout.setAlign(Alignment.RIGHT);  
    eastLayout.setHeight(MASTHEAD_HEIGHT);
    eastLayout.setWidth("30%");
    eastLayout.addMember(signedInUser);   
    
    // agrego el contenedor izquierdo y derecho al contenedor principal
    this.addMember(westLayout);   
    this.addMember(eastLayout); 
  } 
}