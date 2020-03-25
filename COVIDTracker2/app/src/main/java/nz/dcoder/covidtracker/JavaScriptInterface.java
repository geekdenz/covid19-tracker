package nz.dcoder.covidtracker;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class JavaScriptInterface {
    private static final int PERMISSION_REQUEST_CODE = 1;
    Context mContext;

    /** Instantiate the interface and set the context */

    public JavaScriptInterface(Context c) {
        mContext = c;
        final Intent intent = new Intent(mContext, BackgroundService.class);
        mContext.startService(intent);
        mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @JavascriptInterface
    public void startTracking() {
        ((Activity) mContext).requestPermissions(
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                },
                PERMISSION_REQUEST_CODE
        );
//        Toast.makeText(mContext, "Before", Toast.LENGTH_LONG).show();
        gpsService.startTracking();
//        Toast.makeText(mContext, "After", Toast.LENGTH_LONG).show();
    }
    @JavascriptInterface
    public void stopTracking() {
        gpsService.stopTracking();
    }
    public BackgroundService gpsService;
    public boolean mTracking = false;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            String name = className.getClassName();
            if (name.endsWith("BackgroundService")) {
                gpsService = ((BackgroundService.LocationServiceBinder) service).getService();
//                btnStartTracking.setEnabled(true);
//                txtStatus.setText("GPS Ready");
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            if (className.getClassName().equals("BackgroundService")) {
                gpsService = null;
            }
        }
    };
}
