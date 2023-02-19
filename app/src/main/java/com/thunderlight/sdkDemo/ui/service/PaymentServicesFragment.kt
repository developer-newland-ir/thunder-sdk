package com.thunderlight.sdkDemo.ui.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.thunderlight.sdkDemo.constant.ConstantsStr.ACTION_OPEN
import com.thunderlight.sdkDemo.constant.ConstantsStr.HASH_PAN
import com.thunderlight.sdkDemo.constant.ConstantsStr.INPUT_TYPE
import com.thunderlight.sdkDemo.constant.ConstantsStr.PAN
import com.thunderlight.sdkDemo.constant.ConstantsStr.TRK2
import com.thunderlight.sdkDemo.constant.ConstantsStr.TXN_TYPE
import com.thunderlight.sdkDemo.constant.ConstantsStr.TXN_TYPE_NORMAL_MENU
import com.thunderlight.sdkDemo.constant.ConstantsStr.TXN_TYPE_REPORT_LAST_TXN_MENU
import com.thunderlight.sdkDemo.constant.ConstantsStr.TXN_TYPE_REPORT_MENU
import com.thunderlight.sdkDemo.ui.service.model.PaymentServiceItem
import com.thunderlight.sdk.databinding.FragmentPaymentServicesBinding


class PaymentServicesFragment : Fragment() {

    //این layout برای انتخاب نوع گزارش نیز استفاده شده است
    private lateinit var binding: FragmentPaymentServicesBinding

    lateinit var serviceAdapter: PaymentServicesAdapter
    private val viewModel: PaymentServicesViewModel by viewModels()
    private var inputType: String = ""

    private lateinit var tr2: String
    private lateinit var pan: String
    private lateinit var hashPan: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inputType = arguments?.getString(INPUT_TYPE) ?: ""
        if (inputType.isNotEmpty() && inputType != TXN_TYPE_NORMAL_MENU)
        else if (inputType == TXN_TYPE_NORMAL_MENU) {
            tr2 = arguments?.getString(TRK2) ?: ""
            pan = arguments?.getString(PAN) ?: ""
            hashPan = arguments?.getString(HASH_PAN) ?: ""
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPaymentServicesBinding.inflate(layoutInflater, container, false)
        val view: View
        view = binding.root
        initView()
        initViewModel()
        return view
    }


    private fun initView() {

    }

    private fun initViewModel() {
        if (inputType == TXN_TYPE_NORMAL_MENU) {
            viewModel.menuList.observe(viewLifecycleOwner) {
                initRecyclerView(it)
            }
            viewModel.getMenuItems()
        } else if (inputType == TXN_TYPE_REPORT_MENU) {
            viewModel.subMenuList.observe(viewLifecycleOwner) {
                initRecyclerView(it)
            }
        } else if (inputType == TXN_TYPE_REPORT_LAST_TXN_MENU) {
            viewModel.subMenuList.observe(viewLifecycleOwner) {
                initRecyclerView(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //(activity as SingleActivity?)?.hideNavigation()
    }

    private fun initRecyclerView(appList: ArrayList<PaymentServiceItem>) {
        binding.apply {
            recyclerView.apply {
                serviceAdapter.setData(appList)

                layoutManager = GridLayoutManager(context, 2)
                adapter = serviceAdapter

                serviceAdapter.setOnItemClickListener { order, txnType ->
                    if (order == ACTION_OPEN) {
                        arguments?.putSerializable(TXN_TYPE, txnType)
                        if (inputType == TXN_TYPE_NORMAL_MENU) {
                            //findNavController().navigateUp()

                        } else/* if (inputType == PAYMENT_REPORT_MENU)*/ {

                        }
                    }
                }
            }
        }
    }
}
