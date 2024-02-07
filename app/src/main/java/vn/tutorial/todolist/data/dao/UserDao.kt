package vn.tutorial.todolist.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import vn.tutorial.todolist.model.User

@Dao
interface UserDao {
    @Query("select * from user where id = :id")
    fun getUserById(id: Int): Flow<User>
}