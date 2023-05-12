package com.mobiles.vinilosapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobiles.vinilosapp.R
import com.mobiles.vinilosapp.databinding.AlbumItemBinding
import com.mobiles.vinilosapp.models.Album
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobiles.vinilosapp.ui.albums.AlbumFragmentDirections


class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {


    var albums :List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)

        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]

        holder.viewDataBinding.also {
            it.album = album
        }

        Glide.with(holder.itemView)
            .load(album.cover).apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(holder.viewDataBinding.albumImage)

        holder.viewDataBinding.root.setOnClickListener {
            val action = AlbumFragmentDirections.actionNavigationAlbumsToNavigationAlbumDetail(idAlbum = album.albumId)
             // Navigate using that action
             holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    fun updateAlbumList(albumList: List<Album>){
        this.albums = albumList
        notifyDataSetChanged()
    }


    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }
}