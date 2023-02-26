package com.thunderlight.thundersmartsdk.constant

/**
 * @author Created by M.Moradikia
 * @date  10/30/2022
 * @company Thunder-Light
 */

object ConstantsStr {

    //---------------------------------------------------------------- Item Actions ------------------------
    const val ACTION_NEW = "new"
    const val ACTION_OPEN = "open"
    const val ACTION_EDIT = "edit"
    const val ACTION_DELETE = "delete"
    const val ACTION_UNKNOWN = "unknown"

    const val RESP_OK = "is_ok"
    const val RESP_NOT_OK = "not_ok"

    //----------------------------------------------------------------- Key Injection -----------------------
    const val REQUEST_TYPE_APPLET = "applet"
    const val REQUEST_TYPE_PIN_VERIFY = "pin_verify"
    const val REQUEST_TYPE_READ_INDEX = "READ_INDEX"
    const val REQUEST_TYPE_READ_DATA = "read_data"

    //----------------------------------------------------------------- Operators ---------------------------

    const val OPERATOR_MCI = "mci"
    const val OPERATOR_MTN = "mtn"
    const val OPERATOR_RIGHTEL = "rightel"

    const val OPERATOR_CODE_MCI = "0919"
    const val OPERATOR_CODE_MTN = "0935"
    const val OPERATOR_CODE_RIGHTEL = "0921"

    //----------------------------------------------------------------- DataBase ----------------------------
    const val TXN_TABLE = "txn_table"
    const val POS_PARAM_TABLE = "pos_param_table"
    const val SHIFT_TABLE = "shift_table"
    const val THUDNER_LIGHT_DATABASE = "thunder_light_database"

    //----------------------------------------------------------------- DataStore ---------------------------
    const val THUDNER_LIGHT_DATASTORE = "thunder_light_datastore"
    var CONNECTION_IP_STORE = "connection_ip" // "185.203.161.9"
    var CONNECTION_PORT_STORE = "connection_port" //8050

    const val POS_MODEL = "pos_model"
    const val SHIFT_NUMBER = "shift_number"
    const val TRACE_NUMBER = "trace_number"

    const val SUPERVISOR_PASSWORD_COUNTER = "supervisor_password_counter"
    const val MERCHANT_PASSWORD_COUNTER = "merchant_password_counter"
    const val CASHIER_PASSWORD = "cashier_password"
    const val MERCHANT_PASSWORD = "merchant_password"
    const val LOCAL_PASSWORD = "local_password"
    const val SUPERVISOR_PASSWORD = "supervisor_password"
    const val CASHIER_PASSWORD_STATE = "cashier_password_state"
    const val SECOND_PRINT_STATE = "second_print_state"
    const val PRINTER_QUALITY = "printer_quality"

    const val CONFIGURATION_STATUS = "configuration_status"
    const val TMS_NEED = "tms_need"
    const val NEED_CHANGE_KEY = "need_change_key"

    //----------------------------------------------------------------- Fragment Transfer Keys ---------------------------

    const val PNS = "com.thunderlight.sadad"
    const val SA = "com.thunderlight.sadad.ui.activity.thirdParty.ThirdPartyActivity"

    const val BUNDLE_ID = "bundle_id"
    const val FROM = "from"
    const val FROM_REPORTS = "reports"
    const val FROM_DO_TRANSACTION = "do_transaction"
    const val TRANSACTION = "transaction"
    const val PAN = "pan"
    const val HASH_PAN = "hash_pan"
    const val AMOUNT = "amount"

    const val AFFECTIVE_AMOUNT = "affective_amount"
    const val TRK2 = "trk2"
    const val SCANNER_TYPE = "scanner_type"
    const val SCANNER_TYPE_BILL = "scanner_type_bill"
    const val RESULT = "result"
    const val LISTENER = "listener"
    const val BILL_ID = "bill_id"
    const val BILL_PAYMENT_ID = "bill_payment_id"
    const val MOBILE_NUMBER = "mobile_number"

    const val CHARGE_TYPE = "charge_type"
    const val CHARGE_OPERATOR_CODE = "charge_operator_code"
    const val CHARGE_SERVICE_CODE = "charge_service_code"
    const val CHARGE_PIN_SERIAL = "charge_pin_serial"
    const val CHARGE_PIN_VOUCHER = "charge_pin_voucher"

    const val INPUT_TYPE = "input_type"
    const val TITLE = "title"
    const val BODY = "body"
    const val START_DATE = "start_date"
    const val END_DATE = "end_date"

    //ThirdParty Keys
    const val REQUEST_TYPE_3RD_PARTY = "request_type_3rd_party"
    const val EXT_RESERVE_NUMBER = "ext_reserve_number"
    const val APPROVE_BY_THIRD = "approve_by_third"
    const val TXN_INQUIRY_TYPE = "txn_inquiry_type"
    const val TRANSACTION_DATA = "transaction_data"
    const val POS_DATA = "pos_data"
    const val ERROR_CODE = "error_code"
    const val ERROR_MESSAGE = "error_message"
    const val PRINT_RECEIPT = "print_receipt"
    const val BITMAP = "bitmap"

    const val RECEIVE_STATE = "state"
    const val RECEIVE_STATE_SUCCESS = "receive_state_success"
    const val RECEIVE_STATE_ERROR = "receive_state_error"
    const val RECEIVE_STATE_CANCEL = "receive_state_cancel"


