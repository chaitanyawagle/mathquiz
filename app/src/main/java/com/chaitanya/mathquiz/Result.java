package com.chaitanya.mathquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class Result extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent resultData = getIntent();
        int correctAnswer = resultData.getIntExtra("correctAnswer",0);
        int wrongAnswer = resultData.getIntExtra("wrongAnswer",0);
        TextView result = (TextView) findViewById(R.id.result);
        result.setText("Correct Answer : " + Integer.toString(correctAnswer) + " Wrong Answer : " + Integer.toString(wrongAnswer));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent mainMenu = new Intent(Result.this, Menu.class);
            startActivity(mainMenu);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
