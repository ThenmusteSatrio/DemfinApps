package com.example.demo_one_a.ui.main

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demo_one_a.EthereumViewModel
import com.example.demo_one_a.data.remote.PostsService
import com.example.demo_one_a.ui.theme.provider
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.metamask.androidsdk.TAG
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@SuppressLint("CommitPrefEdits")
@Composable
fun Register(
    navController: NavController?= null,
    ethereumViewModel: EthereumViewModel?= null,
    postsService: PostsService = PostsService.create(),
    address: String = "",
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }

    val sharedPreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    fun uriToFile(context: Context, uri: Uri): File {
        val contentResolver: ContentResolver = context.contentResolver
        val mimeType = contentResolver.getType(uri)
        val extension = mimeType?.let { MimeTypeMap.getSingleton().getExtensionFromMimeType(it) } ?: "tmp"

        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_image.$extension")

        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        return file
    }


    var username by remember { mutableStateOf(TextFieldValue("")) }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFF191720))){
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column (verticalArrangement = Arrangement.spacedBy(20.dp) ) {
                Column(){
                    Text(modifier = Modifier.padding(bottom = 7.dp), text = "Let's sign you in.", fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 40.sp, fontFamily = FontFamily(
                        Font(googleFont = GoogleFont("Amatic SC"), fontProvider = provider)
                    ))
                    Text(text = "Welcome back. \nYou've been missed!", lineHeight = 34.sp, fontWeight = FontWeight.Light, color = Color.White, fontSize = 30.sp, fontFamily = FontFamily(
                        Font(googleFont = GoogleFont("Amatic SC"), fontProvider = provider)
                    ))
                }

                Column(modifier = Modifier.padding(top = 20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)){
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(color = Color.Black.copy(alpha = 0.3f)),
                        value = username,
                        onValueChange = { newText ->
                            username = newText
                        },
                        placeholder = { Text(text = "Username ", maxLines = 1, overflow = TextOverflow.Ellipsis, color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))},
                        textStyle = TextStyle(color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))
                    )
                    TextField(
                        modifier = Modifier
                            .clickable {
                                launcher.launch("image/*")
                                Log.d("Register", "Selected URI: $selectedImageUri")
                            }
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(color = Color.Black.copy(alpha = 0.3f)),
                        value = "",
                        enabled = false,
                        onValueChange = {},
                        trailingIcon = {
                            Icon(tint = Color.White, modifier = Modifier
                                .size(40.dp)
                                .padding(end = 10.dp), imageVector = Icons.Default.Face, contentDescription = "profile foto")
                        },
                        placeholder = { Text(text = "Image ", maxLines = 1, overflow = TextOverflow.Ellipsis, color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))},
                        textStyle = TextStyle(color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))
                    )
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .height(45.dp)
                    .clickable {
                        Log.d("Register", "Selected URI: $selectedImageUri")
                        selectedImageUri?.let { uri ->
                        val file = uriToFile(context, selectedImageUri!!)
                        Log.d("Register", "File path: ${file.absolutePath}, Size: ${file.length()}")
                        Log.d(TAG, "Uploading file: ${file.name}, username: ${username.text}, address: $address")
                            scope.launch {
                                val response = postsService.uploadImage(file, username.text, address)
                                Log.i(TAG, response?.status?.toString() ?: "No response")
                                if (response?.status == HttpStatusCode.OK) {
                                    val responseBody = response.bodyAsText()
                                    Log.d(TAG, "success register: $responseBody")
                                    // Parse response JSON and extract data
                                    val jsonResponse = JSONObject(responseBody)
                                    val imageUrl = jsonResponse.getString("image")
                                    val userId = jsonResponse.getInt("id")
                                    val userUsername = jsonResponse.getString("username")
                                    val userAddress = jsonResponse.getString("address")

                                    // Save data to SharedPreferences
                                    editor.putString("userId", userId.toString())
                                    editor.putString("username", userUsername)
                                    editor.putString("address", userAddress)
                                    editor.putString("imageUrl", imageUrl)
                                    editor.apply()
                                    navController?.navigate("MainPage")
                                } else {
                                    Log.d(TAG, "gagal terhubung: ${response?.status}")
                                }
                            }

                        }
                    }
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Sign In", color = Color.Black, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}


@Preview(name = "Register", showBackground = true)
@Composable
private fun PreviewRegister() {
    Register()
}