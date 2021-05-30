package suel.client;


import suel.forms.FormularioSesion;
import suel.forms.PaginaPrincipal;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RecSueldo implements EntryPoint {
 
  public void onModuleLoad() {
    
   FormularioSesion cu = new FormularioSesion();
   
    PaginaPrincipal pagPrp = new PaginaPrincipal();
//    pagPrp.dibujar();
    RootLayoutPanel.get().add(pagPrp.asWidget());   

    
  }
}
