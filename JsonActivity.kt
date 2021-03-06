package com.example.android.miwok

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_earth_quake.view.*
import org.json.JSONObject
import java.io.File
import java.io.InputStream
import android.support.v4.content.ContextCompat
import java.security.AccessController.getContext


class JsonActivity : AppCompatActivity() {

    lateinit var list : MutableList<EarthQuake>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)
        list = listOf<EarthQuake>().toMutableList()
        val inputStream: InputStream = getResources().openRawResource(R.raw.data);
        val inputString = inputStream.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(inputString)
        val dataArray = jsonObject.getJSONArray("data")
        for (index in 0..dataArray.length()-1) {
            val data = dataArray.getJSONObject(index)
            list.add(EarthQuake(
                    data.getDouble("mag"),
                    data.getString("place"),
                    data.getLong("time"),
                    data.getString("url")))
        }

        val earthQuakeRV = findViewById(R.id.earthQuakeRV) as RecyclerView
        earthQuakeRV.layoutManager = LinearLayoutManager(this)
        val adapter = EarthQuakeListAdapter(this, list)
        earthQuakeRV.adapter = adapter
    }
}


class EarthQuakeListAdapter(val context: Context, private val list: MutableList<EarthQuake>) :
        RecyclerView.Adapter<EarthQuakeListAdapter.ViewHolder>() {

    companion object {
        var LOCATION_SEPARATOR = " of "
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_earth_quake, parent, false)
        return ViewHolder(view,context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindEarthQuake(list[position])
    }
    override fun getItemCount() = list.size

    class ViewHolder(view: View, val context: Context)
        : RecyclerView.ViewHolder(view) {

        fun bindEarthQuake(earthQuake: EarthQuake) {
            with(earthQuake) {
                var primaryLocation = ""
                var locationOffset = ""
                if (earthQuake.place.contains(LOCATION_SEPARATOR)) {
                    val parts = earthQuake.place.split(LOCATION_SEPARATOR)
                    locationOffset = parts[0] + LOCATION_SEPARATOR
                    primaryLocation = parts[1]
                } else {
                    locationOffset = context.getString(R.string.near_the)
                    primaryLocation = earthQuake.place
                }

                itemView.magnitude.text = earthQuake.mag.toString()
                itemView.date.text = earthQuake.getDate()
                itemView.primary_location.setText(primaryLocation)
                itemView.location_offset.setText(locationOffset)
            }
        }
    }

    private fun getMagnitudeColor(magnitude: Double): Int {
        val magnitudeColorResourceId: Int
        val magnitudeFloor = Math.floor(magnitude).toInt()
        when (magnitudeFloor) {
            0, 1 -> magnitudeColorResourceId = R.color.magnitude1
            2 -> magnitudeColorResourceId = R.color.magnitude2
            3 -> magnitudeColorResourceId = R.color.magnitude3
            4 -> magnitudeColorResourceId = R.color.magnitude4
            5 -> magnitudeColorResourceId = R.color.magnitude5
            6 -> magnitudeColorResourceId = R.color.magnitude6
            7 -> magnitudeColorResourceId = R.color.magnitude7
            8 -> magnitudeColorResourceId = R.color.magnitude8
            9 -> magnitudeColorResourceId = R.color.magnitude9
            else -> magnitudeColorResourceId = R.color.magnitude10plus
        }
        return ContextCompat.getColor(context, magnitudeColorResourceId)
    }
}
