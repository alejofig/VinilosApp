package com.mobiles.vinilosapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobiles.vinilosapp.R
import com.mobiles.vinilosapp.databinding.ArtistDetailItemBinding
import com.mobiles.vinilosapp.models.Artist

class ArtistDetailAdapter: RecyclerView.Adapter<ArtistDetailAdapter.ArtistDetailViewHolder>() {
    var artist : Artist = Artist(artistId = 0,
                                name = "",
                                image = "",
                                description = "",
                                birthDate = "",
                                albums =  emptyList())
        set(value) {
            field = value
            notifyItemChanged(0)
        }
    class ArtistDetailViewHolder(val viewDataBinding: ArtistDetailItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_detail_item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistDetailAdapter.ArtistDetailViewHolder {
        val withDataBinding: ArtistDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistDetailAdapter.ArtistDetailViewHolder.LAYOUT,
            parent,
            false)
        return ArtistDetailAdapter.ArtistDetailViewHolder(withDataBinding)
    }
    override fun onBindViewHolder(holder: ArtistDetailAdapter.ArtistDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.artist = artist
        }
        holder.viewDataBinding.root.setOnClickListener {
            //val action = CollectorFragmentDirections.actionCollectorFragmentToAlbumFragment()
            // Navigate using that action
            //holder.viewDataBinding.root.findNavController().navigate(action)
        }
        Glide.with(holder.itemView)
            .load(artist.image).apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(holder.viewDataBinding.artistImage)
    }
    override fun getItemCount(): Int {
        return 1
    }
    }