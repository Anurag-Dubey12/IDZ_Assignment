package com.anurag.idz_assignment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user_data.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DOB = "dob"
        private const val COLUMN_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_DOB TEXT," +
                "$COLUMN_EMAIL TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addUser(user:UserDetails) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, user.name)
        values.put(COLUMN_DOB, user.DOB)
        values.put(COLUMN_EMAIL, user.email)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    @SuppressLint("Range")
    fun getalluser():List<UserDetails>{
        val allusername=ArrayList<UserDetails>()
        val db=this.readableDatabase
        val cursor=db.rawQuery("SELECT * FROM $TABLE_NAME",null)
        cursor.let {
            val userlist=ArrayList<UserDetails>()
            if(it.moveToFirst()){
                val name=it.getString(it.getColumnIndex(COLUMN_NAME))
                val dob=it.getString(it.getColumnIndex(COLUMN_DOB))
                val email=it.getString(it.getColumnIndex(COLUMN_EMAIL))
                val user=UserDetails(name,dob,email)
                userlist.add(user)
                allusername.addAll(userlist)
                cursor.close()
            }
        }
        return allusername
    }
    fun getUserData(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
}