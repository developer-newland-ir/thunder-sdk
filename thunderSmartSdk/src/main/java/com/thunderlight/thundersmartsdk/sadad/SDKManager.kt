package com.thunderlight.thundersmartsdk.sadad

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import com.thunderlight.thundersmartsdk.R
import com.thunderlight.thundersmartsdk.constant.ConstantsStr
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.ADDRESS_FA
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.AFFECTIVE_AMOUNT
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.AMOUNT
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.APP_VERSION
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.BANK
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.BILL_ID
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.BILL_PAYMENT_ID
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.CHARGE_OPERATOR_CODE
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.CHARGE_PIN_SERIAL
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.CHARGE_PIN_VOUCHER
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.CHARGE_TYPE
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.DATE
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.ERROR_CODE
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.ERROR_MESSAGE
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.EXTRA_DATA
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.HASH_PAN
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.MERCHANT_ID
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.MERCHANT_NAME
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.MOBILE_NUMBER
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.POS_BRAND_NUMBER
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.POS_CODE
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.POS_MODEL
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.POS_PART_NUMBER
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.POS_SERIAL
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.RECEIVE_STATE
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.RECEIVE_STATE_CANCEL
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.RECEIVE_STATE_ERROR
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.RECEIVE_STATE_SUCCESS
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.REQUEST_TYPE_3RD_PARTY
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.RESERVE_NUMBER
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.RESPONSE_CODE
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.RESPONSE_MESSAGE
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.RRN
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.SDK_VERSION
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.SHIFT_NO
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.SOLAR_DATE
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.TELEPHONE
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.TERMINAL_ID
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.TIME
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.TIME_STAMP
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.TRACE
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.TRUNCATE_PAN
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.TXN_TYPE
import com.thunderlight.thundersmartsdk.constant.RequestType
import com.thunderlight.thundersmartsdk.constant.TxnInquiryType
import com.thunderlight.thundersmartsdk.data.PosData
import com.thunderlight.thundersmartsdk.data.TransactionData
import com.thunderlight.thundersmartsdk.generalManager.PosDataCallBack
import com.thunderlight.thundersmartsdk.generalManager.ResultCallBack
import com.thunderlight.thundersmartsdk.generalManager.TransactionCallBack
import java.io.ByteArrayOutputStream

/**
 * @author Created by M.Moradikia
 * @date  2/14/2023
 * @company Thunder-Light
 */
internal class SDKManager {

    private val TAG = "SDKManager"

    companion object {
        private var transactionCallBack: TransactionCallBack? = null
        private var posDataCallBack: PosDataCallBack? = null
        private var resultCallBack: ResultCallBack? = null
        private val ca = Intent.CATEGORY_LAUNCHER //category
    }

    //چاپ رسید
    fun printBitmap(context: Activity, bitmap: Bitmap?, resultCallBack: ResultCallBack) {
        SDKManager.resultCallBack = resultCallBack
        val i = Intent(ca)
        val bs = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.WEBP, 100, bs)

        Log.i(TAG, "printBitmap Size: ${bs.size()}")
        i.component = ComponentName(ConstantsStr.PNS, ConstantsStr.SA)
        i.putExtra(REQUEST_TYPE_3RD_PARTY, RequestType.REQUEST_TYPE_PRINT_BITMAP.value)
        i.putExtra(ConstantsStr.BITMAP, bs.toByteArray())

