package com.techpakka.eerattupetta;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alexfu.countdownview.CountDownListener;
import com.alexfu.countdownview.CountDownView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.techpakka.eerattupetta.Model.Data;

import java.util.ArrayList;
import java.util.Timer;

public class QuizActivity extends AppCompatActivity{

    private TextView time,txt_listsize,txt_index;
    private RelativeLayout option1,option2,option3,option4;
    private TextView txt_opt1,txt_opt2,txt_opt3,txt_opt4,txt_question;
    private String answer;
    ArrayList<Data> quiz = new ArrayList<>();
    int index = 0;
    String position;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CountDownTimer countDownTimer;
    private TextView current_question;
    private TextView total_question;
    private int point = 0;
    private TextView txt_score;
    private ProgressBar progressBar5;
    private ConstraintLayout main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initContents();
        answer = "jobin";
        onOptionsClicked();
        getQuizData();
        setTimerNotDanger();

     }

    private void setTimerNotDanger() {
        time.setTextColor(getResources().getColor(R.color.notdanger));
    }

    private void getQuizData() {
        db.collection("quiz").orderBy("id", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        progressBar5.setVisibility(View.GONE);
                        main.setVisibility(View.VISIBLE);
                        for (DocumentSnapshot document : task.getResult()) {
                            Data data = new Data();
                            data.setOption1(document.getString("option1"));
                            data.setOption2(document.getString("option2"));
                            data.setOption3(document.getString("option3"));
                            data.setOption4(document.getString("option4"));
                            data.setPosition(document.getString("position"));
                            data.setQuestion(document.getString("question"));
                            data.setAnswer(document.getString("answer"));
                            quiz.add(data);
                        }
                        Data data = quiz.get(index);
                        txt_opt1.setText(data.getOption1());
                        txt_opt2.setText(data.getOption2());
                        txt_opt3.setText(data.getOption3());
                        txt_opt4.setText(data.getOption4());
                        txt_question.setText(data.getQuestion());
                        position = data.getPosition();
                        answer = data.getAnswer();
                        index++;
                        resetOptLayout();
                        countDownTimer = new CountDownTimer(20000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                time.setText(String.valueOf(millisUntilFinished / 1000));
                                if (millisUntilFinished / 1000 < 10){
                                    time.setTextColor(getResources().getColor(R.color.danger));
                                }
                            }

                            public void onFinish() {
                                if (index < quiz.size()-1) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            changeQuestions();
                                        }
                                    }, 1000);
                                }else {
                                    finish();
                                }

                            }
                        }.start();
                        current_question.setText(String.valueOf(index));
                        if (quiz.size() > 0) total_question.setText(String.valueOf(quiz.size()));
                    } else {
                        Log.d("data", "Error getting documents.", task.getException());
                    }
                });
