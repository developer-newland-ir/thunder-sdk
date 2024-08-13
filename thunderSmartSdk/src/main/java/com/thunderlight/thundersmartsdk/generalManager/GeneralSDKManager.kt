package com.thunderlight.thundersmartsdk.generalManager

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.thunderlight.thundersmartsdk.constant.ConstantsStr
import com.thunderlight.thundersmartsdk.constant.HostApp
import com.thunderlight.thundersmartsdk.constant.RequestType
import com.thunderlight.thundersmartsdk.constant.TxnInquiryType
import com.thunderlight.thundersmartsdk.sadad.SDKManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @author Created by M.Moradikia
 * @date  2/14/2023
 * @company Thunder-Light
 */
class GeneralSDKManager {

    private val TAG = "GeneralSDKManager"
    private var host = HostApp.HOST_UNKNOWN

    fun init(context: Context): HostApp {
        val packages: List<PackageInfo> = context.packageManager.getInstalledPackages(0)
        val allowedApplication = ArrayList<String>()

        allowedApplication.add(ConstantsStr.PNSEP)
        allowedApplication.add(ConstantsStr.PNS)
        allowedApplication.add(ConstantsStr.PNSEPEHR)
        allowedApplication.add(ConstantsStr.PNIKCC)

        var x = HostApp.HOST_UNKNOWN.value

        for (i in packages.indices) {
            val packageInfo = packages[i]
            var foundIt = false
            for (appPackageName in allowedApplication)
                if (packageInfo.applicationInfo?.packageName == appPackageName) {
                    x = appPackageName
                    foundIt = true
                    break
                }
            if (foundIt)
                break
        }
        if (x.contains(HostApp.HOST_SEP.value))
            host = HostApp.HOST_SEP
        else if (x.contains(HostApp.HOST_SEPEHR.value))
            host = HostApp.HOST_SEPEHR
        else if (x.contains(HostApp.HOST_IRANKISH.value))
            host = HostApp.HOST_IRANKISH
        else if (x.contains(HostApp.HOST_SADAD.value))
            host = HostApp.HOST_SADAD
        else
            host = HostApp.HOST_UNKNOWN

        //todo test, Comment this
        //host = HostApp.HOST_SEP

        saveLogs(context)

        return host
    }

    //ذخیره کردن لاگ های دستگاه
    private fun saveLogs(context: Context) = CoroutineScope(Dispatchers.IO).launch {
        deleteAllFileInDirectory()
        val dateTime = getDateTimeMMddHHmmss()

        val address = Environment.getExternalStorageDirectory().absolutePath + "/ThunderSdk"
        Log.i(TAG, "saveLogs1: $address")

        val mydir = File(address)
        if (!mydir.exists()) {
            mydir.mkdirs()
            launch(Dispatchers.Main) {
                Toast.makeText(context.applicationContext, "Directory Created", Toast.LENGTH_LONG).show()
            }
        } else {
            Log.i(TAG, "saveLogs2: exists: ${mydir.exists()}")
        }

        val filePath: String = mydir.absolutePath + "/log-${dateTime}.txt"
        Log.i(TAG, "saveLogs: $filePath")
        var process = Runtime.getRuntime().exec("logcat -c")
        process = Runtime.getRuntime().exec(arrayOf("logcat", "-f", filePath))

        Log.i(
            TAG,
            "*************************************************** Log Saving Started & ThunderSDK Verion :  ${getAppVersion_(context)} *************************************************"
        )
        Log.i(TAG, "********************************************************************************************************************************************************")
    }

