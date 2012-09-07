package org.osito.javafx.dialog.message;

import javafx.scene.image.Image;

public enum MessageType {

	ERROR("images/32x32/dialog-error.png"),
	QUESTION("images/32x32/dialog-help.png");
	
	private String imageLocation;

	private MessageType(String imageLocation) {
		this.imageLocation = imageLocation;
	}
	
	Image image() {
		return new Image(imageLocation);
	}
	
}
