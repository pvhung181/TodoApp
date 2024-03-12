package vn.tutorial.todolist.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import vn.tutorial.todolist.model.User

@Dao
interface UserDao {
    @Query("select * from user where id = :id")
    fun getUserById(id: Int): Flow<User>

    @Query("select * from user where id = :id")
    suspend fun getUserByIdNotTracking(id: Int): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)
}