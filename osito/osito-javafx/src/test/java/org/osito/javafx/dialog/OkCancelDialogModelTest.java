package org.osito.javafx.dialog;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import javafx.util.Callback;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OkCancelDialogModelTest {

	@Mock
	private Callback<?, ?> okCallback;

	@Mock
	private Callback<?, ?> cancelCallback;
	
	private OkCancelDialogModel model;
	
	@Before
	public void setUp() {
		model = new OkCancelDialogModel(okCallback, cancelCallback);
	}
	
	@Test
	public void handleOk_ThenCallOkCallback() {
		model.handleOk();
		
		verify(okCallback).call(null);
		verifyZeroInteractions(cancelCallback);
	}

	@Test
	public void handleCancel_ThenCallOkCallback() {
		model.handleCancel();
		
		verify(cancelCallback).call(null);
		verifyZeroInteractions(okCallback);
	}
}
