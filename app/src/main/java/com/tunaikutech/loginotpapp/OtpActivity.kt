package com.tunaikutech.loginotpapp

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import kotlinx.android.synthetic.main.activity_otp.*


/**
 *
 * Created by Suyanwar on 19/01/19.
 * Android Engineer
 *
 **/

class OtpActivity : AppCompatActivity(),
    SMSReceiver.OTPReceiveListener{


    private val smsBroadcast = SMSReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)


        val client = SmsRetriever.getClient(this /* context */)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
            // Successfully started retriever, expect broadcast intent
        }

        task.addOnFailureListener {
        }

        smsBroadcast.initOTPListener(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)


        this.registerReceiver(smsBroadcast, intentFilter)
    }


    override fun onOTPReceived(otp: String) {
        tvOtpCode.text = otp
    }

    override fun onOTPTimeOut() {
        //
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(smsBroadcast)
    }
}