package com.thunderlight.sdk.ui.service.model

import com.thunderlight.thundersmartsdk.constant.RequestType

/**
 * @author Created by M.Moradikia
 * @date  11/5/2022
 * @company Thunder-Light
 */
data class PaymentServiceItem(
    var action: RequestType,
    var title: String,
    var icon: Int
)
