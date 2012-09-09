package org.osito.javafx;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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
