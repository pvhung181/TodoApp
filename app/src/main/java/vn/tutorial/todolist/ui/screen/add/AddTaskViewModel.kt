package vn.tutorial.todolist.ui.screen.add

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.tutorial.todolist.data.repository.CategoryRepository
import vn.tutorial.todolist.model.Category

class AddTaskViewModel(

) : ViewModel() {
    val selectedCategory = mutableIntStateOf(1)


}