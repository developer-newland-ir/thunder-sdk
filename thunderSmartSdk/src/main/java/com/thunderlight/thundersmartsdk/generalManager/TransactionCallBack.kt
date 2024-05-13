package com.thunderlight.thundersmartsdk.generalManager

import com.thunderlight.thundersmartsdk.data.TransactionData

/**
 * @author Created by M.Moradikia
 * @date  2/14/2023
 * @company Thunder-Light
 */

interface TransactionCallBack {
    fun onSuccess(transactionData: TransactionData)
    fun onUndeterminedStateOfPreviousTxn(transactionData: TransactionData)
    fun onError(errorCode: String, errorMsg: String)
    fun onCanceled()
}