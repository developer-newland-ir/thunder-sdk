package com.thunderlight.thundersmartsdk.generalManager

/**
 * @author Created by M.Moradikia
 * @date  2/14/2023
 * @company Thunder-Light
 */

interface ResultCallBack {
    fun onSuccess()
    fun onError(errorCode: String, errorMsg: String)
}