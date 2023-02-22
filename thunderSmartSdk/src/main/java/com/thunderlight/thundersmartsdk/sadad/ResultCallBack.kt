package com.thunderlight.thundersmartsdk.sadad

/**
 * @author Created by M.Moradikia
 * @date  2/14/2023
 * @company Thunder-Light
 */

interface KeyChangeCallBack {
    fun onSuccess()
    fun onError(errorCode: String, errorMsg: String)
}