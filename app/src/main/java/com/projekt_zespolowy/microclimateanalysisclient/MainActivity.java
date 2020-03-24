package com.projekt_zespolowy.microclimateanalysisclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Custom OnClickListener
    private class MenuButtonListener implements View.OnClickListener {

        private Intent intent;

        MenuButtonListener(Intent intent) {
            this.intent = intent;
        }

        @Override
        public void onClick(View view) {
            //Opening given activity on click
            openGivenActivity(intent);
            Toast.makeText(getApplicationContext(), view.getContentDescription(), Toast.LENGTH_SHORT).show();
        }
    }

    private Intent activityIntent1, activityIntent2, activityIntent3, activityIntent4;
    private ImageButton ib1, ib2, ib3, ib4; //Activity buttons
    private TextView tb1, tb2, tb3, tb4; //Text names for buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMainActivity();
    }

    private void initMainActivity() {
        setTitle("Micro Climate Client");

        //Setup toolbar aka actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO Rename intents and activities (once we figure out what is what)
        activityIntent1 = new Intent(getApplicationContext(), MeasurementsActivity.class);
        activityIntent2 = new Intent(getApplicationContext(), Activity2.class);
        activityIntent3 = new Intent(getApplicationContext(), Activity3.class);
        activityIntent4 = new Intent(getApplicationContext(), Activity4.class);

        //Setting up buttons and listeners
        //TODO button icons
        ib1 = (ImageButton) findViewById(R.id.imageButton1);
        ib1.setOnClickListener(new MenuButtonListener(activityIntent1));
        ib2 = (ImageButton) findViewById(R.id.imageButton2);
        ib2.setOnClickListener(new MenuButtonListener(activityIntent2));
        ib3 = (ImageButton) findViewById(R.id.imageButton3);
        ib3.setOnClickListener(new MenuButtonListener(activityIntent3));
        ib4 = (ImageButton) findViewById(R.id.imageButton4);
        ib4.setOnClickListener(new MenuButtonListener(activityIntent4));

        //TODO correct(?) names
        //Setting buttons names
        tb1 = (TextView) findViewById(R.id.textViewButton1);
        tb1.setText(ib1.getContentDescription());
        tb2 = (TextView) findViewById(R.id.textViewButton2);
        tb2.setText(ib2.getContentDescription());
        tb3 = (TextView) findViewById(R.id.textViewButton3);
        tb3.setText(ib3.getContentDescription());
        tb4 = (TextView) findViewById(R.id.textViewButton4);
        tb4.setText(ib4.getContentDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSettings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openGivenActivity(Intent intent) {
        startActivity(intent);
    }

}
