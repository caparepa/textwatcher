package com.h2g2.textwatcher;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by C. Serrano (cserrano@teravisiontech.com)
 * Teravision Technologies
 * Date: 2018/02/06
 */
public class PriceInputTextWatcher implements TextWatcher {

    private static String empty = "";
    private int index;
    private int startChanged, beforeChanged, countChanged;
    private boolean deletingDecimalPoint;

    private char localGroupingSeparator;
    private char localDecimalSeparator;

    private EditText et;
    private String current = "";
    private Locale currentLocale;
    private int fractionDigits = 2;
    private NumberFormat formatter;
    private String regex;
    private double v_value = 0;
    private String v_formattedValue;
    private String v_text;
    private DecimalFormat df;
    private StringBuffer fString;
    private String oString, cString;
    private int digits, oSize;
    private Double valor;


    public PriceInputTextWatcher() {
    }

    public PriceInputTextWatcher(EditText price, char decimal, char grouping, Locale locale) {
        this.et = price;
        this.localDecimalSeparator = decimal;
        this.localGroupingSeparator = grouping;
        this.currentLocale = locale;
        this.formatter = NumberFormat.getNumberInstance(currentLocale);
        formatter.setMaximumFractionDigits(2);
        this.regex = "[" + this.localDecimalSeparator + this.localGroupingSeparator + "]";
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Set position variables for cursor
        startChanged = start;
        beforeChanged = before;
        countChanged = count;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //do nothing
    }

    @Override
    public synchronized void afterTextChanged(Editable s) {

        if (!s.toString().equals(current)) {
            et.removeTextChangedListener(this);

            v_text = s.toString().replaceAll(regex, "");
            v_value = 0;

            if (v_text != null && v_text.length() > 0)
                v_value = Double.parseDouble(v_text);

            v_formattedValue = formatter.format((v_value / 100));

            //Make a stringbuilder with the resulting format
            StringBuilder num = new StringBuilder(v_formattedValue);

            //TODO: check border cases here!
            //this is only to get the zero value as 0.00!
            if (num.toString().equals("0")) {
                while (num.length() < 3)
                    num.insert(0, '0');
                num.insert(num.length() - 2, localDecimalSeparator);
            }

            //assign values
            v_formattedValue = num.toString();
            current = v_formattedValue;

            //set text
            et.setText(v_formattedValue);

            //set selection index...
            int selectionIndex = index > v_formattedValue.length() ?
                    v_formattedValue.length() : v_formattedValue.length() - index;
            et.setSelection(selectionIndex);

            // set cursor to the end after text is formatted
            et.setSelection(startChanged + countChanged);
            et.addTextChangedListener(this);
        }
    }
}
