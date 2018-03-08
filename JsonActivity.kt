package com.example.android.miwok

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.json.JSONObject
import java.io.File
import java.io.InputStream

class JsonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)

        val inputStream: InputStream = getResources().openRawResource(R.raw.data);
        val inputString = inputStream.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(inputString)
        val dataArray = jsonObject.getJSONArray("data")

        val data1 = dataArray.getJSONObject(0)
        val data2 = dataArray.getJSONObject(1)
        val data3 = dataArray.getJSONObject(2)
        val data4 = dataArray.getJSONObject(3)

        var mag1 = data1.getDouble("mag")
    }
}
