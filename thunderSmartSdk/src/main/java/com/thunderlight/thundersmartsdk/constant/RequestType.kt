package com.thunderlight.thundersmartsdk.constant

/**
 * @author Created by M.Moradikia
 * @date  10/30/2022
 * @company Thunder-Light
 */
enum class RequestType(val value: String) {
    REQUEST_TYPE_PRINT_BITMAP("request_type_print_bitmap"),
    REQUEST_TYPE_INQUIRY_POS_DATA("request_type_inquiry_pos_data"),
    REQUEST_TYPE_INQUIRY_TRANSACTION("request_type_inquiry_transaction"),
    REQUEST_TYPE_DO_KEY_CHANGE("request_type_do_key_change"),

    REQUEST_TYPE_SALE("request_type_sale_third_party"),
    REQUEST_TYPE_BALANCE("request_type_balance_third_party"),
    REQUEST_TYPE_BILL("request_type_bill_third_party"),
    REQUEST_TYPE_CHARGE("request_type_charge_third_party"),
    REQUEST_TYPE_CHARGE_PIN("request_type_charge_pin_third_party"),
    REQUEST_TYPE_CHARGE_TOP_UP("request_type_charge_top_up_third_party")
}