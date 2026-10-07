package com.example.zsl_egz_2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.zsl_egz_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val talia = listOf(
        Card(R.drawable.karta2, 2),
        Card(R.drawable.karta3, 3),
        Card(R.drawable.karta4, 4),
        Card(R.drawable.karta5, 5),
        Card(R.drawable.karta6, 6),
        Card(R.drawable.karta7, 7),
        Card(R.drawable.karta8, 8),
        Card(R.drawable.karta9, 9),
        Card(R.drawable.karta10, 10),
        Card(R.drawable.karta_walet, 10),
        Card(R.drawable.karta_dama, 10),
        Card(R.drawable.karta_krol, 10),
        Card(R.drawable.karta_as, 11)
    )

    private val rekaGracza = mutableListOf<Card>()
    private val rekaKomputera = mutableListOf<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDobierz.setOnClickListener {
            dobierzKarte()
        }

        binding.btnPass.setOnClickListener {
            pasuj()
        }

        binding.btnNowaGra.setOnClickListener {
            nowaGra()
        }

        nowaGra()
    }

    private fun obliczSume(reka: List<Card>): Int {
        var suma = 0
        var asy = 0

        for (karta in reka) {
            suma += karta.wartosc
            if (karta.idObrazka == R.drawable.karta_as) {
                asy++
            }
        }

        while (suma > 21 && asy > 0) {
            suma -= 10
            asy--
        }

        return suma
    }

    private fun nowaGra() {
        rekaGracza.clear()
        rekaKomputera.clear()

        binding.playerCard3.visibility = View.GONE
        binding.playerCard4.visibility = View.GONE
        binding.playerCard5.visibility = View.GONE

        binding.compCard3.visibility = View.GONE
        binding.compCard4.visibility = View.GONE
        binding.compCard5.visibility = View.GONE

        rekaGracza.add(talia.random())
        rekaGracza.add(talia.random())

        binding.playerCard1.setImageResource(rekaGracza[0].idObrazka)
        binding.playerCard2.setImageResource(rekaGracza[1].idObrazka)
        binding.tvPlayerSum.text = "Suma gracza: ${obliczSume(rekaGracza)}"

        rekaKomputera.add(talia.random())
        rekaKomputera.add(talia.random())

        binding.compCard1.setImageResource(R.drawable.rewers)
        binding.compCard2.setImageResource(R.drawable.rewers)
        binding.tvCompSum.text = "Suma komputera: ?"

        binding.tvMessage.text = "Twoja kolej! Dobierz kartę lub pasuj."
        binding.btnDobierz.isEnabled = true
        binding.btnPass.isEnabled = true
    }

    private fun dobierzKarte() {
        if (rekaGracza.size < 5) {
            val nowaKarta = talia.random()
            rekaGracza.add(nowaKarta)

            if (rekaGracza.size == 3) {
                binding.playerCard3.setImageResource(nowaKarta.idObrazka)
                binding.playerCard3.visibility = View.VISIBLE
            } else if (rekaGracza.size == 4) {
                binding.playerCard4.setImageResource(nowaKarta.idObrazka)
                binding.playerCard4.visibility = View.VISIBLE
            } else if (rekaGracza.size == 5) {
                binding.playerCard5.setImageResource(nowaKarta.idObrazka)
                binding.playerCard5.visibility = View.VISIBLE
            }

            val suma = obliczSume(rekaGracza)
            binding.tvPlayerSum.text = "Suma gracza: $suma"

            if (suma > 21) {
                binding.tvMessage.text = "Przegrana! Przekroczono 21 punktów (Fura)."
                binding.btnDobierz.isEnabled = false
                binding.btnPass.isEnabled = false
            } else if (rekaGracza.size == 5) {
                binding.btnDobierz.isEnabled = false
            }
        }
    }

    private fun pasuj() {
        binding.btnDobierz.isEnabled = false
        binding.btnPass.isEnabled = false

        binding.compCard1.setImageResource(rekaKomputera[0].idObrazka)
        binding.compCard2.setImageResource(rekaKomputera[1].idObrazka)

        var sumaKomp = obliczSume(rekaKomputera)

        while (sumaKomp < 17 && rekaKomputera.size < 5) {
            val nowaKarta = talia.random()
            rekaKomputera.add(nowaKarta)
            sumaKomp = obliczSume(rekaKomputera)

            if (rekaKomputera.size == 3) {
                binding.compCard3.setImageResource(nowaKarta.idObrazka)
                binding.compCard3.visibility = View.VISIBLE
            } else if (rekaKomputera.size == 4) {
                binding.compCard4.setImageResource(nowaKarta.idObrazka)
                binding.compCard4.visibility = View.VISIBLE
            } else if (rekaKomputera.size == 5) {
                binding.compCard5.setImageResource(nowaKarta.idObrazka)
                binding.compCard5.visibility = View.VISIBLE
            }
        }

        binding.tvCompSum.text = "Suma komputera: $sumaKomp"

        val sumaGracza = obliczSume(rekaGracza)

        if (sumaKomp > 21) {
            binding.tvMessage.text = "Wygrana! Komputer przekroczył 21 punktów."
        } else if (sumaGracza > sumaKomp) {
            binding.tvMessage.text = "Wygrana! Masz więcej punktów."
        } else if (sumaGracza < sumaKomp) {
            binding.tvMessage.text = "Przegrana! Komputer ma więcej punktów."
        } else {
            binding.tvMessage.text = "Remis!"
        }
    }
}