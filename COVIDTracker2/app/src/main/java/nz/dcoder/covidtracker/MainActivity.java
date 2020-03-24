package nz.dcoder.covidtracker;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setClickable(true);
        WebSettings wSettings = myWebView.getSettings();
        wSettings.setJavaScriptEnabled(true);
//        myWebView.addJavascriptInterface(new JavaScriptInterface(getApplicationContext()), "android");
        myWebView.addJavascriptInterface(new JavaScriptInterface(this), "android");
        myWebView.loadUrl("http://192.168.122.1:5000");
    }
}
