package com.erui.report.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Demo01 {


    public static void main(String[] args) throws IOException {
        // Open connection
        URL url = new URL("https://www.google.com/");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();



        // Set properties of the connection
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(false);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestMethod("GET");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;
        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        reader.close();
        urlConnection.disconnect();
    }
}
