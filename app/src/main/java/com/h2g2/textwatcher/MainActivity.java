package com.h2g2.textwatcher;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
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

    private static final Set<String> troubleLanguages = new HashSet<>(Arrays.asList(
            new String[]{"fr", "ru", "ar", "uk"}
    ));

    private final String numbers = "1234567890";
    private String acceptedDigits;
    private DecimalFormatSymbols localDecimalFormatSymbols;
    private char localDecimalSeparator;
    private char localGroupingSeparator;
    private int count = 0;
    private int startChanged;
    private int beforeChanged;
    private int countChanged;
    private String current = "";

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

        String inputInit = "0" + localDecimalSeparator + "00";
        Editable priceText = et.getText();
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(16);
        et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et.setFilters(filters);
        et.setText(inputInit);
        et.setSelection(inputInit.length());

        final NumberInputTextWatcher itw = new NumberInputTextWatcher(et, localDecimalSeparator, localGroupingSeparator, currentLocale);
        et.addTextChangedListener(new NumberInputTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int after) {
                itw.onTextChanged(s, start, before, after);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                itw.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void afterTextChanged(Editable s) {
                itw.afterTextChanged(s);
            }
        });
    }

    //TODO: patch up things here...
    private void updateLanguageOptions() {
        currentLocale = LanguageManager.getCurrentLocale(this);
        if (troubleLanguages.contains(currentLocale.getLanguage())) {
            currentLocale = new Locale("es");
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
