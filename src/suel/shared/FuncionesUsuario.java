package suel.shared;

public class FuncionesUsuario {

  public static boolean controlarInicioSesion(String usuario, String password){
    boolean valorDevolucion;
    if (usuario == null || password == null) valorDevolucion = false;
    else{
      int comparaUsuario = usuario.compareToIgnoreCase("Gregorio");
      int comparaPassw = 0;
      
      if (comparaUsuario == 0){
    	  comparaPassw = password.compareTo("grego2013");    	  
      }
      else{ 
    	  comparaUsuario = usuario.compareToIgnoreCase("Nestor");
    	  comparaPassw = password.compareTo("nes2013");    	  
      }
      
      int resultadoCompara = comparaUsuario + comparaPassw;
      valorDevolucion = (resultadoCompara == 0);
    }
    return valorDevolucion;
  }
}
