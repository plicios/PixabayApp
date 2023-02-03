package pl.pgorny.pixabayapp.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {
    @Query("SELECT * FROM image WHERE searchQuery == :searchQuery")
    suspend fun getAllMatchingSearchQuery(searchQuery: String): List<Image>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg image: Image)
}
