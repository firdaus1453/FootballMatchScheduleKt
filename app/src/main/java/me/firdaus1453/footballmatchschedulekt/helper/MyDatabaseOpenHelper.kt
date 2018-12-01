package me.firdaus1453.footballmatchschedulekt.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteModel
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            FavoriteModel.TABLE_FAVORITE, true,
            FavoriteModel.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteModel.EVENT_ID to TEXT,
            FavoriteModel.ID_HOME to TEXT,
            FavoriteModel.TEAM_HOME to TEXT,
            FavoriteModel.SCORE_HOME to TEXT,
            FavoriteModel.ID_AWAY to TEXT,
            FavoriteModel.TEAM_AWAY to TEXT,
            FavoriteModel.SCORE_AWAY to TEXT,
            FavoriteModel.EVENT_DATE to TEXT
            )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteModel.TABLE_FAVORITE, true)
    }

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }

    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)