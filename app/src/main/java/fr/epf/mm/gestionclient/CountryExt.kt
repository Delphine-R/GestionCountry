package fr.epf.mm.gestionclient

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import fr.epf.mm.gestionclient.model.Country

fun Country.loadImageInto(imageView: ImageView) {
    Glide.with(imageView.context)
        .load(flagUrl) // Assuming flagUrl is the property containing the image URL
        .apply(RequestOptions().placeholder(R.drawable.placeholder)) // Placeholder image while loading
        .into(imageView)
}

val Country.nomComplet: String
    get() = "${commonName} ${officialName}"

fun View.click( action : (View) -> Unit){
    Log.d("CLICK","click !")
    this.setOnClickListener(action)
}