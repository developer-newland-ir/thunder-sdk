package com.thunderlight.sdk.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.thunderlight.sdk.R
import com.thunderlight.sdk.constant.ConstantsStr
import com.thunderlight.sdk.databinding.ActivityMainBinding
import com.thunderlight.sdk.ui.service.PaymentServicesAdapter
import com.thunderlight.sdk.ui.service.PaymentServicesViewModel
import com.thunderlight.sdk.ui.service.model.PaymentServiceItem
import com.thunderlight.sdk.utils.hideSoftKeyboard
import com.thunderlight.sdk.utils.strToDigit
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.POS_DATA
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.TRANSACTION_DATA
import com.thunderlight.thundersmartsdk.constant.RequestType
import com.thunderlight.thundersmartsdk.constant.TxnInquiryType
import com.thunderlight.thundersmartsdk.data.PosData
import com.thunderlight.thundersmartsdk.data.TransactionData
import com.thunderlight.thundersmartsdk.generalManager.GeneralSDKManager
import com.thunderlight.thundersmartsdk.generalManager.PosDataCallBack
import com.thunderlight.thundersmartsdk.generalManager.ResultCallBack
import com.thunderlight.thundersmartsdk.generalManager.TransactionCallBack

//https://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android
//https://developer.android.com/training/basics/intents/result#kotlin
open class MainActivity : AppCompatActivity() {

    private val TAG = "3RD MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PaymentServicesViewModel by viewModels()
    private val serviceAdapter by lazy { PaymentServicesAdapter() }
    private var mShadowPaint: Paint? = null
    private val size = 100
    private val mShadowBounds = RectF()

    lateinit var transactionCallBack: TransactionCallBack
    lateinit var sdkManager: GeneralSDKManager
    // private var _stackBlurManager: StackBlurManager? = null