    private fun getDateTimeMMddHHmmss(): String {
        val sdf = SimpleDateFormat("MMdd_HHmmss", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getAppVersion_(context: Context): String { // with "_"
        var version = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version = "${pInfo.versionName}_${pInfo.versionCode}"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return version
    }

    private fun deleteAllFileInDirectory() = CoroutineScope(Dispatchers.IO).launch {
        val address = Environment.getExternalStorageDirectory().absolutePath + "/ThunderSdk"
        Log.i(TAG, "deleteAllFileInDirectory: $address")

        val dir = File(address)
        if (dir.exists()) {
            if (dir.isDirectory) {
                val children: Array<String> = dir.list()!!
                children.sort()
                children.reverse()
                Log.i(TAG, "File In Directory size ${children.size}")
                for (i in children.indices) {
                    Log.i(TAG, "File In Directory $i: ${children[i]}")
                    if (i > 13) {
                        Log.i(TAG, "File deleted $i: ${children[i]}")
                        File(dir, children[i]).delete()
                    }
                }
            }
        }
    }


    //چاپ رسید
    fun printBitmap(context: Activity, bitmap: Bitmap?, resultCallBack: ResultCallBack) {
        when (host) {
            HostApp.HOST_SADAD -> {
                val sdkManagerSadad = SDKManager()
                sdkManagerSadad.printBitmap(context, bitmap, resultCallBack)
            }

            HostApp.HOST_SEP -> {
                val sdkManagerSep = com.thunderlight.thundersmartsdk.sep.SDKManager()
                sdkManagerSep.printBitmap(context, bitmap, resultCallBack)
            }

            HostApp.HOST_SEPEHR -> {
                val sdkManagerSepehr = com.thunderlight.thundersmartsdk.sepehr.SDKManager()
                sdkManagerSepehr.printBitmap(context, bitmap, resultCallBack)
            }

            HostApp.HOST_IRANKISH -> {
                val sdkManagerIranKish = com.thunderlight.thundersmartsdk.irankish.SDKManager()
                sdkManagerIranKish.printBitmap(context, bitmap, resultCallBack)
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

            HostApp.HOST_SEP -> {
                val sdkManagerSep = com.thunderlight.thundersmartsdk.sep.SDKManager()
                sdkManagerSep.inquiryBalance(context, transactionCallBack)
            }

            HostApp.HOST_SEPEHR -> {
                val sdkManagerSepehr = com.thunderlight.thundersmartsdk.sepehr.SDKManager()
                sdkManagerSepehr.inquiryBalance(context, transactionCallBack)
            }

            HostApp.HOST_IRANKISH -> {
                val sdkManagerIranKish = com.thunderlight.thundersmartsdk.irankish.SDKManager()
                sdkManagerIranKish.inquiryBalance(context, transactionCallBack)
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

            HostApp.HOST_SEP -> {
                val sdkManagerSep = com.thunderlight.thundersmartsdk.sep.SDKManager()
                sdkManagerSep.doSaleTransaction(context, amount, reserveNumber, approveByThird, transactionCallBack)
            }

            HostApp.HOST_SEPEHR -> {
                val sdkManagerSepehr = com.thunderlight.thundersmartsdk.sepehr.SDKManager()
                sdkManagerSepehr.doSaleTransaction(context, amount, reserveNumber, approveByThird, transactionCallBack)
            }

            HostApp.HOST_IRANKISH -> {
                val sdkManagerIranKish = com.thunderlight.thundersmartsdk.irankish.SDKManager()
                sdkManagerIranKish.doSaleTransaction(context, amount, reserveNumber, approveByThird, transactionCallBack)
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

            HostApp.HOST_SEP -> {
                val sdkManagerSep = com.thunderlight.thundersmartsdk.sep.SDKManager()
                sdkManagerSep.doServiceTransaction(context, requestType, approveByThird, transactionCallBack)
            }

            HostApp.HOST_SEPEHR -> {
                val sdkManagerSepehr = com.thunderlight.thundersmartsdk.sepehr.SDKManager()
                sdkManagerSepehr.doServiceTransaction(context, requestType, approveByThird, transactionCallBack)
            }

            HostApp.HOST_IRANKISH -> {
                val sdkManagerIranKish = com.thunderlight.thundersmartsdk.irankish.SDKManager()
                sdkManagerIranKish.doServiceTransaction(context, requestType, approveByThird, transactionCallBack)
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

            HostApp.HOST_SEP -> {
                val sdkManagerSep = com.thunderlight.thundersmartsdk.sep.SDKManager()
                sdkManagerSep.inquiryTransactionData(context, inquiryType, inquiryId, printReceipt, transactionCallBack)
            }

            HostApp.HOST_SEPEHR -> {
                val sdkManagerSepehr = com.thunderlight.thundersmartsdk.sepehr.SDKManager()
                sdkManagerSepehr.inquiryTransactionData(context, inquiryType, inquiryId, printReceipt, transactionCallBack)
            }

            HostApp.HOST_IRANKISH -> {
                val sdkManagerIranKish = com.thunderlight.thundersmartsdk.irankish.SDKManager()
                sdkManagerIranKish.inquiryTransactionData(context, inquiryType, inquiryId, printReceipt, transactionCallBack)
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

            HostApp.HOST_SEP -> {
                val sdkManagerSep = com.thunderlight.thundersmartsdk.sep.SDKManager()
                sdkManagerSep.inquiryPosData(context, posDataCallBack)
            }

            HostApp.HOST_SEPEHR -> {
                val sdkManagerSepehr = com.thunderlight.thundersmartsdk.sepehr.SDKManager()
                sdkManagerSepehr.inquiryPosData(context, posDataCallBack)
            }

            HostApp.HOST_IRANKISH -> {
                val sdkManagerIranKish = com.thunderlight.thundersmartsdk.irankish.SDKManager()
                sdkManagerIranKish.inquiryPosData(context, posDataCallBack)
            }

            else -> {
                posDataCallBack.onError("-9999", "اپلیکیشن میزبان یافت نشد.")
            }
        }
    }

    //انجام تراکنش پیکربندی
    fun doConfiguration(context: Activity, resultCallBack: ResultCallBack) {
        when (host) {
            HostApp.HOST_SADAD -> {
                val sdkManagerSadad = SDKManager()
                sdkManagerSadad.doKeyChange(context, resultCallBack)
            }

            HostApp.HOST_SEPEHR -> {
                val sdkManagerSepehr = com.thunderlight.thundersmartsdk.sepehr.SDKManager()
                sdkManagerSepehr.doKeyChange(context, resultCallBack)
            }

            HostApp.HOST_SEP -> {
                val sdkManagerSep = com.thunderlight.thundersmartsdk.sep.SDKManager()
                sdkManagerSep.doKeyChange(context, resultCallBack)
            }

            HostApp.HOST_IRANKISH -> {
                val sdkManagerIranKish = com.thunderlight.thundersmartsdk.irankish.SDKManager()
                sdkManagerIranKish.doKeyChange(context, resultCallBack)
            }

            else -> {
                resultCallBack.onError("-9999", "اپلیکیشن میزبان یافت نشد.")
            }
        }
    }

    fun doApprove220(context: Activity, rrn: String, resultCallBack: ResultCallBack) {
        when (host) {
            HostApp.HOST_SADAD -> {
                val sdkManagerSadad = SDKManager()
                sdkManagerSadad.doApprove(context, rrn, resultCallBack)
            }

            HostApp.HOST_SEPEHR -> {
                val sdkManagerSepehr = com.thunderlight.thundersmartsdk.sepehr.SDKManager()
                //sdkManagerSepehr.doApprove(context, rrn,  resultCallBack)
            }

            HostApp.HOST_SEP -> {
                val sdkManagerSep = com.thunderlight.thundersmartsdk.sep.SDKManager()
                sdkManagerSep.doApprove(context, rrn, resultCallBack)
            }

            HostApp.HOST_IRANKISH -> {
                val sdkManagerIranKish = com.thunderlight.thundersmartsdk.irankish.SDKManager()
                //sdkManagerIranKish.doApprove(context, rrn, resultCallBack)
            }

            else -> {
                resultCallBack.onError("-9999", "اپلیکیشن میزبان یافت نشد.")
            }
        }
    }

    fun doReverse420(context: Activity, trace: String, resultCallBack: ResultCallBack) {
        when (host) {
            HostApp.HOST_SADAD -> {
                val sdkManagerSadad = SDKManager()
                sdkManagerSadad.doReverse(context, trace, resultCallBack)
            }

            HostApp.HOST_SEPEHR -> {
                val sdkManagerSepehr = com.thunderlight.thundersmartsdk.sepehr.SDKManager()
                //sdkManagerSepehr.doReverse(context,trace, resultCallBack)
            }

            HostApp.HOST_SEP -> {
                val sdkManagerSep = com.thunderlight.thundersmartsdk.sep.SDKManager()
                sdkManagerSep.doReverse(context, trace, resultCallBack)
            }

            HostApp.HOST_IRANKISH -> {
                val sdkManagerIranKish = com.thunderlight.thundersmartsdk.irankish.SDKManager()
                //sdkManagerIranKish.doReverse(context,trace, resultCallBack)
            }

            else -> {
                resultCallBack.onError("-9999", "اپلیکیشن میزبان یافت نشد.")
            }
        }
    }
}