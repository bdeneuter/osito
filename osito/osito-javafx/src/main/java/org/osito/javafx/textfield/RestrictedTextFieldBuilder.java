package org.osito.javafx.textfield;

import javafx.scene.control.TextFieldBuilder;

public class RestrictedTextFieldBuilder extends TextFieldBuilder<RestrictedTextFieldBuilder> {

    private Integer maxCharacters;
    private boolean upperCase;
    
    public RestrictedTextFieldBuilder maxCharacters(int maxCharacters) {
        this.maxCharacters  = maxCharacters;
        return this;
    }
    
    public RestrictedTextFieldBuilder upperCase(boolean uppercase) {
    	this.upperCase = uppercase;
    	return this;
    }
    
    public static RestrictedTextFieldBuilder aTextField() {
        return new RestrictedTextFieldBuilder();
    }
    
    @Override
    public RestrictedTextField build() {
        RestrictedTextField textField = new RestrictedTextField();
        super.applyTo(textField);
        
        CompositTextFieldValidator validator = new CompositTextFieldValidator();
        if (maxCharacters != null) {
        	validator.addValidator(new MaxCharactersTextFieldValidator(maxCharacters));
        }
        textField.setValidator(validator);
        if (upperCase) {
        	textField.setUpperCase(true);            
        }
        return textField;
    }
    
}
