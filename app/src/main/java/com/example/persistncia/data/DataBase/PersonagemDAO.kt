package com.example.persistencia.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.persistencia.data.entity.PersonagemEntity
//Interface DAO com métodos CRUD

@Dao
interface PersonagemDAO {
    @Insert
    suspend fun insert(personagem: PersonagemEntity): Long

    @Update
    suspend fun update(personagem: PersonagemEntity)

    @Delete
    suspend fun delete(personagem: PersonagemEntity)

    @Query("SELECT * FROM personagem")
    suspend fun getAllPersonagens(): List<PersonagemEntity>
}
