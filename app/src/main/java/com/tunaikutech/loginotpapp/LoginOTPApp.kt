package com.tunaikutech.loginotpapp

import android.app.Application
import android.util.Log


/**
 *
 * Created by Suyanwar on 19/01/19.
 * Android Engineer
 *
 **/

class LoginOTPApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //Generate Hash
        Log.i("AppApplication", "appSignatures = ${AppSignatureHelper(this).getAppSignatures()}")
    }

}