package com.example.demo_one_a.ui.main

import android.provider.FontsContract.FontFamilyResult
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demo_one_a.R
import com.example.demo_one_a.ui.theme.provider

@Composable
fun Detail(
    key: Int,
    navController: NavController?= null
) {
    Column (modifier = Modifier.fillMaxSize()) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
//                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            contentAlignment = Alignment.BottomStart,
            content = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.images),
                    contentDescription = "content",
                    contentScale = ContentScale.Crop,
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, end = 10.dp, start = 10.dp,),
                    contentAlignment = Alignment.TopStart,
                    content = {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Box(
                                modifier = Modifier
                                    .size(width = 50.dp, height = 50.dp)
                                    .clickable {
                                        navController?.popBackStack()
//                                        if (navController != null) {
//                                            navController.navigate("MainPage")
//                                        }
                                    }
                                    .clip(shape = CircleShape)
                                    .background(
                                        color = Color.Black.copy(alpha = 0.3f),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center,
                                content = {
                                    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "add", tint = Color.White, modifier = Modifier.clickable { navController?.popBackStack() })
                                }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 50.dp, height = 50.dp)
                                    .background(color = Color.Black.copy(0.3f), shape = CircleShape),
                                contentAlignment = Alignment.Center,
                                content = {
                                    Icon(
                                        imageVector = Icons.TwoTone.MoreVert,
                                        contentDescription = "add",
                                        tint = Color.White,
                                    )
                                }
                            )
                        }
                    }
                )
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                )
                {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    colorStops = arrayOf(
                                        0f to Color.Transparent,
                                        0.1f to Color.White.copy(alpha = 0.1f),
                                        0.2f to Color.White.copy(alpha = 0.2f),
                                        0.3f to Color.White.copy(alpha = 0.3f),
                                        0.4f to Color.White.copy(alpha = 0.4f),
                                        0.5f to Color.White.copy(alpha = 0.5f),
                                        0.6f to Color.White.copy(alpha = 0.6f),
                                        0.7f to Color.White.copy(alpha = 0.7f),
                                        0.8f to Color.White.copy(alpha = 0.8f),
                                        0.9f to Color.White.copy(alpha = 0.9f),
                                        1f to Color.White.copy(alpha = 10f),

                                        ),
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, Float.POSITIVE_INFINITY)
                                )
                            )
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 5.dp)
                    )
                    {
                        Box(modifier = Modifier
                            .size(80.dp, 80.dp)
                            .background(Color.White, CircleShape)
                            .clip(
                                CircleShape
                            ), contentAlignment = Alignment.Center){
                            Box(
                                modifier = Modifier
                                    .size(width = 74.dp, height = 74.dp)
                                    .background(color = Color.White, shape = CircleShape)
                                    .clip(CircleShape)
                                    .clipToBounds()
                                ,
                                contentAlignment = Alignment.Center,
                                content = {
                                    Image(painter = painterResource(id = R.drawable.metamask), contentScale = ContentScale.Crop, contentDescription = "profil", modifier = Modifier.fillMaxSize())
                                }
                            )
                        }
                        Column (verticalArrangement = Arrangement.Top) {
                            Text(text = "Savana Monkey", fontWeight = FontWeight.Bold, fontSize = 20.sp, fontFamily = FontFamily.Serif)
                            Text(text = "by Mike Shinoda", color = Color.DarkGray)
                        }
                    }


                }

            },
        )
            Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),) {
                Row (horizontalArrangement = Arrangement.spacedBy(15.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(text = "6.12ETH", color = Color.Black, fontWeight = FontWeight.SemiBold)
                        Text(text = "Price", color = Color.Gray)
                    }
                    Box(modifier = Modifier
                        .size(width = 1.dp, height = 30.dp)
                        .background(color = Color.Gray))
                    Column {
                        Text(text = "Avalanche", color = Color.Black, fontWeight = FontWeight.SemiBold)
                        Text(text = "Chain", color = Color.Gray)
                    }
                    Box(modifier = Modifier
                        .size(width = 1.dp, height = 30.dp)
                        .background(color = Color.Gray))
                    Column {
                        Text(text = "ERC-721", color = Color.Black, fontWeight = FontWeight.SemiBold)
                        Text(text = "Token Standart", color = Color.Gray)
                    }
                    Box(modifier = Modifier
                        .size(width = 1.dp, height = 30.dp)
                        .background(color = Color.Gray))
                    Column {
                        Text(text = "721454", color = Color.Black, fontWeight = FontWeight.SemiBold)
                        Text(text = "Token ID", color = Color.Gray)
                    }
                    Box(modifier = Modifier
                        .size(width = 1.dp, height = 30.dp)
                        .background(color = Color.Gray))
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp),
                ) {
                    Text(text = "Overview",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 5.dp, bottom = 3.dp)
                    )
                    Text(text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type",
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(fontProvider = provider, googleFont = GoogleFont("Montserrat"))),
                        fontSize = 13.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center, content = {
                    ElevatedButton(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 7.dp,
                                shape = RoundedCornerShape(10.dp),
                                spotColor = Color.Blue,
                                ambientColor = Color.White
                            )
                            .padding(top = 10.dp), colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.Blue,)) {
                        Text(text = "Buy", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                })
            }
        Text(text = "New",
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 25.dp),
            fontWeight = FontWeight.SemiBold
        )
        Box(modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 2.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color.LightGray),
            content = {
                Box(modifier = Modifier
                    .width(30.dp)
                    .absoluteOffset(0.dp, 0.dp)
                    .height(1.dp)
                    .background(color = Color.Black))
            }
        )
        Box(content = {
            Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp,)){
                Content(navController = navController)
            }
        })
    }
}

@Preview(name = "Detail", showBackground = true)
@Composable
private fun PreviewDetail() {
    Detail(key = 1)
}