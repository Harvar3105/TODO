package com.example.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddViewModel(private val db: DataBase) : ViewModel() {

    fun saveToDB(item: TodoDTO) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                var itemEntity = TodoDTO.fromDTO(item)
                if (itemEntity.id == 0L) itemEntity.id = null
                db.getDAO().insertItem(itemEntity)
            }
        }
    }

    fun updateItem(item: TodoDTO){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.getDAO().update(TodoDTO.fromDTO(item))
            }
        }
    }
}