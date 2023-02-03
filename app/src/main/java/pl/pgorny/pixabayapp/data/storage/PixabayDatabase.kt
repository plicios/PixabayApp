package pl.pgorny.pixabayapp.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Image::class], version = 1)
abstract class PixabayDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}