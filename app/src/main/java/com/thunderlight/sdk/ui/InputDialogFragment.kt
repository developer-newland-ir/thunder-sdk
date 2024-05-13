package com.thunderlight.sdk.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.thunderlight.sdk.databinding.FragmentInputDialogBinding


private const val ARG_PARAM1 = "rrn"
private const val ARG_PARAM2 = "hint"
private const val ARG_PARAM3 = "listener"

class InputDialogFragment : DialogFragment() {

    private val TAG = "InputDialogFragment"
    private lateinit var binding: FragmentInputDialogBinding
    private var rrn: String? = null
    private var textHint: String? = null
    private var listener: InputDialogDataCallBack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rrn = it.getString(ARG_PARAM1)
            textHint = it.getString(ARG_PARAM2)
            listener = it.getSerializable(ARG_PARAM3) as InputDialogDataCallBack
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputDialogBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.apply {
            etInput.hint = textHint
            btnOk.setOnClickListener {
                etInput.error = null
                val firstValue = etInput.text.toString()
                if (firstValue.isNotEmpty()) {
                    etInput.clearFocus()

                    Log.i(TAG, "initView: $firstValue")
                    listener?.getData(firstValue)
                    dismiss()
                } else {
                    etInput.error = "مقدار صحیح وارد نمایید"
                }
            }
        }
    }
}