import android.content.Context
import com.mobiles.vinilosapp.models.Album
import com.mobiles.vinilosapp.models.Artist
import com.mobiles.vinilosapp.models.Collector
import com.mobiles.vinilosapp.models.Comment

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
    private var detalleArtist: HashMap<Int,Artist> = hashMapOf()
    private var collectorDetail: HashMap<Int,Collector> = hashMapOf()
    fun addAlbumDetail(albumId: Int, album: Album){
        if (!detallesAlbum.containsKey(albumId)){
            detallesAlbum[albumId] = album
        }
    }
    fun addCollectorDetail(collectorId: Int, collector: Collector){
        if (!collectorDetail.containsKey(collectorId)){
            collectorDetail[collectorId] = collector
        }
    }
    fun updateAlbumDetail(albumId: Int, album: Album){
        detallesAlbum[albumId] = album
    }
    fun addArtistDetail(artistId: Int, artist: Artist){
        if (!detalleArtist.containsKey(artistId)){
            detalleArtist[artistId] = artist
        }
    }

    fun getAlbumDetail(albumId: Int) : Album{
        return if (detallesAlbum.containsKey(albumId)) detallesAlbum[albumId]!! else Album(0, "cache", "cover", "01-01-2023","des", "genre", "record", ArrayList<Comment>() )
    }
    fun getCollectorDetail(collectorId: Int) : Collector{
        return if (collectorDetail.containsKey(collectorId)) collectorDetail[collectorId]!! else Collector(0, "name", "1234", "prueba@gmail.com", ArrayList<Comment>() )
    }
    fun getArtistDetail(artistId: Int) : Artist{
        return if (detalleArtist.containsKey(artistId)) detalleArtist[artistId]!! else Artist(0, "cache", "cover", "desc","01-01-2023",mutableListOf<Album>())
    }
    private var cacheObjects: HashMap<String, List<Any>> = hashMapOf()
    fun addListToCache(key: String, cacheObject: List<Any>){
        if (!cacheObjects.containsKey(key)){
            cacheObjects[key] = cacheObject
        }
    }

    fun updateListToCache(key: String, cacheObject: List<Any>){
        cacheObjects[key] = cacheObject
    }

    fun getListFromCache(key: String) : List<Any>{
        return if (cacheObjects.containsKey(key)) cacheObjects[key]!! else listOf<Any>()
    }

}