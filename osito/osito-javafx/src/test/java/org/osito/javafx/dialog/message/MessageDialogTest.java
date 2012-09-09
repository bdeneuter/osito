package org.osito.javafx.dialog.message;

import static org.mockito.Mockito.verify;
import static org.osito.javafx.dialog.message.Option.YES;
import static org.osito.javafx.dialog.message.OptionType.YES_NO_OPTION;
import static org.osito.javafx.pageobjects.dialog.MessageDialogFixture.messageDialog;
import javafx.util.Callback;

import org.junit.Test;
import org.mockito.Mock;
import org.osito.javafx.dialog.AbstractDialogTest;
import org.osito.javafx.dialog.Dialog;

public class MessageDialogTest extends AbstractDialogTest {

	private static final String MESSAGE = "Hello world";
	
	@Mock
	private Callback<Option, ?> callback;

	@Override
	protected Dialog createDialog() {
		return  new MessageDialog(null, MessageType.QUESTION, MESSAGE, YES_NO_OPTION, callback);
	}
	
	@Test
	public void clickOk() {
		messageDialog()
				.assertLabelText(MESSAGE)
				.clickOk()
				.assertIsClosed();
		
		verify(callback).call(YES);
	}

}
