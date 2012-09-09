package org.osito.javafx.dialog;


import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.osito.javafx.dialog.MessageKey.OK;
import static org.osito.javafx.pageobjects.dialog.MessageDialogFixture.messageDialog;
import static org.osito.javafx.pageobjects.dialog.OkCancelDialogFixture.okCancelDialog;
import javafx.scene.Node;
import javafx.stage.Stage;

import org.junit.Test;
import org.mockito.Mock;
import org.osito.javafx.ApplicationException;
import org.osito.javafx.pageobjects.dialog.OkCancelDialogFixture;

public class OkCancelDialogTest extends AbstractDialogTest {
    
    private static final MessageKey MESSAGE_KEY = OK;
    
	@Mock
    private OkCancelDialogModel presenter;

	@Override
    protected Dialog createDialog() {
        return new OkCancelDialog(presenter) {
            @Override
            protected void initializeStage(Stage stage) {
            }
            
            @Override
            protected Node createContent() {
                return new javafx.scene.Parent() {};
            }
        };
    }

    @Test
    public void cancel_WhenCancelClicked_ThenPresenterIsCalledAndStageIsClosed() {
        OkCancelDialogFixture.okCancelDialog()
                .assertIsShowing()
                .clickCancel()
                .assertIsClosed();
        
        verify(presenter).handleCancel();
        verifyNoMoreInteractions(presenter);
    }

    @Test
    public void ok_WhenOkClicked_ThenPresenterIsCalledAndStageIsClosed() {
        okCancelDialog()
                .assertIsShowing()
                .clickOk()
                .assertIsClosed();
        verify(presenter).handleOk();
        verifyNoMoreInteractions(presenter);
    }

    @Test
    public void ok_WhenOkClickedAndApplicationExceptionIsThrown_ThenMessageDialogIsShown() {
    	doThrow(new ApplicationException(MESSAGE_KEY)).when(presenter).handleOk();
    	
    	okCancelDialog()
    			.assertIsShowing()
		    	.clickOk();
    	
    	messageDialog()
    			.assertIsShowing()
    			.assertLabelText("Ok");
    }
    
}
