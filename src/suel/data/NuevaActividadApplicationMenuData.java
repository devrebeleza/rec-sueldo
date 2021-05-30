package suel.data;



import suel.views.AjusteView;

import suel.views.SueldoView;


public class NuevaActividadApplicationMenuData {


    private static NavigationPaneRecord[] records;

    public static NavigationPaneRecord[] getRecords() {
    if (records == null) {
      records = getNewRecords();
    }
    return records;
    }
    
    public static NavigationPaneRecord[] getNewRecords() {
      return new NavigationPaneRecord[]{
        new NavigationPaneRecord("sueldo", "Sueldo", new SueldoView.Factory(), null),
        new NavigationPaneRecord("ajuste", "Ajuste", new AjusteView.Factory(), null),
//        new NavigationPaneRecord("concepto", "Concepto", new ConceptoView.Factory(), null)
      };
    }
  }