package suel.forms;



import suel.data.NavigationPaneRecord;
import suel.views.ContextAreaFactory;

import suel.data.NuevaActividadApplicationMenuData;
import suel.division.ApplicationMenu;
import suel.division.Masthead;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class PaginaPrincipal extends Composite {

  static SectionStack sectionStack;
  
  private static final int NORTH_HEIGHT = 85; // MASTHEAD_HEIGHT + APPLICATION_MENU_HEIGHT
  private static final int DEFAULT_MENU_WIDTH = 80;
  
  private VLayout mainVLayout; 
  private HLayout northLayout;  
  private VLayout southLayout;
  private VLayout eastLayout;  
  private VLayout westLayout;
  private TabSet toptabSet;
  
  ApplicationMenu applicationMenu ;
  
  interface GlobalResources extends ClientBundle {
    @NotStrict
    @Source("RecSueldo.css")
    CssResource css();
    }  
  
  public PaginaPrincipal(){

    super();
    
    GWT.log("init OnLoadModule()...", null);  
    
    // inject global styles
    GWT.<GlobalResources>create(GlobalResources.class).css().ensureInjected();
 
    // nos deshacemos de las barras de dezplazamiento y limpiamos el margen de las ventanas,
    // por que queremos aprovechar el area completa del cliente
    Window.enableScrolling(false);
    Window.setMargin("0px");
    
 // inicializamos el plano principal
    mainVLayout = new VLayout();
    mainVLayout.setWidth100();  
    mainVLayout.setHeight100();  
    
    
    // inicializo el plano contenedor norte
    northLayout = new HLayout();  
    northLayout.setHeight(NORTH_HEIGHT); 
    
    VLayout vLayout = new VLayout(); 
    // agrego la cabecera al contenedor plano anidado
    vLayout.addMember(new Masthead());
    
    // intitialise the Application menu
    applicationMenu = new ApplicationMenu();
//    applicationMenu.addMenu("<u>N</u>ueva Actividad", DEFAULT_MENU_WIDTH,  
//                "Sueldo, Ajuste, Concepto", new ApplicationMenuClickHandler());
    
    applicationMenu.addMenuNavegable("<u>N</u>ueva Actividad", DEFAULT_MENU_WIDTH, 
        NuevaActividadApplicationMenuData.getRecords(), new ApplicationMenuClickHandler());
    
//    applicationMenu.addMenu("New Re<u>c</u>ord", DEFAULT_MENU_WIDTH, 
//    "Account, Contact, separator, Lead, Opportunity", new ApplicationMenuClickHandler());
//    
//    Menu goToMenu = applicationMenu.addMenu("<u>G</u>o To", DEFAULT_MENU_WIDTH - 30);
//    applicationMenu.addSubMenu(goToMenu, "Sales", "Leads, Opportunities, Accounts, Contacts", new ApplicationMenuClickHandler());
//    applicationMenu.addSubMenu(goToMenu, "Settings", "Administration, Templates, Data Management", new ApplicationMenuClickHandler());
//    applicationMenu.addSubMenu(goToMenu, "Resource Centre", "Highlights, Sales, Settings", new ApplicationMenuClickHandler());
//    
//    applicationMenu.addMenu("<u>T</u>ools", DEFAULT_MENU_WIDTH - 30,
//                            "Import Data, Duplicate Detection, Advanced Find, Options", new ApplicationMenuClickHandler());
//    applicationMenu.addMenu("<u>H</u>elp", DEFAULT_MENU_WIDTH - 30, 
//                            "Help on this Page, Contents, myCRM Online, About myCRM", new ApplicationMenuClickHandler());
    
    
    
    //  agrego el menu de aplicación al plano contenedor anidado
    vLayout.addMember(applicationMenu);

    // agrego el plano contenedor anidado al plano contenedor norte
    northLayout.addMember(vLayout);
    
    // inicializo el plano contenedor este
    toptabSet = new TabSet();  
    toptabSet.addStyleName("crm-TabSet");
    toptabSet.setTabBarPosition(Side.TOP);  
    toptabSet.setWidth100();  
    toptabSet.setHeight100();  
           
    // inicializo el plano contenedor sur
    southLayout = new VLayout(); 
    
    // seteo el panel de navegacion (menu izquierdo) y el area de contexto () como miembros del 
    // plano contenedor sur 
    southLayout.setMembers(toptabSet);  
    
    
    mainVLayout.addMember(northLayout);
    mainVLayout.addMember(southLayout); 
    
    initWidget(mainVLayout);
    
  }
  
  private class ApplicationMenuClickHandler implements RecordClickHandler {
    @Override
    public void onRecordClick(RecordClickEvent event) {
//      String applicationName = ((Object) event).getItem().getTitle();
//      SC.say("You clicked: " + applicationName);      
      NavigationPaneRecord record = (NavigationPaneRecord) event.getRecord();
      setContextAreaView(record);
    }  
  }
  

  private void setContextAreaView(NavigationPaneRecord record) {
    
    int cont = 0;
    boolean creoTab = true;
    Tab tabRecorre;
    
    while ((cont < toptabSet.getNumTabs()) && (creoTab)){
      tabRecorre = toptabSet.getTab(cont); 
     
      boolean comparo = tabRecorre.getTitle().equalsIgnoreCase(record.getName());
//      String say = "titulo tab seleccionado tabrecorre: " + comparo + " title " + tabRecorre.getTitle() + " name " + record.getName();
//      SC.say(say);
      if (comparo){
        
        creoTab = false;
        toptabSet.selectTab(tabRecorre);
      }     
      cont++;
    }
    
  
    if (creoTab) {
      ContextAreaFactory factory = record.getFactory();
      Canvas view = factory.create();
      HLayout layoutTab = new HLayout();
      layoutTab.setWidth100();
      layoutTab.setHeight100();
      layoutTab.addMember(view);
      layoutTab.draw();
    
      Tab tTab1 = new Tab(record.getName());
      //SC.say("titulo tab creado: " + tTab1.getTitle());
     
      tTab1.setPane(layoutTab);
      tTab1.setCanClose(true);
    
      toptabSet.addTab(tTab1);
      toptabSet.selectTab(tTab1);

//      southLayout.setMembers(westLayout, toptabSet); 
      southLayout.setMembers(toptabSet);
    } 
  } 
  
  public static Object getSectionStack(){
    return sectionStack;
  }
  public static void dibujar(){
    sectionStack.draw(); 
    sectionStack.show();
 }
  
}
