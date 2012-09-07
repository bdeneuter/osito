package org.osito.javafx.textfield;

import javafx.scene.control.PasswordFieldBuilder;

public class RestrictedPasswordFieldBuilder extends PasswordFieldBuilder<RestrictedPasswordFieldBuilder> {

    private Integer maxCharacters;
    private boolean upperCase;
    
    public RestrictedPasswordFieldBuilder maxCharacters(int maxCharacters) {
        this.maxCharacters  = maxCharacters;
        return this;
    }
    
    public RestrictedPasswordFieldBuilder upperCase(boolean uppercase) {
    	this.upperCase = uppercase;
    	return this;
    }
    
    public static RestrictedPasswordFieldBuilder aPasswordField() {
        return new RestrictedPasswordFieldBuilder();
    }
    
    @Override
    public RestrictedPasswordField build() {
        RestrictedPasswordField passwordField = new RestrictedPasswordField();
        super.applyTo(passwordField);
        CompositTextFieldValidator validator = new CompositTextFieldValidator();
        if (maxCharacters != null) {
        	validator.addValidator(new MaxCharactersTextFieldValidator(maxCharacters));
        }
        passwordField.setValidator(validator);
        if (upperCase) {
        	passwordField.setUpperCase(true);            
        }
        return passwordField;
    }
    
}
