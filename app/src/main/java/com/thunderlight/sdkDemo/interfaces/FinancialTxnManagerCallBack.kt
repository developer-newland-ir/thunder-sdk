package com.thunderlight.sadad.interfaces

/**
 * the thread runs the callback
 *
 * @author CB
 * @date 2014/12/25
 */
interface FinancialTxnManagerCallBack {
    fun onFinish()
    fun onSuccess()
    fun onFailed()
}