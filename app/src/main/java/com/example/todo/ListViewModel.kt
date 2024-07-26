package com.example.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(private val db: DataBase) : ViewModel() {

    private val _todos = MutableLiveData<List<TodoDTO>>()
    val todos: LiveData<List<TodoDTO>> = _todos

    fun loadTodos() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.getDAO().getAllItems().collect { items ->
                    _todos.postValue(items.map { TodoDTO.toDTO(it) })
                }
            }
        }
    }

    fun updateTodo(dto: TodoDTO) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.getDAO().update(TodoDTO.fromDTO(dto))
            }
        }
    }

    fun deleteTodo(dto: TodoDTO){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.getDAO().delete(TodoDTO.fromDTO(dto))
            }
        }
    }
}