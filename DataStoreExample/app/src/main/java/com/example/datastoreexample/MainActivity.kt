package com.example.datastoreexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

//val Context.dataStore by preferencesDataStore(name="USER_PREFERENCES_NAME")

//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USER_PREFERENCES_NAME")
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USER_PREFERENCES NAME")

private operator fun <T, V> ReadOnlyProperty<T, V>.getValue(t: T, property: KProperty<V?>): DataStore<Preferences> {

}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSave = findViewById<Button>(R.id.btnSave)
        val etName = findViewById<EditText>(R.id.etName)
        val cbVIP = findViewById<CheckBox>(R.id.cbVIP)

        btnSave.setOnClickListener {
            lifecycleScope.launch (Dispatchers.IO) {
                saveValues(etName.text.toString(), cbVIP.isChecked)
            }
        }
    }

    private suspend fun saveValues(name:String,checked:Boolean) {
        dataStore.edit{preferences ->
            preferences[stringPreferencesKey("name")]=name
            preferences[booleanPreferencesKey("vip")]=checked

        }
    }
}