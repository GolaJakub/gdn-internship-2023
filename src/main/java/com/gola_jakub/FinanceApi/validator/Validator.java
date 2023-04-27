package com.gola_jakub.FinanceApi.validator;


import lombok.Getter;

public class Validator {

    @Getter
    private static String error;

    public static boolean validate(String currencyCode, Integer lastQuotations) {


        if("pln".equals(currencyCode)) {
              error = "There is no exchange rate for PLN ";
              return false;
        }

        if (lastQuotations != null && (lastQuotations > 255 || lastQuotations <= 0)) {
            error = "Amount of quotations should be in range 1-255";
            return false;
        }
        return true;
    }
}
