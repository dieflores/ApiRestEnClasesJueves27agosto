package com.example.restapi.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.restapi.model.pojo.Post

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