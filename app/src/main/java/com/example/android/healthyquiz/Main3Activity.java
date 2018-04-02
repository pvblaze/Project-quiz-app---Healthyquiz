package com.example.android.healthyquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        // Retrieve score from second activitiy
        Intent intent = getIntent();
        String score = intent.getStringExtra("theScore");

        // Display score
        TextView scoreField = findViewById(R.id.score_field);
        scoreField.setText(score + "/100");
    }

    public void restartQuiz(View view) {

        // Restart the second activity where the quiz is.
        Intent intent = new Intent(this, com.example.android.healthyquiz.Main2Activity.class);
        startActivity(intent);
    }
}
