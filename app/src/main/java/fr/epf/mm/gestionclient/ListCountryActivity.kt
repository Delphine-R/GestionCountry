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
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val TAG = "ListCountryActivity"
private const val MAX_RETRIES = 5

class ListCountryActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_country)

        recyclerView = findViewById<RecyclerView>(R.id.list_country_recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_countries, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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

        val retryInterceptor = Interceptor { chain ->
            var response: Response? = null
            var attempt = 0
            while (attempt < MAX_RETRIES && (response == null || !response.isSuccessful)) {
                attempt++
                try {
                    response = chain.proceed(chain.request())
                } catch (e: IOException) {
                    if (attempt >= MAX_RETRIES) {
                        throw e
                    }
                }
            }
            response ?: throw IOException("Max retries reached")
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(retryInterceptor)
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        val PaysService = retrofit.create(FindPaysService::class.java)

        runBlocking {
            try {
                val pays = PaysService.getPays(15)
                Log.d(TAG, "synchro: ${pays}")

                val countries = pays.results.map {
                    Country(
                        it.name.common,
                        it.name.official,
                        it.capital.firstOrNull() ?: "No capital",
                        it.flags.png
                    )
                }

                val adapter = CountryAdapter(countries)
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                Log.e(TAG, "Error during synchronization", e)
            }
        }
    }
}
