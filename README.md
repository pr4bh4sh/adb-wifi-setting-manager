# adb-wifi-setting-manager
Control wifi connection with adb

To enable wifi:
  `adb shell 'am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn enableWifi`

To disable wifi:
  `adb shell 'am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn disableWifi`

To connect to saved wifi network:
  `adb shell 'am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn connect -e ssid $ssid_name`

To connect to new wifi network:
  `adb shell 'am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn newConnection -e ssid $ssid_name -e password $password`

Connect to Wifi with user name and Password
  `adb shell am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn connect -e ssid $ssid_name -e userName $user_name -e password $password`

Forget a saved Wifi SSID
1. Forget a specific SSID
  `adb shell am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn remove -e ssid $ssid_name`
2. Forget all SSID
  `adb shell am start -n 'com.adbwifisettingsmanager/.WifiSettingsManagerActivity' --esn remove -e ssid 'all' # you can put 'all' or 'All'`

# TODO
* [x] Add all adb commands in readme
* [ ] Add support for more encryption types


# Note 
This app is created for interacting with wifi networks while running automated tests. Due to the security messures android doesn't allowes manupulating the saved wifi networks if you have added them manually or it wasn't added by the "adb-wifi-setting-manager" app. So if you wish to use this app please remove all the saved network connection in your device and use this app for all action related to wifi.

Reference:
1. https://github.com/steinwurf/adb-join-wifi
2. https://github.com/bitweft/android-settings-manager
