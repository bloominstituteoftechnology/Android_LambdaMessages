package com.thadocizn.lambdamessageboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkAdapter {

    private  static final String USER_ID = "-LSQUhWH-I1jXlH_FRi3";
    public static final String  BASE_URL = "https://lambda-message-board.firebaseio.com/";
    public static final String  END_URL = "https://lambda-message-board.firebaseio.com/";

    public static final String GET    = "GET";
    public static final String POST   = "POST";
    public static final String PUT    = "PUT";
    public static final String DELETE = "DELETE";
    public static final int TIMEOUT   = 3000;

    //only handle get and post request now
    public static String httpRequest(String strUrl, String requestType){
        return httpRequest(strUrl, requestType, "");
    }
    public static String httpRequest(String strUrl, String requestType, String body){

        String result = "";
        InputStream stream = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(strUrl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(TIMEOUT);
            connection.setConnectTimeout(TIMEOUT);
            connection.setRequestMethod(requestType);

            if (requestType.equals(GET)) {
                connection.connect();
            }else if (requestType.equals(POST)){
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes());
                outputStream.close();
            }
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                stream = connection.getInputStream();
                if (stream!=null){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        builder.append(line);
                    }
                    result = builder.toString();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();

        }finally {
            if (connection != null){
                connection.disconnect();
            }

            if (stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
