package com.example.bugster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;


public class Dashboard extends AppCompatActivity {

    private Button button;
    private Button buttonLog;

    TabLayout tabIndicator;
    int postion = 0;
    ImageButton nextBtn;
    ImageButton previousButton;
    ViewPager viewPager;
    int bacground[] = {R.drawable.screen1_bac, R.drawable.screen2_bac, R.drawable.screen3_bac};
    int girlimage[] = {R.drawable.reviewed_docs, R.drawable.progressive_app, R.drawable.conviniency};
    int[] heading = {R.string.heading1,R.string.heading2,R.string.heading3};
    int[] description = {R.string.description1, R.string.description2, R.string.description3};

    //Variables
    ImageView image;
    TextView slogan;
    Animation topAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        //Animation
        Context context;
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        tabIndicator = findViewById(R.id.tabIndicator);
        viewPager = findViewById(R.id.viewPager);
        Adapter adapter = new Adapter(Dashboard.this, bacground, girlimage, heading, description);
        viewPager.setAdapter(adapter);
        tabIndicator.setupWithViewPager(viewPager);

        nextBtn = findViewById(R.id.nextBtn);
        previousButton = findViewById(R.id.previousButton);
        button = (Button) findViewById(R.id.regButton);
        buttonLog = (Button) findViewById(R.id.buttonLog);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
                //Тут має бути функція openRegButton(); - я замінив її в цілях тестування
            }
        });
        buttonLog.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 openLogButton();
             }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postion = viewPager.getCurrentItem();
                if (postion < bacground.length) {
                    postion++;
                    viewPager.setCurrentItem(postion);
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postion = viewPager.getCurrentItem();
                if (postion < bacground.length) {
                    postion--;
                    viewPager.setCurrentItem(postion);
                }
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void openHomePage(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

    public void openRegButton()
    {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);

    }
    public void openLogButton()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}