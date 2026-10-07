package com.example.wizytoweczka

import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment
import com.example.wizytoweczka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bttnGenerate.setOnClickListener {
            generateCard()
        }

        binding.bttnReset.setOnClickListener{
            resetCard()
        }

    }

    private fun generateCard(){
        binding.CardView.visibility = View.INVISIBLE;

        if(binding.nameInput.text.isEmpty() || binding.emailInput.text.isEmpty() || binding.addressInput.text.isEmpty() || binding.phoneInput.text.isEmpty()){
            binding.tvAlerts.text = "Wypełnij wszystkie pola";
        }
        else if(binding.phoneInput.text.length != 9 && !binding.phoneInput.text.isDigitsOnly()){
            binding.tvAlerts.text = "Nieprawidłowy numer telefonu";
        }
        else if(!binding.emailInput.text.contains('@')){
            binding.tvAlerts.text = "Nieprawidłowy adres e-mail";
        }
        else{
            binding.tvAlerts.text = "";
            binding.CardView.visibility = View.VISIBLE;

            binding.cvName.text = binding.nameInput.text;
            binding.cvMail.text = binding.emailInput.text;
            binding.cvPhone.text = binding.phoneInput.text;
            binding.cvAddress.text = binding.addressInput.text;
        }
    }

    private fun resetCard(){
        binding.nameInput.text.clear();
        binding.emailInput.text.clear();
        binding.phoneInput.text.clear();
        binding.addressInput.text.clear();

        binding.CardView.visibility = View.INVISIBLE;
        binding.tvAlerts.text = "Autor: 12345678910";
    }
}

