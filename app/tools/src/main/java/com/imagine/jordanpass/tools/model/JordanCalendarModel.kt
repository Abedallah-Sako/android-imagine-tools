package com.imagine.jordanpass.tools.model


import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class JordanCalendarModel(
    @SerializedName("Events", alternate = ["item"])
    val events: List<Event?>?
) {
    data class Event(
        @SerializedName("ArabicBriefDescription")
        val arabicBriefDescription: String?,
        @SerializedName("ArabicLargeImage")
        val arabicLargeImage: String?, // https://calendar.jo/UI/photos/592c970e-7195-4533-ada3-9cf0cfffb54f.jpg
        @SerializedName("ArabicSmalImage")
        val arabicSmalImage: String?, // https://calendar.jo/UI/photos/98fd5660-ba17-477d-aded-ad9ac7c8d485.jpg
        @SerializedName("ArabicTitle")
        val arabicTitle: String?, // بازار صيف عمان
        @SerializedName("BriefDescription")
        val briefDescription: String?,
        @SerializedName("Category")
        val category: String?, // Entertainment
        @SerializedName("City")
        val city: String?, // Amman
        @SerializedName("Date")
        val date: String?, // 3/08/2024 12:00:00 AM
        @SerializedName("FacebookEventAPI")
        val facebookEventAPI: String?,
        @SerializedName("FacebookPageAPI")
        val facebookPageAPI: String?,
        @SerializedName("Featured")
        val featured: String?, // False
        @SerializedName("FromDateTime")
        val fromDateTime: String?, // 3/08/2024 12:00:00 AM
        @SerializedName("JTPEventsId")
        val jTPEventsId: String?, // 7253
        @SerializedName("LargeImage")
        val largeImage: String?, // https://calendar.jo/UI/photos/fc48d416-8ae1-4478-a776-678ed8910177.jpg
        @SerializedName("SmalImage")
        val smalImage: String?, // https://calendar.jo/UI/photos/0f7dbd2b-645a-479e-b595-24e9b6ff0684.jpg
        @SerializedName("Title")
        val title: String?, // Amman Summer Bazaar
        @SerializedName("ToDateTime")
        val toDateTime: String?, // 4/08/2024 10:00:00 PM
        @SerializedName("TypeTicket")
        val typeTicket: String?, // Registration
        @SerializedName("FullDescription")
        val fullDescription: String?,
        @SerializedName("Website")
        val website: String?,
        @SerializedName("BookingWeb")
        val bookingWeb: String?,
        @SerializedName("Latitude")
        val latitude: String?,
        @SerializedName("Longitude")
        val longitude: String?,
    ) {


        fun getParsedDate(): String {
            var finalDate = ""

            //get locale
            val locale = if (Locale.getDefault().language == "ar") {
                Locale("ar")
            } else {
                Locale("en")
            }

            val formatter = SimpleDateFormat("dd/MM/yyyy", locale)
            val toFormatter = SimpleDateFormat("E, d MMM yyyy", locale)

            //used if the dates are on multiple days
            val dayFormatter = SimpleDateFormat("dd", locale)
            val monthAndYearFormatter = SimpleDateFormat("MMMM, yyyy", locale)

            //used if fromDateTime and toDateTime are empty or null
            val yearFormatter = SimpleDateFormat("MMMM dd yyyy", locale)



            if (!fromDateTime.isNullOrEmpty() && !toDateTime.isNullOrEmpty()) {

                try {
                    val fromDateString = fromDateTime.split(" ").get(0)
                    val toDateString = toDateTime.split(" ").get(0)

                    val dateFrom = formatter.parse(fromDateString)
                    val dateTo = formatter.parse(toDateString)

                    val dateFromCalendar = Calendar.getInstance()
                    dateFromCalendar.time = dateFrom

                    val dateToCalendar = Calendar.getInstance()
                    dateToCalendar.time = dateTo

                    //check if the dates are not on the same year
                    if (dateFromCalendar[Calendar.YEAR] != dateToCalendar[Calendar.YEAR]) {

                        //25 January, 2022 - 25 March, 2024
                        finalDate = "${toFormatter.format(dateFrom.time)} - ${toFormatter.format(dateTo.time)}"
                    }
                    //check if the dates are not on the same month
                    else if (dateFromCalendar[Calendar.MONTH] != dateToCalendar[Calendar.MONTH]){

                        //25 January, 2022 - 25 March, 2022
                        finalDate = "${toFormatter.format(dateFrom.time)} - ${toFormatter.format(dateTo.time)}"
                    }
                    //check if the dates are on the same day
                    else if (dateFromCalendar[Calendar.DAY_OF_YEAR] == dateToCalendar[Calendar.DAY_OF_YEAR] &&
                        dateFromCalendar[Calendar.DAY_OF_YEAR] == dateToCalendar[Calendar.DAY_OF_YEAR]) {

                        //25 January, 2022
                        finalDate = toFormatter.format(dateFrom.time)
                    }
                    //else the dates are on the same month and year but not on the same day (multiple days)
                    else {
                        val fromDay = dayFormatter.format(dateFrom.time)
                        val toDay = dayFormatter.format(dateTo.time)

                        //since both fromDate and toDate are on the same month and year, it does not matter which one to use
                        val monthAndYear = monthAndYearFormatter.format(dateFrom.time)


                        //02 - 25 January, 2022
                        finalDate = "$fromDay - $toDay $monthAndYear"
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else {
                val dateSplitter: Array<String> =
                    fromDateTime?.split(" ".toRegex())?.dropLastWhile { it.isEmpty() }
                        ?.toTypedArray() ?: arrayOf("")
                try {
                    val dateFrom = formatter.parse(dateSplitter[0])
                    finalDate = yearFormatter.format(dateFrom.time)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            return finalDate
        }


        fun getParsedTime(): String {
            var finalDate: String = fromDateTime ?: ""


            val locale = if (Locale.getDefault().language == "ar") {
                Locale("ar")
            } else {
                Locale("en")
            }

            val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", locale)
            val timeFormatter = SimpleDateFormat("h:mm a", locale)


            if (fromDateTime?.isEmpty() == false && toDateTime?.isEmpty() == false) {
                var dateFrom = Date()
                var dateTo = Date()
                try {
                    dateFrom = formatter.parse(fromDateTime)
                    dateTo = formatter.parse(toDateTime)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                if (fromDateTime.split(" ")[1] == toDateTime.split(" ")[1]) {
                    finalDate = timeFormatter.format(dateFrom.time)
                } else {
                    try {
                        val calendarFrom = Calendar.getInstance()
                        val calendarTo = Calendar.getInstance()
                        calendarFrom.time = dateFrom
                        calendarTo.time = dateTo
                        val from = timeFormatter.format(dateFrom.time)
                        val to = timeFormatter.format(dateTo.time)
                        finalDate = "$from - $to"
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else {
                val dateSplitter: Array<String> =
                    fromDateTime?.split(" ".toRegex())?.dropLastWhile { it.isEmpty() }
                        ?.toTypedArray() ?: arrayOf("")
                try {
                    val dateFrom = formatter.parse(dateSplitter[0])
                    finalDate = timeFormatter.format(dateFrom.time)
                } catch (e: Exception) {
                }
            }
            return finalDate
        }

        fun getAvailableImageURL(): String {
            var url = ""

            if (Locale.getDefault().language == "ar") {
                if (arabicLargeImage != null) {
                    url = arabicLargeImage
                } else {
                    url = arabicSmalImage ?: ""
                }
            } else {
                if (largeImage != null) {
                    url = largeImage
                } else {
                    url = smalImage ?: ""
                }
            }

            return url
        }

    }

}