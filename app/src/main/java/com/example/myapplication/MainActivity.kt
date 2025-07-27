package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.com.example.myapplication.ProductService
import com.example.myapplication.ui.theme.MyApplicationTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
            ObtenerProductos()
        }
    }
}
private fun obtenerListadoProductos(productos: List<Product>): String {
    return if (productos.isNotEmpty()) {
        productos.joinToString(separator = "/n") { producto ->
            "ID: ${producto.id}. Nombre: ${producto.name}"
        }
        else {
            "no hay productos disponibles"
        }
    }

}
fun setProductService(productService : ProductService) {
    this.productService = productService
}
private fun ObtenerProductos(){

    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.jsonkeeper.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val service = retrofit.create(ProductService::class.java)

val call =  service.getProducts()


    call.enqueue(object : Callback<ProductResponse> {
        override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
            if (response.isSuccessful) {
                val products = response.body()?.products
                products?.forEach { product ->
                    Log.d("MainActivity", "Product: ${product.name} Precio: ${product.price}")
                }

            } else {
                Log.e("MainActivity", "API Error: ${response.code()}")

            }

        }
        override fun onFailure(call: Call<ProductResponse?>, t: Throwable) {
            Log.e("MainActivity", "Error: ${t.message}")


        }

    }


    )


}





@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}