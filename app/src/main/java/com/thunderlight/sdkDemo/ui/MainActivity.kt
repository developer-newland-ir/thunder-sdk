package com.thunderlight.sdkDemo.ui

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.enrique.stackblur.StackBlurManager
import com.thunderlight.sdkDemo.constant.ConstantsStr
import com.thunderlight.sdk.R
import com.thunderlight.sdk.databinding.ActivityMainBinding
import com.thunderlight.sdkDemo.ui.service.PaymentServicesAdapter
import com.thunderlight.sdkDemo.ui.service.PaymentServicesViewModel
import com.thunderlight.sdkDemo.ui.service.model.PaymentServiceItem
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.POS_DATA
import com.thunderlight.thundersmartsdk.constant.ConstantsStr.TRANSACTION_DATA
import com.thunderlight.thundersmartsdk.constant.RequestType
import com.thunderlight.thundersmartsdk.constant.TxnInquiryType
import com.thunderlight.thundersmartsdk.data.PosData
import com.thunderlight.thundersmartsdk.data.TransactionData
import com.thunderlight.thundersmartsdk.sadad.ResultCallBack
import com.thunderlight.thundersmartsdk.sadad.PosDataCallBack
import com.thunderlight.thundersmartsdk.sadad.SDKManager
import com.thunderlight.thundersmartsdk.sadad.TransactionCallBack


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

    private var _stackBlurManager: StackBlurManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initView()
    }

    private fun initViewModel() {
        viewModel.menuList.observe(this@MainActivity) {
            initRecyclerView(it)
        }
        viewModel.getMenuItems()

        val largeIcon: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.free)
        /* val blurDrawable = BlurDrawable(binding.imageView, 50)
         binding.imageView.setBackgroundDrawable(blurDrawable);
 */
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

    }

    private fun initRecyclerView(appList: ArrayList<PaymentServiceItem>) {
        binding.apply {
            recyclerView.apply {
                serviceAdapter.setData(appList)

                layoutManager = GridLayoutManager(context, 2)
                adapter = serviceAdapter
                val sdkManager = SDKManager()

                val transactionCallBack = object : TransactionCallBack {
                    override fun onReceive(transactionData: TransactionData) {
                        Log.i(TAG, "transactionCallBack onReceive: $transactionData")
                        val intent = Intent(this@MainActivity, ResultActivity::class.java)
                        intent.putExtra(TRANSACTION_DATA, transactionData)
                        startActivity(intent)
                    }

                    override fun onError(errorCode: String, errorMsg: String) {
                        Log.i(TAG, "transactionCallBack onError: $errorCode :  $errorMsg ")
                        Toast.makeText(this@MainActivity, "error: $errorCode : $errorMsg ", Toast.LENGTH_LONG).show()
                    }

                    override fun onCanceled() {
                        Log.i(TAG, "transactionCallBack onCanceled: ")
                        Toast.makeText(this@MainActivity, "Canceled", Toast.LENGTH_LONG).show()
                    }
                }

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

                val resultCallBack = object : ResultCallBack {
                    override fun onSuccess() {
                        Log.i(TAG, "keyChangeCallBack onSuccess ")
                        Toast.makeText(this@MainActivity, "--------- Result Success ---------", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(errorCode: String, errorMsg: String) {
                        Log.i(TAG, "keyChangeCallBack onError: $errorCode :  $errorMsg ")
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
                                val amount = "10000"
                                val reserveNumber = "0123456879"//شناسه پرداخت
                                sdkManager.doSaleTransaction(this@MainActivity, amount, reserveNumber, false, transactionCallBack)
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
                                val rrn = "320138312569"
                                val reserveNumber = "69"

                                // TxnInquiryType به صورت enum تعریف شده است، که براساس نیاز میتوانید مقدار آنرا تغییر دهید
                                val inquiryType = TxnInquiryType.REQUEST_TYPE_INQUIRY_BY_RRN

                                sdkManager.inquiryTransactionData(this@MainActivity, inquiryType, rrn, true, transactionCallBack)
                            }

                            RequestType.REQUEST_TYPE_DO_KEY_CHANGE -> {
                                sdkManager.doKeyChange(this@MainActivity, resultCallBack)
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
}