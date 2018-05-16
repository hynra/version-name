package com.hynra.versionnameexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.hynra.versionname.VersionName;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VersionName.get("app.pptik.itb.semut", new VersionName.Listener() {
            @Override
            public void onVersionLoaded(String versionName, boolean isWithVaries) {
                Log.i("Version", versionName+", is varies: "+isWithVaries);
            }
        });
    }
}
