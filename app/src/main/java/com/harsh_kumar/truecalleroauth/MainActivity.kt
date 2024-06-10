package com.harsh_kumar.truecalleroauth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.harsh_kumar.truecalleroauth.ui.theme.TrueCallerOAuthTheme
import com.harsh_kumar.truecalleroauth.viewModels.MainViewModel
import com.harsh_kumar.truecalleroauth.views.Mainscreen
import com.truecaller.android.sdk.oAuth.CodeVerifierUtil
import com.truecaller.android.sdk.oAuth.TcOAuthCallback
import com.truecaller.android.sdk.oAuth.TcOAuthData
import com.truecaller.android.sdk.oAuth.TcOAuthError
import com.truecaller.android.sdk.oAuth.TcSdk
import com.truecaller.android.sdk.oAuth.TcSdkOptions
import java.math.BigInteger
import java.security.SecureRandom

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel : MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val tcOAuthCallback: TcOAuthCallback = object : TcOAuthCallback {
            override fun onSuccess(tcOAuthData: TcOAuthData) {
                viewModel._authData.value = tcOAuthData
                println(tcOAuthData.state)
                Toast.makeText(this@MainActivity,"FROM SUCCESS : "+tcOAuthData.authorizationCode,Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationRequired(tcOAuthError: TcOAuthError?) {
                println(tcOAuthError?.errorMessage)
            }

            override fun onFailure(tcOAuthError: TcOAuthError) {
                Toast.makeText(this@MainActivity,"FROM Failure : "+tcOAuthError?.errorMessage,Toast.LENGTH_SHORT).show()
                viewModel._error.value = tcOAuthError
                println(tcOAuthError.errorMessage)
            }
        }
        fun init(){

            // clear any existing instance before initializing a new one
            TcSdk.clear()
            val tcSdkOptions = TcSdkOptions.Builder(this, tcOAuthCallback).build()
            TcSdk.init(tcSdkOptions)


        }

        fun startAuth(){

            val isUsable = TcSdk.getInstance().isOAuthFlowUsable
            if(!isUsable){
                Toast.makeText(this,"Can't use Truecaller Auth",Toast.LENGTH_SHORT).show()
                return
            }


            try{
                TcSdk.getInstance().setOAuthScopes(arrayOf("openid"))
                val codeVerifier = CodeVerifierUtil.generateRandomCodeVerifier()
                val codeChallenge = CodeVerifierUtil.getCodeChallenge(codeVerifier)
                if(codeChallenge!=null){
                    TcSdk.getInstance().setCodeChallenge(codeChallenge)
                }else {
                    print("Code challenge is Null. Can’t proceed further")
                }
                val stateRequested = BigInteger(130, SecureRandom()).toString(32)

                TcSdk.getInstance().setOAuthState(stateRequested)
                TcSdk.getInstance().getAuthorizationCode(this)
            }catch(e:Error){
                println("Error: "+ e.message)
            }

        }
        init()

        enableEdgeToEdge()
        setContent {
            TrueCallerOAuthTheme {
                Mainscreen(::startAuth,viewModel)
            }
        }
    }
}

