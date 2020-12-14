package com.myproject.onetotwelve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static List<Integer> numbers = new ArrayList<>();
    static List<Integer> clickNumbers = new ArrayList<>();
    static List<Button> buttons = new ArrayList<>();

    public void init(){
        Collections.shuffle(numbers);
        for(int i = 0; i < numbers.size(); i++) {
            buttons.get(i).setText(numbers.get(i).toString());
            buttons.get(i).setTextColor(0xFF000000);
            buttons.get(i).setTextSize(20F);
            buttons.get(i).setVisibility(View.VISIBLE);
        }
        clickNumbers.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0; i < 12; i++)
            numbers.add(i + 1);

        buttons.add(findViewById(R.id.button_1));
        buttons.add(findViewById(R.id.button_2));
        buttons.add(findViewById(R.id.button_3));
        buttons.add(findViewById(R.id.button_4));
        buttons.add(findViewById(R.id.button_5));
        buttons.add(findViewById(R.id.button_6));
        buttons.add(findViewById(R.id.button_7));
        buttons.add(findViewById(R.id.button_8));
        buttons.add(findViewById(R.id.button_9));
        buttons.add(findViewById(R.id.button_10));
        buttons.add(findViewById(R.id.button_11));
        buttons.add(findViewById(R.id.button_12));

        init();
    }

    public void buttonClick(View view) {
        Button button = findViewById(view.getId());
        if(view.getId() == R.id.button_start){
            init();
            return;
        }
        int clickNumber= Integer.valueOf(button.getText().toString());
        if(clickNumbers.size() == clickNumber - 1){
            clickNumbers.add(clickNumber);
            button.setVisibility(View.GONE);
        }
    }
}
