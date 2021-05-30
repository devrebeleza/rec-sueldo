package suel.data;

public class ConceptosAjusteData {  
  
    private static ConceptosRecord[] records;  
  
    public static ConceptosRecord[] getRecords() {  
        if (records == null) {  
            records = getNewRecords();  
        }  
        return records;  
    }  
  
    public static ConceptosRecord[] getNewRecords() {  
        return new ConceptosRecord[]{  
                new ConceptosRecord("H","Ajuste Resolución ST N° 1279/10", null, "1458.35" , null),                
                new ConceptosRecord("R","Jubilacion 11%",  null, null, null),  
                new ConceptosRecord("R","Ley 19032 3%",  null, null, null),  
                new ConceptosRecord("R","Obra Social 3%", null, null, null),  
                new ConceptosRecord("R","Cuota Sindical 1.5%",  null, null, null),  
                new ConceptosRecord("R","Cuota Social 1%",  null, null, null),          
                new ConceptosRecord("H","Viáticos",  null, null, null),  
                new ConceptosRecord("H","Redondeo", null, null, null)
            
        };  
    }  
}  