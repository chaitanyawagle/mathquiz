package com.chaitanya.mathquiz;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {

    String quizTypes[] = {"Addition","Subtraction","Multiplication"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_menu);
        setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_expandable_list_item_1 ,quizTypes));


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try {
            System.out.println(position);
            Class quizClass = Class.forName("com.chaitanya.mathquiz.QuizActivity");
            Intent quizIntent = new Intent(Menu.this, quizClass);
            quizIntent.putExtra("operation", position);
            startActivity(quizIntent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        System.out.println("Orientation changes");
    }
}