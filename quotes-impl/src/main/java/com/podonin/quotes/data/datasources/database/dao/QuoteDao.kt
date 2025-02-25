package com.podonin.quotes.data.datasources.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.podonin.quotes.data.model.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: QuoteEntity)

    @Query(
        """
        UPDATE quotes
        SET 
            lastTradeExchange = COALESCE(:lastTradeExchange, lastTradeExchange),
            name = COALESCE(:name, name),
            lastTradePrice = COALESCE(:lastTradePrice, lastTradePrice),
            lastTradeChange = COALESCE(:lastTradeChange, lastTradeChange),
            lastTradeChangePercent = COALESCE(:lastTradeChangePercent, lastTradeChangePercent),
            minStep = COALESCE(:minStep, minStep)
        WHERE ticker = :ticker
        """
    )
    suspend fun update(
        ticker: String,
        lastTradeExchange: String?,
        name: String?,
        lastTradePrice: Double?,
        lastTradeChange: Double?,
        lastTradeChangePercent: Double?,
        minStep: Double?
    )

    @Query("SELECT * FROM quotes WHERE ticker = :ticker")
    suspend fun getQuote(ticker: String): QuoteEntity?

    @Query("SELECT * FROM quotes")
    fun getAllQuotes(): Flow<List<QuoteEntity>>
}
