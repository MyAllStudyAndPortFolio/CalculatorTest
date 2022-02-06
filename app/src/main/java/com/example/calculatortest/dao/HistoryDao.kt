package com.example.calculatortest.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.calculatortest.model.History

@Dao

interface HistoryDao{

    @Insert
    fun insertAll(vararg history: History)

    @Query("DELETE FROM history")
    fun deleteAll()

    @Query("SELECT * FROM history")
    fun getAll() : List<History>

    @Delete
    fun delete(history : History)

    @Query("SELECT * FROM history WHERE result LIKE :result LIMIT 1")
    fun findResult(result : String) : History
}
