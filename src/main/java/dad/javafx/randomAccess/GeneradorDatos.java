package dad.javafx.randomAccess;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.SeekableByteChannel;

public class GeneradorDatos {
	private Residencias residencias;
	
	public static void main(String[] args) throws IOException {
		FileInputStream archivo;
	
		String nResidencia="";
		String codigoUniversidad="";
		try {
//			archivo = new FileOutputStream("personas_var.dat");
//			DataOutputStream datos= new DataOutputStream(archivo);
//			int id,codigoUniversidad,precioMensual;
//			boolean comedor;
//			String nombreResidencia;
			File f = new File ("usuarios.dat");
			RandomAccessFile fichero = new RandomAccessFile(f, "rw");
			
			
			fichero.writeInt(1);
			fichero.writeChar(',');
			fichero.writeChars("residenci1");
			fichero.writeChar(',');
			fichero.writeChars("u00001");
			fichero.writeChar(',');
			fichero.writeInt(900);
			fichero.writeChar(',');
			fichero.writeBoolean(true);
			fichero.writeChar(',');
			
			fichero.writeInt(2);
			fichero.writeChar(',');
			fichero.writeChars("residenci2");
			fichero.writeChar(',');
			fichero.writeChars("u00002");
			fichero.writeChar(',');
			fichero.writeInt(900);
			fichero.writeChar(',');
			fichero.writeBoolean(false);
			fichero.writeChar(',');
			
			fichero.writeInt(3);
			fichero.writeChar(',');
			fichero.writeChars("residenci3");
			fichero.writeChar(',');
			fichero.writeChars("u00003");
			fichero.writeChar(',');
			fichero.writeInt(999);
			fichero.writeChar(',');
			fichero.writeBoolean(false);
			fichero.writeChar(',');
	
			fichero.seek(0);
			while(true) {
			System.out.println(fichero.readInt());
			System.out.println(fichero.readChar());
			for(int i = 0; i < 10 ; i++) {
				nResidencia=nResidencia+fichero.readChar();
			}
			System.out.println(nResidencia);
			nResidencia="";
			System.out.println(fichero.readChar());
			
			for(int i = 0; i < 6 ; i++) {
				 codigoUniversidad=codigoUniversidad+fichero.readChar();
			}
			System.out.println(codigoUniversidad);
			codigoUniversidad="";
			System.out.println(fichero.readChar());
			System.out.println(fichero.readInt());
			System.out.println(fichero.readChar());
			System.out.println(fichero.readBoolean());
			System.out.println(fichero.readChar());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
System.out.println("--------------------------------------------");
		
		try {
//			a
			File f = new File ("usuarios.dat");
			RandomAccessFile fichero = new RandomAccessFile(f, "rw");
//			fichero.seek(51);
//			fichero.writeInt(55);
//			fichero.seek(0);
			while(true) {
			System.out.println(fichero.readInt());
			System.out.println(fichero.readChar());
			for(int i = 0; i < 10 ; i++) {
				nResidencia=nResidencia+fichero.readChar();
			}
			System.out.println(nResidencia);
			nResidencia="";
			System.out.println(fichero.readChar());
			
			for(int i = 0; i < 6 ; i++) {
				 codigoUniversidad=codigoUniversidad+fichero.readChar();
			}
			System.out.println(codigoUniversidad);
			codigoUniversidad="";
			System.out.println(fichero.readChar());
			System.out.println(fichero.readInt());
			System.out.println(fichero.readChar());
			System.out.println(fichero.readBoolean());
			System.out.println(fichero.readChar());
			System.out.println("Tamaño fichero"+fichero.length());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException ex) {
            //System.out.println(ex.getMessage());
			
        }
		File f = new File ("usuarios.dat");
		RandomAccessFile fichero = new RandomAccessFile(f, "rw");
		fichero.seek(144);
		System.out.println(fichero.readInt());
		fichero.seek(0);
	}

}
