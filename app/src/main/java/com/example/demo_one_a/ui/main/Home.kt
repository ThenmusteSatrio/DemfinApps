package com.example.demo_one_a.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demo_one_a.R
import com.example.demo_one_a.data.remote.PostsService
import com.example.demo_one_a.data.remote.dto.PostResponse
import com.example.demo_one_a.ui.theme.provider


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Home(navController: NavController?= null) {
    val client = PostsService.create()
    val posts = produceState<List<PostResponse>>(initialValue = emptyList(), producer = {
        value = client.getNFTs()
    })
    data class Item(val id: Int, val menu: String)
    val items = listOf(
        Item(0, "Recent"),
        Item(1, "Trending"),
        Item(2, "Art")
    )
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    )
    {
        Image(painter = painterResource(id = R.drawable.patternbg), contentDescription = "Pattern", contentScale = ContentScale.FillHeight, modifier = Modifier.fillMaxHeight() )
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f))
            .blur(radius = 10.dp),)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colorStops = arrayOf(
                            0.1f to Color(0xFF413f72),
                            0.3f to Color(0xFF15141b).copy(alpha = 0.3f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                )
        )
        Column (verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(painter = painterResource(id = R.drawable.demfi), contentDescription = "logo", modifier = Modifier.size(55.dp) )
                ElevatedButton(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFF1f1e28)
                    ),
                    shape = RoundedCornerShape(17.dp),
                    content = {
                        Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Image(painter = painterResource(id = R.drawable.wallet_outline), contentDescription = "wallet", modifier = Modifier.size(20.dp))
                            Text(
                                buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = Color.White)){
                                        append("6.980")
                                    }
                                    withStyle(style = SpanStyle(color = Color.Blue.copy(alpha = 1f))){
                                        append("ETH")
                                    }
                                }
                            )
                        }
                    },
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                    modifier = Modifier
                        .size(width = 130.dp, height = 40.dp))
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Discover, collect, and\n" +
                        "sell extraordinary NFTs",
                    color = Color.White,
                    fontFamily = FontFamily(Font(fontProvider = provider, googleFont = GoogleFont("Amatic SC"))),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        lineHeight = 28.sp,
                        letterSpacing = 0.51.sp
                    )

                )
                Box(modifier = Modifier
                    .size(width = 50.dp, height = 60.dp)
                    .border(4.dp, Color.DarkGray, CircleShape), contentAlignment = Alignment.Center){
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search", tint = Color.White)
                }
            }
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)){
                items.forEach{
                        item -> Box(
                    modifier = Modifier
                        .clickable { selectedIndex = item.id }
                        .drawWithContent {
                            drawContent()
                            drawLine(
                                color = (if (selectedIndex == item.id) Color.Blue else Color.Transparent) as Color,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 5f,
                            )
                        }, content = {
                        Text(text = item.menu, fontFamily = FontFamily(Font(fontProvider = provider, googleFont = GoogleFont("Amatic SC"))), color = (if (selectedIndex == item.id) Color.White else Color.Gray) as Color, modifier = Modifier.padding(bottom = 5.dp), fontSize = 21.sp, fontWeight = FontWeight.ExtraBold)
                    })
                }
            }

            Column (modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp), verticalArrangement = Arrangement.Top){
                when(selectedIndex){
                    0 -> Recent(navController = navController, posts = posts)
                    1 -> Trending()
                    2 -> Art()
                }
            }
        }
    }
}



@Composable
fun Recent(navController: NavController?= null, posts: State<List<PostResponse>>){
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment =
    Alignment.CenterVertically){
        Text(text = "Recent",
            color = Color.White,
            fontFamily = FontFamily(Font(fontProvider = provider, googleFont = GoogleFont("Montserrat Alternates"))),
            fontSize = 21.sp,
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                lineHeight = 24.sp,
                letterSpacing = 0.51.sp
            )
        )

        Text(text = "See all", color = Color.Gray)
    }
    Content(navController = navController, posts = posts)
}

@Composable
fun Trending(){
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment =
    Alignment.CenterVertically){
        Text(text = "Trending",
            color = Color.White,
            fontFamily = FontFamily(Font(fontProvider = provider, googleFont = GoogleFont("Montserrat Alternates"))),
            fontSize = 21.sp,
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                lineHeight = 24.sp,
                letterSpacing = 0.51.sp
            )
        )

        Text(text = "See all", color = Color.Gray)
    }
}

@Composable
fun Art(){
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment =
    Alignment.CenterVertically){
        Text(text = "Art",
            color = Color.White,
            fontFamily = FontFamily(Font(fontProvider = provider, googleFont = GoogleFont("Montserrat Alternates"))),
            fontSize = 21.sp,
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                lineHeight = 24.sp,
                letterSpacing = 0.51.sp
            )
        )

        Text(text = "See all", color = Color.Gray)
    }
}


@Preview(name = "Home")
@Composable
private fun PreviewHome() {
    Home()
}