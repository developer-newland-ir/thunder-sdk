package com.thunderlight.sdk.utils

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager

/**
 * @author Created by M.Moradikia
 * @date  2/19/2023
 * @company Thunder-Light
 */

fun Bundle.toString2(): String {
    val TAG = "Bundle.toString"
    var x = ""
    try {
        val bundle = this
        if (bundle != null) {
            for (key in bundle.keySet()) {
                Log.e(TAG, key + " : " + if (bundle[key] != null) bundle[key] else "NULL")
                x += "\n" + key + " : " + if (bundle[key] != null) bundle[key] else "NULL"
            }
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return x
}

fun strToDigit(value: String): String {
    if (value.isNullOrEmpty())
        return ""
    return value.replace("[^0-9]".toRegex(), "")
}

fun hideSoftKeyboard(context: Activity) {
    try {

        Log.i("TAG", "-------------------------------------- hideSoftKeyboard ")
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(context.window.decorView.rootView.windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}