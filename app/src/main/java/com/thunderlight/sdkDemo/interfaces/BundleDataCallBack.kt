package com.thunderlight.sadad.interfaces

import android.os.Bundle

/**
 * @author Created by M.Moradikia
 * @date  11/7/2022
 * @company Thunder-Light
 */
interface BundleDataCallBack : java.io.Serializable {
    fun getData(data: Bundle)
}