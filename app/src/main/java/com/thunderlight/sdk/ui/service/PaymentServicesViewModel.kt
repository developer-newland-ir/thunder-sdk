package com.thunderlight.sdk.ui.service

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thunderlight.sdk.ui.service.model.PaymentServiceItem
import com.thunderlight.sdk.R
import com.thunderlight.thundersmartsdk.constant.RequestType


class PaymentServicesViewModel : ViewModel() {

    var menuList = MutableLiveData<ArrayList<PaymentServiceItem>>()
    var subMenuList = MutableLiveData<ArrayList<PaymentServiceItem>>()

    fun getMenuItems() {
        val list = ArrayList<PaymentServiceItem>()
        list.add(PaymentServiceItem(RequestType.REQUEST_TYPE_SALE, "خرید", R.drawable.buy))
        list.add(PaymentServiceItem(RequestType.REQUEST_TYPE_BALANCE, "موجودی", R.drawable.wallet))
        list.add(PaymentServiceItem(RequestType.REQUEST_TYPE_CHARGE_PIN, "شارژ", R.drawable.sim))
        list.add(PaymentServiceItem(RequestType.REQUEST_TYPE_BILL, "قبض", R.drawable.bill))

        list.add(PaymentServiceItem(RequestType.REQUEST_TYPE_DO_KEY_CHANGE, "تراکنش پیکربندی", R.drawable.ic_configuration))
        list.add(PaymentServiceItem(RequestType.REQUEST_TYPE_INQUIRY_TRANSACTION, "استعلام تراکنش", R.drawable.inquiry))
        list.add(PaymentServiceItem(RequestType.REQUEST_TYPE_PRINT_BITMAP, "چاپ bitmap", R.drawable.printer))
        list.add(PaymentServiceItem(RequestType.REQUEST_TYPE_INQUIRY_POS_DATA, "اطلاعات دستگاه", R.drawable.merchant_inquiry))

        menuList.postValue(list)
    }
}