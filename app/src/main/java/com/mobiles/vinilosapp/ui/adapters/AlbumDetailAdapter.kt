package com.mobiles.vinilosapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobiles.vinilosapp.R
import com.mobiles.vinilosapp.databinding.AlbumDetailItemBinding
import com.mobiles.vinilosapp.models.Album
import com.mobiles.vinilosapp.ui.albums.AlbumDetailFragmentDirections

class AlbumDetailAdapter: RecyclerView.Adapter<AlbumDetailAdapter.AlbumDetailViewHolder>() {

    var album :Album = Album(albumId = 0, name = "", cover = "", releaseDate = "", description = "", genre = "", recordLabel = "")
        set(value) {
            field = value
            notifyItemChanged(0)
        }

    class AlbumDetailViewHolder(val viewDataBinding: AlbumDetailItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        val btnAddComment: Button = viewDataBinding.addComment
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_detail_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDetailViewHolder {
        val withDataBinding: AlbumDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumDetailViewHolder.LAYOUT,
            parent,
            false)
        return AlbumDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = album
        }
        holder.viewDataBinding.root.setOnClickListener {
            //val action = CollectorFragmentDirections.actionCollectorFragmentToAlbumFragment()
            // Navigate using that action
            //holder.viewDataBinding.root.findNavController().navigate(action)
        }
        Glide.with(holder.itemView)
            .load(album.cover).apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(holder.viewDataBinding.albumImage)



        holder.btnAddComment.setOnClickListener {
            val action = AlbumDetailFragmentDirections.actionNavigationAlbumDetailToAlbumCommentFragment(idAlbum = album.albumId!!)
            holder.viewDataBinding.root.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return 1
    }

}