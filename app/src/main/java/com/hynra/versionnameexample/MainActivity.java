package com.hynra.versionnameexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.hynra.versionname.VersionName;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VersionName.get("com.google.android.youtube", new VersionName.Listener() {
            @Override
            public void onVersionLoaded(String versionName, boolean isWithVaries) {
                TextView t = findViewById(R.id.text);
                t.setText(versionName+" is varies "+isWithVaries);
            }
        });
    }
}
