package com.madcamp.week1

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

// 추가 (from 1st attempt - develop 파일)
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.madcamp.week1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    var tv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //tv = findViewById(R.id.tv)
    }

    fun clickBtn(view: View?) {

        //json 파일 읽어와서 분석하기

        //assets폴더의 파일을 가져오기 위해
        //창고관리자(AssetManager) 얻어오기
        val assetManager = assets

        //assets/ test.json 파일 읽기 위한 InputStream
        try {
            val `is` = assetManager.open("jsons/test.json")
            val isr = InputStreamReader(`is`)
            val reader = BufferedReader(isr)
            val buffer = StringBuffer()
            var line = reader.readLine()
            while (line != null) {
                buffer.append(
                    """
                        $line
                        
                        """.trimIndent()
                )
                line = reader.readLine()
            }
            val jsonData = buffer.toString()

            //읽어온 json문자열 확인
            tv?.text = jsonData;

            //json 분석
            //json 객체 생성
//            JSONObject jsonObject= new JSONObject(jsonData);
//            String name= jsonObject.getString("name");
//            String msg= jsonObject.getString("msg");
//
//            tv.setText("이름 : "+name+"\n"+"메세지 : "+msg);

            //json 데이터가 []로 시작하는 배열일때..
            val jsonArray = JSONArray(jsonData)
            var s = ""
            for (i in 0 until jsonArray.length()) {
                val jo = jsonArray.getJSONObject(i)
                val name = jo.getString("name")
                val msg = jo.getString("msg")
                val flag = jo.getJSONObject("flag")
                val aa = flag.getInt("aa")
                val bb = flag.getInt("bb")
                s += "$name : $msg==>$aa,$bb\n"
            }
            tv!!.text = s
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}