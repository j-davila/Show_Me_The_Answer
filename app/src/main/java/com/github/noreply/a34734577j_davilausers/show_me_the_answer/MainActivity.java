package com.github.noreply.a34734577j_davilausers.show_me_the_answer;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public boolean isShowingAnswers = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.answer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor
                        (R.color.my_red_color, null));
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor
                        (R.color.my_red_color, null));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor
                        (R.color.colorPrimary));
            }
        });

        findViewById(R.id.answer2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor
                        (R.color.my_red_color, null));
                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor
                        (R.color.my_red_color, null));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor
                        (R.color.colorPrimary));
            }
        });

        findViewById(R.id.answer3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor
                        (R.color.colorPrimary));
            }
        });
        findViewById(R.id.visible).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (isShowingAnswers) {
                    findViewById(R.id.answer1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer3).setVisibility(View.INVISIBLE);
                    ((ImageView) findViewById(R.id.visible)).setImageResource(
                            R.drawable.eye_invisible);
                    isShowingAnswers = false;
                }

                else {
                    findViewById(R.id.answer1).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer2).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer3).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.visible)).setImageResource(
                            R.drawable.visible_eye);
                    isShowingAnswers = true;
                }
            }
        });
    }

}