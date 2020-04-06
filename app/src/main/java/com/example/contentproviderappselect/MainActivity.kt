package com.example.contentproviderappselect

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contentproviderappselect.model.Student
import com.example.sqlitedemo1.adapter.StudentsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //ChildApp chỉ có duy nhất quyền đọc data từ BaseApp
    lateinit var arrST: ArrayList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arrST = ArrayList()

        val contentResolver = contentResolver
        val uri =
            Uri.parse("content://com.example.democontentprovidersqlite.Provider.StudentsProvider/students")
        //Select theo ID:
        //val cursor = contentResolver.query(uri, null, "students_id=?", arrayOf("3"), null)

        //Select 1 columns:
        //val cursor = contentResolver.query(uri, arrayOf("students_address"), null, null, null)

        //Select all:
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor!!.moveToFirst()) {
            do {
                val student = Student(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getBlob(5)
                )
                arrST.add(student)
            } while (cursor!!.moveToNext())
        }

        if (arrST.isNotEmpty()) {
            recyclerview.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = StudentsAdapter(arrST)
            }
        }
    }
}
