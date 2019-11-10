package dad.javafx.randomAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class RandomAccessControllerNEW implements Initializable {

	@FXML
	private BorderPane view;
	@FXML
	private GridPane GridRoot;
	@FXML
	private Label rutaLabel, BuscarId;
	@FXML
	private TextField rutaText, idText;
	@FXML
	private ListView<File> ListadoFicheros;
	@FXML
	private VBox VboxBotones, VboxConsultas;
	@FXML
	private Button VerFicherosBoton, VisualizarBoton, InsertarDatosBoton, ActualizarDatosBoton, BuscarIdBoton;

	@FXML
	private HBox HboxRadioBoton, HboxID;
	@FXML
	private TableView<Residencias> TablaDatos;
	@FXML
	private TableColumn<Residencias, Integer> idColumna;

	@FXML
	private TableColumn<Residencias, String> NombreResidenciaColumna;
	@FXML
	private TableColumn<Residencias, String> CodigoUniversidadColumna;
	@FXML
	private TableColumn<Residencias, Integer> PrecioMensualColumna;
	@FXML
	private TableColumn<Residencias, Boolean> ComedorColumna;

	@FXML
	private void onVerFicherosAction(ActionEvent e) {

		File dir = null;
		// Si hay un objeto seleccionado de la lista, entonces se hará el listado sobre
		// ese

		if (!ListadoFicheros.getSelectionModel().isEmpty()) {
			dir = new File(ListadoFicheros.getSelectionModel().getSelectedItem().getAbsolutePath());
			model.rutaProperty().setValue(dir.getAbsolutePath());

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
			alert.setTitle("Error ruta");
			alert.setHeaderText("ERROR al visualizar");
			alert.setContentText("No hay una ruta seleccionada");
			alert.showAndWait();
		}

	}
//-------------------------------------------Visualizar contenido Fichero----------------------------------
	@FXML
	private void onVisualizarOnAction(ActionEvent e) throws IOException {
		// Limpiamos la tabla
		TablaDatos.getItems().clear();
		// Cogemos como fichero la ruta selecciona en el ListView
		File f = new File(ListadoFicheros.getSelectionModel().getSelectedItem().getAbsolutePath());
		RandomAccessFile fichero = null;
		//comprobamos que sea un archivo
		if (f.isFile()) {
			try {
				fichero = new RandomAccessFile(f, "r");
				fichero.seek(0);
				//leemos por bloques de 51 bytes
				while (true) {
					int id, precioMensual;
					String nResidencia = "", codigoUniversidad = "";
					boolean comedor;
					id = fichero.readInt();// Leemos el id
					fichero.readChar();// leemos la coma
					for (int i = 0; i < 10; i++) {// leemos el nombre de residencia
						nResidencia = nResidencia + fichero.readChar();
					}
					fichero.readChar();
					for (int i = 0; i < 6; i++) {// leemos el nombre de residencia
						codigoUniversidad = codigoUniversidad + fichero.readChar();
					}
					fichero.readChar();//leemos la coma
					precioMensual = fichero.readInt();//leemos el precio
					fichero.readChar();//leemos la coma
					comedor = fichero.readBoolean();//leemos el comedor
					fichero.readChar();//leemos la coma
					Residencias.add(new Residencias(id, nResidencia, codigoUniversidad, precioMensual, comedor));
					codigoUniversidad = "";
					nResidencia = "";
				}

			} catch (FileNotFoundException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("ERROR al visualizar");
				alert.setContentText("No se ha encontrado el archivo indicado");
				alert.showAndWait();
			} catch (IOException e1) {

			}
			fichero.close();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("ERROR al visualizar");
			alert.setContentText("La ruta seleccionado no es de un archivo válido para la lectura");
			alert.showAndWait();
		}

	};

	@FXML
	private void onInsertarDatosAction(ActionEvent e) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/FormAccessViewNEW.fxml"));
		loader.setController(this);
		loader.load();
		Stage stage = new Stage();
		stage.setTitle("Insertar Datos");
		stage.setScene(new Scene(FormularioView));
		stage.show();

	};

	@FXML
	private void onActualizarDatosAction(ActionEvent e) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ActualizarDatosView.fxml"));
		loader.setController(this);
		loader.load();
		Stage stage = new Stage();
		stage.setTitle("Actualizar Datos");
		stage.setScene(new Scene(ActualizarDatosView));
		stage.show();

	}

