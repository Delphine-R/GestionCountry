package fr.epf.mm.gestionclient

import retrofit2.http.GET
import retrofit2.http.Query

interface FindPaysService {
    @GET("v3.1/name/{country}")
suspend  fun getPays(@Query("results") size: Int) : GetPaysResult
}

data class GetPaysResult(val results: List<Pays>)
data class Pays(
    val flags: Flags,
    val name: Name,
    val capital: List<String>
)

data class Flags(val svg: String, val png: String)
data class Name(val common: String, val official: String)
