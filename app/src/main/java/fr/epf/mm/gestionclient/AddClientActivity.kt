package fr.epf.mm.gestionclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AddCountryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextView(this).apply {
            text = "Nothing to see here"
        })
    }
}
