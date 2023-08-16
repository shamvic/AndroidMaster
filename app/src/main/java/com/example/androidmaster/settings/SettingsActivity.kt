package com.example.androidmaster.settings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Switch
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidmaster.R
import com.google.android.material.slider.RangeSlider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable.key
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {


    private lateinit var switchDarkMode: Switch
    private lateinit var switchBluetooth: Switch
    private lateinit var switchVibration: Switch
    private lateinit var rsVolume: RangeSlider
    private lateinit var btnTest:Button

    var firstTime: Boolean = true

    companion object {
        const val VOLUME_LVL = "volume_lvl"
        const val KEY_BLUETOOTH = "key_bluetooth"
        const val KEY_VIBRATION = "key_vibration"
        const val KEY_DARK_MODE = "key_dark_mode"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initComponents()
        btnTest.setOnClickListener {

            Log.i("settings", "El firstTime UI es: $firstTime")
            //Log.i("settings", "El valor de volumenes: ${getSettings().settingsModel.volume.toFloat()}")

        }
        //Log.i("settings", "El firstTime antes es: $firstTime")
        CoroutineScope(Dispatchers.IO).launch {
            getSettings().filter { firstTime }.collect { settingsModel ->
                //datos from SettingsModel()
                if (settingsModel != null) {
                    // metemos en hilo principal:
                    runOnUiThread {
                        switchDarkMode.isChecked = settingsModel.darkMode
                        switchBluetooth.isChecked = settingsModel.bluetooth
                        switchVibration.isChecked = settingsModel.vibration
                        rsVolume.setValues(settingsModel.volume.toFloat())
                        firstTime = !firstTime
                        Log.i("settings", "El firstTime despues es: $firstTime")
                    }

                }
            }
        }
       // Log.i("settings", "El firstTime despues es: $firstTime")
        initUI()
    }



    private fun initUI() {
        Log.i("settings", "El firstTime UI es: $firstTime")
        rsVolume.addOnChangeListener { _, value, _ ->
            Log.i("settings", "El valor de volumen es: $value")
            CoroutineScope(Dispatchers.IO).launch {
                saveVolume(value.toInt())
            }
        }

        switchBluetooth.setOnCheckedChangeListener { _, value ->
            Log.i("settings", "El valor de bluetooh es: $value")
            CoroutineScope(Dispatchers.IO).launch {
                saveBluetooth( value)
            }
        }

        switchVibration.setOnCheckedChangeListener { _, value ->
            Log.i("settings", "El valor de vibration es: $value")
            CoroutineScope(Dispatchers.IO).launch {
                saveVibration( value)
            }
        }

        switchDarkMode.setOnCheckedChangeListener { _, value ->
            Log.i("settings", "El valor de dark mode es: $value")
            CoroutineScope(Dispatchers.IO).launch {
                saveDarkMode( value)
            }
        }

        /*
        switchBluetooth.setOnCheckedChangeListener { _, value ->
             Log.i("settings", "El valor de bluetooh es: $value")
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions("KEY_BLUETOOTH", value)
            }
        }

        switchVibration.setOnCheckedChangeListener { _, value ->
            Log.i("settings", "El valor de vibration es: $value")
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions("KEY_VIBRATION", value)
            }
        }

        switchDarkMode.setOnCheckedChangeListener { _, value ->
            Log.i("settings", "El valor de dark mode es: $value")
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions("KEY_DARK_MODE", value)
            }
        }
        */
    }

    private suspend fun saveDarkMode(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(KEY_DARK_MODE)] = value
        }
    }

    private suspend fun saveVibration(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(KEY_VIBRATION)] = value
        }
    }

    private suspend fun saveBluetooth( value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(KEY_BLUETOOTH)] = value
        }
    }

    private suspend fun saveVolume(value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(VOLUME_LVL)] = value
        }
    }
    /*
    private suspend fun saveOptions(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }
    */

    private suspend fun saveOptions(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }

    }


    private fun getSettings(): Flow<SettingsModel?> {
        return dataStore.data.map { preferences ->

            SettingsModel(

                bluetooth = preferences[booleanPreferencesKey(KEY_BLUETOOTH)] ?: true,
                vibration = preferences[booleanPreferencesKey(KEY_VIBRATION)] ?: true,
                darkMode = preferences[booleanPreferencesKey(KEY_DARK_MODE)] ?: false,
                volume = preferences[intPreferencesKey(VOLUME_LVL)] ?: 50,

            )

        }
    }


    private fun initComponents() {
        switchBluetooth = findViewById(R.id.switchBluetooth)
        switchDarkMode = findViewById(R.id.switchDarkMode)
        switchVibration = findViewById(R.id.switchVibration)
        rsVolume = findViewById(R.id.rsVolume)
        btnTest=findViewById(R.id.btnTest)
    }
}