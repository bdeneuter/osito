package org.osito.javafx.dialog;


import static org.mockito.Mockito.verify;
import static org.osito.javafx.pageobjects.dialog.CloseDialogFixture.closeDialog;
import javafx.scene.Node;
import javafx.stage.Stage;

import org.junit.Test;
import org.mockito.Mock;

public class CloseDialogTest extends AbstractDialogTest {

	@Mock
	private CloseDialogModel model;
	
    @Override
    protected Dialog createDialog() {
        return new CloseDialog(model) {
        	
        	@Override
        	protected void initializeStage(Stage stage) {}
        	
        	@Override
        	protected Node createContent() {
        		return new javafx.scene.Parent() {};
        	};
		};
    }

    @Test
    public void closer_WhenCloseClicked_ThenPresenterIsCalledAndStageIsClosed() {
        closeDialog().assertIsShowing()
                	 .clickClose()
                	 .assertIsClosed();
        
        verify(model).handleClose();
    }
    
}
