package org.osito.javafx.dialog;

import static javafx.geometry.Pos.CENTER;
import static org.osito.javafx.dialog.MessageKey.OK;
import static org.osito.javafx.dialog.NodeId.OK_BUTTON;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.HBoxBuilder;

public abstract class OkDialog extends Dialog {
    
    protected Button okButton;
        
    @Override
    public final Parent createRoot() {
        
        return BorderPaneBuilder.create()
                                .center(createContent())
                                .bottom(HBoxBuilder.create()
                                					   .children(
                                							   ButtonBuilder.create()
                                                               				.id(OK_BUTTON.name())
                                                                            .text(OK.getText())
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
