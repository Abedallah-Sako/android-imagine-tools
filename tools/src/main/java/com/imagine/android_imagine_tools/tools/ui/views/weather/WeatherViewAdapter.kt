package com.imagine.android_imagine_tools.tools.ui.views.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.imagine.android_imagine_tools.tools.R
import com.imagine.android_imagine_tools.tools.databinding.ItemWeatherBinding
import java.sql.Date
import java.sql.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherViewAdapter : Adapter<ViewHolder>() {
    private var textColor = 0
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val dateFormat2 = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
    private val stamp = Timestamp(System.currentTimeMillis())
    private var stamp2 = stamp.time
    private val date = Date(stamp2)

    private val formattedDate = dateFormat.format(date)
    private var arabicNumberFormat = NumberFormat.getInstance(Locale("ar"))

    inner class WeatherViewHolder(private val binding: ItemWeatherBinding) :
        ViewHolder(binding.root) {

        fun bind(item: WeatherModel.WeatherModelItem) {

            if (item.dayTime.image is Int) {
                binding.weatherImage.load(item.dayTime.image) {
//                    placeholder(R.drawable.weather_loading)
                    error(R.drawable.weather_loading)
                }
                binding.view.visibility = View.GONE
                binding.location.visibility = View.GONE

            } else {
                binding.view.visibility = View.VISIBLE
                binding.location.visibility = View.VISIBLE
            }

            if (item.day.isNotEmpty()) {
                if (formattedDate != item.day) {
                    stamp2 += 86440000
                }
            }


//            date.logd("date")
//            formattedDate.logd("formattedDate")

//            item.dayTime.ends.logd("Ends")
//            (formattedDate != item.day).logd("Bolivian")

            if ((item.dayTime.starts * 1000) <= stamp2 && stamp2 <= (item.dayTime.ends * 1000)) {

                //Set text based on device language
                if (Locale.getDefault().language == "en") {
                    binding.weatherImage.scaleX = 1f
                    binding.weatherState.text = item.dayTime.status.englishDesc
                    binding.weatherDegrees.text = item.dayTime.temperature.max.toString() + "°"
                    binding.weatherDate.text = dateFormat2.format(dateFormat.parse(item.day))
                    binding.weatherLocation.text = item.cityName.capitalize()
                } else if (Locale.getDefault().language == "ar") {
                    binding.weatherImage.scaleX = -1f
                    binding.weatherState.text = item.dayTime.status.arabicDesc
                    binding.weatherDegrees.text =
                        arabicNumberFormat.format(item.dayTime.temperature.max).toString() + "°"
                    binding.weatherDate.text = dateFormat2.format(dateFormat.parse(item.day))
                    binding.weatherLocation.text = item.cityNameAr.capitalize()
                }


                //Set text color
                binding.weatherState.setTextColor(textColor)
                binding.weatherDegrees.setTextColor(textColor)
                binding.weatherDate.setTextColor(textColor)
                binding.weatherLocation.setTextColor(textColor)
//                item.dayTime.image.logd("item.dayTime.image")
                //set image
                binding.weatherImage.load(item.dayTime.image) {
//                    placeholder(R.drawable.weather_loading)
                    error(R.drawable.weather_loading)
                }
            }

            if ((item.nightTime.starts * 1000) <= stamp2 && stamp2 <= (item.nightTime.ends * 1000)) {

                //Set text based on device language
                if (Locale.getDefault().language == "en") {
                    binding.weatherImage.scaleX = 1f
                    binding.weatherState.text = item.nightTime.status.englishDesc
                    binding.weatherDegrees.text = item.nightTime.temperature.max.toString() + "°"
                    binding.weatherDate.text = dateFormat2.format(dateFormat.parse(item.day))
                    binding.weatherLocation.text = item.cityName.capitalize()
                } else if (Locale.getDefault().language == "ar") {
                    binding.weatherImage.scaleX = -1f
                    binding.weatherState.text = item.dayTime.status.arabicDesc
                    binding.weatherDegrees.text =
                        arabicNumberFormat.format(item.dayTime.temperature.max).toString() + "°"
                    binding.weatherDate.text = dateFormat2.format(dateFormat.parse(item.day))
                    binding.weatherLocation.text = item.cityNameAr.capitalize()
                }


                //Set text color
                binding.weatherState.setTextColor(textColor)
                binding.weatherDegrees.setTextColor(textColor)
                binding.weatherDate.setTextColor(textColor)
                binding.weatherLocation.setTextColor(textColor)

                //set image
                binding.weatherImage.load(item.nightTime.image) {
//                    placeholder(R.drawable.weather_loading)
                    error(R.drawable.weather_loading)
                }
            }

        }


    }

    private val diffCallBack = object : DiffUtil.ItemCallback<WeatherModel.WeatherModelItem>() {
        override fun areItemsTheSame(
            oldItem: WeatherModel.WeatherModelItem,
            newItem: WeatherModel.WeatherModelItem
        ): Boolean {
            return oldItem.dayTime.status.id == newItem.dayTime.status.id
        }

        override fun areContentsTheSame(
            oldItem: WeatherModel.WeatherModelItem,
            newItem: WeatherModel.WeatherModelItem
        ): Boolean {
            return oldItem.dayTime.status.id == newItem.dayTime.status.id
        }

    }

    private val diff = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = diff.currentList[position]

        (holder as WeatherViewHolder).bind(item)
    }

    fun setTextColor(@ColorInt color: Int) {
        textColor = color
    }

    fun submitList(list: List<WeatherModel.WeatherModelItem>?) {
        diff.submitList(list)
    }

    fun clearList() {
        diff.submitList(null)
    }

    fun doesListHaveRealData(): Boolean {
        if (diff.currentList != null) {
            return diff.currentList.get(0).dayTime.status.id != "-1"
        }
        return true
    }
}