    const val POS_PART_NUMBER = "pos_part_number"
    const val POS_BRAND_NUMBER = "pos_brand_number"
    const val POS_CODE = "pos_code"
    const val APP_VERSION = "app_version"
    const val SDK_VERSION = "sdk_version"
    const val TELEPHONE = "telephone"
    const val ADDRESS_FA = "address_fa"

    const val TXN_TYPE = "txn_type"
    const val RESPONSE_CODE = "responseCode"
    const val RESPONSE_MESSAGE = "responseMessage"
    const val TIME_STAMP = "timeStamp"
    const val SOLAR_DATE = "solarDate"
    const val DATE = "date"
    const val TIME = "time"
    const val TRACE = "trace"
    const val RRN = "rrn"
    const val RESERVE_NUMBER = "reserveNumber"
    const val TRUNCATE_PAN = "truncatePan"
    const val BANK = "bank"
    const val SHIFT_NO = "shiftNo"
    const val TERMINAL_ID = "terminalId"
    const val MERCHANT_ID = "merchantId"
    const val MERCHANT_NAME = "merchantName"
    const val POS_SERIAL = "posSerial"
    const val EXTRA_DATA = "extraData"


    //----------------------------------------------------------------- Socket Api ----------------------------

    var CONNECTION_IP = "185.203.161.9"
    var CONNECTION_PORT = 8050

    //----------------------------------------------------------------- Input Dialog Type ----------------------------
    const val INPUT_DIALOG_TYPE_RRN = "reference"
    const val INPUT_DIALOG_TYPE_TRACE = "trace"
    const val INPUT_DIALOG_TYPE_INPUT_CASHIER_PASSWORD = "input_cashier_password"// 4 رقمی
    const val INPUT_DIALOG_TYPE_INPUT_MERCHANT_PASSWORD = "input_merchant_password"// 6 رقمی
    const val INPUT_DIALOG_TYPE_INPUT_SUPERVISOR_PASSWORD = "input_supervisor_password"// 8 رقمی پیشفرض برعکس ساعت
    const val INPUT_DIALOG_TYPE_INPUT_RESET_SUPERVISOR_PASSWORD = "input_reset_supervisor_password"// 8 رقمی پیشفرض برعکس ساعت

    const val INPUT_DIALOG_TYPE_DEFINE_PASSWORD = "define_password"

    const val INPUT_DIALOG_TYPE_DEFINE_CASHIER_PASSWORD = "define_cashier_password"// 4 رقمی
    const val INPUT_DIALOG_TYPE_CASHIER_PASSWORD = "cashier_password"
    const val INPUT_DIALOG_TYPE_DEFINE_MERCHANT_PASSWORD = "define_merchant_password"// 6 رقمی پیشفرض 4 تا صفر
    const val INPUT_DIALOG_TYPE_MERCHANT_PASSWORD = "merchant_password"
    const val INPUT_DIALOG_TYPE_SUPERVISOR_PASSWORD = "supervisor_password"

    //----------------------------------------------------------------- Payment Service Menu Type ----------------------------
    const val TXN_TYPE_NORMAL_MENU = "txn_type_normal_menu" //انتخاب نوع تراکنش
    const val TXN_TYPE_REPORT_MENU = "txn_type_report_menu" // انتخاب نوع گزارش
    const val TXN_TYPE_REPORT_LAST_TXN_MENU = "txn_type_report_last_txn_menu" // انتخاب نوع گزارش برای آخرین تراکنش

    //----------------------------------------------------------------- Reports Type ----------------------------
    const val REPORTS_TYPE_BY_LAST_TXN = "last_txn"
    const val REPORTS_TYPE_BY_TRACE = "trace"
    const val REPORTS_TYPE_BY_RRN = "reference"
    const val REPORTS_TYPE_DAILY = "reports_daily"
    const val REPORTS_TYPE_MICRO = "reports_micro" // ریزتراکنش ها
    const val REPORTS_TYPE_SUMMERY = "reports_summery" //سرجمع
    const val REPORTS_TYPE_INVOICE = "reports_invoice" //صورتحساب

    //----------------------------------------------------------------- SETTING Type ----------------------------
    const val SETTING_TYPE_SUPERVISOR = "setting_type_supervisor"
    const val SETTING_TYPE_PASSWORD_MANAGEMENT = "type_password_management"
    const val SETTING_TYPE_DEVICE_SETTING = "type_device_setting"

    const val SUPERVISOR_TYPE_INIT = "supervisor_type_init"
    const val SUPERVISOR_TYPE_KEY_MANAGEMENT = "supervisor_type_key_management"
    const val SUPERVISOR_TYPE_MAINTENANCE = "supervisor_type_maintenance"

    const val SETTING_TYPE_CONFIGURATION = "type_configuration"
    const val SETTING_TYPE_KEY_INJECTION = "type_key_injection"
    const val SETTING_TYPE_MAINTENANCE = "type_maintenance"

    //Alarm Manager
    const val ACTION_SET_EXACT_ALARM = "ACTION_SET_EXACT_ALARM"
    const val ACTION_SET_REPETITIVE_ALARM = "ACTION_SET_REPETITIVE_ALARM"
    const val EXTRA_EXACT_ALARM_TIME = "ACTION_SET_REPETITIVE_ALARM"

}
