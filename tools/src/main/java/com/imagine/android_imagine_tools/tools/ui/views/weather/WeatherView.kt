package com.imagine.android_imagine_tools.tools.ui.views.weather

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.imagine.android_imagine_tools.tools.R
import com.imagine.android_imagine_tools.tools.utils.PagerIndicatorDecoration

class WeatherView @JvmOverloads constructor(
    context: Context, private val attr: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attr, defStyleAttr) {

    var indicatorSelectedColor: Int = Color.parseColor("#000000")
    var indicatorDefaultColor: Int = Color.parseColor("#c2c1c0")
    var textColor: Int = 0

    //recyclerview (use recyclerView only after view init)
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.weather_recycler_view) }
    private val adapter: WeatherViewAdapter by lazy { WeatherViewAdapter() }
    private val pagerItemDecoration: ItemDecoration by lazy {
        PagerIndicatorDecoration(
            indicatorSelectedColor, indicatorDefaultColor
        )
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_weather, this, true)
        LayoutInflater.from(context).inflate(R.layout.layout_loading_gps_enable, this, true)

        setViewAttributes()
        setRecyclerView()
//        setupDummyData()

    }

    /**
     * Get attributes (indicatorSelectedColor,indicatorDefaultColor) passed in xml
     * */
    private fun setViewAttributes() {
        val arr: TypedArray = context.obtainStyledAttributes(attr, R.styleable.WeatherView)
        indicatorSelectedColor = arr.getColor(
            R.styleable.WeatherView_indicatorSelectedColor, Color.parseColor("#000000")
        )
        indicatorDefaultColor =
            arr.getColor(R.styleable.WeatherView_indicatorDefaultColor, Color.parseColor("#c2c1c0"))
        textColor =
            arr.getColor(R.styleable.WeatherView_WeatherTextColor, Color.parseColor("#ffffff"))

        arr.recycle()
    }

    private fun setRecyclerView() {

        //set layout manager
        recyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )

        //setup snap helper
        val helper = PagerSnapHelper()
        helper.attachToRecyclerView(recyclerView)

        //init adapter
        recyclerView.adapter = adapter

        //set view text color
        adapter.setTextColor(textColor)

        //item decoration
        recyclerView.addItemDecoration(pagerItemDecoration)

        setupInitalItem()

    }
    //todo set item decoration colors

    fun setupInitalItem() {
        val temp = mutableListOf<WeatherModel.WeatherModelItem>()

        temp.add(
            WeatherModel.WeatherModelItem(
                "", "", "", WeatherModel.WeatherModelItem.DayTime(
                    50, R.drawable.weather_loading,
                    WeatherModel.WeatherModelItem.DayTime.Pressure(0.0, 0.0),
                    WeatherModel.WeatherModelItem.DayTime.RelativeHumidity(0.0, 0.0), 100,
                    WeatherModel.WeatherModelItem.DayTime.Status("test", "test", "-1"),
                    WeatherModel.WeatherModelItem.DayTime.Temperature(0, 0),
                    WeatherModel.WeatherModelItem.DayTime.WindSpeed(0.0, 0.0)
                ),
                WeatherModel.WeatherModelItem.NightTime(
                    50,
                    R.drawable.weather_loading,
                    WeatherModel.WeatherModelItem.NightTime.Pressure(0.0, 0.0),
                    WeatherModel.WeatherModelItem.NightTime.RelativeHumidity(0.0, 0.0),
                    100,
                    WeatherModel.WeatherModelItem.NightTime.Status("test", "test", "-1"),
                    WeatherModel.WeatherModelItem.NightTime.Temperature(0, 0),
                    WeatherModel.WeatherModelItem.NightTime.WindSpeed(0.0, 0.0)
                )
            )
        )


        adapter.submitList(temp)
    }

    fun setGpsButtonOnClick(callback: ((Boolean) -> Unit)) {
        findViewById<AppCompatButton>(R.id.btn_gps_enable).setOnClickListener {
            callback.invoke(true)
        }
    }


    fun showOrHideEnableGpsButton(show: Boolean) {
        if (show) {
            findViewById<ViewGroup>(R.id.gps_enable).visibility = View.VISIBLE
        } else {
            findViewById<ViewGroup>(R.id.gps_enable).visibility = View.GONE
        }
    }

    fun showOrHideWeatherProgressBar(show: Boolean) {
        if (show) {
            findViewById<ProgressBar>(R.id.weather_progress_bar).visibility = View.VISIBLE
        } else {
            findViewById<ProgressBar>(R.id.weather_progress_bar).visibility = View.GONE

        }
    }


    fun submitList(list: List<WeatherModel.WeatherModelItem>?) {
        if (list == null) {
            adapter.clearList()
            showOrHideEnableGpsButton(true)
        } else {
            adapter.submitList(list)
            showOrHideEnableGpsButton(false)

        }
    }
}