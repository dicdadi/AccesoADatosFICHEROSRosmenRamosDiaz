package dad.javafx.randomAccess;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Residencias {
	private SimpleIntegerProperty id,precioMensual;
	private SimpleStringProperty nombreResidencia,codigoUniversidad;
	private SimpleBooleanProperty comedor;
	
public Residencias(int id, String nombreResidencia, String codigoUniversidad, int precioMensual,boolean comedor) {
this.id= new SimpleIntegerProperty(id) ;
this.nombreResidencia=new SimpleStringProperty(nombreResidencia);
this.codigoUniversidad=new  SimpleStringProperty(codigoUniversidad);
this.precioMensual=new SimpleIntegerProperty(precioMensual);
this.comedor=new SimpleBooleanProperty(comedor);
}

public final SimpleIntegerProperty idProperty() {
	return this.id;
}


public final int getId() {
	return this.idProperty().get();
}


public final void setId(final int id) {
	this.idProperty().set(id);
}


public final SimpleIntegerProperty precioMensualProperty() {
	return this.precioMensual;
}


public final int getPrecioMensual() {
	return this.precioMensualProperty().get();
}


public final void setPrecioMensual(final int precioMensual) {
	this.precioMensualProperty().set(precioMensual);
}


public final SimpleStringProperty nombreResidenciaProperty() {
	return this.nombreResidencia;
}


public final String getNombreResidencia() {
	return this.nombreResidenciaProperty().get();
}


public final void setNombreResidencia(final String nombreResidencia) {
	this.nombreResidenciaProperty().set(nombreResidencia);
}


public final SimpleStringProperty codigoUniversidadProperty() {
	return this.codigoUniversidad;
}


public final String getCodigoUniversidad() {
	return this.codigoUniversidadProperty().get();
}


public final void setCodigoUniversidad(final String codigoUniversidad) {
	this.codigoUniversidadProperty().set(codigoUniversidad);
}


public final SimpleBooleanProperty comedorProperty() {
	return this.comedor;
}


public final boolean isComedor() {
	return this.comedorProperty().get();
}


public final void setComedor(final boolean comedor) {
	this.comedorProperty().set(comedor);
}






}
