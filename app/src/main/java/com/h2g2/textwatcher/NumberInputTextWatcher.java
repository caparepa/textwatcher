package com.h2g2.textwatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by C. Serrano (cserrano@teravisiontech.com)
 * Teravision Technologies
 * Date: 2018/02/15
 */
public class NumberInputTextWatcher implements TextWatcher {

    private Locale DEFAULT_LOCALE;

    private DecimalFormat NUMBER_FORMAT;

    private int FRACTION_DIGITS = 2;

    private char DECIMAL_SEPARATOR;

    private char GROUPING_SEPARATOR;

    private EditText et;

    private String regex;

    private String current = "";

    public NumberInputTextWatcher() {

    }

    public NumberInputTextWatcher(EditText price, char decimal, char grouping, Locale locale) {
        this.et = price;
        this.DECIMAL_SEPARATOR = decimal;
        this.GROUPING_SEPARATOR = grouping;
        this.DEFAULT_LOCALE = locale;
        this.NUMBER_FORMAT = new DecimalFormat("0" + DECIMAL_SEPARATOR + "00");
        this.regex = "[" + this.DECIMAL_SEPARATOR + this.GROUPING_SEPARATOR + "]";
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        //do nothing?
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //do shit here?
    }

    @Override
    public void afterTextChanged(Editable s) {
        //do shit here?
    }
}
