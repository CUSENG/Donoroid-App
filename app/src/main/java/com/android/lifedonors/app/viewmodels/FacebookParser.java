package com.android.lifedonors.app.viewmodels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookParser {

    static final String PAGE_URL = "https://graph.facebook.com/115307748252387/feed?limit=100&access_token=EAAJBMy48za0BAJr01rnVURTO5ZCYOazhWmL5K0HQxLnZC8x0fc45ioCujVXEYHCWbwEKg5XSS14ncSASyBRaGbAXbvEv9UQSq9f6RnNx0ZBYnn0FLuX1qZCySZB4XZCZAa1EUg2Mfo8j7aI8h6gWs1xI7C1DUfdvQRvU0JTbXZAmyIwqoIpsjooKC5xyToidghLKgiVvsesFJ9z4Y6BQ8tcX";

    public List<String> parse() {
        List<String> messages = new ArrayList<>();
        try {
            URL url = new URL(PAGE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray dataArray = jsonResponse.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObj = dataArray.getJSONObject(i);
                    String message = dataObj.getString("message");
                    messages.add(message);
                }

            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
