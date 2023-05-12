import android.content.Context
import com.mobiles.vinilosapp.models.Album

class CacheManager(context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }
    private var detallesAlbum: HashMap<Int, Album> = hashMapOf()

    fun addAlbumDetail(albumId: Int, album: Album){
        if (!detallesAlbum.containsKey(albumId)){
            detallesAlbum[albumId] = album
        }
    }
    fun getAlbumDetail(albumId: Int) : Album{
        return if (detallesAlbum.containsKey(albumId)) detallesAlbum[albumId]!! else Album(0, "cache", "cover", "01-01-2023","des", "genre", "record")
    }

    private var cacheObjects: HashMap<String, List<Any>> = hashMapOf()
    fun addListToCache(key: String, cacheObject: List<Any>){
        if (!cacheObjects.containsKey(key)){
            cacheObjects[key] = cacheObject
        }
    }

    fun getListFromCache(key: String) : List<Any>{
        return if (cacheObjects.containsKey(key)) cacheObjects[key]!! else listOf<Any>()
    }

}