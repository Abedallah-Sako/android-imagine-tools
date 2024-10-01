package com.imagine.jordanpass.tools.ext

import android.util.Log

fun Any.handleErrors(errorString: String?): String {
    if (errorString != null && errorString == "Empty response") {
        Log.e("ViewModelsLog", "handleErrors: $errorString")
        return errorString
    }
    Log.e("ViewModelsLog", "handleErrors: $errorString")
    return errorString?:""
}