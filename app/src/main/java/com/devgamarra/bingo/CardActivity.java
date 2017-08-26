package com.devgamarra.bingo;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class CardActivity extends AppCompatActivity {

    private int[] numbers = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
            31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
            51, 52, 53, 54, 55, 56, 57, 58, 59, 60,
            61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
            71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
            81, 82, 83, 84, 85, 86, 87, 88, 89, 90
    };

    private int[][] result = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
        generate();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_clean_table) {
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.lyMain);
            mainLayout.removeAllViews();
            generate();
            return true;
        } else if (id == R.id.action_add_card) {
            generate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int extract(ArrayList<Integer> num) {
        Random r = new Random();
        int index = r.nextInt(num.size());
        int result = num.get(index);
        num.remove(index);
        return result;
    }

    public boolean checkSpace(int[] array) {
        int counter = 0;
        for(int i = 0; i < array.length; i++) {
            if(array[i] != 0) {
                counter++;
            }
        }
        return counter < 5;
    }

    public void insert(int index, int n) {
        if(result[0][index] == 0 && checkSpace(result[0])) {
            result[0][index] = n;
            count++;
        } else if(result[1][index] == 0 && checkSpace(result[1])) {
            result[1][index] = n;
            count++;
        } else if(result[2][index] == 0 && checkSpace(result[2])) {
            result[2][index] = n;
            count++;
        }
    };

    public void generate() {
        cleanResult();
        count = 0;
        ArrayList<Integer> num = new ArrayList<>();
        for(int i = 0; i < numbers.length; i++) {
            num.add(numbers[i]);
        }

        while(count < 15) {
            int n = extract(num);
            if(n <= 0) {
                break;
            }
            if (n < 10) {
                insert(0, n);
            }
            else if (n < 20) {
                insert(1, n);
            }
            else if (n < 30) {
                insert(2, n);
            }
            else if (n < 40) {
                insert(3, n);
            }
            else if (n < 50) {
                insert(4, n);
            }
            else if (n < 60) {
                insert(5, n);
            }
            else if (n < 70) {
                insert(6, n);
            }
            else if (n < 80) {
                insert(7, n);
            }
            else if (n <= 90) {
                insert(8, n);
            }
        }
        result = order(result);
        System.out.println(print(result));
        render(result);
    }

    public int[][] order(int[][] result) {
        for(int i = 0; i < result[0].length; i++) {
            if(result[0][i] > result[1][i] && result[0][i] != 0 && result[1][i] != 0) {
                int num = result[0][i];
                result[0][i] = result[1][i];
                result[1][i] = num;
            }
            if(result[1][i] > result[2][i] && result[1][i] != 0 && result[2][i] != 0) {
                int num = result[1][i];
                result[1][i] = result[2][i];
                result[2][i] = num;
            }
            if(result[0][i] > result[2][i] && result[0][i] != 0 && result[2][i] != 0) {
                int num = result[0][i];
                result[0][i] = result[2][i];
                result[2][i] = num;
            }
            if(result[0][i] > result[1][i] && result[0][i] != 0 && result[1][i] != 0) {
                int num = result[0][i];
                result[0][i] = result[1][i];
                result[1][i] = num;
            }
            if(result[1][i] > result[2][i] && result[1][i] != 0 && result[2][i] != 0) {
                int num = result[1][i];
                result[1][i] = result[2][i];
                result[2][i] = num;
            }
        }
        return result;
    }

    public String print(int[][] result) {
        String res = "";
        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[i].length; j++) {
                if(result[i][j] < 10) {
                    res += " ";
                }
                res += result[i][j] + " ";
            }
            res += "\n";
        }
        return res;
    }

    public void render(int[][] numbers) {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.lyMain);
        LinearLayout subLayout = new LinearLayout(this);
        subLayout.setOrientation(LinearLayout.VERTICAL);
        subLayout.setPadding(2, 2, 2, 2);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 20);
        subLayout.setLayoutParams(params);
        subLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        for(int i = 0; i < numbers.length; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < numbers[i].length; j++) {
                final TextView textView = new TextView(this);
                textView.setId(8000+numbers[i][j]);
                if(numbers[i][j] > 0) {
                    textView.setText(numbers[i][j] + "");
                    textView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorLight));
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(textView.getTag() == null) {
                                textView.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorGreen));
                                textView.setTag(true);
                            } else {
                                textView.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorLight));
                                textView.setTag(null);
                            }
                        }
                    });
                } else {
                    textView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryGray));
                }
                TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, 1f);
                layoutParams.setMargins(2, 2, 2, 2);
                textView.setLayoutParams(layoutParams);
                textView.setTextSize(20);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setTextColor(ContextCompat.getColor(this, R.color.colorDark));
                textView.setPadding(0, 40, 0, 40);
                linearLayout.addView(textView);
            }
            subLayout.addView(linearLayout);
        }
        mainLayout.addView(subLayout);
    }

    public void cleanResult() {
        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[i].length; j++) {
                result[i][j] = 0;
            }
        }
    }
}
