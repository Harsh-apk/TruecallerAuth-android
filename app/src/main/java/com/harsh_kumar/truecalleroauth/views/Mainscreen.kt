package com.harsh_kumar.truecalleroauth.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.harsh_kumar.truecalleroauth.viewModels.MainViewModel

@Composable
//@Preview(showBackground = true, showSystemUi = true)
fun Mainscreen(usable:()->Unit,viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ){
    if (viewModel.authData.value != null) {
        Text(text = viewModel.authData.value!!.authorizationCode)
    }else if(viewModel.error.value!=null){
        Text(text = viewModel.error.value!!.errorMessage)
    }else {
       
            Button(onClick = {
                usable()

            }) {
                Text(text = "Start")
            }
        }
    }
    
}