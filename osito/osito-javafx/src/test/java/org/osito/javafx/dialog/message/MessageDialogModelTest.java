package org.osito.javafx.dialog.message;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.osito.javafx.dialog.message.Option.CANCEL;
import static org.osito.javafx.dialog.message.Option.NO;
import static org.osito.javafx.dialog.message.Option.OK;
import static org.osito.javafx.dialog.message.Option.YES;
import static org.osito.javafx.dialog.message.OptionType.OK_CANCEL_OPTION;
import static org.osito.javafx.dialog.message.OptionType.YES_NO_OPTION;
import javafx.util.Callback;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageDialogModelTest {

	@Mock
	private Callback<Option, ?> callback;
	
	@Test
	public void handleOk_WhenYesNoOptionType_ThenReturnYes() {
		MessageDialogModel model = new MessageDialogModel(callback, YES_NO_OPTION);
		
		model.handleOk();
		
		verify(callback).call(YES);
		verifyNoMoreInteractions(callback);
	}

	@Test
	public void handleCancel_WhenYesNoOptionType_ThenReturnNo() {
		MessageDialogModel model = new MessageDialogModel(callback, YES_NO_OPTION);
		
		model.handleCancel();
		
		verify(callback).call(NO);
		verifyNoMoreInteractions(callback);
	}

	@Test
	public void handleOk_WhenOkCancelOptionType_ThenReturnOk() {
		MessageDialogModel model = new MessageDialogModel(callback, OK_CANCEL_OPTION);
		
		model.handleOk();
		
		verify(callback).call(OK);
		verifyNoMoreInteractions(callback);
	}

	@Test
	public void handleCancel_WhenOkCancelOptionType_ThenReturnNo() {
		MessageDialogModel model = new MessageDialogModel(callback, OK_CANCEL_OPTION);
		
		model.handleCancel();
		
		verify(callback).call(CANCEL);
		verifyNoMoreInteractions(callback);
	}
	
}
