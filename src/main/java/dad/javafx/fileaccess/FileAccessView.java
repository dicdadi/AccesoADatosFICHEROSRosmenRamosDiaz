package dad.javafx.fileaccess;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Pestaña de acceso a fichero
 * Vista
 * @author Alumno
 *
 */
public class FileAccessView extends BorderPane {

	private Label nombreLbl, rutaLbl;
	private TextField rutaTxt, nombreFichTxt,buscarTxt;
	private TextArea contentArea;
	private ListView<File> fileList;
	private Button createBt, removeBt, moveBt, viewBt, contentBt, modBt,buscarBt,ayudaBt;
	private RadioButton folderBt, fichBt;

	public FileAccessView() {
		
		// Declaración principal
		nombreLbl = new Label("Rosmen Ramos Díaz");
		nombreLbl.setPrefHeight(40);
		rutaLbl = new Label("Ruta actual:");
		
		rutaTxt = new TextField();
		nombreFichTxt = new TextField();
		nombreFichTxt.setPromptText("Carpeta o fichero a crear,  o destino a mover");
		
		buscarTxt = new TextField();
		buscarTxt.setPromptText("Introduce el fichero o carpeta que deseas buscar(sobre la ruta actual)");

		contentArea = new TextArea();
		contentArea.setPromptText("Contenido del fichero");
		contentArea.setEditable(true);

		fileList = new ListView<>(); // De momento no sabbemos cuantos crear, así que TO DO.....
		
		createBt = new Button("Crear");
		removeBt = new Button("Eliminar");
		moveBt = new Button("Mover");
		viewBt = new Button("Ver ficheros y carpetas");
		contentBt = new Button("Ver contenido archivo");
		modBt = new Button("Modificar archivo");
		buscarBt = new Button("Buscar");
		ayudaBt = new Button("?");
		ayudaBt.setStyle("-fx-background-radius: 4em; ");

		// Un truco para hacer que se expandan al máximo
		contentBt.setMaxWidth(Double.MAX_VALUE);
		modBt.setMaxWidth(Double.MAX_VALUE);
		viewBt.setMaxWidth(Double.MAX_VALUE);

		folderBt = new RadioButton("Es carpeta");
		fichBt = new RadioButton("Es fichero");
		ToggleGroup grp = new ToggleGroup();
		grp.getToggles().addAll(folderBt, fichBt);

		// Ajuste del Grid Layout
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 30, 5, 5));

		// Filas y columnas
		grid.addRow(0, rutaLbl, rutaTxt); // Ruta

		// Los botones en un Horizontal Box
		HBox btBox = new HBox(80, ayudaBt,createBt, removeBt, moveBt, folderBt, fichBt); // Botones principales de manejo de
		HBox buscarOpciones= new HBox(5,buscarBt,buscarTxt);		
		// ficheros
		HBox.setHgrow(buscarTxt, Priority.ALWAYS);
		btBox.setAlignment(Pos.BASELINE_CENTER);

		grid.addRow(1, btBox);
		GridPane.setColumnSpan(btBox, 2);

		grid.addRow(2, nombreFichTxt ); // El nombre Del fichero
		GridPane.setColumnSpan(nombreFichTxt, 2);

		grid.addRow(3, viewBt,buscarOpciones); // Ver contenido
		
		grid.addRow(4, fileList); // Lista de ficheros
		fileList.setPrefHeight(100);
		GridPane.setColumnSpan(fileList, 2);

		VBox contentBtBox = new VBox(10, contentBt, modBt); // Botones de modificación y ver fichero
		grid.add(contentBtBox, 0, 5);

		grid.add(contentArea, 1, 5); // Contenido del fichero

		// Ajustamos las filas y las columnas a nuestras necesidades
		ColumnConstraints[] cols = {

				new ColumnConstraints(), 
				new ColumnConstraints() 
		};

		cols[1].setHgrow(Priority.ALWAYS);
		cols[1].setFillWidth(true);
		grid.getColumnConstraints().addAll(cols);

		GridPane.setVgrow(fileList, Priority.ALWAYS);
		GridPane.setVgrow(contentArea, Priority.ALWAYS);

		// Ajuste de Border
		setTop(nombreLbl);
		setCenter(grid);
		setPadding(new Insets(5));

		setAlignment(nombreLbl, Pos.CENTER);
		setAlignment(grid, Pos.CENTER);
	}

	public Button getAyudaBt() {
		return ayudaBt;
	}

	public Label getNombreLbl() {
		return nombreLbl;
	}

	public TextField getBuscarTxt() {
		return buscarTxt;
	}

	public Button getBuscarBt() {
		return buscarBt;
	}

	public Label getRutaLbl() {
		return rutaLbl;
	}

	public TextField getRutaTxt() {
		return rutaTxt;
	}

	public TextField getNombreFichTxt() {
		return nombreFichTxt;
	}

	public TextArea getContentArea() {
		return contentArea;
	}

	public ListView<File> getFileList() {
		return fileList;
	}

	public Button getCreateBt() {
		return createBt;
	}

	public Button getRemoveBt() {
		return removeBt;
	}

	public Button getMoveBt() {
		return moveBt;
	}

	public Button getViewBt() {
		return viewBt;
	}

	public Button getContentBt() {
		return contentBt;
	}

	public Button getModBt() {
		return modBt;
	}

	public RadioButton getFolderBt() {
		return folderBt;
	}

	public RadioButton getFichBt() {
		return fichBt;
	}
}
