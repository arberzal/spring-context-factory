/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isban.rtp.naming;

import javax.naming.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author arberzal
 */
//sh jmeter.sh -Djava.library.path=/opt/mqm/java/lib64/ -Dcom.ibm.mq.cfg.jmqi.libpath=/opt/mqm/java/lib64
public class SpringContextFactory implements javax.naming.spi.InitialContextFactory {

    private static final Logger log = LoggerFactory.getLogger(SpringContextFactory.class);

    private AbstractXmlApplicationContext springContext;
    private Map<String, Object> beans;

    @Override
    public Context getInitialContext(final Hashtable<?, ?> environment) throws NamingException {
        return new Context() {

            @Override
            public Object lookup(Name name) throws NamingException {
                Enumeration<String> parts = name.getAll();
                StringBuilder nameBuilder = new StringBuilder();
                while (parts.hasMoreElements()) {
                    nameBuilder.append(parts.nextElement());
                    if (parts.hasMoreElements()) {
                        nameBuilder.append(";");
                    }
                }
                return lookup(nameBuilder.toString());
            }

            @Override
            public Object lookup(String name) throws NamingException {
                if (springContext == null) {
                    synchronized (SpringContextFactory.class) {
                        if (springContext == null) {
                            springContext = new FileSystemXmlApplicationContext(environment.get("java.naming.provider.url").toString());
                            beans = new HashMap<>();
                            log.info("Spring context loaded.");
                        }
                    }
                }
                log.info("Looking up bean '" + name + "' in spring context.");
                Object bean = springContext.getBean(name);
                if (!beans.containsKey(name)) {
                    synchronized (beans) {
                        beans.put(name, bean);
                    }
                }
                return bean;
            }

            @Override
            public void bind(Name name, Object obj) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void bind(String name, Object obj) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void rebind(Name name, Object obj) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void rebind(String name, Object obj) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void unbind(Name name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void unbind(String name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void rename(Name oldName, Name newName) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void rename(String oldName, String newName) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void destroySubcontext(Name name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void destroySubcontext(String name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Context createSubcontext(Name name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Context createSubcontext(String name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object lookupLink(Name name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object lookupLink(String name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public NameParser getNameParser(Name name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public NameParser getNameParser(String name) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Name composeName(Name name, Name prefix) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String composeName(String name, String prefix) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object addToEnvironment(String propName, Object propVal) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object removeFromEnvironment(String propName) throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Hashtable<?, ?> getEnvironment() throws NamingException {
                return System.getProperties();
            }

            @Override
            public synchronized void close() throws NamingException {

                log.info("Closing context...");

                if (springContext == null) {
                    log.warn("Nothing to close, the spring context is null which "
                            + "is a little bit odd. Check the log to see if it was "
                            + "closed previously.");
                    return;
                }

                String[] fmbbNames = springContext.getBeanNamesForType(FinishMethodsByBean.class);

                for (String fmbbName : fmbbNames) {
                    FinishMethodsByBean fmbb = (FinishMethodsByBean) springContext.getBean(fmbbName, FinishMethodsByBean.class);
                    for (String name: fmbb.getBeanNames()) {
                        if (beans.containsKey(name)) {
                            FinishMethodsByClass fmbc = fmbb.getFinishMethodsbyClass();
                            for (String m : fmbc.getMethods()) {
                                try {
                                    fmbc.getClazz().getMethod(m, null).invoke(beans.get(name), null);
                                    log.info("Invoked method '" + m + "' in bean '" + fmbbName + "'");
                                } catch (Exception ex) {
                                    log.error("Error invoking method '" + m + "'", ex);
                                }
                            }
                        }
                    }
                }

                springContext.close();
                springContext = null;

                log.info("...context closed.");
            }

            @Override
            public String getNameInNamespace() throws NamingException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        };
    }
}
