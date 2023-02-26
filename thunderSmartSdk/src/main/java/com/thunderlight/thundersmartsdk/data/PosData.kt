package com.thunderlight.thundersmartsdk.data

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readBundle(Bundle::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(timeStamp)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(terminalId)
        parcel.writeString(merchantId)
        parcel.writeString(merchantName)
        parcel.writeString(posSerial)
        parcel.writeString(posPartNumber)
        parcel.writeString(posBrandName)
        parcel.writeString(posModel)
        parcel.writeString(posCode)
        parcel.writeString(appVersion)
        parcel.writeString(sdkVersion)
        parcel.writeString(telNo)
        parcel.writeString(mobileNo)
        parcel.writeString(addressFa)
        parcel.writeBundle(extraData)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PosData> {
        override fun createFromParcel(parcel: Parcel): PosData {
            return PosData(parcel)
        }

        override fun newArray(size: Int): Array<PosData?> {
            return arrayOfNulls(size)
        }
    }
}