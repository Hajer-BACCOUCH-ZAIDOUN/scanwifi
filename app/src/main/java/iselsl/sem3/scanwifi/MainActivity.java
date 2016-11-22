package iselsl.sem3.scanwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView txtListe;
    WifiManager wifi;
    MonWifiReceiver receiverWifi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        wifi =  (WifiManager) getSystemService(Context.WIFI_SERVICE);
        receiverWifi = new MonWifiReceiver();
        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();
    }

    protected void onPause() {
        unregisterReceiver(receiverWifi);
        super.onPause();
    }

    protected void onResume() {
        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    class MonWifiReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            StringBuilder sb = new StringBuilder();
            List<ScanResult> wifiList = wifi.getScanResults();
            for(int i = 0; i < wifiList.size(); i++){
                sb.append((i+1) + ".");
                sb.append((wifiList.get(i)).BSSID.toString());
                sb.append("\n------------------------------");
                sb.append("\n");
            }

            txtListe=(TextView) findViewById(R.id.txtListe) ;
            txtListe.setText(sb);
        }
    }
}


