package com.example.calculatortest

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.calculatortest.dao.HistoryDao
import com.example.calculatortest.model.History

@Database(version=1,
    entities = [History::class],
)

abstract class AppDatabase : RoomDatabase(){
    abstract fun historyDao(): HistoryDao
}