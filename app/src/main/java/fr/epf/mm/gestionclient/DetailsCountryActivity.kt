package fr.epf.mm.gestionclient

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.epf.mm.gestionclient.model.Country

class DetailsCountryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_country)

        val lastNameTextView = findViewById<TextView>(R.id.details_country_lastname_textview)
        val imageView = findViewById<ImageView>(R.id.details_country_imageview)

        intent.extras?.apply {
            val country = getParcelable(COUNTRY_ID_EXTRA) as? Country

            country?.let {
                commonNameTextView.text = it.commonName
                officialNameTextView.text = it.officialName
                capitalTextView.text = it.capital
                imageView.setImageResource(country.getImage())
            }
        }
    }
}
