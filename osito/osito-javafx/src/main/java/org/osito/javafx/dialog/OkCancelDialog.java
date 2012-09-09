package org.osito.javafx.dialog;

import static javafx.geometry.Pos.CENTER_RIGHT;
import static org.osito.javafx.dialog.MessageKey.CANCEL;
import static org.osito.javafx.dialog.MessageKey.OK;
import static org.osito.javafx.dialog.NodeId.CANCEL_BUTTON;
import static org.osito.javafx.dialog.NodeId.OK_BUTTON;
import static org.osito.javafx.dialog.message.FXOptionPane.showMessageDialog;
import static org.osito.javafx.dialog.message.MessageType.ERROR;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.HBoxBuilder;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.osito.javafx.ApplicationException;

public abstract class OkCancelDialog extends Dialog {
    
    private OkCancelDialogModel model;

    private Pos buttonAlignment = CENTER_RIGHT;

    public OkCancelDialog(OkCancelDialogModel model) {
    	this.model = model;
    }
    
    public void setButtonAlignment(Pos buttonAlignment) {
		this.buttonAlignment = buttonAlignment;
	}
    
    @Override
    public final Parent createRoot() {
        BorderPane root = BorderPaneBuilder.create()
                                           .center(createContent())
                                           .bottom(HBoxBuilder.create()
                                                       .children(
                                                    		   ButtonBuilder.create()
                                                               				.id(OK_BUTTON.name())
			                                                                .text(getOkLabel().getText())
			                                                                .onAction(new EventHandler<ActionEvent>() {
			                                                                	@Override
			                                                                	public void handle(ActionEvent event) {
			                                                                		try {
			                                                                			model.handleOk();
			                                                                			getStage().close();
			                                                                		} catch (ApplicationException e) {
			                                                                			showMessageDialog(e.getKey().getText(), ERROR);
			                                                                		}
			                                                                	}
			                                                                })
			                                                                .build(), 
			                                                   ButtonBuilder.create()
			                                                                .id(CANCEL_BUTTON.name())
			                                                                .text(getCancelLabel().getText())
			                                                                .onAction(new EventHandler<ActionEvent>() {
			                                                                    @Override
			                                                                    public void handle(ActionEvent event) {
			                                                                        model.handleCancel();
			                                                                        getStage().close();
			                                                                    }
			                                                                })
			                                                                .build())
                                                       .alignment(buttonAlignment)
                                                       .spacing(5)
                                                       .padding(new Insets(5, 5, 5, 5))
                                                       .build())
                                           .build();
        return root;
    }

    protected MessageKey getCancelLabel() {
    	return CANCEL;
    }

    protected MessageKey getOkLabel() {
    	return OK;
    }
    
    @Override
    protected final void initStage(Stage stage) {
    	initializeStage(stage);
    	stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
    		public void handle(WindowEvent event) {
    			model.handleCancel();
    		};
		});
    }
    
    protected abstract void initializeStage(Stage stage);
    
    protected abstract Node createContent();

}
