package pl.pgorny.pixabayapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.pgorny.pixabayapp.data.storage.PixabayDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @DataBaseName
    @Singleton
    @Provides
    fun provideDataBaseName(): String {
        return "PixabayDatabase"
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext applicationContext: Context, @DataBaseName databaseName: String): PixabayDatabase {
        return Room.databaseBuilder(
            applicationContext,
            PixabayDatabase::class.java,
            databaseName
        ).build()
    }
}
