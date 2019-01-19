package com.tunaikutech.loginotpapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_login.*


/**
 *
 * Created by Suyanwar on 1/19/19.
 * Android Engineer
 *
 **/

class LoginActivity : AppCompatActivity(),
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {


    companion object {
        const val TAG_RC_HINT = 1
    }

    private var mCredentialsApiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        mCredentialsApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .enableAutoManage(this, this)
            .addApi(Auth.CREDENTIALS_API)
            .build()

        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()

        val intent = Auth.CredentialsApi.getHintPickerIntent(
            mCredentialsApiClient, hintRequest)

        try {
            startIntentSenderForResult(intent.intentSender, TAG_RC_HINT, null, 0, 0, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        btnSubmitPhoneNumber.setOnClickListener{
            //Submit your phone number to your server and request OTP
            val otpIntent = Intent(this, OtpActivity::class.java)
            startActivity(otpIntent)
        }
    }
    override fun onConnected(p0: Bundle?) {
        //
    }

    override fun onConnectionSuspended(p0: Int) {
        //
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        //
    }



    /**
     * Reformat phone number for login
     * */

    fun String.reformatPhoneNumber(): String {
        return when {
            this.startsWith("+62") -> this.substring(3)
            this.startsWith("08") -> this.substring(1)
            else -> this
        }
    }

    /**
     * Getting phone number from IntentResult
     *
     * */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TAG_RC_HINT && resultCode == Activity.RESULT_OK) {

            //Get phone number selected
            val credential: Credential = data!!.getParcelableExtra(Credential.EXTRA_KEY)

            etLoginPhoneNumber.setText(credential.id.reformatPhoneNumber())
        }
    }
}
