package com.imagine.jordanpass.tools.views.customAlertDialog

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import androidx.core.view.updatePaddingRelative
import com.google.android.material.button.MaterialButton
import com.imagine.jordanpass.tools.databinding.DialogCustomBinding

class CustomAlertDialogBuilder(private val activity:Activity):AlertDialog.Builder(activity) {
    private val binding = DialogCustomBinding.inflate(activity.layoutInflater)
    private lateinit var dialog:AlertDialog

    init {
        this.setView(binding.root)
    }

    //--------------------------- Title ------------------------

    override fun setTitle(titleId: Int): CustomAlertDialogBuilder {
        binding.customDialogTitleTextView.text = context.getString(titleId)
        return this
    }

    override fun setTitle(title: CharSequence?): CustomAlertDialogBuilder {
        binding.customDialogTitleTextView.text = title
        return this
    }

    fun setTitleTextColor(color:Int):CustomAlertDialogBuilder{
        binding.customDialogTitleTextView.setTextColor(ContextCompat.getColor(context,color))
        return this
    }

    fun setTitleTextColor(color:String):CustomAlertDialogBuilder{
        binding.customDialogTitleTextView.setTextColor(Color.parseColor(color))
        return this
    }

    fun getTitleTextView(): TextView {
        return binding.customDialogTitleTextView
    }


    //--------------------------- Message ------------------------

    override fun setMessage(messageId: Int): CustomAlertDialogBuilder {
        binding.customDialogDescriptionTextView.text = context.getString(messageId)
        return this
    }

    override fun setMessage(message: CharSequence?): CustomAlertDialogBuilder {
        binding.customDialogDescriptionTextView.text = message
        return this
    }

    fun setMessageTextColor(color:Int):CustomAlertDialogBuilder{
        binding.customDialogDescriptionTextView.setTextColor(ContextCompat.getColor(context,color))
        return this
    }

    fun setMessageTextColor(color:String):CustomAlertDialogBuilder{
        binding.customDialogDescriptionTextView.setTextColor(Color.parseColor(color))
        return this
    }

    fun getMessageTextView(): TextView {
        return binding.customDialogDescriptionTextView
    }


    //--------------------------- Positive Button ------------------------

    override fun setPositiveButton(
        textId: Int,
        listener: DialogInterface.OnClickListener?
    ): CustomAlertDialogBuilder {
        binding.customDialogPositiveMtrlButton.text = context.getString(textId)

        binding.customDialogPositiveMtrlButton.setOnClickListener {
            listener?.onClick(dialog, DialogInterface.BUTTON_POSITIVE)
        }

        return this
    }

    override fun setPositiveButton(
        text: CharSequence?,
        listener: DialogInterface.OnClickListener?
    ): CustomAlertDialogBuilder {
        binding.customDialogPositiveMtrlButton.text = text

        binding.customDialogPositiveMtrlButton.setOnClickListener {
            listener?.onClick(dialog, DialogInterface.BUTTON_POSITIVE)
        }
        return this
    }

    fun setPositiveButtonTextColor(color:Int):CustomAlertDialogBuilder{
        binding.customDialogPositiveMtrlButton.setTextColor(color)
        return this
    }

    fun setPositiveButtonTextColor(color:String):CustomAlertDialogBuilder{
        binding.customDialogPositiveMtrlButton.setTextColor(Color.parseColor(color))
        return this
    }

    fun setPositiveButtonBackgroundColor(color:Int):CustomAlertDialogBuilder{
        binding.customDialogPositiveMtrlButton.backgroundTintList = ColorStateList.valueOf(color)
        return this
    }

    fun setPositiveButtonBackgroundColor(color:String):CustomAlertDialogBuilder{
        binding.customDialogPositiveMtrlButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
        return this
    }

    fun getPositiveButton(): MaterialButton {
        return binding.customDialogPositiveMtrlButton
    }

    //--------------------------- Negative Button ------------------------

    override fun setNegativeButton(
        textId: Int,
        listener: DialogInterface.OnClickListener?
    ): CustomAlertDialogBuilder {

        binding.customDialogNegativeMtrlButton.text = context.getString(textId)
        binding.customDialogNegativeMtrlButton.setOnClickListener {
            listener?.onClick(dialog, DialogInterface.BUTTON_NEGATIVE)
        }
        return this
    }

    override fun setNegativeButton(
        text: CharSequence?,
        listener: DialogInterface.OnClickListener?
    ): CustomAlertDialogBuilder {

        binding.customDialogNegativeMtrlButton.text = text
        binding.customDialogNegativeMtrlButton.setOnClickListener {
            listener?.onClick(dialog, DialogInterface.BUTTON_NEGATIVE)
        }
        return this
    }

    fun setNegativeButtonTextColor(color:Int):CustomAlertDialogBuilder{
        binding.customDialogNegativeMtrlButton.setTextColor(color)
        return this
    }

    fun setNegativeButtonTextColor(color:String):CustomAlertDialogBuilder{
        binding.customDialogNegativeMtrlButton.setTextColor(Color.parseColor(color))
        return this
    }

    fun setNegativeButtonBackgroundColor(color:Int):CustomAlertDialogBuilder{
        binding.customDialogNegativeMtrlButton.backgroundTintList = ColorStateList.valueOf(color)
        return this
    }

    fun setNegativeButtonBackgroundColor(color:String):CustomAlertDialogBuilder{
        binding.customDialogNegativeMtrlButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
        return this
    }

    fun getNegativeButton(): MaterialButton {
        return binding.customDialogNegativeMtrlButton
    }

    //--------------------------- Dialog ------------------------

    fun setBackgroundColor(color:Int):CustomAlertDialogBuilder{
        binding.customDialogContainerConstraintLayout.background.colorFilter = PorterDuffColorFilter(color,PorterDuff.Mode.SRC)
        return this
    }

    fun setBackgroundColor(color:String):CustomAlertDialogBuilder{
        binding.customDialogContainerConstraintLayout.background.colorFilter = PorterDuffColorFilter(Color.parseColor(color),PorterDuff.Mode.SRC)
        return this
    }

    fun setDialogHorizontalMargin(margin:Int):CustomAlertDialogBuilder{
        binding.customDialogTopConstraintLayout.updatePaddingRelative(start = margin,end = margin)
        return this
    }



    fun build():AlertDialog{
        dialog = this.create()

        //make original background transparent
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }



}