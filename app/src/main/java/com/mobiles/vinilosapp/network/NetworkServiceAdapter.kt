package com.mobiles.vinilosapp.network

import android.content.Context

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mobiles.vinilosapp.models.Album
import com.mobiles.vinilosapp.models.Artist
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://vynilsback.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ cont ->
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                var item:JSONObject? = null
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Album(albumId = item.getInt("id"),
                        name = item.getString("name"), cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")
                    ))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }


    suspend fun getAlbum(albumId: Int) = suspendCoroutine<Album>{ cont ->

        requestQueue.add(getRequest("albums/$albumId",
            { response ->
                val resp = JSONObject(response)
                val album = Album(albumId = resp.getInt("id"),
                    name = resp.getString("name"), cover = resp.getString("cover"),
                    recordLabel = resp.getString("recordLabel"), releaseDate = resp.getString("releaseDate"), genre = resp.getString("genre"), description = resp.getString("description"))
                cont.resume(album)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    fun getArtists(onComplete:(resp:List<Artist>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artist>()

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    val artistAlbums = item.getJSONArray("albums")
                    val albums = mutableListOf<Album>()
                    for (j in 0 until artistAlbums.length()) {
                        val albumItem = artistAlbums.getJSONObject(j)
                        val album = Album(
                            albumId = albumItem.getInt("id"),
                            name = albumItem.getString("name"),
                            cover = albumItem.getString("cover"),
                            releaseDate = albumItem.getString("releaseDate"),
                            description = albumItem.getString("description"),
                            genre = albumItem.getString("genre"),
                            recordLabel = albumItem.getString("recordLabel")
                        )
                        albums.add(album)
                    }
                    val artist = Artist(
                        artistId = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        birthDate = item.getString("birthDate"),
                        albums = albums
                    )
                    list.add(artist)
                }
                val sortedList = list.sortedBy { it.name } // Ordena la lista por nombre
                onComplete(sortedList)
            },
            {
                onError(it)
            }))
    }


    fun postAlbum(body: JSONObject,  onComplete:(resp:JSONObject)->Unit , onError: (error:VolleyError)->Unit){
        requestQueue.add(postRequest("albums", body,
            { resp ->
                onComplete(resp)
            },
            {
                onError(it)
            }))
    }

    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }

}