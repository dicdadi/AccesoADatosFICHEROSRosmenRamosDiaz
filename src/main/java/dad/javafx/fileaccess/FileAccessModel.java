package dad.javafx.fileaccess;

import java.io.File;
import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class FileAccessModel {

	private StringProperty ruta = new SimpleStringProperty();
	private StringProperty file = new SimpleStringProperty();
	private StringProperty content = new SimpleStringProperty();
	private StringProperty buscar = new SimpleStringProperty();
	private ListProperty<File> fileList = new SimpleListProperty<File>(FXCollections.observableArrayList(new ArrayList<File>()));
	private IntegerProperty seleccionado = new SimpleIntegerProperty();
	private BooleanProperty isFolder= new SimpleBooleanProperty();
	private BooleanProperty isFile = new SimpleBooleanProperty();
	private String nombreArchivoModicar = "";
	
	

	public String getNombreArchivoModicar() {
		return nombreArchivoModicar;
	}

	public void setNombreArchivoModicar(String nombreArchivoModicar) {
		this.nombreArchivoModicar = nombreArchivoModicar;
	}

	public final StringProperty rutaProperty() {
		return this.ruta;
	}
	
	public final String getRuta() {
		return this.rutaProperty().get();
	}
	
	public final void setRuta(final String ruta) {
		this.rutaProperty().set(ruta);
	}
	
	public final StringProperty fileProperty() {
		return this.file;
	}
	
	public final String getFile() {
		return this.fileProperty().get();
	}
	
	public final void setFile(final String file) {
		this.fileProperty().set(file);
	}
	
	public final StringProperty contentProperty() {
		return this.content;
	}
	
	public final String getContent() {
		return this.contentProperty().get();
	}
	
	public final void setContent(final String content) {
		this.contentProperty().set(content);
	}
	
	public final ListProperty<File> fileListProperty() {
		return this.fileList;
	}
	
	public final ObservableList<File> getFileList() {
		return this.fileListProperty().get();
	}
	
	public final void setFileList(final ObservableList<File> fileList) {
		this.fileListProperty().set(fileList);
	}

	public final BooleanProperty isFolderProperty() {
		return this.isFolder;
	}
	

	public final boolean isIsFolder() {
		return this.isFolderProperty().get();
	}
	

	public final void setIsFolder(final boolean isFolder) {
		this.isFolderProperty().set(isFolder);
	}
	

	public final BooleanProperty isFileProperty() {
		return this.isFile;
	}
	

	public final boolean isIsFile() {
		return this.isFileProperty().get();
	}
	

	public final void setIsFile(final boolean isFile) {
		this.isFileProperty().set(isFile);
	}

	public final IntegerProperty seleccionadoProperty() {
		return this.seleccionado;
	}
	

	public final int getSeleccionado() {
		return this.seleccionadoProperty().get();
	}
	

	public final void setSeleccionado(final int seleccionado) {
		this.seleccionadoProperty().set(seleccionado);
	}

	public final StringProperty buscarProperty() {
		return this.buscar;
	}
	

	public final String getBuscar() {
		return this.buscarProperty().get();
	}
	

	public final void setBuscar(final String buscar) {
		this.buscarProperty().set(buscar);
	}
	
	
		
	
	
}
