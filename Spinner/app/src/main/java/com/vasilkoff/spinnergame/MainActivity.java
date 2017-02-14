package com.vasilkoff.spinnergame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initSpinner();
    }

    /**
     * All the vars you need
     */

    final int prizes[] = {200,1000,200,1000,200,400,400,200,3000,400,1000,400};
    private long mSpinDuration;
    private float mSpinRevolutions;
    ImageView pointerImageView;
    ImageView buttonImage;
    TextView infoText;
    String prizeText = "N/A";

    private void initSpinner() {
        pointerImageView = (ImageView)findViewById(R.id.imageView);
        buttonImage = (ImageView)findViewById(R.id.buttonImage);
        infoText = (TextView) findViewById(R.id.info);
        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSpinner();
            }
        });
        mSpinRevolutions = 3600;
    }


    public void StartSpinner() {
        mSpinDuration = (long)(2000 + Math.random() * 3000);//random: 2000-5000

        // Final point of rotation defined right here:
        int end = (int)Math.floor(Math.random() * 360);//random: 0-360
        int numOfPrizes = prizes.length;// quantity of prizes
        int degreesPerPrize = 360/numOfPrizes;// size of sector per prize in degrees
        int shift = 0; //shit where the arrow points
        int prizeIndex = (shift + end/degreesPerPrize)%numOfPrizes;
        prizeText = "Prize is: "+ prizes[prizeIndex];


        RotateAnimation rotateAnim = new RotateAnimation(0f, mSpinRevolutions + end,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setInterpolator(new DecelerateInterpolator());
        rotateAnim.setRepeatCount(0);
        rotateAnim.setDuration(mSpinDuration);
        rotateAnim.setAnimationListener(this);
        rotateAnim.setFillAfter(true);

        pointerImageView.startAnimation(rotateAnim);
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

    @Override
    public void onAnimationStart(Animation animation) {
        infoText.setText("Spinning...");
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        infoText.setText(prizeText);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
