package com.gola_jakub.FinanceApi.minmaxaverage;
import com.gola_jakub.FinanceApi.validator.Validator;
import lombok.NonNull;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


@RestController
public class MinMaxAverageController {

    @GetMapping("/minMaxAverage")
    @ResponseBody
    public ResponseEntity getMinMaxAverage(@NonNull @RequestParam("currencyCode") String currencyCode, @NonNull @RequestParam("lastQuotations") int lastQuotations) throws IOException {
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/last/%d/", currencyCode, lastQuotations);
        HttpURLConnection connection = null;
        try {
            URL apiEndpoint = new URL(url);
            connection = (HttpURLConnection) apiEndpoint.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (!Validator.validate(currencyCode, lastQuotations)){
                return ResponseEntity.status(400).body(Validator.getError());
            }

            try (Scanner scanner = new Scanner(connection.getInputStream(), "UTF-8")) {
                String response = scanner.useDelimiter("\\A").next();
                JSONObject json = new JSONObject(response);
                JSONArray rates = json.getJSONArray("rates");
                List<Double> averageRates = new ArrayList<>();
                for (int i = 0; i < rates.length(); i++) {
                    JSONObject rate = rates.getJSONObject(i);
                    averageRates.add((rate.getDouble("bid") + rate.getDouble("ask")) / 2);
                }
                double min = Collections.min(averageRates);
                double max = Collections.max(averageRates);

                return ResponseEntity.status(200).body(String.format("Minimal average exchange rate: %s, and maximum average exchange rate: %s", Double.toString(min), Double.toString(max)));
            } catch (Exception e) {
                return ResponseEntity.status(404).body("No data found for this currency code");
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
