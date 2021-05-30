package suel.views;

import suel.widget.AjusteForm;


import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

public class AjusteView extends VLayout {
  
  private static final String DESCRIPTION = "AccountsView";

  public AjusteView() {
  super();
    
  GWT.log("init AccountView()...", null);
    
  // initialise the Account View layout container
    this.addStyleName("crm-ContextArea");
    this.setWidth("*"); 
    
    // add the List Grid to the Account View layout container
    this.addMember(new AjusteForm());
  }
  
 public static class Factory implements ContextAreaFactory {
    
    private String id;
    
    public Canvas create() {
      AjusteView view = new AjusteView();
      id = view.getID();
        
      GWT.log("AccountsView.Factory.create()->view.getID() - " + id, null);
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