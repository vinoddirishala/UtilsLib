package com.vdtlabs.utilslibsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.vdtlabs.utilslib.ProjectUtils;

public class MainActivity extends AppCompatActivity {

    String appInfo = "";
    TextView appInfofromLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appInfofromLib = (TextView)findViewById(R.id.appInfofromLib);
        appInfo = ProjectUtils.getAppInfo();
        appInfofromLib.setText(appInfo.equalsIgnoreCase("") ? "App Info Not Available":appInfo);
    }
}
