package com.imagine.jordanpass.tools.ext

import android.content.Context
import androidx.core.content.ContextCompat

fun Int.toCompatDrawable(context: Context) = ContextCompat.getDrawable(context, this)