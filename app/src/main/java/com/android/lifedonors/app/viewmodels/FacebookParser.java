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

    static final String PAGE_URL = "https://graph.facebook.com/115307748252387/feed?limit=100&access_token=EAAJBMy48za0BAKPTQjoXOK4RwNflbYGYBVdKMAzdansbjHEpSw11h1pFQE2Lul2GBcT7XDAGeRXCXH0TkNeYy48W1BNSBGNB3pjD73QuNSvFzx9M0UDCymacznGbMHskWj70PpE9zenbOEeA75JM9mH195OUsVEpa6PjASG3FmyCmaaQ8KFOTZB8fvahNSPYZAzqhbaMVM7MDTnHdg";

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
