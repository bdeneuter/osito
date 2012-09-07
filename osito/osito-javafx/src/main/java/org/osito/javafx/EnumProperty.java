package org.osito.javafx;

import javafx.beans.property.SimpleObjectProperty;


public class EnumProperty<T extends Enum<T>> extends SimpleObjectProperty<T> {

	public EnumProperty() {
	}
	
	public EnumProperty(T value) {
		set(value);			
    }
    
}
