package fr.epf.mm.gestionclient

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.gestionclient.model.Country


//public class ClientViewHolder extends RecyclerView.ViewHolder{
//
//    public ClientViewHolder(View view){
//        super(view)
//    }

const val COUNTRY_ID_EXTRA = "countryId"

class CountryViewHolder(view : View) : RecyclerView.ViewHolder(view)


class CountryAdapter(val clients: List<Country>) : RecyclerView.Adapter<CountryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.country_view, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount() = clients.size


    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = clients[position]
        val view = holder.itemView
        val countryNameTextView = view.findViewById<TextView>(R.id.country_view_textview)
//        countryNameTextView.text = "${country.firstName} ${country.lastName}"
        countryNameTextView.text = country.nomComplet

        val imageView = view.findViewById<ImageView>(R.id.country_view_imageview)
        country.loadImageInto(imageView)

        val cardVIew = view.findViewById<CardView>(R.id.country_view_cardview)
        cardVIew.click {
            with(it.context){
                val intent = Intent(this, DetailsCountryActivity::class.java)
                intent.putExtra(COUNTRY_ID_EXTRA, country)
                startActivity(intent)
            }
        }
    }
}


