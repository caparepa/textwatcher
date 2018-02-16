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
    NumberFormat formatter;
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

        /*if (!s.toString().equals(current) && !s.toString().equals(empty)) {
            et.removeTextChangedListener(this);

            String cleanString = s.toString();

            if (count != 0 && count > 2) {
                String substr = cleanString.substring(cleanString.length() - 2);

                if (substr.contains(Character.toString(localDecimalSeparator)) ||
                        substr.contains(Character.toString(localGroupingSeparator))) {
                    cleanString += "0";
                }
            }

            cleanString = cleanString.replaceAll(regex, "");

            Double parsed = new Double(cleanString);
            Double value = parsed / 100;
            String formatted = formatter.format(value);
            current = formatted;

            et.setText(formatted);
            et.setSelection(formatted.length());
            et.addTextChangedListener(this);
        }*/
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        index = after > 0 ? s.length() - start : s.length() - start - 1;
        deletingDecimalPoint = count > 0 && s.charAt(start) == localDecimalSeparator;
    }

    @Override
    public synchronized void afterTextChanged(Editable s) {
        /*if (!s.toString().equals(current)) {
            et.removeTextChangedListener(this);
            Log.d("SHIT", "current " + s.toString());

            oString = s.toString().replaceAll(regex, "");
            oSize = oString.length();

            //acomodar esta shit
            valor = Double.valueOf(oString);
            valor = valor / 100;

            fString = new StringBuffer(String.valueOf(valor.toString()));

            //usar esta mierda de nuevo
            digits = oSize - (fString.length() - 1);
            if (digits > 0) {
                for (int i = 0; i < digits; i++) {
                    fString.append("0");
                }
            }

            if (fString.toString().equals("0")) {
                while (fString.length() < 3) {
                    fString.insert(0, '0');
                }
                fString.insert(fString.length() - 2, localDecimalSeparator);
            }
            Log.d("SHIT2", fString.toString());

            v_formattedValue = formatter.format(Double.valueOf(fString.toString()));
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
        }*/

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
