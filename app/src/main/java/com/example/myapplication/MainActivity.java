package com.example.myapplication;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] c = { "UA - Українська гривня", "USD - Долар США", "Євро", "Фунт", "PLN - Польський злотий"};
        Spinner currency1 = findViewById(R.id.currency1);
        Spinner currency2 = findViewById(R.id.currency2);
        EditText sumET = findViewById(R.id.sum);
        EditText resultET = findViewById(R.id.result);
        EditText courseET = findViewById(R.id.course);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton buyRB = findViewById(R.id.buy);
        RadioButton sellRB = findViewById(R.id.sell);
        RadioButton nbuRB = findViewById(R.id.nbu);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, c);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency1.setAdapter(adapter);
        currency2.setAdapter(adapter);

        NetworkService.getInstance()
                .getJSONApi()
                .getPostWithID()
                .enqueue(new Callback<ArrayList<Post>>() {
                    @Override
                    public void onResponse(@NonNull Call<ArrayList<Post>> call, @NonNull Response<ArrayList<Post>> response) {
                        ArrayList<Post> post = response.body();
                        for(Post p : post) {
                            System.out.println(p.getCurrencyCodeA());
                        }
//                        textView.append(post.getId() + "\n");
//                        textView.append(post.getUserId() + "\n");
//                        textView.append(post.getTitle() + "\n");
//                        textView.append(post.getBody() + "\n");
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArrayList<Post>> call, @NonNull Throwable t) {

//                        textView.append("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
    }
}