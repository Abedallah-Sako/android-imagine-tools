package com.imagine.jordanpass.tools.views.imageTextList

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.imagine.jordanpass.tools.databinding.ItemImageTextListBinding

class ImageTextListAdapter : Adapter<ViewHolder>() {

    inner class ImageTextListViewHolder(private val binding: ItemImageTextListBinding) :
        ViewHolder(binding.root) {

        fun bind(item: Pair<Drawable?, String?>) {

            binding.itemImageTextListImageView.load(item.first) {
                crossfade(true)
            }

            binding.itemImageTextListTextView.text = item.second

        }


    }

    private val diffCallback = object : DiffUtil.ItemCallback<Pair<Drawable?, String?>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Drawable?, String?>,
            newItem: Pair<Drawable?, String?>
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Pair<Drawable?, String?>,
            newItem: Pair<Drawable?, String?>
        ): Boolean {
            return oldItem == newItem
        }

    }

    private val diff = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemImageTextListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageTextListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = diff.currentList[position]
        (holder as ImageTextListViewHolder).bind(item)
    }

    fun submitList(list: List<Pair<Drawable?, String?>>) {
        diff.submitList(list)
    }

}