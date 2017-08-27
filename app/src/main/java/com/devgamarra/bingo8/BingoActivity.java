package com.devgamarra.bingo8;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class BingoActivity extends AppCompatActivity {
    private int numbers[][] = {
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15, 16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25, 26, 27, 28, 29, 30},
            {31, 32, 33, 34, 35, 36, 37, 38, 39, 40},
            {41, 42, 43, 44, 45, 46, 47, 48, 49, 50},
            {51, 52, 53, 54, 55, 56, 57, 58, 59, 60},
            {61, 62, 63, 64, 65, 66, 67, 68, 69, 70},
            {71, 72, 73, 74, 75, 76, 77, 78, 79, 80},
            {81, 82, 83, 84, 85, 86, 87, 88, 89, 90}
    };

    private ArrayList<Integer> mynum = new ArrayList<>();
    private TextToSpeech tts;
    private TextView tvNumber;

    private int PLAY_SPEED = 5000;
    private Handler h;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bingo);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(new Locale("es", "ES"));
                }
            }
        });
        init();
    }

    @Override
    protected void onDestroy() {
        if(isRunning) {
            isRunning = false;
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bingo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_auto_play) {
            if(!isRunning) {
                isRunning = true;
                h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isRunning) {
                            play();
                            h.postDelayed(this, PLAY_SPEED);
                        }
                    }
                }, PLAY_SPEED);
            }
            return true;
        } else if(id == R.id.action_stop_play) {
            if(isRunning) {
                isRunning = false;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void play() {
        if(mynum.size() > 0) {
            Random r = new Random();
            int n = r.nextInt(mynum.size());
            int num = mynum.get(n);
            tts.speak(getResources().getString(R.string.play_audio)+num, TextToSpeech.QUEUE_FLUSH, null);
            mynum.remove(n);
            tvNumber.setText(num+"");
            TextView tv = (TextView) findViewById(8000+num);
            tv.setBackgroundResource(R.drawable.miniball);
        }
    }

    public void drawNumber(View view) {
        if(!isRunning) {
            play();
        }
    }

    public void init() {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.lyNumber);
        for(int i = 0; i < numbers.length; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
           linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j <= numbers.length; j++) {
                mynum.add(numbers[i][j]);
                TextView textView = new TextView(this);
                textView.setId(8000+numbers[i][j]);
                textView.setText(numbers[i][j]+"");
                textView.setBackgroundResource(R.drawable.miniballdark);
                ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(66,66);
                layoutParams.setMargins(2, 2, 2, 2);
                textView.setLayoutParams(layoutParams);
                textView.setTextSize(10);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setTextColor(ContextCompat.getColor(this, R.color.colorDark));
                textView.setPadding(0, 10, 0, 0);
                linearLayout.addView(textView);
            }
            mainLayout.addView(linearLayout);
        }
    }

}
