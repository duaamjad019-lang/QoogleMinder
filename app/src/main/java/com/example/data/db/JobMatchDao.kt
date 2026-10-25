package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JobMatchDao {
    @Query("SELECT * FROM job_matches ORDER BY timestamp DESC")
    fun getAllMatches(): Flow<List<JobMatchEntity>>

    @Query("SELECT * FROM job_matches WHERE id = :id LIMIT 1")
    suspend fun getMatchById(id: String): JobMatchEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: JobMatchEntity)

    @Query("DELETE FROM job_matches WHERE id = :id")
    suspend fun deleteMatchById(id: String)

    @Query("DELETE FROM job_matches")
    suspend fun clearAll()
}
