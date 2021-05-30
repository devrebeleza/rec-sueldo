package suel.views;


import suel.widget.SueldoForm;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

public class SueldoView extends VLayout {
  
  private static final String DESCRIPTION = "AccountsView";

  public SueldoView() {
  super();
    
  GWT.log("init AccountView()...", null);
    
  // initialise the Account View layout container
    this.addStyleName("crm-ContextArea");
    this.setWidth("*"); 
    this.setAlign(Alignment.CENTER);
    
    // add the List Grid to the Account View layout container
    this.addMember(new SueldoForm());
  }
  
 public static class Factory implements ContextAreaFactory {
    
    private String id;
    
    public Canvas create() {
      SueldoView view = new SueldoView();
      id = view.getID();
        
      GWT.log("SueldoView.Factory.create()->view.getID() - " + id, null);
      return view;
    }

    public String getID() {
      return id;
    }

    public String getDescription() {
      return DESCRIPTION;
    }
    
  }   
}