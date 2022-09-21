package com.example.quotesapp.Utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import com.example.quotesapp.Screen.CategoryScreen.ModelClass.ModelData
import com.example.quotesapp.Screen.QuotesScreen.ModelClass.ModelDataQuotes
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.ArrayList

class DBHelper(val context: Context?) : SQLiteOpenHelper(context, "quotes.sqlite", null, 1) {

    var DB_NAME = "quotes.sqlite"
    var path: String? = null

    private fun checkDataBase(): Boolean {
        val dbFile = File(path + DB_NAME)
        return dbFile.exists()
    }

    private fun copyDataBase() {
        if (!checkDataBase()) {
            this.readableDatabase
            close()

            copyDBFile()

        }
    }


    private fun copyDBFile() {
        val mInput = context!!.assets.open(DB_NAME)
        val mOutput: OutputStream = FileOutputStream(path + DB_NAME)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) mOutput.write(mBuffer, 0, mLength)
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}


    init {
        path = if (Build.VERSION.SDK_INT >= 17) {
            context!!.applicationInfo.dataDir + "/databases/"
        } else {
            "/data/data/" + context!!.packageName + "/databases/"

        }
        copyDataBase()
        this.readableDatabase
    }

    @SuppressLint("Range")
    fun readDataQuotes(id: String): ArrayList<ModelDataQuotes> {

        var list = ArrayList<ModelDataQuotes>()

        var db = readableDatabase

        var query = "SELECT * FROM quotes WHERE category_id = $id"

        var cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {


            do {

                var quote = cursor.getString(cursor.getColumnIndex("quote"))
                var _id = cursor.getString(cursor.getColumnIndex("_id"))
                var liked = cursor.getString(cursor.getColumnIndex("liked"))

                var modelDataquotes = ModelDataQuotes(quote,_id,liked)
                list.add(modelDataquotes)

            } while (cursor.moveToNext())

        }
        return list
    }


    @SuppressLint("Range")
    fun readDataLike(): ArrayList<ModelDataQuotes> {

        var list = ArrayList<ModelDataQuotes>()

        var db = readableDatabase

        var query = "SELECT * FROM quotes"

        var cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {


            do {

                var quote = cursor.getString(cursor.getColumnIndex("quote"))
                var _id = cursor.getString(cursor.getColumnIndex("_id"))
                var liked = cursor.getString(cursor.getColumnIndex("liked"))

                var modelDataquotes = ModelDataQuotes(quote,_id,liked)
                list.add(modelDataquotes)

            } while (cursor.moveToNext())

        }
        return list
    }


    @SuppressLint("Range")
    fun readDataCat(): ArrayList<ModelData> {

        var list = ArrayList<ModelData>()

        var db = readableDatabase

        var query = "SELECT * FROM category "

        var cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {


            do {

                var _id = cursor.getString(cursor.getColumnIndex("_id"))
                var name = cursor.getString(cursor.getColumnIndex("name"))
                var status = cursor.getString(cursor.getColumnIndex("status"))

                Log.e("TAG", "readDataCat: ======== $name")

                var modelData = ModelData(_id, name, status)
                list.add(modelData)

            } while (cursor.moveToNext())

        }
        return list
    }


    fun updateData(_id: String, liked:String) {
        var db = writableDatabase

        var cv = ContentValues()

        cv.put("liked",liked)

        db.update("quotes",cv,"_id = $_id",null)

    }


}