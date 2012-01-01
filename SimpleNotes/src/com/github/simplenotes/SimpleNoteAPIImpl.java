package com.github.simplenotes;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import com.google.gson.Gson;
import android.util.Base64;

public class SimpleNoteAPIImpl implements SimpleNoteAPI {
    public static final String BASE_URL = "https://simple-note.appspot.com/api2";

    public static final String LOGIN_URL = "https://simple-note.appspot.com/api/login";
    public static final String DATA_PATH = "/data";
    public static final String INDEX_PATH = "/index";

    public static Logger Log = Logger.getLogger(SimpleNoteAPIImpl.class.getName());
    private String email;
    private String password;

    private String authToken;
    private Gson gson;
    public String getAuthToken() {
        return this.authToken;
    }
    public SimpleNoteAPIImpl() {
        this.email = null;
        this.password = null;
        this.gson = new Gson();
     }
    
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    
    private void writePostData(URLConnection connection, String data,
            boolean encode) throws IOException {
        connection.setDoOutput(true);
        OutputStream out = null;
        try {
            out = connection.getOutputStream();
            if(encode) {
                data = Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
            }
            out.write(data.getBytes());
            out.flush();
        } finally {
            if (out != null) try { out.close(); } catch (IOException logOrIgnore) {}
        }
    }
    private BufferedReader connect(String url, String body, boolean encode) throws IOException{
        BufferedReader buffer = null;
        try {
            URL u = new URL(url);
            URLConnection ucon = u.openConnection();
            if (body != null) {
                writePostData(ucon, body, encode);
            }
            buffer = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
        } catch(MalformedURLException me) {
            Log.info("Generated malformed url " + url);
            // TODO: Should provide some kind of error message for the user
            // this would also mean something is really broken and should get 
            // logged to an online server so that can fix later on.
        }

        return buffer;
    }

    private String requestURL(String url, String body, boolean encode) throws IOException {
        System.out.println("Body is: " + body);
        BufferedReader buff = connect(url, body, encode);
        ByteArrayOutputStream baf = new ByteArrayOutputStream(50);
        int current = 0;
        while((current = buff.read()) != -1){
            baf.write((byte)current);
        }
        String response = new String(baf.toByteArray()); 
        System.out.println("Repsonse is: " + response);
        return response;
    }


    @Override
    public void login() throws IOException {
        String url = LOGIN_URL; 
        String body="email=" + this.email + "&password=" + this.password;

        this.authToken = requestURL(url, body, true);
    }
    
    @Override
    public void setUserName(String username) {
        this.email = username;
    }
    
    @Override
    public String getUserName() {
        return email;
    }
    
    @Override
    public Note add(Note note) throws IOException {
        String url = BASE_URL + DATA_PATH + "?auth=" + this.authToken + "&email=" + this.email;
        String body = note.toJSON();
        String newNoteJSON = requestURL(url, body, false);
        return Note.fromJSON(newNoteJSON);
    }

    @Override
    public Note get(String key) throws IOException {
        String url = BASE_URL + DATA_PATH + "/" + key + "?auth=" + this.authToken + "&email=" + this.email;
        String noteJSON = requestURL(url, null, false);
        return Note.fromJSON(noteJSON);
    }

}
