# adb-wifi-setting-manager
Control wifi connection with adb

Here are some use-cases
1. Switch between wifi networks when running automation tests without manual intervention.
2. You have 2 wifi networks, one fast and one slow and you want to create automated tests for the app's behaviour on low bandwidth.
3. You have two Wi-Fi networks, one in the internal network and the other outside the internal network, and you want to test the app behaviour on both networks.
4. You have several wifi networks, and you are too lazy to type the wifi passwords every time you set up a new Android phone. Just create a shell script or copy-paste the command to set up the new device.

## Note 
```diff
- This app is created for interacting with Wi-Fi networks while running automated tests.
- Due to the security measures of Android OS, this app can't manipulate(delete, update) existing
- wifi networks unless it was added by the 'adb-wifi-setting-manager' app.
- So if you wish to have full control of the SSID saved on the device with this app,
- please remove all saved Wi-Fi networks on your device manually and use this app for all action 
- e.g. adding, or removing new Wi-Fi networks.
```

## Commands:
---

To download apk:

  `wget https://github.com/pr4bh4sh/adb-wifi-setting-manager/releases/download/1.0/adb-wifi-setting-manager-debug.apk`

To install apk

  `adb install adb-wifi-setting-manager-debug.apk`

To enable wifi:

  `adb shell 'am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn enableWifi'`

To disable wifi:

  `adb shell 'am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn disableWifi'`

To connect to saved wifi network:

  `adb shell 'am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn connect -e ssid <ssid_name>'`

To connect to new wifi network:

  `adb shell 'am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn newConnection -e ssid <ssid_name> -e password <password>'`

Connect to Wifi with user name and Password:

  `adb shell am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn connect -e ssid <ssid_name> -e userName <user_name> -e password <password>'`

Forget a saved Wi-Fi SSID
1. Forget a specific SSID

    `adb shell am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn remove -e ssid <ssid_name>'`
  
2. Forget all SSID

    `adb shell am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn remove -e ssid 'all'' # you can put 'all' or 'All'`


## TODO
* [x] Add all adb commands in readme
* [ ] Add proxy server support
* [ ] Add support for more encryption types


Reference:
1. https://github.com/steinwurf/adb-join-wifi
2. https://github.com/bitweft/android-settings-manager
