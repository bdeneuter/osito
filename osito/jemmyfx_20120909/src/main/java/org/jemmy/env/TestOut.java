/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this License Header
 * Notice in each file.
 *
 * If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 */
package org.jemmy.env;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 * Test output.
 *	
 * @author Alexandre Iline (alexandre.iline@sun.com)
 */
public class TestOut {

    private InputStream input;
    private PrintWriter output;
    private PrintWriter errput;
    private BufferedReader buffInput;
    private boolean autoFlushMode = true;

    /**
     * Constructor.
     * @param	in Input stream
     * @param	out Output stream
     * @param	err Errput stream
     */
    public TestOut(InputStream in, PrintStream out, PrintStream err) {
        this(in, out, err, null);
    }

    /**
     * Constructor.
     * @param	in Input stream
     * @param	out Output stream
     * @param	err Errput stream
     * @param	golden Golgen output stream
     */
    public TestOut(InputStream in, PrintStream out, PrintStream err, PrintStream golden) {
        super();
        PrintWriter tout = null;
        if (out != null) {
            tout = new PrintWriter(out);
        }
        PrintWriter terr = null;
        if (err != null) {
            terr = new PrintWriter(err);
        }
        initStreams(in, tout, terr);
    }

    /**
     * Constructor.
     * @param	in Input stream
     * @param	out Output stream
     * @param	err Errput stream
     */
    public TestOut(InputStream in, PrintWriter out, PrintWriter err) {
        super();
        initStreams(in, out, err);
        autoFlushMode = true;
    }

    /**
     * Creates unstance using System.in, System.out and System.err streams.
     */
    public TestOut() {
        this(System.in,
                new PrintWriter(System.out),
                new PrintWriter(System.err));
    }

    /**
     * Creates output which does not print any message anywhere.
     * @return a TestOut object which does not print any message anywhere.
     */
    public static TestOut getNullOutput() {
        return (new TestOut((InputStream) null, (PrintWriter) null, (PrintWriter) null));
    }

    /**
     * Specifies either flush is invoked after each output.
     * @param autoFlushMode If true flush is invoking after each output.
     * @return Old value of the auto flush mode.
     * @see #getAutoFlushMode
     */
    public boolean setAutoFlushMode(boolean autoFlushMode) {
        boolean oldValue = getAutoFlushMode();
        this.autoFlushMode = autoFlushMode;
        return (oldValue);
    }

    /**
     * Says if flush is invoked after each output.
     * @return Value of the auto flush mode.
     * @see #setAutoFlushMode
     */
    public boolean getAutoFlushMode() {
        return (autoFlushMode);
    }

    /**
     * Read one byte from input.
     * @return an int from input stream.
     * @exception	IOException
     */
    public int read() throws IOException {
        if (input != null) {
            return (input.read());
        } else {
            return (-1);
        }
    }

    /**
     * Read a line from input.
     * @return a line from input stream.
     * @exception	IOException
     */
    public String readln() throws IOException {
        if (buffInput != null) {
            return (buffInput.readLine());
        } else {
            return (null);
        }
    }

    /**
     * Prints a line into output.
     * @param	line a string to print into output stream.
     */
    public void print(String line) {
        if (output != null) {
            output.print(line);
        }
    }

    /**
     * Prints a line and then terminate the line by writing the line separator string.
     * @param	line a string to print into output stream.
     */
    public void println(String line) {
        if (output != null) {
            output.println(line);
            if (autoFlushMode) {
                output.flush();
            }
        }
    }

    /**
     * Prints a line into error output.
     * @param	line a string to print into error output stream.
     */
    public void printerrln(String line) {
        if (errput != null) {
            errput.println(line);
            if (autoFlushMode) {
                errput.flush();
            }
        }
    }

    /**
     * Prints a line into either output or errput.
     * @param	toOut If true prints a line into output.
     * @param	line a string to print.
     */
    public void println(boolean toOut, String line) {
        if (toOut) {
            println(line);
        } else {
            printerrln(line);
        }
    }

    /**
     * Prints a error line.
     * @param	text a error text.
     */
    public void printerr(String text) {
        printerrln("Error:");
        printerrln(text);
    }

    /**
     * Prints an exception stack trace into error stream.
     * @param e exception
     */
    public void printStackTrace(Throwable e) {
        if (errput != null) {
            e.printStackTrace(errput);
            if (autoFlushMode) {
                errput.flush();
            }
        }
    }

    /**
     * Returns input stream.
     * @return an input stream
     */
    public InputStream getInput() {
        return (input);
    }

    /**
     * Returns output writer.
     * @return an output stream
     */
    public PrintWriter getOutput() {
        return (output);
    }

    /**
     * Returns errput writer.
     * @return a error stream
     */
    public PrintWriter getErrput() {
        return (errput);
    }

    /**
     * Creates an output which prints only error messages.
     * @return a TestOut instance which has only error stream.
     */
    public TestOut createErrorOutput() {
        return (new TestOut(null, null, getErrput()));
    }

    /**
     * Flushes all output threads.
     */
    public void flush() {
        if (output != null) {
            output.flush();
        }
        if (errput != null) {
            errput.flush();
        }
    }

    private void initStreams(InputStream in, PrintWriter out, PrintWriter err) {
        input = in;
        output = out;
        errput = err;
        if (input != null) {
            buffInput = new BufferedReader(new InputStreamReader(in));
        } else {
            buffInput = null;
        }
    }
}
