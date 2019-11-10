package dad.javafx.randomAccess;

import java.io.File;
import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RandomAccessModel {
	private StringProperty ruta = new SimpleStringProperty();
	private StringProperty id = new SimpleStringProperty();
	private ListProperty<File> fileList = new SimpleListProperty<File>(FXCollections.observableArrayList(new ArrayList<File>()));
	private BooleanProperty soloLectura= new SimpleBooleanProperty();
	private BooleanProperty lecturaYEscritura = new SimpleBooleanProperty();
	private String nombreArchivoModicar = "";
	public final StringProperty rutaProperty() {
		return this.ruta;
	}
	
	public String getNombreArchivoModicar() {
		return nombreArchivoModicar;
	}

	public void setNombreArchivoModicar(String nombreArchivoModicar) {
		this.nombreArchivoModicar = nombreArchivoModicar;
	}

	public final String getRuta() {
		return this.rutaProperty().get();
	}
	
	public final void setRuta(final String ruta) {
		this.rutaProperty().set(ruta);
	}
	
	public final StringProperty idProperty() {
		return this.id;
	}
	
	public final String getId() {
		return this.idProperty().get();
	}
	
	public final void setId(final String id) {
		this.idProperty().set(id);
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
	
	public final BooleanProperty soloLecturaProperty() {
		return this.soloLectura;
	}
	
	public final boolean isSoloLectura() {
		return this.soloLecturaProperty().get();
	}
	
	public final void setSoloLectura(final boolean soloLectura) {
		this.soloLecturaProperty().set(soloLectura);
	}
	
	public final BooleanProperty lecturaYEscrituraProperty() {
		return this.lecturaYEscritura;
	}
	
	public final boolean isLecturaYEscritura() {
		return this.lecturaYEscrituraProperty().get();
	}
	
	public final void setLecturaYEscritura(final boolean lecturaYEscritura) {
		this.lecturaYEscrituraProperty().set(lecturaYEscritura);
	}
	
	
	
}
