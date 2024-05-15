package com.CelebTrivia.FamousFacesChallenge.famousfaceschallenge.database

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.CelebTrivia.FamousFacesChallenge.famousfaceschallenge.model.ActorModel

class ActorDao {
    fun getRandomTenRecords(helper : DatabaseCopyHelper) : ArrayList<ActorModel>{

        val recordList = ArrayList<ActorModel>()
        val database : SQLiteDatabase = helper.writableDatabase
        val cursor : Cursor = database.rawQuery("SELECT * FROM actor ORDER BY RANDOM() LIMIT 15",null)

        val idIndex = cursor.getColumnIndex("actor_id")
        val actorsNameIndex = cursor.getColumnIndex("actors_name")
        val actorNameIndex = cursor.getColumnIndex("actor_name")
        val actorGenderIndex = cursor.getColumnIndex("actors_gender")

        while (cursor.moveToNext()){

            val record = ActorModel(
                cursor.getInt(idIndex),
                cursor.getString(actorsNameIndex),
                cursor.getString(actorNameIndex),
                cursor.getString(actorGenderIndex)
            )

            recordList.add(record)

        }

        cursor.close()

        return recordList

    }

    fun getRandomThreeRecords(helper : DatabaseCopyHelper, id : Int,genderFilter: String) : ArrayList<ActorModel>{

        val recordList = ArrayList<ActorModel>()
        val database : SQLiteDatabase = helper.writableDatabase
        val cursor : Cursor = database.rawQuery("SELECT * FROM actor WHERE actor_id != ? AND actors_gender = ? ORDER BY RANDOM() LIMIT 3",
            arrayOf(id.toString(), genderFilter))

        val idIndex = cursor.getColumnIndex("actor_id")
        val actorsNameIndex = cursor.getColumnIndex("actors_name")
        val actorNameIndex = cursor.getColumnIndex("actor_name")
        val actorGenderIndex = cursor.getColumnIndex("actors_gender")

        while (cursor.moveToNext()){

            val record = ActorModel(
                cursor.getInt(idIndex),
                cursor.getString(actorsNameIndex),
                cursor.getString(actorNameIndex),
                cursor.getString(actorGenderIndex)

            )

            recordList.add(record)

        }

        cursor.close()

        return recordList

    }
}