//        Data data2 = new Data();
//        data2.setOption1("jobin2");
//        data2.setQuestion("Why did the government decide to abolish the office of Ombudsman?");
//        data2.setOption2("jithin2");
//        data2.setOption3("neena2");
//        data2.setOption4("mummy2");
//        data2.setAnswer("mummy2");
//        data2.setPosition("4");
//        quiz.add(data2);
//        Data data3 = new Data();
//        data3.setOption1("jobin3");
//        data3.setQuestion("Why did the government decide to abolish the office of Ombudsman?");
//        data3.setOption2("jithin3");
//        data3.setOption3("neena3");
//        data3.setOption4("mummy3");
//        data3.setAnswer("neena3");
//        data3.setPosition("3");
//        quiz.add(data3);
//        Data data4 = new Data();
//        data4.setOption1("jobin3");
//        data4.setQuestion("Why did the government decide to abolish the office of Ombudsman?");
//        data4.setOption2("jithin3");
//        data4.setOption3("neena3");
//        data4.setOption4("mummy3");
//        data4.setAnswer("neena3");
//        data4.setPosition("3");
//        quiz.add(data4);
//        Data data5 = new Data();
//        data5.setOption1("jobin3");
//        data5.setQuestion("Why did the government decide to abolish the office of Ombudsman?");
//        data5.setOption2("jithin3");
//        data5.setOption3("neena3");
//        data5.setOption4("mummy3");
//        data5.setAnswer("neena3");
//        data5.setPosition("3");
//        quiz.add(data5);
//        Data data6 = new Data();
//        data6.setOption1("jobin3");
//        data6.setQuestion("Why did the government decide to abolish the office of Ombudsman?");
//        data6.setOption2("jithin3");
//        data6.setOption3("neena3");
//        data6.setOption4("mummy3");
//        data6.setAnswer("neena3");
//        data6.setPosition("3");
//        quiz.add(data6);
//        Data data7 = new Data();
//        data7.setOption1("jobin3");
//        data7.setQuestion("Why did the government decide to abolish the office of Ombudsman?");
//        data7.setOption2("jithin3");
//        data7.setOption3("neena3");
//        data7.setOption4("mummy3");
//        data7.setAnswer("mummy3");
//        data7.setPosition("4");
//        quiz.add(data7);

    }

    private void changeQuestions() {
        txt_score.setText("Score : " + point);
        current_question.setText(String.valueOf(index + 1));
        setTimerNotDanger();
        txt_index.setText(String.valueOf(index));
        txt_listsize.setText(String.valueOf(quiz.size()));
        if (index < quiz.size()){
            Data data = quiz.get(index);
            txt_opt1.setText(data.getOption1());
            txt_opt2.setText(data.getOption2());
            txt_opt3.setText(data.getOption3());
            txt_opt4.setText(data.getOption4());
            txt_question.setText(data.getQuestion());
            position = data.getPosition();
            answer = data.getAnswer();
            index++;
            resetOptLayout();
            countDownTimer = new CountDownTimer(20000, 1000) {

                public void onTick(long millisUntilFinished) {
                    time.setText(String.valueOf(millisUntilFinished / 1000));
                    if (millisUntilFinished / 1000 < 10){
                        time.setTextColor(getResources().getColor(R.color.danger));
                    }
                }

                public void onFinish() {
                    if (index < quiz.size()-1) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                changeQuestions();
                            }
                        }, 1000);
                    }else {
                        Intent intent = new Intent(QuizActivity.this,RewardActivity.class);
                        intent.putExtra("points",point);
                        intent.putExtra("total",quiz.size());
                        startActivity(intent);
                        finish();
                    }

                }
            }.start();
        }

    }

    private void resetOptLayout() {
        option4.setBackground(getResources().getDrawable(R.drawable.unselected));
        option3.setBackground(getResources().getDrawable(R.drawable.unselected));
        option2.setBackground(getResources().getDrawable(R.drawable.unselected));
        option1.setBackground(getResources().getDrawable(R.drawable.unselected));
    }

    private void onOptionsClicked() {
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkifCorrect(option1,txt_opt1.getText().toString());
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkifCorrect(option2,txt_opt2.getText().toString());
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkifCorrect(option3,txt_opt3.getText().toString());
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkifCorrect(option4,txt_opt4.getText().toString());
            }
        });
    }

    private void initContents() {
        progressBar5 = findViewById(R.id.progressBar5);
        main = findViewById(R.id.main);
        current_question = findViewById(R.id.current_question);
        total_question = findViewById(R.id.total_question);
        txt_score = findViewById(R.id.txt_score);
        txt_listsize = findViewById(R.id.txt_listsize);
        txt_index = findViewById(R.id.txt_index);
        time = findViewById(R.id.time);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        txt_opt1 = findViewById(R.id.txt_opt1);
        txt_opt2 = findViewById(R.id.txt_opt2);
        txt_opt3 = findViewById(R.id.txt_opt3);
        txt_opt4 = findViewById(R.id.txt_opt4);
        txt_question = findViewById(R.id.txt_question);
    }

    private void checkifCorrect(RelativeLayout layout, String selected) {
        if (selected.equals(answer)){
            point++;
            layout.setBackground(getResources().getDrawable(R.drawable.selected));
            countDownTimer.cancel();
            if (index < quiz.size()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeQuestions();
                    }
                }, 500);

            }else {

                Intent intent = new Intent(QuizActivity.this,RewardActivity.class);
                intent.putExtra("points",point);
                intent.putExtra("total",quiz.size());
                startActivity(intent);
                finish();
            }
        }else {

            switch (position){
                case "1":
                    option1.setBackground(getResources().getDrawable(R.drawable.selected));
                    countDownTimer.cancel();
                    if (index < quiz.size()) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                changeQuestions();
                            }
                        }, 500);

                    }else {
                     Intent intent = new Intent(QuizActivity.this,RewardActivity.class);
                        intent.putExtra("points",point);
                        intent.putExtra("total",quiz.size());
                        startActivity(intent);
                        finish();
                    }
                    break;
                case "2":
                    option2.setBackground(getResources().getDrawable(R.drawable.selected));
                    countDownTimer.cancel();
                    if (index < quiz.size()) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                changeQuestions();
                            }
                        }, 500);

                    }else {
                       Intent intent = new Intent(QuizActivity.this,RewardActivity.class);
                        intent.putExtra("points",point);
                        intent.putExtra("total",quiz.size());
                        startActivity(intent);
                        finish();
                    }
                    break;
                case "3":
                    option3.setBackground(getResources().getDrawable(R.drawable.selected));
                    countDownTimer.cancel();
                    if (index < quiz.size()) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                changeQuestions();
                            }
                        }, 500);

                    }else {
                     Intent intent = new Intent(QuizActivity.this,RewardActivity.class);
                        intent.putExtra("points",point);
                        intent.putExtra("total",quiz.size());
                        startActivity(intent);
                        finish();
                    }
                    break;
                case "4":
                    option4.setBackground(getResources().getDrawable(R.drawable.selected));
                    countDownTimer.cancel();
                    if (index < quiz.size()) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                changeQuestions();
                            }
                        }, 500);

                    }else {
                      Intent intent = new Intent(QuizActivity.this,RewardActivity.class);
                        intent.putExtra("points",point);
                        intent.putExtra("total",quiz.size());
                        startActivity(intent);
                        finish();
                    }
                    break;
            }
            layout.setBackground(getResources().getDrawable(R.drawable.selected_wrong));
        }
    }


}
