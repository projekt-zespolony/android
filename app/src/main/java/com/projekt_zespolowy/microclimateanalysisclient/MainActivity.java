package com.projekt_zespolowy.microclimateanalysisclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar) Toolbar toolbar;
    // Activity buttons
    @BindView(R.id.imageButton1) ImageButton ib1;
    @BindView(R.id.imageButton2) ImageButton ib2;
    @BindView(R.id.imageButton3) ImageButton ib3;
    @BindView(R.id.imageButton4) ImageButton ib4;
    //Text names for buttons
    @BindView(R.id.textViewButton1) TextView tb1;
    @BindView(R.id.textViewButton2) TextView tb2;
    @BindView(R.id.textViewButton3) TextView tb3;
    @BindView(R.id.textViewButton4) TextView tb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Subscribe to 'default` FCM topic
        FirebaseMessaging messaging = FirebaseMessaging.getInstance();
        Task<Void> tasks = messaging.subscribeToTopic("default");
        tasks.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i(TAG, "Subscription to topic succeeded");
            }
        });
        tasks.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Subscription to topic failed");
            }
        });

        initMainActivity();
    }

    private void initMainActivity() {
        setTitle("Micro Climate Client");

        //Setup toolbar aka actionbar
        setSupportActionBar(toolbar);

        //TODO Rename intents and activities (once we figure out what is what)
        Intent activityIntent1 = new Intent(getApplicationContext(), MeasurementsActivity.class);
        Intent activityIntent2 = new Intent(getApplicationContext(), Activity2.class);
        Intent activityIntent3 = new Intent(getApplicationContext(), Activity3.class);
        Intent activityIntent4 = new Intent(getApplicationContext(), Activity4.class);

        //Setting up buttons listeners
        //TODO button icons
        ib1.setOnClickListener(new MenuButtonListener(activityIntent1));
        ib2.setOnClickListener(new MenuButtonListener(activityIntent2));
        ib3.setOnClickListener(new MenuButtonListener(activityIntent3));
        ib4.setOnClickListener(new MenuButtonListener(activityIntent4));

        //TODO correct(?) names
        //Setting buttons names
        tb1.setText(ib1.getContentDescription());
        tb2.setText(ib2.getContentDescription());
        tb3.setText(ib3.getContentDescription());
        tb4.setText(ib4.getContentDescription());
    }

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
