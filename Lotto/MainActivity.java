package com.myproject.lotto;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    static int[] numbers = new int[45];
    static List<Integer> randNumbers = new ArrayList<>();
    static List<MyNumber> myNumbers = new ArrayList<>();

    TextView textView;
    int number = 939;
    String url = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textString);

        for (int i = 0; i < 10; i++)
            run();
    }

    void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + number--).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String strResponse = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject parser = new JSONObject(strResponse);
                            String num = parser.get("drwNo").toString();
                            String num1 = parser.get("drwtNo1").toString();
                            String num2 = parser.get("drwtNo2").toString();
                            String num3 = parser.get("drwtNo3").toString();
                            String num4 = parser.get("drwtNo4").toString();
                            String num5 = parser.get("drwtNo5").toString();
                            String num6 = parser.get("drwtNo6").toString();

                            numbers[Integer.valueOf(num1) - 1]++;
                            numbers[Integer.valueOf(num2) - 1]++;
                            numbers[Integer.valueOf(num3) - 1]++;
                            numbers[Integer.valueOf(num4) - 1]++;
                            numbers[Integer.valueOf(num5) - 1]++;
                            numbers[Integer.valueOf(num6) - 1]++;

                            String prevString = textView.getText().toString();
                            String text = num + " = " + num1 + ", " + num2 + ", " + num3 + ", " +
                                    num4 + ", " + num5 + ", " + num6 + "\n";
                            prevString += text;
                            textView.setText(prevString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    public static class MyNumber {
        public int number;
        public int count;

        public MyNumber(int number, int count){
            this.number = number;
            this.count = count;
        }
        public int getCount(){
            return this.count;
        }
    }

    public static String randSortNumber(){
        List<Integer> sortNumbers = new ArrayList<>();
        for(int i = 0; i < 6; i++)
            sortNumbers.add(randNumbers.get(i));
        Collections.sort(sortNumbers);
        String randString = "";
        for(int i = 0; i < sortNumbers.size(); i++){
            int number = sortNumbers.get(i);
            if(i == sortNumbers.size() - 1)
                randString += number;
            else
                randString += number + ", ";
        }
        return randString;
    }

    public List<MyNumber> getSortMyNumber(int start, int end){
        List<MyNumber> sortNumbers = new ArrayList<>();

        for(int i = start ; i >= end ; i--)
            sortNumbers.add(myNumbers.get(i));

        return sortNumbers;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<MyNumber> sortNumber() {
        myNumbers = new ArrayList<>();
        for (int i = 0; i < 45; i++) {
            MyNumber myNumber = new MyNumber((i + 1), numbers[i]);
            myNumbers.add(myNumber);
        }
        myNumbers.sort(Comparator.comparing(MyNumber::getCount));
        return myNumbers;
    }

    public static List<Integer> init(){
        List<Integer> sortNumbers = new ArrayList<>();
        List<Integer> sortNumbers2 = new ArrayList<>();

        for (MyNumber myNumber : myNumbers) {
            if (myNumber.count == 0) {
                sortNumbers.add(myNumber.number);
            }
        }
        Collections.shuffle(sortNumbers);
        for (int i = 0; i < 6; i++)
            sortNumbers2.add(sortNumbers.get(i));

        Collections.sort(sortNumbers2);

        return sortNumbers2;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void button_click(View view) {
        switch (view.getId()){
            case R.id.button1:
                for(int i = 1; i <= 45; i++)
                    randNumbers.add(i);
                randNumbers = new ArrayList<>(new HashSet<>(randNumbers));
                Collections.shuffle(randNumbers);
                textView.setText(randSortNumber());
                break;
            case R.id.button2: {
                textView.setText("");
                sortNumber();
                String prevText = textView.getText().toString();
                {
                    prevText += "===가장 많이 나온 6개===\n";
                    List<MyNumber> sortMyNumber = getSortMyNumber(myNumbers.size() - 1, myNumbers.size() - 6);
                    for (MyNumber myNumber : sortMyNumber)
                        prevText += (myNumber.number + " = " + myNumber.count + "번\n");
                    textView.setText(prevText);
                }
                {
                    prevText += "===그 다음 많이 나온 6개===\n";
                    List<MyNumber> sortMyNumber = getSortMyNumber(myNumbers.size() - 7, myNumbers.size() - 12);
                    for (MyNumber myNumber : sortMyNumber)
                        prevText += (myNumber.number + " = " + myNumber.count + "번\n");
                    textView.setText(prevText);
                }
            }
                break;
            case R.id.button3: {
                textView.setText("");
                sortNumber();
                String prevText = textView.getText().toString();
                {
                    prevText += "===한번도 나오지 않은 숫자들===\n";
                    for (MyNumber myNumber : myNumbers) {
                        if (myNumber.count == 0)
                            prevText += (myNumber.number + " = " + myNumber.count + "번\n");
                    }
                    textView.setText(prevText);
                }
                {
                    prevText += "===한번도 나오지 않은 수로 랜덤 6개===\n";
                    prevText += (init() + "\n");
                    textView.setText(prevText);
                }
            }
                break;
        }


        //1  랜덤으로 6자리 숫자 하나를 만들어서 출력
        //2  10주동안 많이나온 숫자를 정렬해서 가장 많이 나온숫자 6개를 출력
        //3  그다음 6기 이후로 많이나온 숫자 6개를 출력
        //4  한번도 나오지 않은 숫자를 갖고 랜덤으로 6개 출력
        //5  한번도 나오지 않은 숫자를 갖고 랜덤으로 6개 출력
    }
}