package com.imagine.android_imagine_tools.tools.ui.views.imageTextList

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imagine.android_imagine_tools.tools.R

class ImageTextList @JvmOverloads constructor(
    private val context: Context, private val attr: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : FrameLayout(context, attr, defStyleAttr) {
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageTextListAdapter

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_image_text_list, this, true)
        recyclerView = findViewById(R.id.layout_image_text_list_recycler_view)

        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        adapter = ImageTextListAdapter()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    fun setAlignDrawableTop(alignDrawableTop: Boolean) {
        adapter.setAlignDrawableTop(alignDrawableTop)
    }

    fun setCrossFade(crossFade: Boolean) {
        adapter.setCrossFade(crossFade)
    }

    fun doUseCoil(enabled:Boolean){
        adapter.doUseCoil(enabled)
    }

    fun setOnBindCallback(callback:(TextView, ImageView) -> Unit){
        adapter.setOnBindCallback(callback)
    }

    fun submitList(list: List<Pair<Drawable?, Spannable?>>) {
        adapter.submitList(list)
    }

    fun submitList(list: List<Spannable?>, drawable: Drawable?) {
        adapter.submitList(list.map { Pair(drawable, it) })
    }


}