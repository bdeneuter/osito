package org.osito.javafx.dialog;

import static javafx.geometry.Pos.CENTER_RIGHT;
import static org.osito.javafx.dialog.MessageKey.CLOSE;
import static org.osito.javafx.dialog.NodeId.CLOSE_BUTTON;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.FlowPaneBuilder;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;


public abstract class CloseDialog extends Dialog {
    
	private CloseDialogModel model;
	private Pos buttonAlignment = CENTER_RIGHT;
    
	public CloseDialog() {
		this(new Callback<Void, Void>() {
			@Override
			public Void call(Void arg) {
				return null;
			}
		});
	}
	
	public CloseDialog(Callback<?, ?> callback) {
		this(new CloseDialogModel(callback));
	}
	
    public CloseDialog(CloseDialogModel model) {
		this.model = model;
	}
    
    public void setButtonAlignment(Pos buttonAlignment) {
		this.buttonAlignment = buttonAlignment;
	}
    
    @Override
    public final Parent createRoot() {
        
        return BorderPaneBuilder.create()
                                .center(createContent())
                                .bottom(FlowPaneBuilder.create()
                                					   .children(
                                							   ButtonBuilder.create()
                                                               				.id(CLOSE_BUTTON.name())
                                                                            .text(CLOSE.getText())
                                                                            .onMouseClicked(new EventHandler<MouseEvent>() {
                                                                            	@Override
                                                                                public void handle(MouseEvent event) {
                                                                            		model.handleClose();
                                                                            		getStage().close();
                                                                            	}
                                                                            })
                                                                            .build())
                                                       .alignment(buttonAlignment)
                                                       .hgap(5)
                                                       .padding(new Insets(5, 5, 5, 5))
                                                       .build())
                                            .build();
    }
    
    @Override
    protected final void initStage(Stage stage) {
    	initializeStage(stage);
    	stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				model.handleClose();
			}
		});
    }
    
    protected abstract void initializeStage(Stage stage);
    
    protected abstract Node createContent();

}
