package com.example.demo_one_a

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.demo_one_a.data.remote.PostsService
import com.example.demo_one_a.data.remote.dto.PostResponse
import com.example.demo_one_a.ui.main.MainPage
import com.example.demo_one_a.ui.main.Detail
import com.example.demo_one_a.ui.main.Register
import dagger.hilt.android.AndroidEntryPoint
//import dagger.hilt.android.AndroidEntryPoint
import io.metamask.androidsdk.Dapp
import io.metamask.androidsdk.Ethereum
import io.metamask.androidsdk.RequestError
import io.metamask.androidsdk.TAG
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val service = PostsService.create()
    val ethereumViewModel: EthereumViewModel by viewModels()
    val dapp = Dapp("Droid Dapp", "https://droiddapp.com")
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ProduceStateDoesNotAssignValue",
        "CoroutineCreationDuringComposition"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val posts = produceState<List<PostResponse>>(
                initialValue = emptyList(),
                producer = {
                    value = service.getPosts()
                })
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "WelcomePage"){
                composable("WelcomePage"){
                    WelcomePage(navController = navController, ethereumViewModel = ethereumViewModel, )
                }
                composable("MainPage") {
                    MainPage(navController = navController)
                }
                composable("Register/{address}") {
                        backStackEntry ->
//                    var address by remember {
//                        mutableStateOf("")
//                    }
//                    rememberCoroutineScope().launch {
//                        val result = service.getEthereumAccounts()
//                    Log.d(TAG, "Address: ${result}")
//                        address = result.toString()
//                    }
                    val address = backStackEntry.arguments?.getString("address")
                    if (address != null) {
                        rememberCoroutineScope().launch {
                            val result = service.isRegistered(metamaskAddress = address)
                            if (result != null){
                                navController.navigate("MainPage")
                                Log.d("Terdaftar", result.toString())
                            }
                                Log.d("Tidak Terdaftar", result.toString())

                        }
                        Register(navController = navController, ethereumViewModel = ethereumViewModel, postsService = service, address = address)
                    }
                }
                composable("detail/{key}") {backStackEntry ->
                    val key = backStackEntry.arguments?.getString("key")?.toIntOrNull() ?: 0
                    Detail(key = key, navController = navController)
                }
            }
        }
    }
}



@Composable
fun Lottie(){
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.nfts))
    LottieAnimation(composition = composition, modifier = Modifier.size(width = 400.dp, height = 600.dp))
}



@Composable
fun WelcomePage(navController: NavController ?= null, ethereumViewModel: EthereumViewModel){
    val dapp = Dapp("Droid Dapp", "https://droiddapp.com")
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 50.dp)) {
        Lottie()
        ElevatedButton(onClick = {
            ethereumViewModel.connect(dapp) { result ->
                Handler(Looper.getMainLooper()).post {
                    if (result is RequestError) {
                        Log.e(TAG, "Ethereum connection error: ${result.message}")
                    } else {
                        Log.d(TAG, "Ethereum connection result: $result")
                        navController?.navigate("Register/$result")
                    }
                }
            }

                                 },
            colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue
        ), shape = RoundedCornerShape(5.dp),
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp),
            content = {
                Row (horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.size(width = 80.dp, height = 20.dp)) {
                    Image(painter = painterResource(id = R.drawable.metamask), contentDescription = "metamask", modifier = Modifier.size(20.dp))
                    Text(text = "Sign In",)
                }
            }
        )
        Text(text = "WELCOME TO DEMFI", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold,)
        Text(text = "Explore, Buy, and Sell Digital Art and Photography on Our \n" +
                "Decentralized Marketplace", textAlign = TextAlign.Center)
    }
}

