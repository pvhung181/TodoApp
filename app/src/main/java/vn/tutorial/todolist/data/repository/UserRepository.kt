package vn.tutorial.todolist.data.repository

import kotlinx.coroutines.flow.Flow
import vn.tutorial.todolist.data.dao.UserDao
import vn.tutorial.todolist.model.User

interface UserRepository {
    suspend fun insert(user: User)
    suspend fun update(user: User)
    fun getUser(id: Int): Flow<User>
}

class UserRepositoryImpl(
    val userDao: UserDao
) : UserRepository {
    override suspend fun insert(user: User) {
        userDao.insert(user)
    }

    override suspend fun update(user: User) {
        userDao.update(user)
    }

    override fun getUser(id: Int): Flow<User> {
        return userDao.getUserById(id)
    }

}

