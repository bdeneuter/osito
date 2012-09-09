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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import org.jemmy.JemmyException;
import org.jemmy.action.ActionExecutor;
import org.jemmy.action.DefaultExecutor;
import org.jemmy.control.Wrap;
import org.jemmy.image.ImageCapturer;
import org.jemmy.image.ImageFactory;
import org.jemmy.image.ImageFactoryImpl;
import org.jemmy.image.ImageLoader;
import org.jemmy.input.CharBindingMap;
import org.jemmy.interfaces.ControlInterfaceFactory;
import org.jemmy.timing.Waiter;

/**
 *
 * @author shura, mrkam
 */
public class Environment {

    /**
     *
     */
    public static final String JEMMY_PROPERTIES_FILE_PROPERTY = "jemmy.properties";
    /**
     *  Information output for Environment class
     */
    public static final String OUTPUT = Environment.class.getName() + ".OUTPUT";
    private final static Environment env = new Environment(null);

    /**
     *
     * @return
     */
    public static Environment getEnvironment() {
        return env;
    }

    static {
        env.setOutput(new TestOut(System.in, System.out, System.err));
        env.setExecutor(new DefaultExecutor());
    }
    private Hashtable<PropertyKey, Object> environment = new Hashtable<PropertyKey, Object>();
    private Environment parent;

    /**
     *
     * @param parent
     */
    public Environment(Environment parent) {
        this.parent = parent;
        environment = new Hashtable<PropertyKey, Object>();
        if (parent == null) {
            loadProperties();
        }
    }

    /**
     *
     */
    public Environment() {
        this(getEnvironment());
    }

    /**
     *
     * @return
     */
    public Environment getParentEnvironment() {
        return parent;
    }

    /**
     *
     * @param parent
     */
    public void setParentEnvironment(Environment parent) {
        this.parent = parent;
    }

    private void loadProperties() {
        String propFileName = System.getProperty(JEMMY_PROPERTIES_FILE_PROPERTY);
        if (propFileName == null || propFileName.length() == 0) {
            propFileName = System.getProperty("user.home") + File.separator + ".jemmy.properties";
        }
        File propFile = new File(propFileName);
        System.out.println("Loading jemmy properties from " + propFile);
        if (propFile.exists()) {
            Properties props = new Properties();
            try {
                props.load(new FileInputStream(propFile));
            } catch (IOException ex) {
                throw new JemmyException("Unable to load properties", ex, propFileName);
            }
            for (Object k : props.keySet()) {
                setProperty(k.toString(), props.getProperty(k.toString()));
            }
        } else {
            System.out.println("Property file " + propFile + " does not exists. Ignoring.");
        }
    }

    /**
     *
     * @param cls
     * @return
     */
    public List<?> get(Class cls) {
        Enumeration<PropertyKey> all = environment.keys();
        ArrayList<Object> result = new ArrayList<Object>();
        PropertyKey key;
        while (all.hasMoreElements()) {
            key = all.nextElement();
            if (key.getCls().equals(cls)) {
                result.add(environment.get(key));
            }
        }
        return result;
    }

    /**
     *
     * @param defaultExecutor
     * @return
     */
    public ActionExecutor setExecutor(ActionExecutor defaultExecutor) {
        return (ActionExecutor) setProperty(ActionExecutor.class, defaultExecutor);
    }

    /**
     *
     * @return
     */
    public ActionExecutor getExecutor() {
        ActionExecutor res = (ActionExecutor) getProperty(ActionExecutor.class);
        if (res == null) {
            String executorClassName = (String) getProperty(ActionExecutor.ACTION_EXECUTOR_PROPERTY);
            try {
                res = ActionExecutor.class.cast(Class.forName(executorClassName).newInstance());
                setExecutor(res);
            } catch (InstantiationException ex) {
                throw new JemmyException("Unable to instantiate executor ", ex, executorClassName);
            } catch (IllegalAccessException ex) {
                throw new JemmyException("Unable to instantiate executor ", ex, executorClassName);
            } catch (ClassNotFoundException ex) {
                throw new JemmyException("No executorclass ", ex, executorClassName);
            }
        }
        return res;
    }

    private <T> T setProperty(Class<T> cls, Object ref, T obj) {
        return setProperty(new PropertyKey<T>(cls, ref), obj);
    }

