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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.projekt_zespolowy.microclimateanalysisclient.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Subscribe to 'default` FCM topic
        FirebaseMessaging messaging = FirebaseMessaging.getInstance();
        Task<Void> tasks = messaging.subscribeToTopic("default");
        tasks.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("FIREBASE", "Subscription to topic succeeded");
            }
        });
        tasks.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("FIREBASE", "Subscription to topic failed");
            }
        });

        initMainActivity();
    }

    private void initMainActivity() {
        setTitle("Micro Climate Client");

        //Setup toolbar aka actionbar
        setSupportActionBar(binding.toolbar);

        //TODO Rename intents and activities (once we figure out what is what)
        Intent activityIntent1 = new Intent(getApplicationContext(), MeasurementsActivity.class);
        Intent activityIntent2 = new Intent(getApplicationContext(), Activity2.class);
        Intent activityIntent3 = new Intent(getApplicationContext(), Activity3.class);
        Intent activityIntent4 = new Intent(getApplicationContext(), Activity4.class);

        //Setting up buttons listeners
        //TODO button icons
        binding.imageButton1.setOnClickListener(new MenuButtonListener(activityIntent1));
        binding.imageButton2.setOnClickListener(new MenuButtonListener(activityIntent2));
        binding.imageButton3.setOnClickListener(new MenuButtonListener(activityIntent3));
        binding.imageButton4.setOnClickListener(new MenuButtonListener(activityIntent4));

        //TODO correct(?) names
        //Setting buttons names
        binding.textViewButton1.setText(binding.imageButton1.getContentDescription());
        binding.textViewButton2.setText(binding.imageButton2.getContentDescription());
        binding.textViewButton3.setText(binding.imageButton3.getContentDescription());
        binding.textViewButton4.setText(binding.imageButton4.getContentDescription());
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
