package dad.javafx.fileaccess;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FileAccessController {

	private FileAccessModel model = new FileAccessModel();
	private FileAccessView view = new FileAccessView();

	public FileAccessController() {

		model.rutaProperty().bindBidirectional(view.getRutaTxt().textProperty());

		model.fileProperty().bindBidirectional(view.getNombreFichTxt().textProperty());
		model.buscarProperty().bindBidirectional(view.getBuscarTxt().textProperty());

		// En este caso, puesto que son los botones los que nos indican el mostrar el
		// contenido y el modificarlo, es un bindeo hacia el modelo
		view.getContentArea().textProperty().bindBidirectional(model.contentProperty());

		// Los radio buttons, es fichero o es carpeta
		model.isFolderProperty().bind(view.getFolderBt().selectedProperty());
		model.isFileProperty().bind(view.getFichBt().selectedProperty());

		// Bindeos del FileList
		// Bindeamos el boton, para que si no hay items seleccionados en la lista, se
		// deshabilite

		view.getFileList().itemsProperty().bindBidirectional(model.fileListProperty());
		// Bindeamos los indice del dato seleccionado en la lista
		model.seleccionadoProperty().bind(view.getFileList().getSelectionModel().selectedIndexProperty());
		view.getCreateBt().disableProperty().bind(model.rutaProperty().isEmpty());
		view.getRemoveBt().disableProperty()
				.bind(view.getFileList().getSelectionModel().selectedItemProperty().isNull());

		view.getContentBt().disableProperty()
				.bind(view.getFileList().getSelectionModel().selectedItemProperty().isNull());

		view.getModBt().disableProperty().bind(view.getFileList().getSelectionModel().selectedItemProperty().isNull());

		view.getMoveBt().disableProperty().bind(view.getFileList().getSelectionModel().selectedItemProperty().isNull());

		// nombresListView.getSelectionModel().getSelectedItem()

		// view.getFileList().getSelectionModel().selectedItemProperty().addListener((o,
		// ov, nv) -> onCambioSeleccionChanged(nv));

		view.getViewBt().setOnAction(e -> onFolderViewAction(e));
		view.getCreateBt().setOnAction(e -> onCreateAction(e));
		view.getMoveBt().setOnAction(e -> onMoveAction(e));
		view.getRemoveBt().setOnAction(e -> onRemoveAction(e));
		view.getContentBt().setOnAction(e -> onContentViewAction(e));
		view.getModBt().setOnAction(e -> onModifyAction(e));
		view.getBuscarBt().setOnAction(e -> onBuscarAction(e));
		view.getAyudaBt().setOnAction(e -> onAyudaAction(e));

	}

//TODO
	private void onAyudaAction(ActionEvent e) {
		
	            File objetofile = new File (getClass().getResource("/Ayuda/ayuda.txt").getFile());
	            
	          try {
				Desktop.getDesktop().open(objetofile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//System.out.println(objetofile.getAbsolutePath());

	}

//Busca un archivo o fichero del listado
//Si se le da a buscar sin introducir ruta ni archivo a buscar, aparecerá el archivo raiz /
	private void onBuscarAction(ActionEvent e) {
		File archivoABuscar = new File(model.getRuta(), model.getBuscar());
		if (archivoABuscar.exists()) {
			model.getFileList().clear();
			model.getFileList().add(archivoABuscar);
		} else {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Buscar");
			alert.setHeaderText("Informacion buscar");
			alert.setContentText("No existe el archivo o directorio dentro de la ruta especificada");
			alert.showAndWait();
		}

	}

//-------------------------------------MOVER ARCHIVO O FICHERO-------------------------------------
	private void onMoveAction(ActionEvent e) {
		if (!model.getFile().equals("")) {
			File archivoNuevaRuta = new File(model.getFile());
			System.out.println(archivoNuevaRuta.getAbsolutePath());
			File archivoViejaRuta = new File(
					view.getFileList().getSelectionModel().getSelectedItem().getAbsolutePath());
			File comprobacionNuevaRuta = new File(archivoNuevaRuta.getAbsoluteFile().getParent());
		
			if (comprobacionNuevaRuta.exists()) {
				archivoViejaRuta.renameTo(archivoNuevaRuta);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Mover");
				alert.setHeaderText("Se ha movido/renombrado correctamente");
				alert.setContentText("Se mostrará la ruta donde ha movido/renombrado");
				alert.showAndWait();
				
				//Nos posicionamos en la raiz padre dodne hemos movido o renombrado el archivo
				model.rutaProperty().setValue(archivoNuevaRuta.getAbsoluteFile().getParent());
				model.fileProperty().setValue("");
				model.getFileList().clear();
				onFolderViewAction(null);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Mover");
				alert.setHeaderText("ERRO al mover");
				alert.setContentText("La ruta introducida no existe en el sistema");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Mover");
			alert.setHeaderText("ERRO al mover");
			alert.setContentText("Debe introducir la nueva ruta");
			alert.showAndWait();
		}

	}
//-------------------------------------MODIFICAR ARCHIVO-------------------------------------

	private void onModifyAction(ActionEvent e) {
		File nombreArchivo = new File(model.getNombreArchivoModicar());
		if (nombreArchivo.isFile()) {
			try {
				FileOutputStream ficheroSalida = new FileOutputStream(nombreArchivo.getAbsolutePath());
				OutputStreamWriter out = new OutputStreamWriter(ficheroSalida, "UTF8");
				BufferedWriter flujoOut = new BufferedWriter(out);

				flujoOut.write(view.getContentArea().getText());

				flujoOut.close();
				
			} catch (FileNotFoundException er) {

				er.printStackTrace();
			} catch (UnsupportedEncodingException er) {

				er.printStackTrace();
			} catch (IOException er) {

				er.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Modificar archivo");
			alert.setHeaderText("Error al modificar:");
			alert.setContentText("Debe seleccionar un archivo para su modificacion. ");
			alert.showAndWait();
		}
	}

//-------------------------------------VISUALIZAR CONTENIDO ARCHIVO-------------------------------------
	private void onContentViewAction(ActionEvent e) {

		model.contentProperty().setValue("");
		File nombreArchivo = new File(view.getFileList().getSelectionModel().getSelectedItem().getAbsolutePath());
		// Guardo la ruta del archivo que estoy visualizando
		// Esto lo hago para que luego en caso de darle al boton modificar y esté
		// seleccionado en ese momento
		// otro archivo que es diferente al que se está modificando, no coja la ruta de
		// ese selected
		model.setNombreArchivoModicar(view.getFileList().getSelectionModel().getSelectedItem().getAbsolutePath());
		if (nombreArchivo.isFile()) {
			try {

				FileInputStream fichero = new FileInputStream(nombreArchivo.getAbsolutePath());
				InputStreamReader in = new InputStreamReader(fichero, "UTF8");
				Scanner entrada = new Scanner(in);

				while (entrada.hasNext()) {

					model.contentProperty().setValue(model.contentProperty().get() + entrada.nextLine() + "\n");

				}
				entrada.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block

			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block

			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Visualizar archivo");
			alert.setHeaderText("Error al visualizar:");
			alert.setContentText("Debe seleccionar un archivo para su visualización. ");
			alert.showAndWait();
		}

	}

//-------------------------------------ELIMINAR ARCHIVO O DIRECTORIO-------------------------------------
	private void onRemoveAction(ActionEvent e) {
		File ficheroBorrar = new File(view.getFileList().getSelectionModel().getSelectedItem().getAbsolutePath());

		deleteDirectory(ficheroBorrar);
		try {
			if (model.fileListProperty().remove(ficheroBorrar)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Eliminacion");
				alert.setHeaderText("Eliminación archivo/directorio");
				alert.setContentText("Se eliminado correctamente el archivo/directorio");
				alert.showAndWait();
			}

		} catch (Exception e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Eliminar");
			alert.setHeaderText("Error al eliminar");
			alert.setContentText("No se ha podido eliminar el archivo/directorio");
			alert.showAndWait();
		}

	}

	private boolean deleteDirectory(File ficheroBorrar) {

		if (ficheroBorrar.isFile()) {
			ficheroBorrar.delete();
		} else {
			for (File ficheros : ficheroBorrar.listFiles()) {
				if (ficheros.isDirectory()) {
					deleteDirectory(ficheros);
				} else {
					ficheros.delete();
				}
			}
		}
		return ficheroBorrar.delete();

	}

//-------------------------------------CREAR DIRECTORIO O ARCHIVO-------------------------------------
	private void onCreateAction(ActionEvent e) {
		File ficheroCarpeta = new File(model.getRuta(), model.getFile());
		if (model.isIsFile()) {
			try {
				if (!ficheroCarpeta.exists()) {
					ficheroCarpeta.createNewFile();

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Crear");
					alert.setHeaderText("Creación satisfactoria");
					alert.setContentText("Se ha creado correctamente el nuevo archivo");
					alert.showAndWait();
					// si se ha creado, se añade a la lista, es una manera de "actualizarlo"
					model.fileListProperty().add(ficheroCarpeta);

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Crear");
					alert.setHeaderText("ERROR al crear");
					alert.setContentText(
							"El archivo con el nombre indicado ya existe o no se ha introducido un nombre válido");
					alert.showAndWait();
				}
			} catch (Exception e2) {
			}

		} else if (model.isIsFolder()) {
			try {
				if (!ficheroCarpeta.exists()) {
					ficheroCarpeta.mkdir();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Crear");
					alert.setHeaderText("Creación satisfactoria");
					alert.setContentText("Se ha creado correctamente el nuevo directorio");
					alert.showAndWait();
					model.fileListProperty().add(ficheroCarpeta);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Crear");
					alert.setHeaderText("ERROR al crear");
					alert.setContentText(
							"El directorio con el nombre indicado ya existe o no se ha introducido un nombre válido");
					alert.showAndWait();
				}
			} catch (Exception e2) {
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Crear");
			alert.setHeaderText("ERROR al crear");
			alert.setContentText("Debe especificar si quiere crear un archivo o un directorio");
			alert.showAndWait();

		}

	}

//-------------------------------------VISUALIZAR LISTADO DE FICHERO -------------------------------------
	private void onFolderViewAction(ActionEvent e) {
		File dir = null;
		// Si hay un objeto seleccionado de la lista, entonces se hará el listado sobre
		// ese

		if (!view.getFileList().getSelectionModel().isEmpty()) {
			dir = new File(view.getFileList().getSelectionModel().getSelectedItem().getAbsolutePath());
			model.rutaProperty().setValue(dir.getAbsolutePath());
			model.fileProperty().setValue("");
		} else {
			// Si no, se hará un listado de la ruta actual
			dir = new File(model.getRuta());
		}
		// Limpiar la lista
		model.getFileList().clear();
		try {
			File[] filelist = dir.listFiles();
			model.fileListProperty().addAll(filelist);

		} catch (NullPointerException e1) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Ver ficheros");
			alert.setHeaderText("Fichero no encontrado");
			alert.setContentText("No hay una ruta seleccionada");
			alert.showAndWait();
			
		}

	}

	public FileAccessView getRootView() {
		return view;
	}
}
