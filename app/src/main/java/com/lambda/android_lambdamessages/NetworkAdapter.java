package com.lambda.android_lambdamessages;

import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.OutputStream;

import java.net.HttpURLConnection;

import java.net.MalformedURLException;

import java.net.URL;

import java.util.Map;



public class NetworkAdapter {



    public static final String GET     = "GET";

    public static final String POST    = "POST";

    public static final String HEAD    = "HEAD";

    public static final String OPTIONS = "OPTIONS";

    public static final String PUT     = "PUT";

    public static final String DELETE  = "DELETE";

    public static final String TRACE = "TRACE";



    public static final int READ_TIMEOUT = 3000;

    public static final int CONNECT_TIMEOUT = 3000;



    static String httpRequest(String urlString){

        return httpRequest(urlString, GET, null, null);

    }



    static String httpRequest(String urlString, String requestMethod){

        return httpRequest(urlString, requestMethod, null, null);

    }



    static String httpRequest(String urlString, String requestMethod, JSONObject requestBody, Map<String, String> headerProperties){

        String result = "";

        InputStream inputStream = null;

        HttpURLConnection connection = null;



        try {

            URL url = new URL(urlString);
          //  5.  Next, you'll need to add a check for the request type. Look for the following block in your method

            connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(READ_TIMEOUT);

            connection.setConnectTimeout(CONNECT_TIMEOUT);



            if(headerProperties != null){

                for(Map.Entry<String, String> entry : headerProperties.entrySet()){

                    connection.setRequestProperty(entry.getKey(), entry.getValue());

                }

            }else{
                return "";
            }


            //7. Next, we will wrap the connect line with an if statement. If the request type is "GET", we'll perform the connect as usual. If not, we'll perform the POST request

            if((requestMethod.equals(POST) || requestMethod.equals(PUT)) && requestBody != null){

                connection.setDoInput(true);
//8.  If the type is "POST" the connection procedure is a bit different. First you'll have to get an OutputStream object by calling getOutputStream() on your connection object

                OutputStream outputStream = connection.getOutputStream();
                //9. Once you have your output stream, you'll need to write the body of your post request to it. We do that by calling its write method. However, unlike with a PrintStream, we must write our data as a byte array. We can get a byte array from the JSONObject by first converting it to a string with toString and then convert the result to bytes with getBytes. Pass those bytes to the outputStream.write method

                outputStream.write(requestBody.toString().getBytes());
                // 10.  Finally, close the OutputStream with the close method


                outputStream.close();

            }else{

                connection.connect();

            }



            connection.connect();



            int responseCode = connection.getResponseCode();



            if(responseCode == HttpURLConnection.HTTP_OK){

                inputStream = connection.getInputStream();

                if(inputStream != null){

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder stringBuilder = new StringBuilder();



                    String line = bufferedReader.readLine();

                    while(line != null){

                        stringBuilder.append(line);

                        line = bufferedReader.readLine();

                    }



                    result = stringBuilder.toString();

                }

            } else{

                throw new IOException();

            }

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if(inputStream != null){

                try {



                    inputStream.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }



            if(connection != null){

                connection.disconnect();

            }

        }

        return result;

    }

}