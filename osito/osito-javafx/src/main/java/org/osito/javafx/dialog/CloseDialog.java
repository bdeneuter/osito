package org.osito.javafx.dialog;

import static javafx.geometry.Pos.CENTER_RIGHT;
import static org.osito.javafx.MessageKey.key;
import static org.osito.javafx.MessageService.messageService;
import static org.osito.javafx.dialog.CloseDialogId.CLOSE_BUTTON;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.FlowPaneBuilder;


public abstract class CloseDialog extends Dialog {
    
    protected Button closeButton;
    
    @Override
    public final Parent createRoot() {
        
        return BorderPaneBuilder.create()
                                .center(createContent())
                                .bottom(FlowPaneBuilder.create()
                                					   .children(
                                							   ButtonBuilder.create()
                                                               				.id(CLOSE_BUTTON.name())
                                                                            .text(messageService().getText(key("Close")))
                                                                            .onMouseClicked(new EventHandler<MouseEvent>() {
                                                                            	@Override
                                                                                public void handle(MouseEvent event) {
                                                                            		getStage().close();
                                                                            	}
                                                                            })
                                                                            .build())
                                                       .alignment(CENTER_RIGHT)
                                                       .hgap(5)
                                                       .padding(new Insets(5, 5, 5, 5))
                                                       .build())
                                            .build();
    }
    
    protected abstract Node createContent();

}
