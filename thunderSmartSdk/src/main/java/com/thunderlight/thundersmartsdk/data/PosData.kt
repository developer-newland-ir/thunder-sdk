package com.thunderlight.thundersmartsdk.data

import android.os.Bundle
import com.google.gson.annotations.SerializedName

/**
 * @author Created by M.Moradikia
 * @date  2/14/2023
 * @company Thunder-Light
 */
data class PosData(

    @SerializedName("timeStamp")
    var timeStamp: Long = 0L,

    @SerializedName("date")
    var date: String = "",

    @SerializedName("time")
    var time: String = "",

    @SerializedName("terminalId")
    var terminalId: String = "",

    @SerializedName("merchantId")
    var merchantId: String = "",

    @SerializedName("merchantName")
    var merchantName: String = "",

    @SerializedName("posSerial")
    var posSerial: String = "",

    @SerializedName("posPartNumber")
    var posPartNumber: String = "",

    @SerializedName("posBrandName")
    var posBrandName: String = "",

    @SerializedName("posModel")
    var posModel: String = "",

    //کد کارتخوان
    @SerializedName("posCode")
    var posCode: String = "",

    @SerializedName("appVersion")
    var appVersion: String = "",

    @SerializedName("sdkVersion")
    var sdkVersion: String = "",

    @SerializedName("telNo")
    var telNo: String = "",

    @SerializedName("mobileNo")
    var mobileNo: String = "",

    @SerializedName("addressFa")
    var addressFa: String = "",

    @SerializedName("extraData")
    var extraData: Bundle? = null
) : java.io.Serializable