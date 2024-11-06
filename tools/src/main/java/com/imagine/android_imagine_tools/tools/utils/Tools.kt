package com.imagine.android_imagine_tools.tools.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.CalendarContract
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.RequiresPermission
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.android.material.snackbar.Snackbar
import com.imagine.android_imagine_tools.tools.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


object Tools {

    @RequiresPermission(android.Manifest.permission.VIBRATE)
    fun vibrate(activity: Activity, milis: Long = 100) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val v = activity.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            v.vibrate(
                CombinedVibration.createParallel(
                    VibrationEffect.createOneShot(
                        milis,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val v = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(VibrationEffect.createOneShot(milis, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            val v = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(milis)
        }


    }

    /**
     * calculate and returns black or white color based on image color
     * @param image
     * @param context
     * @return color (black or white)
     * */

    suspend fun getColorBasedOnBackgroundImage(image: Any?, context: Context): Int {
        //get image as bitmap
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(image)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        val bitmap = (result as BitmapDrawable).bitmap

        //get average image color
        val bgColor = calculateAverageColorOfABitmap(bitmap, 10)

        //get icon color based on background color (average image color)
        return getColorBasedOnBackgroundColor(bgColor)
    }

    /**
     *  calculates and returns black or white color based on background color
     *  @param color background color
     *  @return color (black or white)
     * */
    fun getColorBasedOnBackgroundColor(@ColorInt color: Int): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val luminance = (0.2126 * red + 0.7152 * green + 0.0722 * blue) / 255
        return if (luminance > 0.5) Color.BLACK else Color.WHITE
    }

    /**
    pixelSpacing tells how many pixels to skip each pixel.
    If pixelSpacing > 1: the average color is an estimate, but higher values mean better performance
    If pixelSpacing == 1: the average color will be the real average
    If pixelSpacing < 1: the method will most likely crash (don't use values below 1)
     */
    fun calculateAverageColorOfABitmap(bitmap: Bitmap, pixelSpacing: Int): Int {
        var R = 0
        var G = 0
        var B = 0
        val height = bitmap.height
        val width = bitmap.width
        var n = 0
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        var i = 0
        while (i < pixels.size) {
            val color = pixels[i]
            R += Color.red(color)
            G += Color.green(color)
            B += Color.blue(color)
            n++
            i += pixelSpacing
        }
        return Color.rgb(R / n, G / n, B / n)
    }

    fun showKeyboard(activity: Activity, view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard(activity: Activity, view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun addEventToCal(
        context: Context,
        title: String,
        startDate: String,
        endDate: String,
        location: String
    ) {
        val cal = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss aa", Locale.ENGLISH)
        try {
            val intent = Intent(Intent.ACTION_EDIT)
            intent.setType("vnd.android.cursor.item/event")
            if (!startDate.isEmpty()) {
                cal.time = dateFormatter.parse(startDate)
                intent.putExtra("beginTime", cal.timeInMillis)
            }
            if (!endDate.isEmpty()) {
                cal.time = dateFormatter.parse(endDate)
                intent.putExtra(CalendarContract.Events.DTEND, cal.timeInMillis)
            }
            if (!location.isEmpty()) {
                intent.putExtra(
                    CalendarContract.Events.EVENT_LOCATION,
                    location
                )
            }
            intent.putExtra("title", title)
            context.startActivity(intent)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    fun openGoogleMapsDirections(
        originLatLng: List<String>,
        dstLatLng: List<String>,
        activity: Activity
    ) {

        val gmmIntentUri = if (originLatLng.size == 2) {
            Uri.parse("https://www.google.com/maps/dir/?api=1&origin=${originLatLng[0]},${originLatLng[1]}&destination=${dstLatLng[0]},${dstLatLng[1]}&travelmode=driving&dir_action=navigate")
        } else {
            Uri.parse("google.navigation:q=${dstLatLng[0]},${dstLatLng[1]}")
        }

        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        if (mapIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(mapIntent)
        }
    }

    fun openGoogleMapsMarker(activity: Activity, marker: String, lat: String, lng: String) {
        val gmmIntentUri = Uri.parse("geo:$lat,${lng}?q=" + Uri.encode(marker))
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.resolveActivity(activity.packageManager)?.let {
            activity.startActivity(mapIntent)
        }
    }

    fun openAppOrWebsite(uri: String, url: String, activity: Activity) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                //check if app is installed
                val list = activity.packageManager.queryIntentActivities(
                    intent,
                    PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
                )

                //if not installed open website
                if (list.size == 0) {
                    intent.data = Uri.parse(url)
                }

                activity.startActivity(intent)

            } else {

                //check if app is installed
                val list =
                    activity.packageManager.queryIntentActivities(
                        intent,
                        PackageManager.MATCH_DEFAULT_ONLY
                    )

                //if not installed open website
                if (list.size == 0) {
                    intent.data = Uri.parse(url)
                }
                activity.startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



}