    private <T> T setPropertyIfNotSet(Class<T> cls, Object ref, T obj) {
        return setPropertyIfNotSet(new PropertyKey<T>(cls, ref), obj);
    }

    private <T> T getProperty(Class<T> cls, Object ref) {
        return getProperty(cls, ref, null);
    }

    @SuppressWarnings("unchecked")
    private <T> T getProperty(Class cls, Object ref, T defaultValue) {
        for (PropertyKey pk : environment.keySet()) {
            if (pk.equals(new PropertyKey(cls, ref))) {
                return (T) environment.get(pk);
            }
        }
        if (getParentEnvironment() != null) {
            return getParentEnvironment().getProperty(cls, ref, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     *
     * @param cls
     * @param obj if null then property is removed
     * @return
     */
    public <T> T  setProperty(Class<T> cls, T obj) {
        return setProperty(cls, null, obj);
    }

    /**
     *
     * @param cls
     * @param obj if null then property is removed
     * @return
     */
    public <T> T  setPropertyIfNotSet(Class<T> cls, T obj) {
        return setPropertyIfNotSet(cls, null, obj);
    }

    /**
     *
     * @param cls
     * @return
     */
    public <T> T  getProperty(Class<T> cls) {
        return getProperty(cls, null);
    }

    /**
     *
     * @param name
     * @param obj if null then property is removed
     * @return
     */
    public Object setProperty(String name, Object obj) {
        return setProperty(Object.class, name, obj);
    }

    /**
     *
     * @param name
     * @param obj
     * @return
     */
    public Object setPropertyIfNotSet(String name, Object obj) {
        return setPropertyIfNotSet(Object.class, name, obj);
    }

    /**
     *
     * @param name
     * @return
     */
    public Object getProperty(String name) {
        return getProperty(Object.class, name);
    }

    /**
     *
     * @param <T>
     * @param name
     * @param defaultValue
     * @return
     */
    public Object getProperty(String name, Object defaultValue) {
        return getProperty(Environment.class, name, defaultValue);
    }

    private <T> T setProperty(PropertyKey<T> key, Object value) {
        if (value == null) {
            return key.cls.cast(environment.remove(key));
        } else {
            return key.cls.cast(environment.put(key, value));
        }
    }

    private <T> T setPropertyIfNotSet(PropertyKey<T> key, T value) {
        T res = key.cls.cast(environment.get(key));
        if (res == null) {
            return key.cls.cast(environment.put(key, value));
        } else {
            return res;
        }
    }

    private Object getProperty(PropertyKey key) {
        return environment.get(key);
    }

    /**
     *
     * @param out
     * @return
     */
    public TestOut setOutput(TestOut out) {
        return (TestOut) setProperty(TestOut.class, out);
    }

    /**
     *
     * @return
     */
    public TestOut getOutput() {
        return (TestOut) getProperty(TestOut.class);
    }

    /**
     * Set some specific output. All classes which provide output
     * should use some specific outputs. Please consult javadoc for a class in question.
     * Use <code>null</code> to unset the property.
     * @param outputName
     * @param out
     * @return
     */
    public TestOut setOutput(String outputName, TestOut out) {
        return (TestOut) setProperty(TestOut.class, outputName, out);
    }

    /**
     * Initializes some specific output only if it is not yet set.
     * @param outputName
     * @param out
     * @return
     */
    public TestOut initOutput(String outputName, TestOut out) {
        TestOut res = (TestOut) getProperty(TestOut.class, outputName);
        if(res == null) {
            return setOutput(outputName, out);
        } else {
            return res;
        }
    }

    /**
     * Get's a specific output. If nothing assigned,
     * returns <code>getOutput()</code>
     * @param outputName
     * @return
     */
    public TestOut getOutput(String outputName) {
        TestOut res = (TestOut) getProperty(TestOut.class, outputName);
        return (res != null) ? res : getOutput();
    }

    /**
     *
     * @param timeout
     * @return
     */
    public Waiter getWaiter(Timeout timeout) {
        return getWaiter(timeout.getName());
    }

    /**
     *
     * @param timeoutName
     * @return
     */
    public Waiter getWaiter(String timeoutName) {
        return new Waiter(getTimeout(timeoutName));
    }

    /**
     *
     * @param timeout
     * @return
     */
    public Timeout getTimeout(Timeout timeout) {
        return getTimeout(timeout.getName());
    }

    /**
     *
     * @param name
     * @return
     */
    public Timeout getTimeout(String name) {
        return (Timeout) getProperty(Timeout.class, name);
    }

    /**
     * Sets timeout.
     * @param timeout Timeout to set.
     * @return replaced timeout if it was already set.
     */
    public Timeout setTimeout(Timeout timeout) {
        return (Timeout) setProperty(Timeout.class, timeout.getName(), timeout);
    }

    /**
     * Initializes timeout only if it is not set.
     * @param timeout Timeout to set.
     * @return replaced timeout if it was already set.
     */
    public Timeout initTimeout(Timeout timeout) {
        if(getProperty(Timeout.class, timeout.getName()) == null) {
            return setTimeout(timeout);
        }
        return getTimeout(timeout);
    }

    /**
     * Sets new value for the timeout specified by Timeout object instance.
     * @param timeout Timeout object instance which identifies the name of the
     * timeout to set.
     * @param value new value for the timout.
     * @return replaced timeout if it was already set.
     */
    public Timeout setTimeout(Timeout timeout, long value) {
        return setTimeout(timeout.getName(), value);
    }

    /**
     * Sets new value for the timeout.
     * @param name Name of the timeout.
     * @param value Value of the timeout.
     * @return replaced timeout if it was already set.
     */
    public Timeout setTimeout(String name, long value) {
        return setTimeout(new Timeout(name, value));
    }

    /**
     *
     * @return
     */
    public CharBindingMap getBindingMap() {
        return (CharBindingMap) getProperty(CharBindingMap.class);
    }

    /**
     *
     * @param map
     * @return
     */
    public CharBindingMap setBindingMap(CharBindingMap map) {
        return (CharBindingMap) setProperty(CharBindingMap.class, map);
    }

    /**
     *
     * @return
     * @deprecated {@linkplain ImageFactory ImageFactory} interface is deprecated,
     * use {@linkplain ImageCapturer ImageCapturer} and
     * {@linkplain ImageLoader} interfaces instead.
     */
    public ImageFactory getImageFactory() {
        ImageFactory res = (ImageFactory) getProperty(ImageFactory.class);
        if (res == null) {
            ImageCapturer imageCapturer = getImageCapturer();
            ImageLoader imageLoader = getImageLoader();
            if (imageCapturer != null && imageLoader != null) {
                return new ImageFactoryImpl(imageCapturer, imageLoader);
            }
            String factoryClass = (String) getProperty(Wrap.IMAGE_FACTORY_PROPERTY);
            if (factoryClass == null) {
                return null;
            }
            try {
                res = ImageFactory.class.cast(Class.forName(String.class.cast(factoryClass)).newInstance());
                setImageFactory(res);
            } catch (InstantiationException ex) {
                throw new JemmyException("Unable to instantiate image factory ", ex, factoryClass);
            } catch (IllegalAccessException ex) {
                throw new JemmyException("Unable to instantiate image factory ", ex, factoryClass);
            } catch (ClassNotFoundException ex) {
                throw new JemmyException("No image factory class ", ex, factoryClass);
            }
        }
        return res;
    }

    /**
     *
     * @return
     */
    public ImageLoader getImageLoader() {
        ImageLoader res = (ImageLoader) getProperty(ImageLoader.class);
        if (res == null) {
            String loaderClass = (String) getProperty(Wrap.IMAGE_LOADER_PROPERTY);
            ImageFactory imageFactory = (ImageFactory) getProperty(ImageFactory.class);
            if (loaderClass == null) {
                return imageFactory;
            }
            try {
                res = ImageLoader.class.cast(Class.forName(String.class.cast(loaderClass)).newInstance());
                setImageLoader(res);
            } catch (InstantiationException ex) {
                throw new JemmyException("Unable to instantiate image loader ", ex, loaderClass);
            } catch (IllegalAccessException ex) {
                throw new JemmyException("Unable to instantiate image loader ", ex, loaderClass);
            } catch (ClassNotFoundException ex) {
                throw new JemmyException("No image loader class ", ex, loaderClass);
            }
        }
        return res;
    }

    /**
     *
     * @return
     */
    public ImageCapturer getImageCapturer() {
        ImageCapturer res = (ImageCapturer) getProperty(ImageCapturer.class);
        if (res == null) {
            String capturerClass = (String) getProperty(Wrap.IMAGE_CAPTURER_PROPERTY);
            ImageFactory imageFactory = (ImageFactory) getProperty(ImageFactory.class);
            if (capturerClass == null) {
                return imageFactory;
            } 
            try {
                res = ImageCapturer.class.cast(Class.forName(String.class.cast(capturerClass)).newInstance());
                setImageCapturer(res);
            } catch (InstantiationException ex) {
                throw new JemmyException("Unable to instantiate image capturer ", ex, capturerClass);
            } catch (IllegalAccessException ex) {
                throw new JemmyException("Unable to instantiate image capturer ", ex, capturerClass);
            } catch (ClassNotFoundException ex) {
                throw new JemmyException("No image capturer class ", ex, capturerClass);
            }
        }
        return res;
    }

    /**
     *
     * @param factory
     * @return
     * @deprecated {@linkplain ImageFactory ImageFactory} interface is deprecated,
     * use {@linkplain ImageCapturer ImageCapturer} and
     * {@linkplain ImageLoader} interfaces instead.
     */
    public ImageFactory setImageFactory(ImageFactory factory) {
        if (factory instanceof ImageFactoryImpl) {
            ImageFactoryImpl impl = (ImageFactoryImpl) factory;
            setImageCapturer(impl.getImageCapturer());
            setImageLoader(impl.getImageLoader());
        }
        return (ImageFactory) setProperty(ImageFactory.class, factory);
    }

    /**
     *
     * @param imageLoader
     * @return
     */
    public ImageLoader setImageLoader(ImageLoader imageLoader) {
        return (ImageLoader) setProperty(ImageLoader.class, imageLoader);
    }

    /**
     *
     * @param imageCapturer
     * @return
     */
    public ImageCapturer setImageCapturer(ImageCapturer imageCapturer) {
        getOutput(OUTPUT).println("ImageCapturer set to " + imageCapturer);
        return (ImageCapturer) setProperty(ImageCapturer.class, imageCapturer);
    }

    /**
     *
     * @return
     */
    public ControlInterfaceFactory getInputFactory() {
        ControlInterfaceFactory res = (ControlInterfaceFactory) getProperty(ControlInterfaceFactory.class);
        if (res == null) {
            String factoryClass = (String) getProperty(Wrap.INPUT_FACTORY_PROPERTY);
            if (factoryClass != null) {
                try {
                    res = ControlInterfaceFactory.class.cast(Class.forName(String.class.cast(factoryClass)).newInstance());
                    setInputFactory(res);
                } catch (InstantiationException ex) {
                    throw new JemmyException("Unable to instantiate input factory", ex, factoryClass);
                } catch (IllegalAccessException ex) {
                    throw new JemmyException("Unable to instantiate input factory", ex, factoryClass);
                } catch (ClassNotFoundException ex) {
                    throw new JemmyException("Unable to load input factory", ex, factoryClass);
                }
            }
        }
        return res;
    }

    /**
     *
     * @param factory
     * @return
     */
    public ControlInterfaceFactory setInputFactory(ControlInterfaceFactory factory) {
        getOutput(OUTPUT).println("Input factory set to " + factory);
        return (ControlInterfaceFactory) setProperty(ControlInterfaceFactory.class, factory);
    }

    private static class PropertyKey<TYPE> {

        private Class<TYPE> cls;
        private Object ref;

        public PropertyKey(Class<TYPE> cls, Object ref) {
            this.cls = cls;
            this.ref = ref;
        }

        private PropertyKey(Class<TYPE> cls) {
            this(cls, null);
        }

        public Class<TYPE> getCls() {
            return cls;
        }

        public Object getRef() {
            return ref;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final PropertyKey other = (PropertyKey) obj;
            if (this.cls != other.cls && (this.cls == null || !this.cls.equals(other.cls))) {
                return false;
            }
            if (this.ref != other.ref && (this.ref == null || !this.ref.equals(other.ref))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 41 * hash + (this.cls != null ? this.cls.hashCode() : 0);
            hash = 41 * hash + (this.ref != null ? this.ref.hashCode() : 0);
            return hash;
        }
    }
}
