package com.cwtstudio.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {

    private TextView txtapiKey;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtapiKey = findViewById(R.id.txtapiKey);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(1)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        //set default values
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        fetchConfig();
        txtapiKey.setOnClickListener(v -> {
            fetchConfig();
        });








    }

    private void fetchConfig() {
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            updateTV();
                            Toast.makeText(MainActivity.this, "Got data", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();

                        }
                    }
                });



    }

    private void updateTV() {
        String apiUrl = mFirebaseRemoteConfig.getString("api_url");
        txtapiKey.setText("Api url: " +apiUrl);
    }
}