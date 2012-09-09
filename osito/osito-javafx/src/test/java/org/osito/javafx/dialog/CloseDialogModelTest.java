package org.osito.javafx.dialog;

import static org.mockito.Mockito.verify;
import javafx.util.Callback;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CloseDialogModelTest {

	@Mock
	private Callback<?, ?> callback;
	
	private CloseDialogModel model;
	
	@Before
	public void setUp() {
		model = new CloseDialogModel(callback);
	}
	
	@Test
	public void handleClose_ThenCallCallback() {
		model.handleClose();
		
		verify(callback).call(null);
	}
	
}
