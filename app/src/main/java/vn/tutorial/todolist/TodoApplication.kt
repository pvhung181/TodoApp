package vn.tutorial.todolist

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger
import vn.tutorial.todolist.data.Container
import vn.tutorial.todolist.data.DefaultContainer

class TodoApplication : Application(), ImageLoaderFactory {
    lateinit var container: Container
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer(this)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .logger(DebugLogger())
            .build()
    }
}