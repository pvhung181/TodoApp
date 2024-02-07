package vn.tutorial.todolist.data.repository

import androidx.room.Query
import vn.tutorial.todolist.data.dao.CategoryDao
import vn.tutorial.todolist.model.Category

interface CategoryRepository {
    suspend fun getALlCategoriesStream(): List<Category>

    suspend fun getCategoryByIdStream(id: Int) : Category
}

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override suspend fun getALlCategoriesStream(): List<Category> {
        return categoryDao.getALlCategories()
    }

    override suspend fun getCategoryByIdStream(id: Int): Category {
        return categoryDao.getCategoryById(id)
    }

}