package org.osito.javafx.dialog.message;

import static org.osito.javafx.dialog.message.OptionType.OK_CANCEL_OPTION;
import static org.osito.javafx.dialog.message.OptionType.YES_NO_OPTION;
import javafx.util.Callback;

import org.junit.Test;
import org.mockito.Mock;
import org.osito.javafx.AbstractGuiTest;


public class FXOptionPaneTest extends AbstractGuiTest {
	
	@Mock
	private Callback<Option, ?> callback;
	
	@Test
	public void showErrorMessage() {
		FXOptionPane.showMessageDialog("This is an example of an error message dialog.", MessageType.ERROR);
	}

	@Test
	public void showQuestionMessage() {
		FXOptionPane.showMessageDialog("This is an example of a question message dialog.", MessageType.QUESTION);
	}

	@Test
	public void showYesNoConfirmDialog() {
		FXOptionPane.showConfirmDialog("This is an example of a confirmation message", YES_NO_OPTION, callback);
	}

	@Test
	public void showOkCancelConfirmDialog() {
		FXOptionPane.showConfirmDialog("This is an example of a confirmation message", OK_CANCEL_OPTION, callback);
	}
	
}
