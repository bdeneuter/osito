package org.osito.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class EnumToggleGroup<T extends Enum<T>> extends ToggleGroup {

	public EnumProperty<T> selectedValue = new EnumProperty<T>();
	private boolean busy;

	@SuppressWarnings("unchecked")
	public EnumToggleGroup() {
		selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if (!busy) {
					busy = true;
					if (newValue == null) {
						selectedValue.set(null);
					} else {
						selectedValue.set((T) newValue.getUserData());					
					}
					busy = false;
				}
				
			}
		});
		selectedValue.addListener(new ChangeListener<T>() {
			@Override
			public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
				if (!busy) {
					busy = true;
					selectToggle(null);
					for (Toggle toggle : getToggles()) {
						if (toggle.getUserData().equals(newValue)) {
							selectToggle(toggle);
						}
					}			
					busy = false;
				}
			}
		});
	}
	

	
}
