package com.github.simplenotes.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;


import junit.framework.Assert;
import junit.framework.TestCase;

import com.github.simplenotes.Note;
import com.github.simplenotes.SimpleNoteAPI;
import com.github.simplenotes.SimpleNoteAPIImpl;

public class SimpleNoteAPIImplTest extends TestCase {
    Logger Log = Logger.getLogger("simplenotelibtest");
    String email;
    String password;
    protected void setUp() throws Exception {
        super.setUp();
        String home = System.getenv().get("HOME");

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(home + "/simplenotestest.properties"));

            email = properties.getProperty("email");
            password = properties.getProperty("password");
        } catch (IOException e) {
            Log.info("Need a $HOME/simplenotestest.properties with email and password to test");
        }

    }

    public void testSimpleNoteAPI() {
        SimpleNoteAPI sn = new SimpleNoteAPIImpl();
        sn.setUserName(email);
        sn.setPassword(password);
        Assert.assertNotNull(sn.getUserName());
    }

    public void testLogin() throws IOException {
        SimpleNoteAPI sn = new SimpleNoteAPIImpl();
        sn.setUserName(email);
        sn.setPassword(password);
        sn.login();
        // Assert.assertNotNull(sn.getAuthToken());
    }

    public void testCreateNote() throws IOException {
        SimpleNoteAPI sn = new SimpleNoteAPIImpl();
        sn.setUserName(email);
        sn.setPassword(password);
        Note newNote = null;
        Note n = new Note();
        n.setContent("This is the minimal content needed");
        sn.login();
        newNote = sn.add(n);
        Assert.assertNotNull(newNote);
        Assert.assertNotNull(newNote.getKey());
        Assert.assertEquals(n.getContent(), newNote.getContent());
    }

    public void testGetNote() throws IOException {
        SimpleNoteAPI sn = new SimpleNoteAPIImpl();
        sn.setUserName(email);
        sn.setPassword(password);
        sn.login();
        Note newNote = null;
        Note n = new Note();
        String content = "This is the minimal content needed";
        n.setContent(content);
        newNote = sn.add(n);
        newNote = sn.get(newNote.getKey());
        Assert.assertEquals(n.getContent(), newNote.getContent());

    }

}
