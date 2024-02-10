package vn.tutorial.todolist

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import vn.tutorial.todolist.data.Container
import vn.tutorial.todolist.data.DataStoreManager
import vn.tutorial.todolist.data.DefaultContainer
import vn.tutorial.todolist.data.dataStore
import vn.tutorial.todolist.data.repository.UserPreferencesRepository

class TodoApplication : Application() {
    lateinit var container: Container
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer(this)
    }
}