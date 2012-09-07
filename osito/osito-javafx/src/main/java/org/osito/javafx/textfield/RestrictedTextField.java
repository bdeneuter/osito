package org.osito.javafx.textfield;

import javafx.scene.control.TextField;

public class RestrictedTextField extends TextField {

	private TextFieldValidator validator = new NullTextFieldValidator();
	private boolean isUpperCase = false;

	private TextField preview = new TextField();

	public void setValidator(TextFieldValidator validator) {
		this.validator = validator;
	}

	public void setUpperCase(boolean isUpperCase) {
		this.isUpperCase = isUpperCase;
	}

	@Override
	public void replaceText(int start, int end, String text) {
		text = toUpperCase(text);
		replaceTextOnPreview(start, end, text);
		if (isPreviewValid()) {
			super.replaceText(start, end, text);
		}
	}

	private void replaceTextOnPreview(int start, int end, String text) {
		preview.setText(getText());
		preview.replaceText(start, end, text);
	}

	@Override
	public void replaceSelection(String text) {
		text = toUpperCase(text);
		replaceSelectionOnPreview(text);
		if (isPreviewValid()) {
			super.replaceSelection(text);
		}
	}

	private void replaceSelectionOnPreview(String text) {
		preview.setText(getText());
		preview.replaceSelection(text);
	}

	private boolean isPreviewValid() {
		return validator.isTextValid(preview.getText());
	}
	
	private String toUpperCase(String text) {
		return isUpperCase ? text.toUpperCase() : text;
	}
}
