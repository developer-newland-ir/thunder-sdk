package com.thunderlight.sdkDemo.constant

/**
 * @author Created by M.Moradikia
 * @date  10/30/2022
 * @company Thunder-Light
 */
object ConstantsInt {

    // Approve Flag
    const val APPROVE_FLAG_NULL = 0
    const val APPROVE_FLAG_NEED = 1
    const val APPROVE_FLAG_DONE = 2
    const val APPROVE_FLAG_NEED_BY_THIRD = 11
    const val APPROVE_FLAG_DONE_BY_THIRD = 22
    const val APPROVE_FLAG_NEED_BY_PC_POS = 111
    const val APPROVE_FLAG_DONE_BY_PC_POS = 222

    const val CHARGE_TYPE_TOP_UP = "1"
    const val CHARGE_TYPE_AMAZING = "2"
    const val CHARGE_TYPE_PIN = "5"

    const val BILL_TYPE_WATER = 1
    const val BILL_TYPE_ELECTRIC = 2
    const val BILL_TYPE_GAS = 3
    const val BILL_TYPE_TELEPHONE = 4
    const val BILL_TYPE_HAMRAH = 5
    const val BILL_TYPE_SHAHRDARI = 6
    const val BILL_TYPE_TAX = 7
    const val BILL_TYPE_POLICE = 8

    // Revers Flag
    const val REVERSE_FLAG_NULL = 0
    const val REVERSE_FLAG_NEED = 1
    const val REVERSE_FLAG_DONE = 2
    const val REVERSE_FLAG_NEED_BY_THIRD = 11
    const val REVERSE_FLAG_DONE_BY_THIRD = 22
    const val REVERSE_FLAG_NEED_BY_PC_POS = 111
    const val REVERSE_FLAG_DONE_BY_PC_POS = 222

    const val LOGON_FLAG_DONE = 0

    // Configuration State
    const val CONFIGURATION_STATE_NEW = "0" // زمانی که اپلیکیشن تازه نصب شده است.
    const val CONFIGURATION_STATE_INIT_RECEIVED = "1" // دستگاه پیکربندی INIT را دریافت کرده است ولی تبادل کلید انجام نشده است
    const val CONFIGURATION_STATE_CONFIGURED = "2" // دستگاه پیکربندی ها را دریافت کرده است و فعال است
    const val CONFIGURATION_STATE_INACTIVE = "-1" // دستگاه پیکربندی داشته است ولی به دلایل مختلف غیر فعال شده است.

    //Time Out
    const val TIME_OUT_PAGE_15S = 15_000L
    const val TIME_OUT_PAGE_30S = 30_000L
    const val TIME_OUT_PAGE_60S = 60_000L
    const val TIME_OUT_PAGE_120S = 120_000L

    //Time Out HW
    const val TIME_OUT_SCANNER = 60
    const val TIME_OUT_PINPAD = 60
    const val TIME_OUT_CARD_READER = 120

    //Socket
    const val TIME_OUT_SOCKET_API = 30L
    const val JOB_SERVICE_INTERVAL = 60_000L

    //Alarm Manager
    const val ALARM_MANAGER_REQUEST_CODE = 1234
    const val ALARM_MANAGER_TIME = 10L
}