package org.osito.javafx.dialog;

import org.junit.Before;
import org.osito.javafx.AbstractGuiTest;

public abstract class AbstractDialogTest extends AbstractGuiTest {

	@Before
	public void showDialog() {
		createDialog().showDialog();
	}
	
    protected abstract Dialog createDialog();
    
}
