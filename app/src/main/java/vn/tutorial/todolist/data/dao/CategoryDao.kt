package vn.tutorial.todolist.data.dao

import androidx.room.Dao
import androidx.room.Query
import vn.tutorial.todolist.model.Category

@Dao
interface CategoryDao {

    @Query("select * from category")
    suspend fun getALlCategories(): List<Category>

    @Query("select * from category where category_id = :id")
    suspend fun getCategoryById(id: Int) : Category
}