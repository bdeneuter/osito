package org.osito.javafx.textfield;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

class CompositTextFieldValidator implements TextFieldValidator {

	private List<TextFieldValidator> validators = newArrayList();
	
	void addValidator(TextFieldValidator validator) {
		validators.add(validator);
	}
	
	@Override
	public boolean isTextValid(String text) {
		for (TextFieldValidator validator : validators) {
			if (!validator.isTextValid(text)) {
				return false;
			}
		}
		return true;
	}
	
}
