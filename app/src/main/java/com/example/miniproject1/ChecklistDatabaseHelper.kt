package com.example.miniproject1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ChecklistDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "checklist.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "checklist"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_IS_CHECKED = "is_checked"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT NOT NULL, " +
                "$COLUMN_IS_CHECKED INTEGER NOT NULL)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addChecklistItem(item: ChecklistItem) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, item.title)
            put(COLUMN_IS_CHECKED, if (item.isChecked) 1 else 0)
        }
        item.id = db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllChecklistItems(): List<ChecklistItem> {
        val items = mutableListOf<ChecklistItem>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val isChecked = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_CHECKED)) == 1
                items.add(ChecklistItem(id, title, isChecked))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return items
    }

    fun updateChecklistItem(item: ChecklistItem) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, item.title)
            put(COLUMN_IS_CHECKED, if (item.isChecked) 1 else 0)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(item.id.toString()))
        db.close()
    }

    fun deleteChecklistItem(itemId: Long) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(itemId.toString()))
        db.close()
    }
}
