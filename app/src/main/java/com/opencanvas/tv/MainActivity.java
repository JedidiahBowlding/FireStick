package com.opencanvas.tv;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView web;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true); s.setAllowFileAccess(true);
        s.setMediaPlaybackRequiresUserGesture(false); s.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        web.setWebViewClient(new WebViewClient()); web.setWebChromeClient(new WebChromeClient());
        web.setBackgroundColor(0xff090b0c); web.loadUrl("file:///android_asset/index.html"); setContentView(web);
    }
    @Override public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getAction() == KeyEvent.ACTION_DOWN) {
            String key = null;
            switch (e.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_UP: key="up"; break; case KeyEvent.KEYCODE_DPAD_DOWN:key="down";break;
                case KeyEvent.KEYCODE_DPAD_LEFT:key="left";break; case KeyEvent.KEYCODE_DPAD_RIGHT:key="right";break;
                case KeyEvent.KEYCODE_DPAD_CENTER: case KeyEvent.KEYCODE_ENTER:key="select";break;
                case KeyEvent.KEYCODE_BACK:key="back";break; case KeyEvent.KEYCODE_MENU:key="menu";break;
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:key="voice";break;
            }
            if (key != null) { web.evaluateJavascript("window.tvKey('"+key+"')", null); return true; }
        }
        return super.dispatchKeyEvent(e);
    }
    @Override public void onBackPressed() { web.evaluateJavascript("window.tvKey('back')", null); }
}
