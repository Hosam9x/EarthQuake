package com.example.android.miwok

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Hosamo on 10/03/18.
 */
class EarthQuake(var mag: Double, var place: String, var time :Long, var url : String) {
    fun getDate():String
    {
        val date = Date(time)
        val dateFormatter = SimpleDateFormat("MMM DD, yyyy")
        return dateFormatter.format(date)
    }
}