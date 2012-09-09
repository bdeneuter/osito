package org.osito.javafx.dialog.message;

import static org.osito.javafx.dialog.message.MessageType.ERROR;
import static org.osito.javafx.pageobjects.dialog.MessageDialogFixture.messageDialog;

import org.junit.Test;
import org.osito.javafx.dialog.AbstractDialogTest;
import org.osito.javafx.dialog.Dialog;

public class OkMessageDialogTest extends AbstractDialogTest {

	private static final String MESSAGE = "Hello World";

	@Override
	protected Dialog createDialog() {
		return new OkMessageDialog(ERROR, MESSAGE);
	}
	
	@Test
	public void clickOk() {
		messageDialog()
				.assertLabelText(MESSAGE)
				.clickOk()
				.assertIsClosed();
	}

}
