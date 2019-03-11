package me.firdaus1453.footballmatchschedulekt.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteModel
import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteTeam
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 2) {
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
            FavoriteModel.EVENT_DATE to TEXT,
            FavoriteModel.EVENT_TIME to TEXT
            )

        db.createTable(
            FavoriteTeam.TABLE_FAVORITE_TEAM, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteModel.TABLE_FAVORITE, true)
        db.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
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