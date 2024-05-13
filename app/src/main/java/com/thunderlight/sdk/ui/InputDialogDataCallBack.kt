package com.thunderlight.sdk.ui

/**
 * @author Created by M.Moradikia
 * @date  11/7/2022
 * @company Thunder-Light
 */
interface InputDialogDataCallBack : java.io.Serializable {
    fun getData(value: String)
    fun onCancel()
}