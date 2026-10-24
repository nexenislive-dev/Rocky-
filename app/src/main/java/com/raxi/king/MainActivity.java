package com.raxi.king;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(0xFF120404);
        
        Button btnStart = new Button(this);
        btnStart.setText("Game खेलें (Open Raxi17)");
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(MainActivity.this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                } else {
                    startService(new Intent(MainActivity.this, FloatingService.class));
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.raxi17.app");
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.raxi17.com")));
                    }
                }
            }
        });
        
        layout.addView(btnStart);
        setContentView(layout);
    }
}