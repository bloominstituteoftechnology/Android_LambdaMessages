package com.rybarstudios.messageboard;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkAdapter {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final int CONNECT_TIMEOUT = 3000;
    public static final int READ_TIMEOUT = 3000;

    public static String httpRequest(String stringUrl, String requestType) {
        return httpRequest(stringUrl,requestType,null);
    }

    static String httpRequest(String urlString, String requestType, JSONObject postBody) {
        String result = "";
        InputStream stream = null;
        HttpsURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestMethod(requestType);
            connection.connect();

            if(requestType.equals(GET) || requestType.equals(DELETE)) {
                connection.connect();
            } else if (requestType.equals(POST) || requestType.equals(PUT)) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(postBody.toString().getBytes());
                outputStream.close();
            }

            final int responseCode = connection.getResponseCode();
            if(responseCode == HttpsURLConnection.HTTP_OK) {
                stream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
                result = builder.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }
}
