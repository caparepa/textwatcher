package com.h2g2.textwatcher;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.widget.EditText;
import android.widget.TextView;

import com.h2g2.textwatcher.databinding.ActivityMainBinding;

import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    EditText et;
    Locale currentLocale;
    String languageCode;

    private static final Set<String> troubleLanguages = new HashSet<String>(Arrays.asList(
            new String[]{"fr", "ru", "ar", "uk"}
    ));

    private final String numbers = "1234567890";
    private String acceptedDigits;
    //private char localDecimalSeparator ='.';
    private DecimalFormatSymbols localDecimalFormatSymbols;
    private char localDecimalSeparator;
    private char localGroupingSeparator;
    private int count = 0;

    private TextView inputText, numericText, parsedText, currentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        languageCode = "en";

        //TODO: russian/ukrainian/french grouping is a space, arabic it's too much of a hassle
        LanguageManager.saveLanguage(languageCode, this);

        et = binding.numberField;
        inputText = binding.inputValue;
        numericText = binding.numericValue;
        parsedText = binding.parsedValue;
        currentText = binding.currentValue;

        updateLanguageOptions();

        System.out.println("Current locale for number shit: " + currentLocale);

        //TODO: set input shit
        String inputInit = "0" + localDecimalSeparator + "00";
        Editable priceText = et.getText();
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(16); //Filter to 10 characters
        et.setFilters(filters);
        et.setText(inputInit); //piedra solutions
        et.setSelection(4);

        //set text with fucking format!
        //set selection after the items on start!

        /*final NumberInputTextWatcher nw = new NumberInputTextWatcher(et, localDecimalSeparator, localGroupingSeparator, currentLocale);
        et.addTextChangedListener(new NumberInputTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int after) {
                nw.onTextChanged(s, start, before, after);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nw.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void afterTextChanged(Editable s) {
                nw.afterTextChanged(s);
            }
        });*/
        final PriceInputTextWatcher ctw = new PriceInputTextWatcher(et, localDecimalSeparator, localGroupingSeparator, currentLocale);
        et.addTextChangedListener(new PriceInputTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ctw.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ctw.onTextChanged(s, start, before, count);
            }

            @Override
            public synchronized void afterTextChanged(Editable s) {
                ctw.afterTextChanged(s);
            }
        });
    }

    //TODO: UPDATE TO HANDLE LANGUAGES WITHOUT GROUPING SEPARATOR!
    private void updateLanguageOptions() {
        currentLocale = LanguageManager.getCurrentLocale(this);
        //TODO: add this shit to the formlist adapter
        if (troubleLanguages.contains(currentLocale.getLanguage())) {
            currentLocale = new Locale("es"); //TODO: watch out!
        }
        localDecimalFormatSymbols = new DecimalFormatSymbols(currentLocale);
        localDecimalSeparator = localDecimalFormatSymbols.getDecimalSeparator();
        localGroupingSeparator = localDecimalFormatSymbols.getGroupingSeparator();
        acceptedDigits = numbers + localDecimalSeparator;
    }

    private Locale getCustomLocale(String languageCode) {
        return new Locale("es");
    }

}
