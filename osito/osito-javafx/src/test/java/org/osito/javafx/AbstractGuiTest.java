package org.osito.javafx;

import static com.sun.javafx.application.PlatformImpl.startup;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javafx.application.Platform;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractGuiTest {
    
    protected Set<Throwable> thrownExceptions = new HashSet<Throwable> ();


    @Before
    public void setUp() {
        Locale.setDefault(new Locale("en"));
    }
    
    @Before
    public void setUpJavaFX() {
    	Platform.setImplicitExit(false);
        startup(new Runnable() {
            
            @Override
            public void run() {          
            }
        });
    }

    @Before
    public void setDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
                thrownExceptions.add(e);
            }
        });
    }

}
