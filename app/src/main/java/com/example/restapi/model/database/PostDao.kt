package com.example.restapi.model.database

import androidx.lifecycle.LiveData
import com.example.restapi.model.pojo.Post
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {
    // insertar un listado de post
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAllPost(listPost: List<Post>)

    //insertar un solo post
    @Insert
    suspend fun insertPost(post: Post)

    //traer los elementos de la tabla
    @Query("SELECT * FROM post_table ORDER BY id DESC")
    fun getAllPostList(): LiveData<List<Post>>

    //para borrarlos todos
    @Query("DELETE FROM post_table")
    suspend fun deleteAllPost()



}