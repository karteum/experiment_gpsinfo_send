package com.example.gpstracker

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SettingsActivity : AppCompatActivity() {

    private lateinit var preferencesManager: PreferencesManager

    private lateinit var mmsiInputLayout: TextInputLayout
    private lateinit var phoneInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var mrccPhoneInputLayout: TextInputLayout

    private lateinit var mmsiInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var mrccPhoneInput: TextInputEditText

    private lateinit var saveButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Enable up button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize preferences manager
        preferencesManager = PreferencesManager.getInstance(this)

        // Initialize views
        mmsiInputLayout = findViewById(R.id.mmsiInputLayout)
        phoneInputLayout = findViewById(R.id.phoneInputLayout)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        mrccPhoneInputLayout = findViewById(R.id.mrccPhoneInputLayout)

        mmsiInput = findViewById(R.id.mmsiInput)
        phoneInput = findViewById(R.id.phoneInput)
        emailInput = findViewById(R.id.emailInput)
        mrccPhoneInput = findViewById(R.id.mrccPhoneInput)

        saveButton = findViewById(R.id.saveButton)

        // Load existing values
        loadSettings()

        // Set up save button
        saveButton.setOnClickListener {
            saveSettings()
        }
    }

    private fun loadSettings() {
        mmsiInput.setText(preferencesManager.getMMSI())
        phoneInput.setText(preferencesManager.getPhoneNumber())
        emailInput.setText(preferencesManager.getTargetEmail())
        mrccPhoneInput.setText(preferencesManager.getMRCCPhone())
    }

    private fun saveSettings() {
        // Clear previous errors
        mmsiInputLayout.error = null
        phoneInputLayout.error = null
        emailInputLayout.error = null
        mrccPhoneInputLayout.error = null

        // Get values
        val mmsi = mmsiInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val mrccPhone = mrccPhoneInput.text.toString().trim()

        // Validate inputs
        var isValid = true

        if (mmsi.isEmpty()) {
            mmsiInputLayout.error = getString(R.string.error_required_field)
            isValid = false
        }

        if (phone.isEmpty()) {
            phoneInputLayout.error = getString(R.string.error_required_field)
            isValid = false
        }

        if (email.isEmpty()) {
            emailInputLayout.error = getString(R.string.error_required_field)
            isValid = false
        } else if (!isValidEmail(email)) {
            emailInputLayout.error = getString(R.string.error_invalid_email)
            isValid = false
        }

        // MRCC phone is optional, no validation required

        if (!isValid) {
            return
        }

        // Save values
        preferencesManager.saveMMSI(mmsi)
        preferencesManager.savePhoneNumber(phone)
        preferencesManager.saveTargetEmail(email)
        preferencesManager.saveMRCCPhone(mrccPhone)

        // Show success message
        Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show()

        // Return to main activity
        finish()
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