//--------------------------Busqueda de registro por ID--------------------------
	@FXML
	private void onBuscarPorIdAction(ActionEvent e) throws IOException {
		TablaDatos.getItems().clear();// Limpiamos la tabla pues solo queremos mostrar un elemento
		int posicion = Integer.parseInt(idText.getText());
		File f = new File(ListadoFicheros.getSelectionModel().getSelectedItem().getAbsolutePath());
		RandomAccessFile fichero = null;
		try {
			fichero = new RandomAccessFile(f, "r");
			// Posicionamos el puntero en el fichero justo 1 byte detras del registro que
			// queremos leer
			// En este caso sería la coma que agregamos al final de cada registro
			// De esta manera, lo siguiente que leeremos sería el ID del siguiente registro
			fichero.seek((posicion - 1) * 51);

			int id, precioMensual;
			String nResidencia = "", codigoUniversidad = "";
			boolean comedor;
			id = fichero.readInt();// Leemos el id
			fichero.readChar();// leemos la coma
			for (int i = 0; i < 10; i++) {// leemos el nombre de residencia
				nResidencia = nResidencia + fichero.readChar();
			}
			fichero.readChar();
			for (int i = 0; i < 6; i++) {// leemos el nombre de residencia
				codigoUniversidad = codigoUniversidad + fichero.readChar();
			}
			fichero.readChar();
			precioMensual = fichero.readInt();
			fichero.readChar();
			comedor = fichero.readBoolean();
			fichero.readChar();
			Residencias.add(new Residencias(id, nResidencia, codigoUniversidad, precioMensual, comedor));
			TablaDatos.refresh();
			
			codigoUniversidad = "";
			nResidencia = "";

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Buscar por id");
			alert.setHeaderText("Registro no encontrado");
			alert.setContentText("El id del registro indicado no se encuentra en el archivo");
			alert.showAndWait();
		}
		fichero.close();

	}
