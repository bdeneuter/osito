package org.osito.javafx;

import java.util.Comparator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class Properties {

    public static <E> ListProperty<E> arrayListProperty() {
        return new SimpleListProperty<E>(FXCollections.<E>observableArrayList());
    }

    public static <E> ListProperty<E> sortedArrayListProperty(Comparator<E> comparator) {
    	return SortedListProperty.sortedListProperty(new SimpleListProperty<E>(FXCollections.<E>observableArrayList()), comparator);
    }

    public static <E> ListProperty<E> sortedListProperty(ListProperty<E> property, Comparator<E> comparator) {
    	return SortedListProperty.sortedListProperty(property, comparator);
    }

    public static <E> ObjectProperty<E> objectProperty() {
        return new SimpleObjectProperty<E>();
    }

    public static BooleanProperty booleanProperty(boolean value) {
        return new SimpleBooleanProperty(value);
    }

    public static StringProperty emptyStringProperty() {
        return new SimpleStringProperty("");
    }

    public static <T extends Enum<T> > EnumProperty<T> enumProperty(T value) {
        return new EnumProperty<T>(value);
    }

    public static <T extends Enum<T> > EnumProperty<T> enumProperty() {
        return new EnumProperty<T>();
    }
    
}
