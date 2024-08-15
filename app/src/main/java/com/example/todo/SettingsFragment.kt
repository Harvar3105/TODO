package com.example.todo

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.core.IOException
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.todo.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private val dataStore by lazy {
        (requireContext().applicationContext as MyApp).dataStore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val select = binding.modeSelect
        val icon = binding.modeIcon

        lifecycleScope.launch {
            val isDarkTheme = readThemePreference().first()

            select.isChecked = isDarkTheme
            updateIcon(icon, isDarkTheme)

            select.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }

                saveThemePreference(isChecked)
                updateIcon(icon, isChecked)
            }
        }
    }

    private fun saveThemePreference(isDarkTheme: Boolean) {
        lifecycleScope.launch {
            dataStore.edit { settings ->
                settings[booleanPreferencesKey("isDarkTheme")] = isDarkTheme
            }
        }
    }

    private fun readThemePreference(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[booleanPreferencesKey("isDarkTheme")] ?: false
            }
    }

    private fun updateIcon(icon: ImageView, isDarkTheme: Boolean) {
        val drawable: Drawable? = if (isDarkTheme) {
            requireContext().getDrawable(R.drawable.baseline_mode_night_24)
        } else {
            requireContext().getDrawable(R.drawable.baseline_light_mode_24)
        }
        icon.setImageDrawable(drawable)
    }
}