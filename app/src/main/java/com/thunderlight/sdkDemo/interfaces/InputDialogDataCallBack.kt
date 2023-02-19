package com.thunderlight.sadad.interfaces

/**
 * @author Created by M.Moradikia
 * @date  11/7/2022
 * @company Thunder-Light
 */
interface InputDialogDataCallBack : java.io.Serializable {
    fun getData(firstValue: String, secondValue: String)
}