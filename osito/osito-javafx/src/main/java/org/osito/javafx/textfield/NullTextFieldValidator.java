package org.osito.javafx.textfield;

class NullTextFieldValidator implements TextFieldValidator {

	@Override
	public boolean isTextValid(String text) {
		return true;
	}
	
}