        context.startActivity(i)
    }

    //تراکنش موجودی
    fun inquiryBalance(context: Activity, transactionCallBack: TransactionCallBack) {
        SDKManager.transactionCallBack = transactionCallBack
        val i = Intent(ca)
        i.component = ComponentName(ConstantsStr.PNS, ConstantsStr.SA)
        i.putExtra(REQUEST_TYPE_3RD_PARTY, RequestType.REQUEST_TYPE_BALANCE.value)
        context.startActivity(i)
    }

    //تراکنش های خرید
    fun doSaleTransaction(context: Activity, amount: String, reserveNumber: String, approveByThird: Boolean, transactionCallBack: TransactionCallBack) {
        SDKManager.transactionCallBack = transactionCallBack
        if (amount.isBlank() || amount.length < 4)
            transactionCallBack.onError("-5000", context.applicationContext.getString(R.string.invalid_amount))
        else {
            val i = Intent(ca)
            i.component = ComponentName(ConstantsStr.PNS, ConstantsStr.SA)
            i.putExtra(REQUEST_TYPE_3RD_PARTY, RequestType.REQUEST_TYPE_SALE.value)
            i.putExtra(AMOUNT, amount)
            i.putExtra(ConstantsStr.EXT_RESERVE_NUMBER, reserveNumber)
            i.putExtra(ConstantsStr.APPROVE_BY_THIRD, approveByThird)
            context.startActivity(i)
        }
    }

    //تراکنش های  قبض، شارژ
    fun doServiceTransaction(context: Activity, requestType: RequestType, approveByThird: Boolean, transactionCallBack: TransactionCallBack) {
        SDKManager.transactionCallBack = transactionCallBack
        val i = Intent(ca)
        i.component = ComponentName(ConstantsStr.PNS, ConstantsStr.SA)
        i.putExtra(REQUEST_TYPE_3RD_PARTY, requestType.value)
        i.putExtra(ConstantsStr.APPROVE_BY_THIRD, approveByThird)
        context.startActivity(i)
    }

    //استعلام تراکنش از طریق Trace ,rrn, reserveNumber
    fun inquiryTransactionData(context: Activity, inquiryType: TxnInquiryType, inquiryId: String, printReceipt: Boolean, transactionCallBack: TransactionCallBack) {
        SDKManager.transactionCallBack = transactionCallBack
        val i = Intent(ca)
        i.component = ComponentName(ConstantsStr.PNS, ConstantsStr.SA)
        i.putExtra(REQUEST_TYPE_3RD_PARTY, RequestType.REQUEST_TYPE_INQUIRY_TRANSACTION.value)
        i.putExtra(ConstantsStr.TXN_INQUIRY_TYPE, inquiryType.value)
        i.putExtra(ConstantsStr.EXT_RESERVE_NUMBER, inquiryId)
        i.putExtra(ConstantsStr.PRINT_RECEIPT, printReceipt)
        context.startActivity(i)
    }

    //استعلام اطلاعات پوز
    fun inquiryPosData(context: Activity, posDataCallBack: PosDataCallBack) {
        SDKManager.posDataCallBack = posDataCallBack
        val i = Intent(ca)
        i.component = ComponentName(ConstantsStr.PNS, ConstantsStr.SA)
        i.putExtra(REQUEST_TYPE_3RD_PARTY, RequestType.REQUEST_TYPE_INQUIRY_POS_DATA.value)
        context.startActivity(i)
    }

    //انجام تراکنش تبادل کلید
    fun doKeyChange(context: Activity, resultCallBack: ResultCallBack) {
        SDKManager.resultCallBack = resultCallBack
        val i = Intent(ca)
        i.component = ComponentName(ConstantsStr.PNS, ConstantsStr.SA)
        i.putExtra(REQUEST_TYPE_3RD_PARTY, RequestType.REQUEST_TYPE_DO_KEY_CHANGE.value)
        context.startActivity(i)
    }

    class SdkBroadcastReceiver : BroadcastReceiver() {
        private val TAG = "SDKManager"
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.i(TAG, "BroadcastReceiver onReceive: 3rd Data Received")

            intent?.let {
                val bundle = it.extras
                if (bundle != null) {
                    for (key in bundle.keySet()) {
                        Log.e(TAG, key + " : " + if (bundle[key] != null) bundle[key] else "NULL")
                    }
                }
                if (it.hasExtra(REQUEST_TYPE_3RD_PARTY)) {
                    val requestType = it.getStringExtra(REQUEST_TYPE_3RD_PARTY) ?: ""

                    if (it.action.equals("com.thunderlight.sadad.SEND_TXN_DATA")) {
                        when (requestType) {
                            RequestType.REQUEST_TYPE_INQUIRY_POS_DATA.value -> {
                                handlePosData(it)
                            }
                            RequestType.REQUEST_TYPE_INQUIRY_TRANSACTION.value,
                            RequestType.REQUEST_TYPE_SALE.value,
                            RequestType.REQUEST_TYPE_BALANCE.value,
                            RequestType.REQUEST_TYPE_BILL.value,
                            RequestType.REQUEST_TYPE_CHARGE.value,
                            RequestType.REQUEST_TYPE_CHARGE_PIN.value,
                            RequestType.REQUEST_TYPE_CHARGE_TOP_UP.value -> {
                                handleTxnData(it, requestType)
                            }
                            RequestType.REQUEST_TYPE_PRINT_BITMAP.value,
                            RequestType.REQUEST_TYPE_DO_KEY_CHANGE.value -> {
                                handleResult(it)
                            }
                            else -> {}
                        }
                    }
                }
            }
        }

        //https://www.baeldung.com/kotlin/data-class-to-map
        //https://stackoverflow.com/questions/49860916/how-to-convert-a-kotlin-data-class-object-to-map
        private fun handleTxnData(intent: Intent, requestType: String) {
            Log.i(TAG, "handle Txn Data : ")
            when (intent.getStringExtra(RECEIVE_STATE) ?: "") {
                RECEIVE_STATE_SUCCESS -> {
                    val transactionData = TransactionData()
                    intent.extras?.let {
                        if (intent.hasExtra(TXN_TYPE)) {
                            transactionData.txnType = it.getString(TXN_TYPE)!!
                            if (it.containsKey(RESPONSE_CODE))
                                transactionData.responseCode = it.getString(RESPONSE_CODE)!!

                            if (it.containsKey(RESPONSE_MESSAGE))
                                transactionData.responseMessage = it.getString(RESPONSE_MESSAGE)!!

                            if (it.containsKey(AMOUNT))
                                transactionData.amount = it.getString(AMOUNT)!!

                            if (it.containsKey(AFFECTIVE_AMOUNT))
                                transactionData.affectiveAmount = it.getString(AFFECTIVE_AMOUNT)!!

                            if (it.containsKey(TIME_STAMP))
                                transactionData.timeStamp = it.getLong(TIME_STAMP)

                            if (it.containsKey(SOLAR_DATE))
                                transactionData.timeFarsi = it.getString(SOLAR_DATE)!!

                            if (it.containsKey(DATE))
                                transactionData.date = it.getString(DATE)!!

                            if (it.containsKey(TIME))
                                transactionData.time = it.getString(TIME)!!

                            if (it.containsKey(TRACE))
                                transactionData.trace = it.getString(TRACE)!!

                            if (it.containsKey(RRN))
                                transactionData.rrn = it.getString(RRN)!!

                            if (it.containsKey(RESERVE_NUMBER))
                                transactionData.reserveNumber = it.getString(RESERVE_NUMBER)!!

                            if (it.containsKey(TRUNCATE_PAN))
                                transactionData.truncatePan = it.getString(TRUNCATE_PAN)!!

                            if (it.containsKey(BANK))
                                transactionData.bank = it.getString(BANK)!!

                            if (it.containsKey(HASH_PAN))
                                transactionData.hashPan = it.getString(HASH_PAN)!!

                            if (it.containsKey(SHIFT_NO))
                                transactionData.shiftNo = it.getInt(SHIFT_NO)

                            if (it.containsKey(TERMINAL_ID))
                                transactionData.terminalId = it.getString(TERMINAL_ID)!!

                            if (it.containsKey(MERCHANT_ID))
                                transactionData.merchantId = it.getString(MERCHANT_ID)!!

                            if (it.containsKey(MERCHANT_NAME))
                                transactionData.merchantName = it.getString(MERCHANT_NAME)!!

                            if (it.containsKey(POS_SERIAL))
                                transactionData.posSerial = it.getString(POS_SERIAL)!!

                            if (requestType == RequestType.REQUEST_TYPE_CHARGE_PIN.value) {
                                var extraData = Bundle()

                                if (it.containsKey(EXTRA_DATA)) {
                                    extraData = it.getBundle(EXTRA_DATA)!!
                                    val voucherPin = extraData.getString(CHARGE_PIN_VOUCHER)!!
                                    val voucherSerial = extraData.getString(CHARGE_PIN_SERIAL)!!
                                    val txnType = extraData.getString(CHARGE_TYPE)!!
                                    val operatorCode = extraData.getString(CHARGE_OPERATOR_CODE)!!

                                    extraData.putString(CHARGE_PIN_VOUCHER, voucherPin)
                                    extraData.putString(CHARGE_PIN_SERIAL, voucherSerial)

                                    extraData.putString(CHARGE_TYPE, txnType)
                                    extraData.putString(CHARGE_OPERATOR_CODE, operatorCode)
                                    transactionData.extraData = extraData
                                }

                            } else if (requestType == RequestType.REQUEST_TYPE_CHARGE_TOP_UP.value) {
                                var extraData = Bundle()

                                if (it.containsKey(EXTRA_DATA)) {
                                    extraData = it.getBundle(EXTRA_DATA)!!
                                    val mobileNumber = extraData.getString(MOBILE_NUMBER)!!
                                    val chargeType = extraData.getString(CHARGE_TYPE)!!
                                    val operatorCode = extraData.getString(CHARGE_OPERATOR_CODE)!!

                                    extraData.putString(MOBILE_NUMBER, mobileNumber)
                                    extraData.putString(CHARGE_TYPE, chargeType)
                                    extraData.putString(CHARGE_OPERATOR_CODE, operatorCode)
                                    transactionData.extraData = extraData
                                }

                            } else if (requestType == RequestType.REQUEST_TYPE_BILL.value) {
                                transactionData.extraData = null

                                var extraData = Bundle()

                                if (it.containsKey(EXTRA_DATA)) {
                                    extraData = it.getBundle(EXTRA_DATA)!!
                                }

                                val billId = extraData.getString(BILL_ID)!!
                                val billPaymentId = extraData.getString(BILL_PAYMENT_ID)!!

                                extraData.putString(BILL_ID, billId)
                                extraData.putString(BILL_PAYMENT_ID, billPaymentId)
                                transactionData.extraData = extraData

                            }//intent.extras?.getBundle(EXTRA_DATA)
                        }
                    }
                    transactionCallBack?.onReceive(transactionData)
                }
                RECEIVE_STATE_ERROR -> {
                    val errorCode = intent.getStringExtra(ERROR_CODE) ?: ""
                    val errorMessage = intent.getStringExtra(ERROR_MESSAGE) ?: ""
                    transactionCallBack?.onError(errorCode, errorMessage)
                }
                RECEIVE_STATE_CANCEL -> {
                    transactionCallBack?.onCanceled()
                }
                else -> {}
            }
        }

        private fun handlePosData(intent: Intent) {
            Log.i(TAG, "handle Pos Data: ")
            when (intent.getStringExtra(RECEIVE_STATE) ?: "") {
                RECEIVE_STATE_SUCCESS -> {
                    val posData = PosData()
                    intent.extras?.let {
                        if (intent.hasExtra(TIME_STAMP))
                            posData.timeStamp = it.getLong(TIME_STAMP)
                        if (it.containsKey(DATE))
                            posData.date = it.getString(DATE)!!

                        if (it.containsKey(TIME))
                            posData.time = it.getString(TIME)!!

                        if (it.containsKey(TERMINAL_ID))
                            posData.terminalId = it.getString(TERMINAL_ID)!!

                        if (it.containsKey(MERCHANT_ID))
                            posData.merchantId = it.getString(MERCHANT_ID)!!

                        if (it.containsKey(MERCHANT_NAME))
                            posData.merchantName = it.getString(MERCHANT_NAME)!!

                        if (it.containsKey(POS_SERIAL))
                            posData.posSerial = it.getString(POS_SERIAL)!!


                        if (it.containsKey(POS_PART_NUMBER))
                            posData.posPartNumber = it.getString(POS_PART_NUMBER)!!


                        if (it.containsKey(POS_BRAND_NUMBER))
                            posData.posBrandName = it.getString(POS_BRAND_NUMBER)!!

                        if (it.containsKey(POS_MODEL))
                            posData.posModel = it.getString(POS_MODEL)!!

                        if (it.containsKey(POS_CODE))
                            posData.posCode = it.getString(POS_CODE)!!

                        if (it.containsKey(APP_VERSION))
                            posData.appVersion = it.getString(APP_VERSION)!!

                        if (it.containsKey(SDK_VERSION))
                            posData.sdkVersion = it.getString(SDK_VERSION)!!

                        if (it.containsKey(TELEPHONE))
                            posData.telNo = it.getString(TELEPHONE)!!

                        if (it.containsKey(MOBILE_NUMBER))
                            posData.mobileNo = it.getString(MOBILE_NUMBER)!!

                        if (it.containsKey(ADDRESS_FA))
                            posData.addressFa = it.getString(ADDRESS_FA)!!

                        if (it.containsKey(EXTRA_DATA))
                            posData.extraData = null

                    }
                    posDataCallBack?.onReceive(posData)
                }
                RECEIVE_STATE_ERROR -> {
                    val errorCode = intent.getStringExtra(ERROR_CODE) ?: ""
                    val errorMessage = intent.getStringExtra(ERROR_MESSAGE) ?: ""
                    posDataCallBack?.onError(errorCode, errorMessage)
                }
                else -> {}
            }
        }

        private fun handleResult(intent: Intent) {
            Log.i(TAG, "key Change Response: ")
            when (intent.getStringExtra(RECEIVE_STATE) ?: "") {
                RECEIVE_STATE_SUCCESS -> {
                    resultCallBack?.onSuccess()
                }
                RECEIVE_STATE_ERROR -> {
                    val errorCode = intent.getStringExtra(ERROR_CODE) ?: ""
                    val errorMessage = intent.getStringExtra(ERROR_MESSAGE) ?: ""
                    resultCallBack?.onError(errorCode, errorMessage)
                }
                else -> {}
            }
        }
    }
}