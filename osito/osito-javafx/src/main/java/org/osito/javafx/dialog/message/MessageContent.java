package org.osito.javafx.dialog.message;

import static org.osito.javafx.MessageKey.key;
import static org.osito.javafx.MessageService.messageService;
import static org.osito.javafx.dialog.message.MessageContentId.LABEL;
import javafx.geometry.Insets;
import javafx.scene.control.LabelBuilder;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPaneBuilder;

import org.osito.javafx.MessageKey;

class MessageContent extends BorderPane {
	
	private MessageKey messageKey;
	private MessageType messageType;

	MessageContent(MessageType messageType, MessageKey messageKey) {
		this.messageType = messageType;
		this.messageKey = messageKey;
		init();
	}
	
	String getTitle() {
	    return messageService().getText(key(messageType.name()));
	}
	
	private void init() {
		BorderPaneBuilder.create()
						 .padding(new Insets(10))
						 .center(LabelBuilder.create()
						                     .id(LABEL.name())
								  			 .graphic(ImageViewBuilder.create()
								  			                          .image(messageType.image())
								  			                          .build())
								  			 .text(messageService().getText(messageKey))
								  			 .build())
						 .applyTo(this);
	}
	
}
