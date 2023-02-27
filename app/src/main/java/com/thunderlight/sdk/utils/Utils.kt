package com.thunderlight.sdk.utils

import android.os.Bundle
import android.util.Log

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