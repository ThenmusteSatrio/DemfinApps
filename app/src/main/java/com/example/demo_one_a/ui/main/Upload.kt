package com.example.demo_one_a.ui.main

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.example.demo_one_a.R
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.exp

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Upload(navController: NavController?= null,) {
    val service = PostsService.create()

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var artist by remember { mutableStateOf(TextFieldValue("")) }
    var chain by remember { mutableStateOf(TextFieldValue("")) }
    var price by remember { mutableStateOf(TextFieldValue("")) }
//    date
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember {
        mutableStateOf("")
    }
//    InspectorInfo.
    val context = LocalContext.current
    val listOfInspector = arrayOf("Mike Shinoda", "John Doe")
    var selectedInspector by remember { mutableStateOf(listOfInspector[0]) }
    var expandedInspector by remember { mutableStateOf(false) }
//    Lender
    val listOfLenders = arrayOf("Joe Hanh", "Brad Delson")
    var selectedLenders by remember { mutableStateOf(listOfLenders[0]) }
    var expandedLender by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }
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

    fun getUserProfile(context: Context): Map<String, String?> {
        val sharedPreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE)
        return mapOf(
            "userId" to sharedPreferences.getString("userId", null),
            "username" to sharedPreferences.getString("username", null),
            "address" to sharedPreferences.getString("address", null),
            "imageUrl" to sharedPreferences.getString("imageUrl", null)
        )
    }
    val userProfile = getUserProfile(context)


    Box(modifier = Modifier
        .fillMaxSize()
        ,
        content = {
            Image(painter = painterResource(id = R.drawable.uploadbackg), contentDescription = "background", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.5f))
                .blur(radius = 10.dp),)
            Column (modifier = Modifier
                .padding(horizontal = 20.dp,)
                .padding(top = 70.dp)) {
                Text(
                    text = "Input NFT Data",
                    fontWeight = FontWeight.Bold,
                    fontSize = 45.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(googleFont = GoogleFont("Amatic SC"), fontProvider = provider))
                )
                Text(
                    text = "The Metadata Fields for  your NFT",
                    color = Color.LightGray,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(googleFont = GoogleFont("Amatic SC"), fontProvider = provider))
                )

                Column (modifier = Modifier.padding(top = 10.dp)) {
                    Text(text = "Name", color = Color.White, modifier = Modifier.padding(bottom = 5.dp))
                    TextField(
                        modifier = Modifier
                            .width(360.dp)
                            .background(color = Color.Black.copy(alpha = 0.3f)),
                        value = name,
                        onValueChange = { newText ->
                            name = newText
                        },
                        placeholder = { Text(text = "Ex: monkey adidas ", color = Color.Gray,  fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))},
                        textStyle = TextStyle(color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))
                    )
                    Row (modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(text = "Artist", color = Color.White, modifier = Modifier.padding(bottom = 5.dp))
                            TextField(
                                modifier = Modifier
                                    .width(160.dp)
                                    .background(color = Color.Black.copy(alpha = 0.3f)),
                                value = artist,
                                onValueChange = { newText ->
                                    artist = newText
                                },
                                placeholder = { Text(text = "Ex: Jonathan Davis ", maxLines = 1, overflow = TextOverflow.Ellipsis, color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))},
                                textStyle = TextStyle(color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))
                            )
                        }
                        Column {
                            Text(text = "Chain", color = Color.White, modifier = Modifier.padding(bottom = 5.dp))
                            TextField(
                                modifier = Modifier
                                    .width(160.dp)
                                    .background(color = Color.Black.copy(alpha = 0.3f)),
                                value = chain,
                                onValueChange = { newText ->
                                    chain = newText
                                },
                                placeholder = { Text(text = "Ex: Avalanche ", maxLines = 1, overflow = TextOverflow.Ellipsis, color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))},
                                textStyle = TextStyle(color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))
                            )
                        }
                    }
                    Row (modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(text = "Price", color = Color.White, modifier = Modifier.padding(bottom = 5.dp))
                            TextField(
                                modifier = Modifier
                                    .width(160.dp)
                                    .background(color = Color.Black.copy(alpha = 0.3f)),
                                value = price,
                                onValueChange = { newText ->
                                    price = newText
                                },
                                placeholder = { Text(text = "Ex: 0.5 ETH ", maxLines = 1, overflow = TextOverflow.Ellipsis, color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))},
                                textStyle = TextStyle(color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))
                            )
                        }
                        Column {
                            Text(text = "Created At", color = Color.White, modifier = Modifier.padding(bottom = 5.dp))
//                            TextField(
//                                label = { Text("Date", modifier = Modifier, maxLines = 1, overflow = TextOverflow.Ellipsis, color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider))) },
//                                modifier = Modifier
//                                    .width(160.dp)
//                                    .background(color = Color.Black.copy(alpha = 0.3f)),
//                                value = price,
//                                onValueChange = { newText ->
//                                    price = newText
//                                },
//                                placeholder = { Text(text = "Ex: 0.5 ETH ", maxLines = 1, overflow = TextOverflow.Ellipsis, color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))},
//                                textStyle = TextStyle(color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))
//                            )
                            OutlinedTextField(
                                value = selectedDate,
                                onValueChange = { },
                                textStyle = TextStyle(color = Color.White),
                                placeholder = { Text(text = selectedDate, maxLines = 1, overflow = TextOverflow.Ellipsis, color = Color.White, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))},
                                label = { Text("Date", modifier = Modifier, maxLines = 1, overflow = TextOverflow.Ellipsis, color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider))) },
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(onClick = { showDatePicker = !showDatePicker }) {
                                        Icon(
                                            imageVector = Icons.Default.DateRange,
                                            contentDescription = "Select date",
                                            tint = Color.Gray
                                        )
                                    }
                                },

                                modifier = Modifier
                                    .width(160.dp)
                                    .height(55.dp)
                                    .background(color = Color.Black.copy(alpha = 0.3f))
                            )
                        }
                    }

                    if (showDatePicker){
                        val dataPickerState = rememberDatePickerState()
                        val confirmEnabled = derivedStateOf { dataPickerState.selectedDateMillis != null }
                        
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(onClick = { showDatePicker = false
                                    var date = "No Selection"
                                    if (dataPickerState.selectedDateMillis != null){
                                        date = convertLongToTime(dataPickerState.selectedDateMillis!!)
                                    }
                                    selectedDate = date
                                }, enabled = confirmEnabled.value) {
                                    Text(text = "Okay")
                                }
                            }) {
                            DatePicker(state = dataPickerState)
                        }
                    }

                    Row (modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                        Column {
//                            Text(text = "Inspector", color = Color.White, modifier = Modifier.padding(bottom = 5.dp))
//                            ExposedDropdownMenuBox(expanded = expandedInspector, onExpandedChange = { expandedInspector = !expandedInspector}, modifier = Modifier.width(160.dp).background(color = Color.Black.copy(alpha = 0.3f))) {
//                                TextField(
//                                    value = selectedInspector,
//                                    colors = TextFieldDefaults.textFieldColors(textColor = Color.Gray),
//                                    onValueChange = {},
//                                    readOnly = true,
//                                    trailingIcon = {  },
//                                    modifier = Modifier.menuAnchor().padding(bottom = 4.dp)
//                                )
//                                ExposedDropdownMenu(
//                                    expanded = expandedInspector,
//                                    onDismissRequest = { expandedInspector = !expandedInspector }
//                                ) {
//                                    listOfInspector.forEach { item ->
//                                        DropdownMenuItem(
//                                            text = { Text(text = item) },
//                                            onClick = {
//                                                selectedInspector = item
//                                                expandedInspector = !expandedInspector
//                                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
//                                            }
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                        Column {
//                            Text(text = "Lender", color = Color.White, modifier = Modifier.padding(bottom = 5.dp))
//                            ExposedDropdownMenuBox(expanded = expandedLender, onExpandedChange = { expandedLender = !expandedLender}, modifier = Modifier.width(160.dp).background(color = Color.Black.copy(alpha = 0.3f))) {
//                                TextField(
//                                    value = selectedLenders,
//                                    colors = TextFieldDefaults.textFieldColors(textColor = Color.Gray),
//                                    onValueChange = {},
//                                    readOnly = true,
//                                    trailingIcon = {  },
//                                    modifier = Modifier.menuAnchor().padding(bottom = 4.dp)
//                                )
//                                ExposedDropdownMenu(
//                                    expanded = expandedLender,
//                                    onDismissRequest = { expandedLender = !expandedLender }
//                                ) {
//                                    listOfLenders.forEach { item ->
//                                        DropdownMenuItem(
//                                            text = { Text(text = item) },
//                                            onClick = {
//                                                selectedLenders = item
//                                                expandedLender = !expandedLender
//                                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
//                                            }
//                                        )
//                                    }
//                                }
//                            }
//                        }
                    }
                    TextField(
                        modifier = Modifier
                            .padding(top = 10.dp)
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
                    Text(text = "Description", color = Color.White, modifier = Modifier.padding(bottom = 5.dp, top = 10.dp))
                    TextField(
                        modifier = Modifier
                            .width(360.dp)
                            .background(color = Color.Black.copy(alpha = 0.3f)),
                        value = description,
                        onValueChange = { newText ->
                            description = newText
                        },
                        placeholder = { Text(text = "Ex: 'New Realities' is an expansive 300 piece exploration of the emerging...", color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))},
                        textStyle = TextStyle(color = Color.Gray, fontFamily = FontFamily(Font(googleFont = GoogleFont("IBM Plex Mono"), fontProvider = provider)))
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp)
                            .height(45.dp)
                            .clickable {
                                Log.d("Register", "Selected URI: $selectedImageUri")
                                selectedImageUri?.let { uri ->
                                    val file = uriToFile(context, selectedImageUri!!)
                                    Log.d(
                                        "Register",
                                        "File path: ${file.absolutePath}, Size: ${file.length()}"
                                    )
                                    Log.d(TAG, "Uploading file: ${file.name}, name: ${name.text}")
                                    scope.launch {
                                        val response = userProfile["address"]?.let {
                                            service.uploadNFTs(
                                                name = name.text,
                                                address = it,
                                                chain = chain.text,
                                                artist = artist.text,
                                                price = price.text,
                                                description = description.text,
                                                created_at = "2022-10-20T00:00:00.000Z",
                                                imageFile = file
                                            )
                                        }
                                        Log.i(TAG, response?.status?.toString() ?: "No response")
                                        if (response?.status == HttpStatusCode.OK) {
                                            val responseBody = response.bodyAsText()
                                            Log.d(TAG, "success register: $responseBody")
                                            val jsonResponse = JSONObject(responseBody)
                                            Log.d("NFTS", jsonResponse.toString())
//                                            val imageUrl = jsonResponse.getString("image")
//                                            val userId = jsonResponse.getInt("id")
//                                            val userUsername = jsonResponse.getString("username")
//                                            val userAddress = jsonResponse.getString("address")


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
                        Text(text = "Minting", color = Color.Black, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    )
}

fun convertLongToTime(timeInMillis: Long): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = Date(timeInMillis)
    return dateFormat.format(date)
}

@Preview(name = "Upload", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewUpload() {
    Upload()
}