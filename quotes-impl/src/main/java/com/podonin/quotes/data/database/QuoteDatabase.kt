package com.podonin.quotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.podonin.quotes.data.database.dao.QuoteDao
import com.podonin.quotes.data.database.entity.QuoteEntity

@Database(entities = [QuoteEntity::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}
