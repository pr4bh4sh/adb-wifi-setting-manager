package com.adbwifisettingsmanager;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.adbwifisettingsmanager.constants.IntentExtras;

import java.util.List;

public class WifiSettingsManagerActivity extends AppCompatActivity {
    Bundle extras;
    private final String TAG = "WiFiSettingsManager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        extras = getIntent().getExtras();
        if (extras != null) {
            changeSettings();
        }
        finish();
    }

    private void changeSettings() {
        if (extras.containsKey(IntentExtras.DISABLE_WIFI)) {
            setWifiEnabledState(false);
        }
        if (extras.containsKey(IntentExtras.REMOVE)) {
            String ssid = extras.getString(IntentExtras.SSID);
            if (ssid.equalsIgnoreCase("All")) {
                removeAllWifiConfig();
            } else {
                int id = getWiFiNetworkId(ssid);
                removeWifi(id);
            }
        }
        if (extras.containsKey(IntentExtras.USER_NAME)) {
            connectToEAPNetwork();
        }
        if (extras.containsKey(IntentExtras.ENABLE_WIFI)) {
            setWifiEnabledState(true);
        }
        if (extras.containsKey(IntentExtras.CONNECT) && extras.containsKey(IntentExtras.SSID)) {
            connectToNetwork();
        }
        if (extras.containsKey(IntentExtras.NEW_CONNECTION)) {
            if (extras.containsKey(IntentExtras.SSID) && extras.containsKey(IntentExtras.PASSWORD)) {
                connectToNewNetwork();
            }
        }
    }

    private void setWifiEnabledState(boolean wifiEnabledState) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(wifiEnabledState);
    }

    private void connectToNewNetwork() {
        String networkSSID = extras.getString(IntentExtras.SSID);
        String networkPassword = extras.getString(IntentExtras.PASSWORD);

        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = String.format("\"%s\"", networkSSID);
        wifiConfiguration.preSharedKey = String.format("\"%s\"", networkPassword);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(wifiConfiguration);
        connectToNetwork();
    }

    private void connectToNetwork() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String networkSSID = extras.getString(IntentExtras.SSID);
        if (wifiManager.isWifiEnabled()) {
            List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
            Log.d(TAG, "Configured network ids = " + configuredNetworks);

            for (WifiConfiguration configuredNetwork : configuredNetworks) {
                if (configuredNetwork.SSID != null && configuredNetwork.SSID.equals(String.format("\"%s\"", networkSSID))) {
                    Log.d(TAG, "Connecting to network with networkSSID = " + networkSSID);
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(configuredNetwork.networkId, true);
                    wifiManager.reconnect();
                    break;
                }
            }
        }
    }

    private void connectToEAPNetwork() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String networkSSID = extras.getString(IntentExtras.SSID);
        String networkUserName = extras.getString(IntentExtras.USER_NAME);
        String networkPassword = extras.getString(IntentExtras.PASSWORD);
        Log.d(TAG, networkSSID);
        Log.d(TAG, networkUserName);
        Log.d(TAG, networkSSID);
        if (wifiManager.isWifiEnabled()) {
            WifiConfiguration config = new WifiConfiguration();
            config.SSID = "\"" + networkSSID + "\"";
            config.status = WifiConfiguration.Status.ENABLED;
            WifiEnterpriseConfig enterpriseConfig = new WifiEnterpriseConfig();
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);
            enterpriseConfig.setIdentity(networkUserName);
            enterpriseConfig.setPassword(networkPassword);
            enterpriseConfig.setEapMethod(WifiEnterpriseConfig.Eap.PEAP);
            enterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.MSCHAPV2);
            config.enterpriseConfig = enterpriseConfig;

            int id = wifiManager.addNetwork(config);
            wifiManager.disconnect();
            wifiManager.enableNetwork(id, true);
            wifiManager.reconnect();
            Log.d(TAG, "# addNetwork returned " + id);
        }
    }

    private int getWiFiNetworkId(String SSID) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> confs = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration conf : confs) {
            String ssid = conf.SSID;
            int nwId = conf.networkId;
            Log.d(TAG, "SSID -" + ssid + " N/W ID" + nwId);
            if (ssid.equals(String.format("\"%s\"", SSID))) {
                Log.d(TAG, "N/W id for '" + ssid + "' is " + nwId);
                return nwId;
            }
        }
        Log.d(TAG, "# N/W id not found for'" + SSID + "'");
        return -1;
    }

    private boolean removeWifi(int networkId) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (networkId == -1) {
            Log.d(TAG, "Something went worng, N/W should value should not be -1 ");
        }
        return wifiManager.removeNetwork(networkId);
    }

    private void removeAllWifiConfig() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> confs = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration conf : confs) {
            String ssid = conf.SSID;
            int nwId = conf.networkId;
            if (removeWifi(nwId)) {
                Log.d(TAG, "Succesfully Remove N/W with name - " + ssid);
            }
        }
    }


}
