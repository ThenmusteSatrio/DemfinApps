package com.example.demo_one_a.ui.main

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.twotone.Create
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material.icons.twotone.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.demo_one_a.R
import com.example.demo_one_a.ui.theme.provider

@Composable
fun Profile(
    navController: NavController?= null
) {
    fun getUserProfile(context: Context): Map<String, String?> {
        val sharedPreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE)
        return mapOf(
            "userId" to sharedPreferences.getString("userId", null),
            "username" to sharedPreferences.getString("username", null),
            "address" to sharedPreferences.getString("address", null),
            "imageUrl" to sharedPreferences.getString("imageUrl", null)
        )
    }
    val context = LocalContext.current
    val userProfile = getUserProfile(context)
    var selectedIndex by remember { mutableIntStateOf(1) }
    Column (modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(355.dp)
//                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            contentAlignment = Alignment.BottomStart,
            content = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ap_),
                    contentDescription = "content",
                    contentScale = ContentScale.Crop,
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, end = 10.dp, start = 10.dp,),
                    contentAlignment = Alignment.TopStart,
                    content = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width = 50.dp, height = 50.dp)
                                    .clickable {
                                        navController?.popBackStack()
                                    }
                                    .clip(shape = CircleShape)
                                    .background(
                                        color = Color.Gray.copy(alpha = 0.1f),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center,
                                content = {
                                    Icon(
                                        imageVector = Icons.Outlined.Check,
                                        contentDescription = "verify",
                                        tint = Color.Blue,
                                        modifier = Modifier.clickable { })
                                }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 50.dp, height = 50.dp)
                                    .background(
                                        color = Color.Gray.copy(0.1f),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center,
                                content = {
                                    Icon(
                                        imageVector = Icons.TwoTone.Create,
                                        contentDescription = "edit",
                                        tint = Color.White,
                                    )
                                }
                            )
                        }
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    colorStops = arrayOf(
                                        0f to Color.Transparent,
                                        0.05f to Color.Black.copy(alpha = 0.05f),
                                        0.1f to Color.Black.copy(alpha = 0.1f),
                                        0.15f to Color.Black.copy(alpha = 0.15f),
                                        0.2f to Color.Black.copy(alpha = 0.2f),
                                        0.25f to Color.Black.copy(alpha = 0.25f),
                                        0.3f to Color.Black.copy(alpha = 0.3f),
                                        0.35f to Color.Black.copy(alpha = 0.4f),
                                        0.4f to Color.Black.copy(alpha = 0.5f),
                                        0.45f to Color.Black.copy(alpha = 0.6f),
                                        0.5f to Color.Black.copy(alpha = 0.7f),
                                        0.55f to Color.Black.copy(alpha = 0.8f),
                                        0.6f to Color.Black.copy(alpha = 0.9f),
                                        0.7f to Color.Black.copy(alpha = 1f),
                                        0.85f to Color.Black,
                                        1f to Color.Black,

                                        ),
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, Float.POSITIVE_INFINITY)
                                )
                            )
                            .fillMaxWidth()
                            .height(280.dp)
                            .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 5.dp)
                    )
                    {
                        Box(
                            modifier = Modifier
                                .size(80.dp, 80.dp)
                                .background(Color.White, CircleShape)
                                .clip(
                                    CircleShape
                                ), contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width = 74.dp, height = 74.dp)
                                    .background(color = Color.White, shape = CircleShape)
                                    .clip(CircleShape)
                                    .clipToBounds(),
                                contentAlignment = Alignment.Center,
                                content = {
                                    AsyncImage(imageLoader = ImageLoader(context = context), contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize(), model = userProfile["imageUrl"], contentDescription = "profilepicture",)
//                                    Image(
//                                        painter = painterResource(id = R.drawable.mike),
//                                        contentScale = ContentScale.Crop,
//                                        contentDescription = "profil",
//                                        modifier = Modifier.fillMaxSize()
//                                    )
                                }
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Mike Shinoda",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                fontFamily = FontFamily.Serif
                            )
                            Text(
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(300.dp),
                                fontSize = 12.sp,
                                color = Color.White,
                                text = "Hello world, I'm Furently from California, I'm creating the beautiful stuff."
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier
                                .padding(top = 13.dp)
                                .height(24.dp)
                                .width(100.dp)) {
                                Image(painter = painterResource(id = R.drawable.facebook), contentDescription = "facebook",)
                                Image(painter = painterResource(id = R.drawable.instagram), contentDescription = "instagram")
                                Image(painter = painterResource(id = R.drawable.twitter), contentDescription = "twitter")
                            }
                            Row (modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, bottom = 3.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                                Column {
                                    Text(text = "Collected", color = (if (selectedIndex == 0) Color.Blue else Color.Gray) as Color, modifier = Modifier
                                        .clickable { selectedIndex = 0 }
                                        .shadow(
                                            elevation = (if (selectedIndex == 0) 5.dp else 0.dp) as Dp,
                                            shape = RoundedCornerShape(10.dp),
                                            spotColor = (if (selectedIndex == 0) Color.Blue else Color.Transparent) as Color,
                                            ambientColor = Color.White
                                        ))
                                }
                                Box(modifier = Modifier
                                    .size(width = 1.dp, height = 10.dp)
                                    .background(color = Color.Gray))
                                Column {
                                    Text(text = "Selling", color = (if (selectedIndex == 1) Color.Blue else Color.Gray) as Color, modifier = Modifier
                                        .clickable { selectedIndex = 1 }
                                        .shadow(
                                            elevation = (if (selectedIndex == 1) 5.dp else 0.dp) as Dp,
                                            shape = RoundedCornerShape(10.dp),
                                            spotColor = (if (selectedIndex == 1) Color.Blue else Color.Transparent) as Color,
                                            ambientColor = Color.White
                                        ))
                                }
                                Box(modifier = Modifier
                                    .size(width = 1.dp, height = 10.dp)
                                    .background(color = Color.Gray))
                                Column {
                                    Text(text = "Bid", color = (if (selectedIndex == 2) Color.Blue else Color.Gray) as Color, modifier = Modifier
                                        .clickable { selectedIndex = 2 }
                                        .shadow(
                                            elevation = (if (selectedIndex == 2) 5.dp else 0.dp) as Dp,
                                            shape = RoundedCornerShape(10.dp),
                                            spotColor = (if (selectedIndex == 2) Color.Blue else Color.Transparent) as Color,
                                            ambientColor = Color.White
                                        ))
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.8.dp)
                                    .background(color = Color.Gray.copy(alpha = 0.5f))
                                    .padding(top = 10.dp)
                            )
                        }
                    }


                }

            },
        )
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 170.dp), modifier = Modifier.fillMaxSize()) {
            items(6){
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(color = Color(0xFF1a2129), shape = RoundedCornerShape(10.dp))
                        .clip(shape = RoundedCornerShape(10.dp))
                    ,content = {
                        Column(modifier = Modifier.padding(start = 10.dp, top = 8.dp, end = 10.dp, bottom = 5.dp)) {
                            Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text(modifier = Modifier.width(80.dp), overflow = TextOverflow.Ellipsis, maxLines = 1, text = "Savana Monkey", color = Color.White, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(
                                            color = Color(0xFF233646).copy(alpha = 0.9f),
                                            shape = CircleShape
                                        )
                                        .clip(shape = CircleShape),
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        Image(painter = painterResource(id = R.drawable.ethereum), contentDescription = "eth")
                                    }
                                )
                            }
                            Column (
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 15.dp, bottom = 10.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color(0xFF1a2129),
                                                Color.Black
                                            )
                                        ), shape = RoundedCornerShape(10.dp)
                                    )
                                ,
                                )
                            {
                                Image(
                                    painter = painterResource(id = R.drawable.images),
                                    contentDescription = "collection",
                                    modifier = Modifier
                                        .padding(start = 5.dp, end = 5.dp, top = 10.dp)
                                        .fillMaxWidth()
                                        .height(140.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Row (modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 5.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                                    Box(contentAlignment = Alignment.Center, content = {
                                        ElevatedButton(onClick = { /*TODO*/ },
                                            modifier = Modifier
                                                .width(70.dp)
                                                .height(30.dp)
                                                .shadow(
                                                    elevation = 7.dp,
                                                    shape = RoundedCornerShape(10.dp),
                                                    spotColor = Color.White,
                                                    ambientColor = Color.White
                                                )
                                                .padding(
                                                    top = 5.dp,
                                                    bottom = 5.dp,
                                                    start = 5.dp,
                                                    end = 5.dp
                                                ),
                                            colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.White,),
                                            contentPadding = PaddingValues(0.dp)
                                        ) {
                                            Text(text = "Detail", color = Color.Black, fontWeight = FontWeight.Light, fontSize = 10.sp )
                                        }
                                    })
                                    Box(contentAlignment = Alignment.Center, content = {
                                        ElevatedButton(onClick = { /*TODO*/ },
                                            modifier = Modifier
                                                .width(70.dp)
                                                .height(30.dp)
                                                .shadow(
                                                    elevation = 7.dp,
                                                    shape = RoundedCornerShape(10.dp),
                                                    spotColor = Color(0xFFff4444),
                                                    ambientColor = Color.White
                                                )
                                                .padding(
                                                    top = 5.dp,
                                                    bottom = 5.dp,
                                                    start = 5.dp,
                                                    end = 5.dp
                                                ),
                                            colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFFff4444),),
                                            contentPadding = PaddingValues(0.dp)
                                        ) {
                                            Text(text = "Place a bid", color = Color.White, fontWeight = FontWeight.Light, fontSize = 10.sp )
                                        }
                                    })
                                }
                            }
                        }
                })
            }

        }

    }
}

@Preview(name = "Profile", showBackground = true)
@Composable
private fun PreviewProfile() {
    Profile()
}