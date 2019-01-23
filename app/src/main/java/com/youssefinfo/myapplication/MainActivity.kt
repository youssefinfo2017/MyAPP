package com.youssefinfo.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    var volleyRequest: RequestQueue? = null

    val PREFS_NAME = "MyPrefs"
    val myPrefs:SharedPreferences?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        volleyRequest = Volley.newRequestQueue(this)
        btn_connect.setOnClickListener {
            var myPrefs  = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            var editor:SharedPreferences.Editor = (myPrefs as SharedPreferences?)!!.edit()
            if (!TextUtils.isEmpty("Merci de remplir les champs")){
                var profil = profil.text.toString()
                var name = name.text.toString()
                var password = password.text.toString()
                editor.putString("profil", "profil")
                editor.putString("name", "name")
                editor.putString("password","password")
                editor.commit()
            }
            getUserStatus()
        }
    }
    var username = name.text.toString()
    var userpassword = password.text.toString()
    val userUrl  =
        "http://npro.one-iptv.com:25443/player_api.php?username=$username&password=$userpassword&action=get_live_categories"
    private fun getUserStatus(Url: String=userUrl) {

        val jsonArray = JsonArrayRequest(
            Request.Method.GET, Url, Response.Listener { response: JSONArray ->
                try {
                    Toast.makeText(this, "Charegement en cours ...", Toast.LENGTH_LONG).show()
                    var i = Intent(this,Home_Page::class.java)
                    startActivity(i)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                try{
                    Toast.makeText(this,"Vous n'êtes pas abonné",Toast.LENGTH_LONG).show()
                }catch (e: JSONException) {
                    e.printStackTrace()
                }

            })
        volleyRequest!!.add(jsonArray)
    }
}