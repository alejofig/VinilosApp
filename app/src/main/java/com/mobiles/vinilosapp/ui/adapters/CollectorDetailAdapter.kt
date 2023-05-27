package com.mobiles.vinilosapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobiles.vinilosapp.R
import com.mobiles.vinilosapp.databinding.CollectorDetailItemBinding
import com.mobiles.vinilosapp.models.Collector

class CollectorDetailAdapter: RecyclerView.Adapter<CollectorDetailAdapter.CollectorDetailViewHolder>() {
    var collector : Collector = Collector(id = 0,
        name = "",
        telephone = "",
        email = "",
        comments =  emptyList())
        set(value) {
            field = value
            notifyItemChanged(0)
        }
    class CollectorDetailViewHolder(val viewDataBinding: CollectorDetailItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_detail_item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorDetailAdapter.CollectorDetailViewHolder {
        val withDataBinding: CollectorDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorDetailAdapter.CollectorDetailViewHolder.LAYOUT,
            parent,
            false)
        return CollectorDetailAdapter.CollectorDetailViewHolder(withDataBinding)
    }
    override fun onBindViewHolder(holder: CollectorDetailAdapter.CollectorDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.collector = collector
        }
        holder.viewDataBinding.root.setOnClickListener {
            //val action = CollectorFragmentDirections.actionCollectorFragmentToAlbumFragment()
            // Navigate using that action
            //holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }
    override fun getItemCount(): Int {
        return 1
    }
}