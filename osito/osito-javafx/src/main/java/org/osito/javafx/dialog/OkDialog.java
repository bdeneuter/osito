package org.osito.javafx.dialog;

import static javafx.geometry.Pos.CENTER;
import static org.osito.javafx.MessageKey.key;
import static org.osito.javafx.dialog.OkDialogId.OK_BUTTON;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.HBoxBuilder;

import org.osito.javafx.MessageService;

public abstract class OkDialog extends Dialog {
    
    protected MessageService messageService;

    protected Button okButton;
    
    public OkDialog(MessageService messageService) {
		this.messageService = messageService;
	}
    
    @Override
    public final Parent createRoot() {
        
        return BorderPaneBuilder.create()
                                .center(createContent())
                                .bottom(HBoxBuilder.create()
                                					   .children(
                                							   ButtonBuilder.create()
                                                               				.id(OK_BUTTON.name())
                                                                            .text(messageService.getText(key("ok")))
                                                                            .onAction(new EventHandler<ActionEvent>() {
                                                                            	@Override
                                                                                public void handle(ActionEvent event) {
                                                                            		getStage().close();
                                                                            	}
                                                                            })
                                                                            .build())
                                                       .alignment(CENTER)
                                                       .padding(new Insets(5, 5, 5, 5))
                                                       .build())
                                            .build();
    }
    
    protected abstract Node createContent();

}
