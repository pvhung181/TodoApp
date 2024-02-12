package vn.tutorial.todolist

import android.app.Application
import vn.tutorial.todolist.data.Container
import vn.tutorial.todolist.data.DefaultContainer

class TodoApplication : Application() {
    lateinit var container: Container
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer(this)
    }
}