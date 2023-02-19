package com.thunderlight.thundersmartsdk.data

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author Created by M.Moradikia
 * @date  2/14/2023
 * @company Thunder-Light
 */
data class TransactionData(
    @SerializedName("txnType")
    var txnType: String = "",

    @SerializedName("responseCode")
    var responseCode: String = "-1",

    @SerializedName("responseMessage")
    var responseMessage: String = "",

    @SerializedName("amount")
    var amount: String = "0",

    @SerializedName("AffectiveAmount")
    var affectiveAmount: String = "0",

    @SerializedName("timeStamp")
    var timeStamp: Long = 0L,

    @SerializedName("timeFarsi")
    var timeFarsi: String = "",

    @SerializedName("date")
    var date: String = "",

    @SerializedName("time")
    var time: String = "",

    @SerializedName("trace")
    var trace: String = "",

    @SerializedName("rrn")
    var rrn: String = "",

    @SerializedName("reserveNumber")
    var reserveNumber: String = "",

    @SerializedName("truncatePan")
    var truncatePan: String = "",

    @SerializedName("bank")
    var bank: String = "",

    @SerializedName("hashPan")
    var hashPan: String = "",

    @SerializedName("shiftNumber")
    var shiftNo: Int = 0,

    @SerializedName("terminalId")
    var terminalId: String = "",

    @SerializedName("merchantId")
    var merchantId: String = "",

    @SerializedName("merchantName")
    var merchantName: String = "",

    @SerializedName("posSerial")
    var posSerial: String = "",

    @SerializedName("extraData")
    var extraData: Bundle? = null

    /*

        @SerializedName("billId")
    var billId: String = "",

    @SerializedName("billPaymentId")
    var billPaymentId: String = "",

    @SerializedName("posSerial")
    var chargePin: String = "",

    @SerializedName("posSerial")
    var chargeSerial: String = "",
     */


) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
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
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readBundle(Bundle::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(txnType)
        parcel.writeString(responseCode)
        parcel.writeString(responseMessage)
        parcel.writeString(amount)
        parcel.writeString(affectiveAmount)
        parcel.writeLong(timeStamp)
        parcel.writeString(timeFarsi)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(trace)
        parcel.writeString(rrn)
        parcel.writeString(reserveNumber)
        parcel.writeString(truncatePan)
        parcel.writeString(bank)
        parcel.writeString(hashPan)
        parcel.writeInt(shiftNo)
        parcel.writeString(terminalId)
        parcel.writeString(merchantId)
        parcel.writeString(merchantName)
        parcel.writeString(posSerial)
        parcel.writeBundle(extraData)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionData> {
        override fun createFromParcel(parcel: Parcel): TransactionData {
            return TransactionData(parcel)
        }

        override fun newArray(size: Int): Array<TransactionData?> {
            return arrayOfNulls(size)
        }
    }
}