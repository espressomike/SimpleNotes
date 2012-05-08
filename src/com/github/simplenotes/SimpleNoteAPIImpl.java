package com.github.simplenotes;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.String;

import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;

public class SimpleNoteAPIImpl implements SimpleNoteAPI {
    public static final String BASE_URL = "https://simple-note.appspot.com/api2";

    public static final String LOGIN_URL = "https://simple-note.appspot.com/api/login";
    public static final String DATA_PATH = "/data";
    public static final String INDEX_PATH = "/index";

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

    private HttpClient createHttpClient()
    {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);

        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        ClientConnectionManager manager = new ThreadSafeClientConnManager(params, registry);

        return new DefaultHttpClient(manager, params);
    }

    
    @Override
    public void login() {
        try {
            String url = LOGIN_URL; 
            String body="email=" + this.email + "&password=" + this.password;

            HttpPost post = new HttpPost(LOGIN_URL);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            String loginBody = Base64.encodeToString(body.getBytes(), Base64.DEFAULT);
            post.setEntity(new StringEntity(loginBody, HTTP.UTF_8));

            HttpClient client = createHttpClient();
            HttpResponse response = client.execute(post);

            BufferedInputStream input = new BufferedInputStream(response.getEntity().getContent());
            int tokenSize = input.available();

            byte tokenBytes[] = new byte[tokenSize];
            input.read(tokenBytes, 0 /* from the beginning */, tokenSize);

            this.authToken = new String(tokenBytes);

        } catch (UnsupportedEncodingException uee) {
            Log.w("SimpleNote API", "UnsupportedEncodingException encountered");
        } catch (IOException ioe) {
            Log.w("SimpleNote API", "IOException encountered");
        }

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
    public Note add(Note note) {
        // String url = BASE_URL + DATA_PATH + "?auth=" + this.authToken + "&email=" + this.email;
        // String body = note.toJSON();
        // String newNoteJSON = requestURL(url, body, false);
        // return Note.fromJSON(newNoteJSON);
        return new Note();
    }

    @Override
    public Note get(String key) {
        // String url = BASE_URL + DATA_PATH + "/" + key + "?auth=" + this.authToken + "&email=" + this.email;
        // String noteJSON = requestURL(url, null, false);
        // return Note.fromJSON(noteJSON);
        return new Note();
    }

}
