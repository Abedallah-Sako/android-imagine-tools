package com.imagine.android_imagine_tools

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.icu.lang.UProperty.INT_START
import android.os.Bundle
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.imagine.android_imagine_tools.tools.ext.logd
import com.imagine.android_imagine_tools.tools.ext.toPx
import com.imagine.android_imagine_tools.tools.ui.fragments.webView.NotificationRequestFragment
import com.imagine.android_imagine_tools.tools.ui.views.customAlertDialog.CustomAlertDialogBuilder
import com.imagine.android_imagine_tools.tools.ui.views.imageTextList.ImageTextList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}