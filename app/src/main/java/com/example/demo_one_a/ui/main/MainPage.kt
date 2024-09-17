package com.example.demo_one_a.ui.main

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable

enum class NavigationBarItems(val icon: ImageVector){
    Home(icon = Icons.Default.Home),
    Selling(icon = Icons.Default.AddCircle),
    Profile(icon = Icons.Default.AccountCircle)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage(navController: NavController?= null){
    val navigationBarItems = remember { NavigationBarItems.values()}
    var selectedIndex by remember { mutableIntStateOf(0) }
    Scaffold (
        bottomBar = {
            AnimatedNavigationBar(selectedIndex = selectedIndex,
                modifier = Modifier
                    .height(64.dp)
                    .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
                cornerRadius = shapeCornerRadius(cornerRadius = 34.dp),
                ballAnimation = Parabolic(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = Color.White,
                ballColor = Color.Blue

            ) {
                navigationBarItems.forEach {
                        items ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .noRippleClickable { selectedIndex = items.ordinal },
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            modifier = Modifier.size(26.dp),
                            imageVector = items.icon,
                            contentDescription = "Bottom Bar Icons",
                            tint = (if (selectedIndex == items.ordinal) Color.Blue.copy(alpha = 0.7f) else Color.Gray) as Color
                        )
                    }
                }
            }
        }
    ){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            when(selectedIndex){
                0 -> Home(navController = navController)
                1 -> Upload(navController = navController)
                2 -> Profile(navController = navController)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainPagePreview(){
    MainPage()
}