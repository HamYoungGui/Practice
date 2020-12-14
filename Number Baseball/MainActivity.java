package com.myproject.numberbaseball;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ListView listView;
    List<Integer> randomNumbers = new ArrayList<>();
    List<Integer> selectNumbers = new ArrayList<>();
    boolean isWin = false;

    public void init(){
        selectNumbers.clear();
        Collections.shuffle(randomNumbers);
        for(int i = 0; i < 3; i++)
            selectNumbers.add(randomNumbers.get(i));

        items = new ArrayList<String>();

        adapter =
                new ArrayAdapter<String>(MainActivity.this
                        , android.R.layout.simple_list_item_1, items);

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i = 1; i < 10; i++)
            randomNumbers.add(i);

        init();
    }


    public void buttonClick(View view) {
        if(view.getId() == R.id.start){
            init();
            return;
        }

        EditText editText = (EditText) findViewById(R.id.editText);
        String str = editText.getText().toString();
        if(str.length() != 3)
            return;

        int num1 = Integer.valueOf(str.substring(0 , 1));
        int num2 = Integer.valueOf(str.substring(1 , 2));
        int num3 = Integer.valueOf(str.substring(2 , 3));

        int strike = 0;
        int ball = 0;
        if(selectNumbers.get(0) == num1)
            strike++;
        else if(selectNumbers.get(0) == num2 || selectNumbers.get(0) == num3)
            ball++;

        if(selectNumbers.get(1) == num2)
            strike++;
        else if(selectNumbers.get(1) == num1 || selectNumbers.get(1) == num3)
            ball++;

        if(selectNumbers.get(2) == num3)
            strike++;
        else if(selectNumbers.get(2) == num1 || selectNumbers.get(2) == num2)
            ball++;

        adapter.insert(str + "\t\t" + strike + " strike, " + ball + " ball " , 0);
    }
}