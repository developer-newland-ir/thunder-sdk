package com.thunderlight.thundersmartsdk.constant

/**
 * @author Created by M.Moradikia
 * @date  10/30/2022
 * @company Thunder-Light
 */
enum class TxnInquiryType(val value: String) {
    REQUEST_TYPE_INQUIRY_BY_TRACE("trace"),
    REQUEST_TYPE_INQUIRY_BY_RRN("rrn"),
    REQUEST_TYPE_INQUIRY_BY_RESERVE_NUMBER("reserve_number"),
}