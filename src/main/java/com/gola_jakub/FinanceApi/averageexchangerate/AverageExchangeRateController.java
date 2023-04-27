package com.gola_jakub.FinanceApi.averageexchangerate;

import com.gola_jakub.FinanceApi.validator.Validator;
import lombok.NonNull;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


@RestController
public class AverageExchangeRateController {

    @GetMapping("/average")
    @ResponseBody
    public ResponseEntity getAverage(@NonNull @RequestParam("currencyCode") String currencyCode, @NonNull @RequestParam("date") String date) throws IOException {
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/%s?format=json", currencyCode, date);
        HttpURLConnection connection = null;
        try {
            URL apiEndpoint = new URL(url);
            connection = (HttpURLConnection) apiEndpoint.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (!Validator.validate(currencyCode, null)){
                return ResponseEntity.status(400).body(Validator.getError());
            }

            try (Scanner scanner = new Scanner(connection.getInputStream(), "UTF-8")) {
                String response = scanner.useDelimiter("\\A").next();
                JSONObject json = new JSONObject(response);
                JSONArray rates = json.getJSONArray("rates");
                JSONObject rate = rates.getJSONObject(0);
                double average = rate.getDouble("mid");
                return ResponseEntity.status(200).body(String.format("Average exchange rate: %s", average));
            } catch (Exception e) {
                return ResponseEntity.status(404).body("No data found for this currency code and date");
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
