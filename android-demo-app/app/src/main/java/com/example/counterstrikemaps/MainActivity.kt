package com.example.counterstrikemaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.counterstrikemaps.ui.theme.CounterStrikeMapsTheme
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.activity.compose.BackHandler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterStrikeMapsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MapsScreen()
                }
            }
        }
    }
}

// Main screen composable
@Composable
fun MapsScreen() {
    var selectedMapIndex by remember { mutableStateOf<Int?>(null) }
    val maps = getSampleMaps()

    if (selectedMapIndex != null) {
        // Show detail screen
        MapDetailScreen(
            maps = maps,
            initialMapIndex = selectedMapIndex!!,
            onBack = { selectedMapIndex = null }
        )
    } else {
        // Show list screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1a1a1a))
        ) {
            // Title - Fixed header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF1a1a1a),
                shadowElevation = 4.dp
            ) {
                Text(
                    text = "CS Maps Explorer",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Maps list with proper spacing
            MapsList(
                maps = maps,
                onMapClick = { index -> selectedMapIndex = index }
            )
        }
    }
}

// List of maps using LazyColumn
@Composable
fun MapsList(maps: List<Map>, onMapClick: (Int) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(maps) { index, map ->
            MapCard(
                map = map,
                onClick = { onMapClick(index) }
            )
        }

        // Ad at the bottom with proper spacing
        item {
            Spacer(modifier = Modifier.height(4.dp))

            // Ad Card wrapper
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2d2d2d)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // "Sponsored" label
                    Text(
                        text = "Sponsored",
                        fontSize = 10.sp,
                        color = Color(0xFFaaaaaa),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    // Ad View
                    AndroidView(
                        factory = { context ->
                            com.example.adssdk.AdView(context).apply {
                                val adManager = com.example.adssdk.AdManager("https://cs-maps-ads-project.vercel.app/")
                                initialize(adManager)
                                loadAd()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// Single map card with improved styling
@Composable
fun MapCard(map: Map, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2d2d2d)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Map name
                Text(
                    text = map.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                // Map description
                Text(
                    text = map.description,
                    fontSize = 13.sp,
                    color = Color(0xFFaaaaaa)
                )

                // Map type badge
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFff6b35)
                ) {
                    Text(
                        text = map.type,
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            // Arrow indicator
            Text(
                text = "›",
                fontSize = 40.sp,
                color = Color(0xFFaaaaaa),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

// Hardcoded sample data with images
fun getSampleMaps(): List<Map> {
    return listOf(
        Map(
            id = 1,
            name = "Dust 2",
            description = "Iconic desert map with balanced gameplay",
            type = "Competitive",
            imageRes = R.drawable.dust2
        ),
        Map(
            id = 2,
            name = "Mirage",
            description = "Classic Middle Eastern setting",
            type = "Competitive",
            imageRes = R.drawable.mirage
        ),
        Map(
            id = 3,
            name = "Inferno",
            description = "Italian village with tight corridors",
            type = "Competitive",
            imageRes = R.drawable.inferno
        ),
        Map(
            id = 4,
            name = "Nuke",
            description = "Nuclear facility with vertical gameplay",
            type = "Competitive",
            imageRes = R.drawable.nuke
        ),
        Map(
            id = 5,
            name = "Overpass",
            description = "Urban canal setting in Germany",
            type = "Competitive",
            imageRes = R.drawable.overpass
        ),
        Map(
            id = 6,
            name = "Ancient",
            description = "Aztec temple ruins",
            type = "Competitive",
            imageRes = R.drawable.ancient
        ),
        Map(
            id = 7,
            name = "Anubis",
            description = "Egyptian archaeological site",
            type = "Competitive",
            imageRes = R.drawable.anubis
        )
    )
}

// Map detail screen with improved layout
@Composable
fun MapDetailScreen(
    maps: List<Map>,
    initialMapIndex: Int,
    onBack: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(initialMapIndex) }
    val currentMap = maps[currentIndex]

    // Handle back button press
    BackHandler {
        onBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1a1a1a))
    ) {
        // Top bar with navigation
        Surface(
            color = Color(0xFF1a1a1a),
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left arrow
                IconButton(
                    onClick = {
                        if (currentIndex > 0) {
                            currentIndex--
                        }
                    },
                    enabled = currentIndex > 0
                ) {
                    Text(
                        text = "←",
                        fontSize = 32.sp,
                        color = if (currentIndex > 0) Color.White else Color.Gray
                    )
                }

                // Map name
                Text(
                    text = currentMap.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                // Right arrow
                IconButton(
                    onClick = {
                        if (currentIndex < maps.size - 1) {
                            currentIndex++
                        }
                    },
                    enabled = currentIndex < maps.size - 1
                ) {
                    Text(
                        text = "→",
                        fontSize = 32.sp,
                        color = if (currentIndex < maps.size - 1) Color.White else Color.Gray
                    )
                }
            }
        }

        // Content - no scroll, but normal sizes
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Map image - normal size
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = currentMap.imageRes),
                    contentDescription = currentMap.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Map details card - normal layout
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2d2d2d)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "About",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = currentMap.description,
                        fontSize = 13.sp,
                        color = Color(0xFFaaaaaa)
                    )

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFff6b35)
                    ) {
                        Text(
                            text = currentMap.type,
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            // Ad View - slightly smaller only
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2d2d2d)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Sponsored",
                        fontSize = 10.sp,
                        color = Color(0xFFaaaaaa),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    AndroidView(
                        factory = { context ->
                            com.example.adssdk.AdView(context).apply {
                                val adManager = com.example.adssdk.AdManager("https://cs-maps-ads-project.vercel.app/")
                                initialize(adManager)
                                loadAd()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }
        }

        // Back button - Fixed at bottom
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF1a1a1a),
            shadowElevation = 8.dp
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFff6b35)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "← Back to List",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}