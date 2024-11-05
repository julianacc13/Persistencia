package com.example.persistencia.ui.viewmodel

import com.example.persistencia.data.entity.AppDatabase
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.persistncia.data.entity.PersonagemEntity
import kotlinx.coroutines.launch
import com.example.persistncia.strategy.personagem.PersonagemStrategy
import toEntity

class PersonagemViewModel : ViewModel() {

    fun insertPersonagem(context: Context, personagem: PersonagemStrategy) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            db.personagemDao().insert(personagem.toEntity())
        }
    }

    fun getAllPersonagens(context: Context, callback: (List<PersonagemEntity>) -> Unit) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val personagens = db.personagemDao().getAllPersonagens() // Chama o método do DAO
            callback(personagens)
        }
    }

    fun updatePersonagem(context: Context, personagem: PersonagemStrategy) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            db.personagemDao().update(personagem.toEntity())
        }
    }

    fun deletePersonagem(context: Context, personagem: PersonagemStrategy) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            db.personagemDao().delete(personagem.toEntity())
        }
    }
}
