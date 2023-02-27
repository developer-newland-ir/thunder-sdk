package com.thunderlight.thundersmartsdk.generalManager

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import com.thunderlight.thundersmartsdk.constant.HostApp
import com.thunderlight.thundersmartsdk.constant.RequestType
import com.thunderlight.thundersmartsdk.constant.TxnInquiryType
import com.thunderlight.thundersmartsdk.sadad.SDKManager

/**
 * @author Created by M.Moradikia
 * @date  2/14/2023
 * @company Thunder-Light
 */
class GeneralSDKManager {

    private val TAG = "GeneralSDKManager"
    private var host = HostApp.HOST_UNKNOWN

    fun init(): HostApp {
        val x = HostApp.HOST_SADAD.value
        host = when (x) {
            HostApp.HOST_SADAD.value -> {
                HostApp.HOST_SADAD
            }

            HostApp.HOST_SEPEHR.value -> {
                HostApp.HOST_SEPEHR
            }
            HostApp.HOST_IRANKISH.value -> {
                HostApp.HOST_IRANKISH
            }
            else -> {
                HostApp.HOST_UNKNOWN
            }
        }
        return host
    }

    //چاپ رسید
    fun printBitmap(context: Activity, bitmap: Bitmap?, resultCallBack: ResultCallBack) {
        when (host) {
            HostApp.HOST_SADAD -> {
                val sdkManagerSadad = SDKManager()
                sdkManagerSadad.printBitmap(context, bitmap, resultCallBack)
            }

            else -> {
                resultCallBack.onError("-9999", "اپلیکیشن میزبان یافت نشد.")
            }
        }
    }

    //تراکنش موجودی
    fun inquiryBalance(context: Activity, transactionCallBack: TransactionCallBack) {
        when (host) {
            HostApp.HOST_SADAD -> {
                val sdkManagerSadad = SDKManager()
                sdkManagerSadad.inquiryBalance(context, transactionCallBack)
            }

            else -> {
                transactionCallBack.onError("-9999", "اپلیکیشن میزبان یافت نشد.")
            }
        }
    }

    //تراکنش های خرید
    fun doSaleTransaction(context: Activity, amount: String, reserveNumber: String, approveByThird: Boolean, transactionCallBack: TransactionCallBack) {
        when (host) {
            HostApp.HOST_SADAD -> {
                val sdkManagerSadad = SDKManager()
                sdkManagerSadad.doSaleTransaction(context, amount, reserveNumber, approveByThird, transactionCallBack)
            }

            else -> {
                transactionCallBack.onError("-9999", "اپلیکیشن میزبان یافت نشد.")
            }
        }
    }

    //تراکنش های  قبض، شارژ
    fun doServiceTransaction(context: Activity, requestType: RequestType, approveByThird: Boolean, transactionCallBack: TransactionCallBack) {
        when (host) {
            HostApp.HOST_SADAD -> {
                val sdkManagerSadad = SDKManager()
                sdkManagerSadad.doServiceTransaction(context, requestType, approveByThird, transactionCallBack)
            }

            else -> {
                transactionCallBack.onError("-9999", "اپلیکیشن میزبان یافت نشد.")
            }
        }
    }

    //استعلام تراکنش از طریق Trace ,rrn, reserveNumber
    fun inquiryTransactionData(context: Activity, inquiryType: TxnInquiryType, inquiryId: String, printReceipt: Boolean, transactionCallBack: TransactionCallBack) {
        when (host) {
            HostApp.HOST_SADAD -> {
                val sdkManagerSadad = SDKManager()
                sdkManagerSadad.inquiryTransactionData(context, inquiryType, inquiryId, printReceipt, transactionCallBack)
            }

            else -> {
                transactionCallBack.onError("-9999", "اپلیکیشن میزبان یافت نشد.")
            }
        }
    }

    //استعلام اطلاعات پوز
    fun inquiryPosData(context: Activity, posDataCallBack: PosDataCallBack) {
        when (host) {
            HostApp.HOST_SADAD -> {
                val sdkManagerSadad = SDKManager()
                sdkManagerSadad.inquiryPosData(context, posDataCallBack)
            }

            else -> {
                posDataCallBack.onError("-9999", "اپلیکیشن میزبان یافت نشد.")
            }
        }
    }

    //انجام تراکنش تبادل کلید
    fun doConfiguration(context: Activity, resultCallBack: ResultCallBack) {
        when (host) {
            HostApp.HOST_SADAD -> {
                val sdkManagerSadad = SDKManager()
                sdkManagerSadad.doKeyChange(context, resultCallBack)
            }

            else -> {
                resultCallBack.onError("-9999", "اپلیکیشن میزبان یافت نشد.")
            }
        }
    }
}