    private var isScanningComplete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTransactionCallBack()
        initSdk()
        initViewModel()
        initView()
    }

    private fun initSdk() {
        sdkManager = GeneralSDKManager()
        val host = sdkManager.init(this@MainActivity)
        binding.topLogo.tvHostName.text = " Host App: Smart " + host.value.substring(0, 1).uppercase() + host.value.substring(1)
    }

    private fun initViewModel() {
        viewModel.menuList.observe(this@MainActivity) {
            initRecyclerView(it)
        }
        viewModel.getMenuItems()

        val largeIcon: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.free)
        /* val blurDrawable = BlurDrawable(binding.imageView, 50)
         binding.imageView.setBackgroundDrawable(blurDrawable);*/
        //binding.imageView.setImageBitmap(blurBitmap(largeIcon))

        var paint = Paint()
        paint.maskFilter = BlurMaskFilter(8f, BlurMaskFilter.Blur.NORMAL)

        paint = Paint(0)
        paint.color = -0xcccccd
        paint.textSize = size.toFloat()
        paint.maskFilter = BlurMaskFilter(8f, BlurMaskFilter.Blur.NORMAL)

        mShadowPaint = Paint(0)
        mShadowPaint!!.color = -0xcccccd
        mShadowPaint!!.maskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL)

        mShadowBounds.top = size.toFloat()
        mShadowBounds.bottom = mShadowBounds.top + size / 2
        mShadowBounds.left = 0f
        mShadowBounds.right = paint.measureText("hello").toInt().toFloat()

        //canvas.drawOval(mShadowBounds,mShadowPaint);
        //_stackBlurManager = StackBlurManager(largeIcon)
        //binding.imageView.setImageBitmap(_stackBlurManager!!.process(50))
        // binding.imageView.setImageBitmap(_stackBlurManager!!.processNatively(50))
        //binding.imageView.setImageBitmap(_stackBlurManager!!.processRenderScript(this,50f))
    }

    private fun initView() {
        // Attention: width of bitmap must be 384 px
        val options = BitmapFactory.Options()
        options.inScaled = false
        //var bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_1)
        //val bitmap = BlurImage.blur(this, binding.imageView.rootView, R.drawable.image_1)
        //bitmap = BlurImage.blurBitmap2(this, bitmap)
        //binding.imageView.setImageBitmap(bitmap)
        // binding.etAmount.requestFocus()

        binding.etAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // بررسی شماره موبایل
                var amount = s.toString()
                if (!isScanningComplete &&
                    amount.isNotEmpty() &&
                    (amount.contains("\n") ||
                            amount.contains("\r") ||
                            amount.contains("\t") ||
                            amount.contains("\t"))
                ) {
                    isScanningComplete = true
                    amount = strToDigit(amount)
                    Log.i(TAG, "afterTextChanged3 amount: $amount")
                    binding.etAmount.text?.clear()
                    binding.etAmount.setText("")
                    sdkManager.doSaleTransaction(this@MainActivity, amount, amount, false, transactionCallBack)
                }
            }
        })
        hideSoftKeyboard(this@MainActivity)
    }

    private fun initTransactionCallBack() {
        transactionCallBack = object : TransactionCallBack {
            override fun onSuccess(transactionData: TransactionData) {
                isScanningComplete = false
                Log.i(TAG, "transactionCallBack onReceive: $transactionData") // toString() of transactionData
                val intent = Intent(this@MainActivity, ResultActivity::class.java)
                intent.putExtra(TRANSACTION_DATA, transactionData)
                startActivity(intent)
            }

            override fun onUndeterminedStateOfPreviousTxn(txn: TransactionData) {
                // در صورت فراخوانی این متد، 3RD party  اجازه شروع تراکنش جدید را ندارد، تا زمانی که تراکنش را تعیین وضعیت کند
                val theProductWasPresented = true

                Log.i(
                    TAG,
                    "onUndeterminedStateOfPreviousTxn: traceNo: ${txn.trace},rrn: ${txn.rrn}, respCode: ${txn.responseCode}"
                )

                val resultCallBack = object : ResultCallBack {
                    override fun onSuccess() {
                        Log.i(TAG, "onSuccess: ")
                    }

                    override fun onError(errorCode: String, errorMsg: String) {
                        Log.i(TAG, "onError: ")
                    }
                }

                if (theProductWasPresented) //اگر 3RD party موفق به ارائه محصول شده است جهت نهایی سازی ارسال تاییدیه میکند
                    ;//   sdkManager.doApprove220(this@MainActivity, transactionData.rrn, resultCallBack)
                else   //اگر 3RD party موفق به ارائه محصول نشد جهت نهایی سازی ارسال اصلاحیه میکند
                ;//   sdkManager.doReverse420(this@MainActivity, transactionData.trace, resultCallBack)

            }

            override fun onError(errorCode: String, errorMsg: String) {
                isScanningComplete = false
                Log.i(TAG, "transactionCallBack onError: $errorCode :  $errorMsg ")
                Toast.makeText(this@MainActivity, "error: $errorCode : $errorMsg ", Toast.LENGTH_LONG).show()
            }

            override fun onCanceled() {
                isScanningComplete = false
                Log.i(TAG, "transactionCallBack onCanceled: ")
                Toast.makeText(this@MainActivity, "Transaction Canceled", Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initRecyclerView(appList: ArrayList<PaymentServiceItem>) {
        binding.apply {
            recyclerView.apply {
                serviceAdapter.setData(appList)
                layoutManager = GridLayoutManager(context, 2)
                adapter = serviceAdapter


                // Transaction Call Back


                // Pos Data Call Back
                val posDataCallBack = object : PosDataCallBack {
                    override fun onReceive(posData: PosData) {
                        Log.i(TAG, "posDataCallBack onReceive: $posData")
                        val intent = Intent(this@MainActivity, ResultActivity::class.java)
                        intent.putExtra(POS_DATA, posData)
                        startActivity(intent)
                    }

                    override fun onError(errorCode: String, errorMsg: String) {
                        Log.i(TAG, "posDataCallBack onError: $errorCode :  $errorMsg ")
                        Toast.makeText(this@MainActivity, "error: $errorCode : $errorMsg ", Toast.LENGTH_LONG).show()
                    }
                }

                // Result Call Back
                val resultCallBack = object : ResultCallBack {
                    override fun onSuccess() {
                        Log.i(TAG, "CallBack onSuccess ")
                        Toast.makeText(this@MainActivity, "--------- Result Success ---------", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(errorCode: String, errorMsg: String) {
                        Log.i(TAG, "CallBack onError: $errorCode :  $errorMsg ")
                        Toast.makeText(this@MainActivity, "error: $errorCode : $errorMsg ", Toast.LENGTH_LONG).show()
                    }
                }

                serviceAdapter.setOnItemClickListener { order, txnType ->
                    if (order == ConstantsStr.ACTION_OPEN) {
                        when (txnType) {
                            RequestType.REQUEST_TYPE_BALANCE -> {
                                sdkManager.inquiryBalance(this@MainActivity, transactionCallBack)
                            }

                            RequestType.REQUEST_TYPE_SALE -> {
                                val reserveNumber = "0123456879"//شناسه پرداخت

                                val listener = object : InputDialogDataCallBack {
                                    override fun getData(amount: String) {
                                        sdkManager.doSaleTransaction(this@MainActivity, amount, reserveNumber, false, transactionCallBack)
                                    }

                                    override fun onCancel() {}
                                }

                                val dialog = InputDialogFragment()
                                val extraData = Bundle()
                                extraData.putSerializable("listener", listener)
                                extraData.putString("hint", "مبلغ")
                                dialog.arguments = extraData
                                dialog.show(supportFragmentManager, "InputDialogFragment")
                            }

                            RequestType.REQUEST_TYPE_DO_APPROVE -> {
                                val listener = object : InputDialogDataCallBack {
                                    override fun getData(rrn: String) {
                                        sdkManager.doApprove220(this@MainActivity, rrn, resultCallBack)
                                    }

                                    override fun onCancel() {}
                                }

                                val dialog = InputDialogFragment()
                                val extraData = Bundle()
                                extraData.putSerializable("listener", listener)
                                extraData.putString("hint", "شماره مرجع")
                                dialog.arguments = extraData
                                dialog.show(supportFragmentManager, "InputDialogFragment")
                            }

                            RequestType.REQUEST_TYPE_DO_REVERSE -> {
                                val listener = object : InputDialogDataCallBack {
                                    override fun getData(trace: String) {
                                        sdkManager.doReverse420(this@MainActivity, trace, resultCallBack)
                                    }

                                    override fun onCancel() {}
                                }

                                val dialog = InputDialogFragment()
                                val extraData = Bundle()
                                extraData.putSerializable("listener", listener)
                                extraData.putString("hint", "شماره پیگیری")
                                dialog.arguments = extraData
                                dialog.show(supportFragmentManager, "InputDialogFragment")
                            }

                            RequestType.REQUEST_TYPE_BILL -> {
                                sdkManager.doServiceTransaction(this@MainActivity, RequestType.REQUEST_TYPE_BILL, false, transactionCallBack)
                            }

                            RequestType.REQUEST_TYPE_CHARGE_PIN -> {
                                sdkManager.doServiceTransaction(this@MainActivity, RequestType.REQUEST_TYPE_CHARGE, false, transactionCallBack)
                            }

                            RequestType.REQUEST_TYPE_INQUIRY_TRANSACTION -> {
                                //شناسه برای استعلام تراکنش
                                val trace = "69"
                                val rrn = "123721175465"
                                val reserveNumber = "123465798"


                                val listener = object : InputDialogDataCallBack {
                                    override fun getData(rrn: String) {
                                        // TxnInquiryType به صورت enum تعریف شده است، که براساس نیاز میتوانید مقدار آنرا تغییر دهید
                                        val inquiryType = TxnInquiryType.REQUEST_TYPE_INQUIRY_BY_RRN

                                        sdkManager.inquiryTransactionData(this@MainActivity, inquiryType, rrn, true, transactionCallBack) // 1
                                        //sdkManager.inquiryTransactionData(this@MainActivity, inquiryType, trace, true, transactionCallBack) // 2
                                        //sdkManager.inquiryTransactionData(this@MainActivity, inquiryType, reserveNumber, true, transactionCallBack) // 3
                                    }

                                    override fun onCancel() {}
                                }

                                val dialog = InputDialogFragment()
                                val extraData = Bundle()
                                extraData.putSerializable("listener", listener)
                                extraData.putString("hint", "شماره مرجع")
                                dialog.arguments = extraData
                                dialog.show(supportFragmentManager, "InputDialogFragment")
                            }

                            RequestType.REQUEST_TYPE_DO_KEY_CHANGE -> {
                                sdkManager.doConfiguration(this@MainActivity, resultCallBack)
                            }

                            RequestType.REQUEST_TYPE_INQUIRY_POS_DATA -> {
                                sdkManager.inquiryPosData(this@MainActivity, posDataCallBack)
                            }

                            RequestType.REQUEST_TYPE_PRINT_BITMAP -> {
                                // Attention: width of bitmap must be 384 px
                                val options = BitmapFactory.Options()
                                options.inScaled = false
                                val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_384, options)
                                sdkManager.printBitmap(this@MainActivity, bitmap, resultCallBack)
                            }

                            else -> {
                            }
                        }
                    }
                }
            }
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

        }
    }

    fun openYourActivity() {
        /*val intent = Intent(this, "")
        resultLauncher.launch(intent)*/
    }


    fun setResult() {
        val intent = intent
        intent.putExtra("Date", "")
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun onActivityResult0(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            val mBundle = data.extras
            val mMessage = mBundle!!.getString("Date")
        }
    }

    open fun blurBitmap(bitmap: Bitmap): Bitmap? {

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        val outBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)

        //Instantiate a new Renderscript
        val rs: RenderScript = RenderScript.create(applicationContext)

        //Create an Intrinsic Blur Script using the Renderscript
        val blurScript: ScriptIntrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

        //Create the in/out Allocations with the Renderscript and the in/out bitmaps
        val allIn: Allocation = Allocation.createFromBitmap(rs, bitmap)
        val allOut: Allocation = Allocation.createFromBitmap(rs, outBitmap)

        //Set the radius of the blur
        blurScript.setRadius(25f)

        //Perform the Renderscript
        blurScript.setInput(allIn)
        blurScript.forEach(allOut)

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap)

        //recycle the original bitmap
        bitmap.recycle()

        //After finishing everything, we destroy the Renderscript.
        rs.destroy()
        return outBitmap
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.i(TAG, "onKeyDown: $keyCode")
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Barcode scan is complete
            Log.i(TAG, "onKeyDown: KEYCODE_ENTER")
            isScanningComplete = true
            handleBarcodeScan()
            return true // Consume the event
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun handleBarcodeScan() {
        if (isScanningComplete) {
            // Perform actions on scanning completion
            // For example, display the scanned barcode
            var amount = binding.etAmount.text.toString()
            amount = strToDigit(amount)
            Log.i(TAG, "handleBarcodeScan: $amount")
            Toast.makeText(this, "Scanned barcode: ", Toast.LENGTH_SHORT).show()

            //sdkManager.doSaleTransaction(this@MainActivity, amount, amount, true, transactionCallBack)
        }
    }
}