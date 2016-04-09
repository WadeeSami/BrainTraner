package com.example.wadee.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static final String DEBUGTAG = "BRAIN_TRAINER_DEBUGGEr";
    Button startBtn;
    Button[] btns;
    Button playAgainBtn;
    TextView sumTextView;
    TextView timeLeftTextView;
    TextView scoreTextView;
    TextView resultTextView;
    int correctAnswer = 0;
    int correcrButton;
    int score = 0;
    int gamesCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        startBtn = (Button) findViewById(R.id.startBtn);
        btns = new Button[4];
        btns[0] = (Button) findViewById(R.id.btn1);
        btns[1] = (Button) findViewById(R.id.btn2);
        btns[2] = (Button) findViewById(R.id.btn3);
        btns[3] = (Button) findViewById(R.id.btn4);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        timeLeftTextView = (TextView) findViewById(R.id.timeTextView);
        playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make it invisible
                startBtn.setVisibility(View.INVISIBLE);
                RelativeLayout layout = (RelativeLayout)findViewById(R.id.wholeLayout);
                layout.setVisibility(RelativeLayout.VISIBLE);
                startNewGame();
            }
        });

        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgainBtn.setVisibility(View.INVISIBLE);
                for (int i = 0; i < 4; i++) {
                    btns[i].setEnabled(false);
                }
                startNewGame();
            }
        });
       // startNewGame();

    }

    public void generateNewQuestion() {
        Random r = new Random();
        int a = r.nextInt(21);
        int b = r.nextInt(21);
        String sum = a + " + " + b;
        correctAnswer = a + b;
        sumTextView.setText(sum);
        Log.i(DEBUGTAG, "The sum is :" + sum);

        //generate random numbers in the buttons and one of them should contain the correct answer
        correcrButton = r.nextInt(3);
        Log.i(DEBUGTAG, "THe correct is " + correcrButton);
        for (int i = 0; i < 4; i++) {
            if (i == correcrButton) {
                btns[i].setText(a + b + "");
            } else {
                int choice = r.nextInt(41);
                while (choice == (a + b)) {
                    choice = r.nextInt(41);
                }
                btns[i].setText(choice + "");
            }

        }



}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkAnswer(View view) {
        if (view.getTag().toString().equals(correcrButton + "")) {
            resultTextView.setText("Correct!");
            score++;
        } else {
            resultTextView.setText("Wrong!");
        }
        gamesCount++;
        generateNewQuestion();
        scoreTextView.setText(score + "/" + gamesCount);
    }

    public void startNewGame() {
        this.score = 0;
        this.gamesCount = 0;
        this.resultTextView.setText("");
        this.timeLeftTextView.setText("30s");
        this.scoreTextView.setText("0/0");
        CountDownTimer countDownTimer = new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                timeLeftTextView.setText(seconds + "s");
                Log.i(DEBUGTAG, seconds + "");
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Your result is : " + score + "/" + gamesCount);
                playAgainBtn.setVisibility(View.VISIBLE);
                //disable btns
                for (int i = 0; i < 4; i++) {
                    btns[i].setEnabled(false);
                }
            }
        }.start();
        generateNewQuestion();
        playAgainBtn.setVisibility(View.INVISIBLE);


    }


}
