package org.osito.javafx.dialog.message;

import static org.osito.javafx.dialog.NodeId.LABEL;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.LabelBuilder;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPaneBuilder;

class MessageContent extends BorderPane {
	
	private Node content;
	private MessageType messageType;

	MessageContent(MessageType messageType, String message) {
		this.messageType = messageType;
		content = LabelBuilder.create()
                			  .id(LABEL.name())
                			  .graphic(ImageViewBuilder.create()
                					   					.image(messageType.image())
                					   					.build())
                			  .text(message)
                			  .build();
		init();
	}
	
	String getTitle() {
	    return messageType.getDefaultTitle().getText();
	}
	
	private void init() {
		BorderPaneBuilder.create()
						 .padding(new Insets(10))
						 .center(content)
						 .applyTo(this);
	}
	
}
