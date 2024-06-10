package com.harsh_kumar.truecalleroauth.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.truecaller.android.sdk.oAuth.TcOAuthData
import com.truecaller.android.sdk.oAuth.TcOAuthError

class MainViewModel:ViewModel() {

    val _authData : MutableState<TcOAuthData?> = mutableStateOf(null)
    val authData :State<TcOAuthData?> = _authData

    val _error : MutableState<TcOAuthError?> = mutableStateOf(null)
    val error :State<TcOAuthError?> = _error


}