package com.nixsolutions.litvinov.vitaliy.security;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import org.springframework.stereotype.Component;

@Component
public class CaptchaUtils {

    private static final String SECRET_KEY =
            "6LegeagUAAAAAMn3vTGGfE39fBY4d98dNsiX872C";
    private static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verify(String googleRecaptchaResponse) {
        if (googleRecaptchaResponse == null
                || googleRecaptchaResponse.length() == 0) {
            return false;
        }

        try {
            URL verifyUrl = new URL(SITE_VERIFY_URL);

            HttpsURLConnection connection = (HttpsURLConnection) verifyUrl
                    .openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String postParams = "secret=" + SECRET_KEY + "&response="
                    + googleRecaptchaResponse;

            connection.setDoOutput(true);

            OutputStream outStream = connection.getOutputStream();
            outStream.write(postParams.getBytes());

            outStream.flush();
            outStream.close();

            InputStream is = connection.getInputStream();

            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            return jsonObject.getBoolean("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
