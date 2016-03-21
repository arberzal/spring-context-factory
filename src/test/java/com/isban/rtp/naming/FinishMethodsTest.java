package com.isban.rtp.naming;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.connection.CachingConnectionFactory;

/**
 *
 * @author arberzal
 */
public class FinishMethodsTest {

    public FinishMethodsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void finishMethods1() throws NamingException {
        InitialContextFactory scf = new SpringContextFactory();
        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.provider.url", "./target/classes/jms-spring-mq.xml");
        Context ctx = scf.getInitialContext(env);
        ctx.lookup("cachingTCF");
        ctx.lookup("cachingQCF");
        ctx.close();
    }
    
}
