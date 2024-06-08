package fr.epf.mm.gestionclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.gestionclient.model.Country
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


private const val TAG = "ListCountryActivity"

class ListCountryActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_country)

        recyclerView =
            findViewById<RecyclerView>(R.id.list_country_recyclerview)

        recyclerView.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_countries, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_country -> {
                startActivity(Intent(this, AddCountryActivity::class.java))
            }
            R.id.action_synchro -> {
                synchro()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun synchro() {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val country = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .country(country)
            .build()

        val userService =
            retrofit.create(PaysService::class.java)

//        CoroutineScope(Dispatchers.IO).launch {
//            val users = userService.getUsers(15)
//        }
//
        runBlocking {
            val pays = PaysService.getPays(15)
            Log.d(TAG, "synchro: ${pays}")

            val countries = pays.results.map {
                Country(
                    it.name.common, it.name.official, it.capital.0, it.flags.png,
                )
            }

            val adapter = CountryAdapter(countries)

            recyclerView.adapter = adapter

        }



    }
}