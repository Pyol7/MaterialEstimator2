package com.example.materialestimator.storage.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.materialestimator.TAG
import com.example.materialestimator.models.entities.*
import com.example.materialestimator.storage.local.dao.*
import com.example.materialestimator.utilities.Converters
import com.example.materialestimator.utilities.DataSource2
import com.example.materialestimator.utilities.MoshiConverters
import com.example.materialestimator.utilities.DataSource1

@Database(
    entities = [
        Project::class,
        Task::class,
        MaterialCategory::class,
        Material::class,
        Employee::class,
        Tool::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    Converters::class,
    MoshiConverters::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao
    abstract fun materialCategoryDao(): MaterialCategoryDao
    abstract fun materialDao(): MaterialDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun toolDao(): ToolDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                /**
                 * This block is executed only once when the app is first installed (Singleton).
                 * As a result the pre-populating coroutine is also launched once.
                 * To re-run this block the app must be uninstalled.
                 */
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
//            Log.i(TAG, "DB singleton returned")
            return INSTANCE!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                DataSource2.insertCategoryAndMaterials(INSTANCE!!)
                DataSource1.insertProjectsAndTasks(INSTANCE!!)
                Log.i(TAG, "DB first run...")
            }
        }

    }
}