package fr.epf.mm.gestionclient.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val commonName: String,
    val officialName: String,
    val capital: String,
    val flagUrl: String,
) : Parcelable{
    companion object {
        fun generate(size : Int = 20) =
            (1..size).map {
                Country("Common Name${it}",
                    "Official Name${it}",
                    "Capital City${it}",
                    "Flag${it}")
            }
    }
}