
package com.example.calculimc

import android.os.Bundle
import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Récupération des composants
        val poids = findViewById<EditText>(R.id.etPoids)
        val taille = findViewById<EditText>(R.id.etTaille)

        val buttonCalculer =
            findViewById<Button>(R.id.btnCalculer)

        val buttonEffacer =
            findViewById<Button>(R.id.btnEffacer)

        val tvIMC = findViewById<TextView>(R.id.tvIMC)
        val tvCategorie =
            findViewById<TextView>(R.id.tvCategorie)

        // Bouton Calculer
        buttonCalculer.setOnClickListener {

            // 1. Récupérer les valeurs saisies
            val poidsStr = poids.text.toString().trim()
            val tailleStr = taille.text.toString().trim()

            // 2. Vérifier que les champs sont renseignés
            if (poidsStr.isEmpty() || tailleStr.isEmpty()) {

                Toast.makeText(
                    this,
                    getString(R.string.erreur_champs),
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                // 3. Convertir les valeurs en nombres décimaux
                val poidsDouble = poidsStr
                    .replace(',', '.')
                    .toDoubleOrNull()

                val tailleDouble = tailleStr
                    .replace(',', '.')
                    .toDoubleOrNull()

                if (poidsDouble == null || tailleDouble == null) {

                    Toast.makeText(
                        this,
                        getString(R.string.erreur_nombre),
                        Toast.LENGTH_SHORT
                    ).show()

                } else if (poidsDouble <= 0 || tailleDouble <= 0) {

                    // 4. Vérifier que les valeurs sont positives
                    Toast.makeText(
                        this,
                        getString(R.string.erreur_positif),
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    // 5. Calculer l'IMC
                    val imc = poidsDouble / (tailleDouble * tailleDouble)

                    // 6. Déterminer la catégorie et la couleur
                    val categorie: String
                    val couleur: Int

                    if (imc < 18.5) {

                        categorie = getString(R.string.insuffisance)
                        couleur = Color.rgb(255, 152, 0)

                    } else if (imc < 25) {

                        categorie = getString(R.string.normale)
                        couleur = Color.rgb(76, 175, 80)

                    } else if (imc < 30) {

                        categorie = getString(R.string.surpoids)
                        couleur = Color.rgb(255, 152, 0)

                    } else if (imc < 35) {

                        categorie = getString(R.string.obesite_moderee)
                        couleur = Color.RED

                    } else if (imc < 40) {

                        categorie = getString(R.string.obesite_severe)
                        couleur = Color.RED

                    } else {

                        categorie = getString(R.string.obesite_morbide)
                        couleur = Color.rgb(139, 0, 0)
                    }

                    // 7. Afficher le résultat avec deux décimales
                    tvIMC.text = getString(
                        R.string.resultat_format,
                        String.format(Locale.FRANCE, "%.2f", imc)
                    )

                    tvCategorie.text = getString(
                        R.string.categorie_format,
                        categorie
                    )

                    // 8. Appliquer la couleur de la catégorie
                    tvCategorie.setTextColor(couleur)
                }
            }
        }

        // Bouton Effacer
        buttonEffacer.setOnClickListener {

            // Vider les champs
            poids.text.clear()
            taille.text.clear()

            // Réinitialiser les résultats
            tvIMC.text = getString(R.string.resultat_imc)
            tvCategorie.text = getString(R.string.categorie)

            // Restaurer la couleur initiale
            tvCategorie.setTextColor(Color.BLACK)

            // Replacer le curseur dans le champ Poids
            poids.requestFocus()
        }
    }
}