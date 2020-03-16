package com.example.counterapp

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class NetworkManager(context : Context) {

    private var requestQueue : RequestQueue
    private val urlPrefix : String = "https://api.imgur.com/"
    private val errorCallbackDefault = fun (error : VolleyError){ println("Error during the HTTP Request: $error") }

    init {
        requestQueue = Volley.newRequestQueue(context.applicationContext)
    }

    fun sendRequest(
        method : Int,
        urlSuffix : String,
        header : MutableMap<String, String>,
        params : MutableMap<String, String>,
        callback : (response: JSONObject) -> Unit = {},
        errorCallback : (error : VolleyError) -> Unit = errorCallbackDefault
    ) {
        val url = urlPrefix + urlSuffix

        val stringRequest = object : StringRequest(method, url,
            Response.Listener<String> { response -> callback(JSONObject(response.toString())) },
            Response.ErrorListener { error -> errorCallback(error) })
        {
            override fun getHeaders() : MutableMap<String, String> { return header }
            override fun getParams() : MutableMap<String, String> { return params }
        }
        requestQueue.add(stringRequest)
    }
}