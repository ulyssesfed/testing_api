package com.company;

import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        HashMap<Integer, String> currencyCodes = new HashMap<Integer,String>();
        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "EUR");
        currencyCodes.put(3, "GBP");
        currencyCodes.put(4, "JPY");
        currencyCodes.put(5, "CAD");
        currencyCodes.put(6, "AUD");

        String fromCode, toCode;
        double amount;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the currency to convert: ");
        fromCode = currencyCodes.get(scanner.nextInt());
        System.out.println("Enter the currency to convert to: ");
        toCode = currencyCodes.get(scanner.nextInt());
        System.out.println("Enter the amount to convert: ");
        amount = scanner.nextFloat();

        sendHttpGETRequest(fromCode, toCode, amount);
    }
    public static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {

        String GET_URL = "https://v6.exchangerate-api.com/v6/f723ef02c3296f4fb698bbc7/pair/" + fromCode + "/" + toCode;
        URL url = new URL(GET_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();

        StringBuffer response = null;
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }

        JSONObject jsonObject = new JSONObject(response.toString());
        double rate = jsonObject.getDouble("conversion_rate");
        System.out.println("The conversion rate is: " + rate);


    }
}
