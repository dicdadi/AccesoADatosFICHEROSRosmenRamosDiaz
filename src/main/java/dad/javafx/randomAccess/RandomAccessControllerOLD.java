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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class RandomAccessControllerOLD implements Initializable {

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

//????????????????????????????????????  Implementar alert 
			System.out.println("Implementar un alert de que no hay ruta seleccionada");
		}

	}

	@FXML
	private void onVisualizarOnAction(ActionEvent e) throws IOException {
		// Limpiamos la tabla
		TablaDatos.getItems().clear();
		// Cogemos como fichero la ruta selecciona en el ListView
		File f = new File(ListadoFicheros.getSelectionModel().getSelectedItem().getAbsolutePath());
		RandomAccessFile fichero = null;
		try {
			fichero = new RandomAccessFile(f, "r");
			fichero.seek(0);
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
				fichero.readChar();
				precioMensual = fichero.readInt();
				fichero.readChar();
				comedor = fichero.readBoolean();
				fichero.readChar();
				Residencias.add(new Residencias(id, nResidencia, codigoUniversidad, precioMensual, comedor));
				codigoUniversidad = "";
				nResidencia = "";
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}
		fichero.close();

	};

	@FXML
	private void onInsertarDatosAction(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/FormAccessView.fxml"));
		loader.setController(this);
		loader.load();
		Stage stage = new Stage();
		stage.setTitle("Insertar Datos");
		stage.setScene(new Scene(FormularioView));
		stage.show();

	};

	@FXML
	private void onActualizarDatosAction(ActionEvent e) {

	}

	@FXML
	private void onBuscarPorIdAction(ActionEvent e) throws IOException {
		TablaDatos.getItems().clear();
		int posicion = Integer.parseInt(idText.getText());
		File f = new File(ListadoFicheros.getSelectionModel().getSelectedItem().getAbsolutePath());
		RandomAccessFile fichero = null;
		try {
			fichero = new RandomAccessFile(f, "r");
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
			// e1.printStackTrace();
		}
		fichero.close();

	}
//--------------------------Formulario

	@FXML
	private BorderPane FormularioView;

	@FXML
	private GridPane FormularioGP;

	@FXML
	private Label idFormulario;

	@FXML
	private Label NombreResidenciaFormulario;

	@FXML
	private Label CodigoUniversidadFormulario;

	@FXML
	private Label PrecioMensualFormulario;

	@FXML
	private Label Comedor;

	@FXML
	private TextField idFormText;

	@FXML
	private TextField nFormText;

	@FXML
	private TextField codFormText;

	@FXML
	private TextField precioFormText;

	@FXML
	private TextField comedorFormText;

	@FXML
	private Button EnviarFormText;

	@FXML
	void onEnviarFormAction(ActionEvent event) {
		insertarEnFichero(
				idFormText.getText(),
				nFormText.getText(), 
				codFormText.getText(), 
				precioFormText.getText(),
				comedorFormText.getText()
				);

		// capturo el stage de donde proviene y lo cierro
		Stage stage = (Stage) EnviarFormText.getScene().getWindow();
		stage.close();
	}

	private void insertarEnFichero (String id, String nombreResidencia,String codForm,String precioForm,String comedorForm) {
    	int idResidencia = Integer.parseInt(id);
		String nResidencia=(nombreResidencia + "          ").substring(0, 10);
		String codigoUniversidad=(codForm + "      ").substring(0, 6);
		int precioMensual= Integer.parseInt(precioForm);
		boolean comedorResidencia= Boolean.valueOf(comedorForm);
		try {
//			
			File f = new File (ListadoFicheros.getSelectionModel().getSelectedItem().getAbsolutePath());
			RandomAccessFile fichero = new RandomAccessFile(f, "rw");
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
			Residencias.add(new Residencias(idResidencia,nResidencia,codigoUniversidad,precioMensual,comedorResidencia));
			TablaDatos.refresh();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
    }

//---------------------------
	private RandomAccessModel model = new RandomAccessModel();

	public RandomAccessControllerOLD() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/RandomAccessView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model.rutaProperty().bindBidirectional(rutaText.textProperty());
		model.idProperty().bindBidirectional(idText.textProperty());
		ListadoFicheros.itemsProperty().bindBidirectional(model.fileListProperty());

		idColumna.setCellValueFactory(new PropertyValueFactory<>("Id"));
		NombreResidenciaColumna.setCellValueFactory(new PropertyValueFactory<>("NombreResidencia"));
		CodigoUniversidadColumna.setCellValueFactory(new PropertyValueFactory<>("CodigoUniversidad"));
		PrecioMensualColumna.setCellValueFactory(new PropertyValueFactory<>("PrecioMensual"));
		ComedorColumna.setCellValueFactory(new PropertyValueFactory<>("Comedor"));

		PrecioMensualColumna.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		// add your data to the table here.
		TablaDatos.setEditable(true);
		TablaDatos.setItems(Residencias);

		// Residencias.add(new Residencias(1,"residenci1","u00001",900,true));
	}

	private ObservableList<Residencias> Residencias = FXCollections.observableArrayList(
	// new Residencias(1,"residenci1", 0001,900,true)
	);

	public BorderPane getView() {
		return view;
	}

}
