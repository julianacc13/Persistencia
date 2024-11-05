package com.example.persistncia.strategy.Habilidade

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habilidade")
data class Habilidade(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val valor: Int
)
