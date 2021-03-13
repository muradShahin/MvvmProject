package com.murad.mvvmmoviws.localDb

import androidx.room.Dao
import androidx.room.*
import com.murad.mvvmmoviws.localDb.entites.Fav
import retrofit2.http.DELETE

@Dao
interface DaoDb {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToList(fav:Fav)

    @Query("SELECT * FROM Fav ")
    suspend fun getFavList():List<Fav>

    @Query("DELETE FROM Fav WHERE show_id =:id")
    suspend fun deleteFav(id: Int): Int


}