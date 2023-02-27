package com.example.myapplication;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private double sum = 0;
    private ArrayList<Post> posts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] c = { "UAH - Українська гривня", "USD - Долар США", "EUR - Євро",
                "GBP - Фунт", "PLN - Польський злотий"};
        Spinner currencySp1 = findViewById(R.id.currency1);
        Spinner currencySp2 = findViewById(R.id.currency2);
        EditText sumET = findViewById(R.id.sum);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, c);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySp1.setAdapter(adapter);
        currencySp2.setAdapter(adapter);


        NetworkService.getInstance()
                .getJSONApi()
                .getPostWithID()
                .enqueue(new Callback<ArrayList<Post>>() {
                    @Override
                    public void onResponse(@NonNull Call<ArrayList<Post>> call, @NonNull Response<ArrayList<Post>> response) {
                        ArrayList<Post> post = response.body();
                        for(Post p : post) {
                            if (p.getCurrencyCodeB() != 980) {
                                continue;
                            }
                            if(p.getCurrencyCodeA() != 840
                                    && p.getCurrencyCodeA() != 978
                                    && p.getCurrencyCodeA() != 826
                                    && p.getCurrencyCodeA() != 985) {
                                continue;
                            }
                            posts.add(p);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ArrayList<Post>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });

        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                System.out.println(sumET.getText().toString());Integer.parseInt(
                sum = Integer.parseInt(sumET.getText().toString());
                mainConvert();

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        currencySp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                mainConvert();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        currencySp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                mainConvert();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                mainConvert();
            }
        });

        sumET.addTextChangedListener(textWatcher);
    }

    public void mainConvert() {
        EditText resultET = findViewById(R.id.result);
        EditText courseET = findViewById(R.id.course);
        Spinner currencySp1 = findViewById(R.id.currency1);
        Spinner currencySp2 = findViewById(R.id.currency2);

        if (currencySp1.getSelectedItem().toString().equals(currencySp2.getSelectedItem().toString())) {
            resultET.setText(String.valueOf(sum));
            return;
        }

        double currencyIn;
        double currencyFrom;
        for(Post p : posts) {

            if (!currencySp2.getSelectedItem().toString().equals(getCurrencyNameByCode(p.getCurrencyCodeA()))) {
                continue;
            }
            currencyIn = getNedCurrency(p);

            if (currencySp1.getSelectedItem().equals("UAH - Українська гривня")) {
                resultET.setText(String.valueOf(convertCurrency(currencyIn)));
                courseET.setText(String.valueOf(currencyIn));
            } else {
                for(Post pFrom : posts) {
                    if (!currencySp1.getSelectedItem().toString().equals(getCurrencyNameByCode(pFrom.getCurrencyCodeA()))) {
                        continue;
                    }
                    currencyFrom = getNedCurrency(pFrom);
                    resultET.setText(String.valueOf(convertCurrency(currencyFrom, currencyIn)));
                    courseET.setText(String.valueOf(currencyFrom / currencyIn));

                }
            }
        }
    }

    public double getNedCurrency (Post p) {
        RadioButton buyRB = findViewById(R.id.buy);
        RadioButton sellRB = findViewById(R.id.sell);
        RadioButton nbuRB = findViewById(R.id.nbu);
        double result;
        if (nbuRB.isChecked()) {
            result = p.getRateCross();
        } else if (buyRB.isChecked()) {
            result = p.getRateBuy();
        } else {
            result = p.getRateSell();
        }
        return result;
    }

    public String getCurrencyNameByCode(int code) {
        String currency;
        switch (code) {
            case 840 :
                currency = "USD - Долар США";
                break;
            case 978 :
                currency = "EUR - Євро";
                break;
            case 826 :
                currency = "GBP - Фунт";
                break;
            case 985 :
                currency = "PLN - Польський злотий";
                break;
            default:
                currency = "Invalid currency";
        }
        return currency;
    }

    public double convertCurrency(double currencyIn) {
        if (currencyIn == 0) return 0;
        double result = sum / currencyIn;
        return result;
    }

    public double convertCurrency(double currencyFrom, double currencyIn) {
        if (currencyIn == 0 || currencyFrom == 0) return 0;
        double currencyDifference = currencyFrom / currencyIn;
        double result = sum * currencyDifference;
        return result;
    }
}