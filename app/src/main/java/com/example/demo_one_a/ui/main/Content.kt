package com.example.demo_one_a.ui.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.demo_one_a.R
import com.example.demo_one_a.data.remote.dto.PostResponse

@Composable
fun Content(navController: NavController?= null, posts: State<List<PostResponse>> ?= null){
    val context = LocalContext.current
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalItemSpacing = 10.dp
    ) {
        posts?.value?.let {
            items(it.size){ index ->
                val post = it[index];
                val height = if (index % 3 == 0) 200.dp else 100.dp
                Box (
                    modifier = Modifier
                        .clickable {
                            println("Box clicked")
                            navController?.navigate("detail/$index")
                        }
                        .fillMaxWidth()
                        .height(height)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.BottomStart,
                    content = {
//                        Image(
//                            modifier = Modifier.fillMaxSize(),
//                            painter = painterResource(id = R.drawable.images),
//                            contentDescription = "content",
//                            contentScale = ContentScale.Crop,
//                        )
                        AsyncImage(contentScale = ContentScale.Crop, model = post.imageUrl, contentDescription = "Product", imageLoader = ImageLoader(context = context))
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 5.dp, end = 4.dp),
                            contentAlignment = Alignment.TopEnd,
                            content = {
                                Box(
                                    modifier = Modifier
                                        .size(width = 30.dp, height = 30.dp)
                                        .background(
                                            color = Color.White.copy(0.6f),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "add", tint = Color.DarkGray,)
                                    }
                                )
                            }
                        )
                        Box (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 7.dp, end = 7.dp, top = 5.dp, bottom = 4.dp)
                        )
                        {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                            )
                            {
                                Column {
                                    Text(text = "Price", color = Color.LightGray, fontSize = 12.sp)
                                    Text(text = "${post.price}ETH", color = Color.White, fontWeight = FontWeight.SemiBold)
                                }
                                Column {
                                    Text(text = "Artist", color = Color.LightGray, fontSize = 12.sp)
                                    Text(text = post.artist, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }


                        }

                    },
                )
            }
        }
    }
}

@Preview(name = "Content")
@Composable
private fun PreviewContent() {
    Content()
}