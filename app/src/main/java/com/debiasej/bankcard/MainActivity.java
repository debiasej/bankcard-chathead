package com.debiasej.bankcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final Class SERVICE_CLASS = BankcardService.class;
    private Button startService,stopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService=(Button)findViewById(R.id.startService);
        startService.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startService(new Intent(getApplication(), SERVICE_CLASS));
            }
        });

        stopService=(Button)findViewById(R.id.stopService);
        stopService.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopService(new Intent(getApplication(), SERVICE_CLASS));
            }
        });

    }
}
