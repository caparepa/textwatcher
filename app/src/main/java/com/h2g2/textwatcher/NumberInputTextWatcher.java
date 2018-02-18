package com.h2g2.textwatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

    private int startChanged;
    private int beforeChanged;
    private int countChanged;
    private int index;
    private boolean busy = false;

    private NumberFormat formatter;

    public NumberInputTextWatcher() {

    }

    public NumberInputTextWatcher(EditText price, char decimal, char grouping, Locale locale) {
        this.et = price;
        this.DECIMAL_SEPARATOR = decimal;
        this.GROUPING_SEPARATOR = grouping;
        this.DEFAULT_LOCALE = locale;
        this.NUMBER_FORMAT = new DecimalFormat("0" + DECIMAL_SEPARATOR + "00");
        this.regex = "[" + this.DECIMAL_SEPARATOR + this.GROUPING_SEPARATOR + "]";
        this.formatter = NumberFormat.getNumberInstance(DEFAULT_LOCALE);
        formatter.setMaximumFractionDigits(2);
        this.regex = "[" + this.DECIMAL_SEPARATOR + this.GROUPING_SEPARATOR+ "]";
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //
        startChanged = start;
        beforeChanged = before;
        countChanged = count;

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //do shit here?
        if (!s.toString().equals(current)) {
            et.removeTextChangedListener(this);

            String v_text = s.toString().replaceAll(regex, "");
            BigDecimal v_value = new BigDecimal(0);

            //TODO: try with double instead of bigdecimal!
            if (v_text != null && v_text.length() > 0)
                v_value = new BigDecimal(v_text)
                        .setScale(2,BigDecimal.ROUND_FLOOR)
                        .divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);

            String v_formattedValue = NUMBER_FORMAT.format(v_value);

            //Make a stringbuilder with the resulting format
            StringBuilder num = new StringBuilder(v_formattedValue);

            //TODO: check border cases here!
            //this is only to get the zero value as 0.00!
            if (num.toString().equals("0")) {
                while (num.length() < 3)
                    num.insert(0, '0');
                num.insert(num.length() - 2, DECIMAL_SEPARATOR);
            }
            Log.d("STRING_",num.toString());

            /**
             * new code begin
             */
            if (s != null && !busy) {
                busy = true;

                int place = 0;
                StringBuilder str = num;
                //StringBuilder str = new StringBuilder(s);
                //String str = s.toString();
                int decimalPointIndex = str.indexOf(String.valueOf(DECIMAL_SEPARATOR));
                int i;
                if (decimalPointIndex == -1) {
                    i= str.length() - 1;
                } else {
                    i = decimalPointIndex - 1;
                }

                while (i >= 0) {
                    char c = s.charAt(i);
                    if (c == GROUPING_SEPARATOR) {
                        str.delete(i, i + 1);
                    } else {
                        if (place % 3 == 0 && place != 0) {
                            // insert a comma to the left of every 3rd digit (counting from right to
                            // left) unless it's the leftmost digit
                            str.insert(i + 1, String.valueOf(GROUPING_SEPARATOR));
                        }
                        place++;
                    }
                    i--;
                }
                Log.d("STRING_",str.toString());
                num = str;
                busy = false;
            }
            /**
             * new code end
             */

            //assign values
            v_formattedValue = num.toString();
            current = v_formattedValue;

            //set text
            et.setText(v_formattedValue);

            //set selection index...
            /*int selectionIndex = index > v_formattedValue.length() ?
                    v_formattedValue.length() : v_formattedValue.length() - index;*/
            et.setSelection(v_formattedValue.length());

            // set cursor to the end after text is formatted
            et.setSelection(startChanged + countChanged);
            et.addTextChangedListener(this);
        }
    }
}
