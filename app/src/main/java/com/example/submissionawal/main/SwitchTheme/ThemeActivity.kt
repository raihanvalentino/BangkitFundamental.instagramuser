package com.example.submissionawal.main.SwitchTheme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submissionawal.R
import com.google.android.material.switchmaterial.SwitchMaterial

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_theme)

        val theme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferenceTheme.getInstance(dataStore)
        val vmTheme = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )
        vmTheme.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                theme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                theme.isChecked = false
            }
        }

        theme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            vmTheme.saveThemeSetting(isChecked)
        }
    }


}