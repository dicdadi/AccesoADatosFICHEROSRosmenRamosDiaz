package dad.javafx.main;

import java.io.File;


public class Pruebas {

	public static void main(String[] args) {
//		String prueba = "abcdf";
//		String espacios = "          ";
//		String concatena = prueba + espacios;
//		concatena = concatena.substring(0,10);
//System.out.println(concatena);
		
	File archivo= new File("C:\\Users\\Rosmen\\Downloads\\Pruebas\\ProbandoCreacion\\2");
	File archivo2= new File("C:\\Users\\Rosmen\\Downloads\\Pruebas\\3");
	File prueba = new File("asdasdasdsd");

		System.out.println(archivo2.getAbsoluteFile().getParent());
	
	//archivo.renameTo(archivo2);
	
	}

}
