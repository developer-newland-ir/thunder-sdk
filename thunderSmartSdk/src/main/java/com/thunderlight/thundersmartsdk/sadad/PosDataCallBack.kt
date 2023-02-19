package com.thunderlight.thundersmartsdk.sadad

import com.thunderlight.thundersmartsdk.data.PosData

/**
 * @author Created by M.Moradikia
 * @date  2/14/2023
 * @company Thunder-Light
 */

interface PosDataCallBack {
    fun onReceive(posData: PosData)
    fun onError(errorCode: String, errorMsg: String)
}