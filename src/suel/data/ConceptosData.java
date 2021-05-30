package suel.data;  
  
public class ConceptosData {  
  
    private static ConceptosRecord[] records;  
  
    public static ConceptosRecord[] getRecords() {  
        if (records == null) {  
            records = getNewRecords();  
        }  
        return records;  
    }  
  
    public static ConceptosRecord[] getNewRecords() {  
        return new ConceptosRecord[]{  
                new ConceptosRecord("H","Básico", "30", "5139.05" , null),
                new ConceptosRecord("H","Días de Enfermedad", null, null , null),
                new ConceptosRecord("H","Boletera", null , "51",  null ),  
                new ConceptosRecord("H","Presentismo Proporcional", null , "836.23",null), 
                new ConceptosRecord("H","Antiguedad", "12", null,null),                                 
                new ConceptosRecord("H","Horas Normales", null , null,null),
                new ConceptosRecord("H","Horas Extras 50%", null, null , null),  
                new ConceptosRecord("H","Horas Extras 100%", null, null , null),  
                new ConceptosRecord("H","Bonos Nocturnos", null, null, null),  
                new ConceptosRecord("H","Vacaciones", null, null, null),  
                new ConceptosRecord("H","Descuento Vacaciones", null, null, null),  
                new ConceptosRecord("R","Jubilacion 11%",  "11", null, null),  
                new ConceptosRecord("R","Ley 19032 3%",  "3", null, null),  
                new ConceptosRecord("R","Obra Social 3%", "3", null, null),  
                new ConceptosRecord("R","Cuota Sindical 1.5%",  "1.5", null, null),  
                new ConceptosRecord("R","Cuota Social 1%",  "1", null, null),  
                new ConceptosRecord("H","Anticipo Vacaciones",  null, null, null),
                new ConceptosRecord("R","Embargo Judicial",  null, null, null),
                new ConceptosRecord("R","Cuota Alimentaria",  null, null, null),
                new ConceptosRecord("R","Dto Anticipo Vacaciones", null,null, null),  
                new ConceptosRecord("H","Viáticos",  null, null, null),                  
                new ConceptosRecord("H","Redondeo", null, null, null)
        		/*
        		new ConceptosRecord("H","Básico", "29", "2665.72" , null),
                new ConceptosRecord("H","Días de Enfermedad", null, "91.92" , null),
                new ConceptosRecord("H","Boletera", null , "51",  null ),  
                new ConceptosRecord("H","Presentismo Proporcional", null , "836.23",null), 
                new ConceptosRecord("H","Antiguedad", "8", null,null),                                 
                new ConceptosRecord("H","Horas Normales", "16" , null,null),
                new ConceptosRecord("H","Horas Extras 50%", "28.47", null , null),  
                new ConceptosRecord("H","Horas Extras 100%", "29.34", null , null),  
                new ConceptosRecord("H","Bonos Nocturnos", null, "286.01", null),  
                new ConceptosRecord("H","Vacaciones", null, "326.58", null),  
                new ConceptosRecord("H","Descuento Vacaciones", null, "-272.15", null),  
                new ConceptosRecord("R","Jubilacion 11%",  "11", null, null),  
                new ConceptosRecord("R","Ley 19032 3%",  "3", null, null),  
                new ConceptosRecord("R","Obra Social 3%", "3", null, null),  
                new ConceptosRecord("R","Cuota Sindical 1.5%",  "1.5", null, null),  
                new ConceptosRecord("R","Cuota Social 1%",  "1", null, null),  
                new ConceptosRecord("H","Anticipo Vacaciones",  null, null, null),
                new ConceptosRecord("R","Embargo Judicial",  null, null, null),
                new ConceptosRecord("R","Cuota Alimentaria",  null, null, "1949.28"),
                new ConceptosRecord("R","Dto Anticipo Vacaciones", null,null, "1400"),  
                new ConceptosRecord("H","Viáticos",  null, "230", null),                  
                new ConceptosRecord("H","Redondeo", null, null, null)*/
            
        };  
    }  
}  