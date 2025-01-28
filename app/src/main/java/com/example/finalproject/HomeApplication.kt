package com.example.finalproject

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.ui.navigation.DestinasiNavigasi

object DestinasiHomeApp : DestinasiNavigasi {
    override val route = "homeApp"
    override val titleRes = "Home App"
}

@Composable
fun HomeApp(
    onHalamanHomeEvent: () -> Unit,
    onHalamanHomePeserta: () -> Unit,
    onHalamanHomeTiket: () -> Unit,
    onHalamanHomeTransaksi: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFFFFAFA)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Image(
            painter = painterResource(
                id = R.drawable.logo
            ),
            contentDescription = "Logo",
            modifier = Modifier
                .size(400.dp) //
                .padding(bottom = 24.dp)
        )

        Text(
            text = "LionSphere Events",
            color = Color(0xFF8B0000),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Selamat Datang",
            color = Color(0xFF8B0000),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Button(
            onClick = { onHalamanHomeEvent() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text(
                text = "EVENT",
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF),
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = { onHalamanHomePeserta() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text(
                text = "PESERTA",
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF),
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = {
                Log.d("HomeApp", "TIKET button clicked")
                try {
                    onHalamanHomeTiket()
                }catch (e: Exception) {
                    // Log kesalahan yang terjadi
                    Log.e("HomeApp", "Error when navigating to Home Tiket: ${e.message}")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text(
                text = "TIKET",
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF),
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = { onHalamanHomeTransaksi() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text(
                text = "TRANSAKSI",
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF),
                fontWeight = FontWeight.Bold
            )
        }

    }
}