package com.raxi.king;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

public class FloatingService extends Service {
    private WindowManager windowManager;
    private View floatingView;

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // एक छोटी खिड़की बनाओ
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(0xFF1F0E11);

        // बंद करने का बटन
        Button closeBtn = new Button(this);
        closeBtn.setText("✕ बंद करें");
        closeBtn.setOnClickListener(v -> stopSelf());

        // वेबव्यू (जिसमें PDF और Key होगी)
        WebView webView = new WebView(this);
        webView.loadUrl("https://raxi-king.onrender.com"); // यहाँ अपनी Render वाली लिंक डालना
        webView.setLayoutParams(new LinearLayout.LayoutParams(600, 800));

        layout.addView(closeBtn);
        layout.addView(webView);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 50;
        params.y = 100;

        floatingView = layout;
        windowManager.addView(floatingView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null) windowManager.removeView(floatingView);
    }
}