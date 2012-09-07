package org.osito.javafx.textfield;

public class MaxCharactersTextFieldValidator implements TextFieldValidator {

	private int maxCharacters;

	public MaxCharactersTextFieldValidator(int maxCharacters) {
		this.maxCharacters = maxCharacters;
	}
	
	@Override
	public boolean isTextValid(String text) {
		return text == null || text.length() <= maxCharacters;
	}
	
}
