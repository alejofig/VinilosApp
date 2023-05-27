package com.mobiles.vinilosapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobiles.vinilosapp.R
import com.mobiles.vinilosapp.databinding.ArtistItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobiles.vinilosapp.models.Artist
import com.mobiles.vinilosapp.ui.artists.ArtistFragmentDirections
import androidx.navigation.findNavController
import com.mobiles.vinilosapp.databinding.CollectorItemBinding
import com.mobiles.vinilosapp.models.Collector
import com.mobiles.vinilosapp.ui.collectors.CollectorFragmentDirections


class CollectorAdapter : RecyclerView.Adapter<CollectorAdapter.CollectorViewHolder>() {

    var collectors :List<Collector> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val withDataBinding: CollectorItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorViewHolder.LAYOUT,
            parent,
            false)

        return CollectorViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        val collector = collectors[position]

        holder.viewDataBinding.also {
            it.collector = collector
        }

        holder.viewDataBinding.root.setOnClickListener {
            val action = CollectorFragmentDirections.actionNavigationCollectorsToNavigationCollectorDetail(idCollector=collector.id)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return collectors.size
    }


    class CollectorViewHolder(val viewDataBinding: CollectorItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_item
        }
    }
}