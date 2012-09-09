package org.osito.javafx.dialog;

import static com.sun.javafx.application.PlatformImpl.startup;
import javafx.application.Platform;

import org.junit.Before;
import org.osito.javafx.AbstractGuiTest;

public abstract class AbstractDialogTest extends AbstractGuiTest {

    @Before
    public void setUpJavaFX() {
    	Platform.setImplicitExit(false);
        startup(new Runnable() {
            
            @Override
            public void run() {          
            }
        });
        createDialog().showDialog();
    }

    protected abstract Dialog createDialog();
    
}
