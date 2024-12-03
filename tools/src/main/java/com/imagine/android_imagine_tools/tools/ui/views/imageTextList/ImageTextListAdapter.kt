package com.imagine.android_imagine_tools.tools.ui.views.imageTextList

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.imagine.android_imagine_tools.tools.databinding.ItemImageTextListBinding
import com.imagine.android_imagine_tools.tools.ext.toPx

class ImageTextListAdapter : Adapter<ViewHolder>() {
    private var alignDrawableTop = false
    private var crossFade = true
    private var callback: ((TextView,ImageView)->Unit)? = null

    inner class ImageTextListViewHolder(private val binding: ItemImageTextListBinding) :
        ViewHolder(binding.root) {

        fun bind(item: Pair<Drawable?, Spannable?>) {

            if(alignDrawableTop){
                val layoutParams = binding.itemImageTextListImageView.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.bottomToBottom = -1
                layoutParams.topMargin = 8.toPx
                binding.itemImageTextListImageView.layoutParams = layoutParams

            }
            binding.itemImageTextListImageView.load(item.first) {
                crossfade(crossFade)
            }

            binding.itemImageTextListTextView.text = item.second

            callback?.invoke(binding.itemImageTextListTextView,binding.itemImageTextListImageView)
        }


    }

    private val diffCallback = object : DiffUtil.ItemCallback<Pair<Drawable?, Spannable?>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Drawable?, Spannable?>,
            newItem: Pair<Drawable?, Spannable?>
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Pair<Drawable?, Spannable?>,
            newItem: Pair<Drawable?, Spannable?>
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

    fun submitList(list: List<Pair<Drawable?, Spannable?>>) {
        diff.submitList(list)
    }

    fun setAlignDrawableTop(alignDrawableTop: Boolean) {
        this.alignDrawableTop = alignDrawableTop
    }

    fun setCrossFade(crossFade:Boolean){
        this.crossFade = crossFade
    }
    
    fun setOnBindCallback(callback: (TextView,ImageView)->Unit){
        this.callback = callback
    }

}