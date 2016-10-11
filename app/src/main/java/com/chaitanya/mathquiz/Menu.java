package com.chaitanya.mathquiz;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends AppCompatActivity {

    String quizTypes[] = {"Addition","Subtraction","Multiplication"};
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_expandable_list_item_1 ,quizTypes));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    System.out.println(position);
                    Class quizClass = Class.forName("com.chaitanya.mathquiz.QuizActivity");
                    Intent quizIntent = new Intent(Menu.this, quizClass);
                    quizIntent.putExtra("operation", position);
                    startActivity(quizIntent);
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        });
    }
}