package org.osito.javafx;

import java.util.Comparator;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class SortedListProperty {

    public static <E> ListProperty<E> sortedListProperty(Comparator<E> comparator) {
        return sortedListProperty(new SimpleListProperty<E>(FXCollections.<E> observableArrayList()), comparator);
    }

    public static <E> ListProperty<E> sortedListProperty(ListProperty<E> property, Comparator<E> comparator) {
    	property.addListener(new Sorter<E>(comparator));
    	return property;
    }

    private static class Sorter<E> implements ListChangeListener<E> {

        private Comparator<E> comparator;
        
        private Sorter(Comparator<E> comparator) {
            this.comparator = comparator;
        }
        
        @Override
        public void onChanged(Change<? extends E> change) {
            ObservableList<? extends E> list = change.getList();
            list.removeListener(this);
            FXCollections.sort(list, comparator);
            list.addListener(this);
        }
    }

}
