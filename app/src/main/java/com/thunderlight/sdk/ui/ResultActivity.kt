package com.thunderlight.sdk.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.thunderlight.sdk.databinding.ActivityResultBinding
import com.thunderlight.sdk.utils.toString2
import com.thunderlight.thundersmartsdk.constant.ConstantsStr
import com.thunderlight.thundersmartsdk.data.PosData
import com.thunderlight.thundersmartsdk.data.TransactionData

class ResultActivity : AppCompatActivity() {

    private val TAG = "ResultActivity"
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i(TAG, "onCreate: ")
        initData()
    }

    private fun initData() {
        val transactionData: TransactionData
        val posData: PosData
        intent?.let {
            if (it.hasExtra(ConstantsStr.TRANSACTION_DATA)) {
                transactionData = it.getParcelableExtra(ConstantsStr.TRANSACTION_DATA)!!
                loadTransactionData(transactionData)
            }

            if (it.hasExtra(ConstantsStr.POS_DATA)) {
                posData = it.getParcelableExtra(ConstantsStr.POS_DATA)!!
                loadPosData(posData)
            }
            Log.i(TAG, "initData: ")
        }
    }

    private fun loadTransactionData(transactionData: TransactionData) {
        binding.apply {
            var value = transactionData.toString().replace(",", "\n")
            if (transactionData.txnType == "CHARGE_PIN" || transactionData.txnType == "BILL" || transactionData.txnType == "CHARGE_TOP_UP") {
                value += transactionData.extraData!!.toString2()
            }
            tvResult.text = value
            Log.i(TAG, "loadTransactionData: ")
        }
    }

    private fun loadPosData(posData: PosData) {
        binding.apply {
            tvResult.text = posData.toString().replace(",", "\n")
            Log.i(TAG, "loadPosData: ")
        }
    }
}