//--------------------------Formulario INSERTAR--------------------------

	@FXML
	private BorderPane FormularioView;

	@FXML
	private GridPane FormularioGP;

	@FXML
	private Label NombreResidenciaFormulario;

	@FXML
	private Label CodigoUniversidadFormulario;

	@FXML
	private Label PrecioMensualFormulario;

	@FXML
	private TextField nFormText;

	@FXML
	private TextField codFormText;

	@FXML
	private TextField precioFormText;

	@FXML
	private Button EnviarFormText;

	@FXML
	private CheckBox comedorCheckBox;

	@FXML
	void onEnviarFormAction(ActionEvent event) {
		insertarEnFichero(nFormText.getText(), codFormText.getText(), precioFormText.getText(),
				comedorCheckBox.isSelected());

		// capturo el stage de donde proviene y lo cierro
		Stage stage = (Stage) EnviarFormText.getScene().getWindow();
		stage.close();
	}

	private void insertarEnFichero(String nombreResidencia, String codForm, String precioForm, Boolean comedorForm) {
		int idResidencia;
		String nResidencia = (nombreResidencia + "          ").substring(0, 10);
		String codigoUniversidad = (codForm + "      ").substring(0, 6);
		int precioMensual = Integer.parseInt(precioForm);
		boolean comedorResidencia = comedorForm;
		try {
//			
			File f = new File(ListadoFicheros.getSelectionModel().getSelectedItem().getAbsolutePath());
			RandomAccessFile fichero = new RandomAccessFile(f, "rw");
			// El tamaño de cada registro como mínimo es 51, por lo que si el archivo no
			// llega ni a 51 bytes, significa que no hay ningún regitro
			if ((int) fichero.length() < 51) {
				idResidencia = 1;
			} else {
				idResidencia = ((int) fichero.length() / 51) + 1;// A traves del tamaño del archivo, calculo cuantos
																	// registro totales hay, al sumarle 1 serie el
																	// registro qu eestamos insertando ahora mismo
			}
			fichero.seek(fichero.length());
			fichero.writeInt(idResidencia);
			fichero.writeChar(',');
			fichero.writeChars(nResidencia);
			fichero.writeChar(',');
			fichero.writeChars(codigoUniversidad);
			fichero.writeChar(',');
			fichero.writeInt(precioMensual);
			fichero.writeChar(',');
			fichero.writeBoolean(comedorResidencia);
			fichero.writeChar(',');
			fichero.seek(0);
			fichero.close();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Insertar");
			alert.setHeaderText("Insertar nuevo registro");
			alert.setContentText("Se ha introducido correctamente el nuevo registro");
			alert.showAndWait();
			Residencias.add(
					new Residencias(idResidencia, nResidencia, codigoUniversidad, precioMensual, comedorResidencia));
			TablaDatos.refresh();
			onVisualizarOnAction(null);

		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		} catch (IOException ex) {
			// System.out.println(ex.getMessage());
		}
	}
	// --------------------------Fin formulario--------------------------

	// -------------------------ActualizaDatos---------------------------
	@FXML
	private BorderPane ActualizarDatosView;

	@FXML
	private TextField ActualizarIdText;

	@FXML
	private TextField ActualizarPrecioText;

	@FXML
	private Button ActualizarBoton;

	@FXML
	void onActualizarPrecioAction(ActionEvent event) {
		actualizarEnFichero(ActualizarIdText.getText(), ActualizarPrecioText.getText());
		Stage stage = (Stage) ActualizarBoton.getScene().getWindow();
		stage.close();
	}

	private void actualizarEnFichero(String id, String precioViejo) {
		try {
			int precioNuevo = Integer.parseInt(precioViejo);
			int posicion = Integer.parseInt(id);
			File f = new File(ListadoFicheros.getSelectionModel().getSelectedItem().getAbsolutePath());
			RandomAccessFile fichero = new RandomAccessFile(f, "rw");

			fichero.seek(((posicion - 1) * 51) + 42);// el precio mensual de cada registro está a 42 bytes, es decir los
														// siguientes 4 bytes sería el precio mensual
			fichero.writeInt(precioNuevo);//actualizamos el dato
			fichero.seek(0);
			fichero.close();
			TablaDatos.refresh();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Actualizar");
			alert.setHeaderText("Dato actualizado");
			alert.setContentText("Se ha actualizado el dato correctamente");
			alert.showAndWait();
			onVisualizarOnAction(null);
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("ERROR al actualizar");
			alert.setContentText("Comprueba que los campos ID y PrecioMensual sean datos de tipo entero");
			alert.showAndWait();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

	// --------------------------
//---------------------------
	private RandomAccessModel model = new RandomAccessModel();

	public RandomAccessControllerNEW() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/RandomAccessView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model.rutaProperty().bindBidirectional(rutaText.textProperty());
		model.idProperty().bindBidirectional(idText.textProperty());
		ListadoFicheros.itemsProperty().bindBidirectional(model.fileListProperty());
		InsertarDatosBoton.disableProperty().bind(ListadoFicheros.getSelectionModel().selectedItemProperty().isNull());
		ActualizarDatosBoton.disableProperty()
				.bind(ListadoFicheros.getSelectionModel().selectedItemProperty().isNull());
		BuscarIdBoton.disableProperty().bind(ListadoFicheros.getSelectionModel().selectedItemProperty().isNull());
		idColumna.setCellValueFactory(new PropertyValueFactory<>("Id"));
		NombreResidenciaColumna.setCellValueFactory(new PropertyValueFactory<>("NombreResidencia"));
		CodigoUniversidadColumna.setCellValueFactory(new PropertyValueFactory<>("CodigoUniversidad"));
		PrecioMensualColumna.setCellValueFactory(new PropertyValueFactory<>("PrecioMensual"));
		ComedorColumna.setCellValueFactory(new PropertyValueFactory<>("Comedor"));

		PrecioMensualColumna.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		// add your data to the table here.
		TablaDatos.setEditable(true);
		TablaDatos.setItems(Residencias);

		// Residencias.add(new Residencias(1,"residenci1","u00001",900,true)); ejemplo
		// prueba
	}

	private ObservableList<Residencias> Residencias = FXCollections.observableArrayList(
	// new Residencias(1,"residenci1", 0001,900,true) ejemplo prueba
	);

	public BorderPane getView() {
		return view;
	}

}
