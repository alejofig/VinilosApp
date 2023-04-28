package com.mobiles.vinilosapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobiles.vinilosapp.R
import com.mobiles.vinilosapp.databinding.AlbumItemBinding
import com.mobiles.vinilosapp.models.Album
import com.bumptech.glide.Glide


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
            .load(album.cover)
            .into(holder.viewDataBinding.albumImage)

        holder.viewDataBinding.root.setOnClickListener {
            // val action = AlbumFragmentDirections.actionCollectorFragmentToAlbumFragment()
            // Navigate using that action
            // holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }


    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }
}