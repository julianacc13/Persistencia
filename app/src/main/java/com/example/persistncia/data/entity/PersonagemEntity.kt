package com.example.persistencia.data.entity
//Entidade personagem, tabela personagem, cada entidade é uma tabela no banco de dados

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.persistencia.strategy.Habilidade.Habilidade
import com.up.ddm.strategy.ClasseStrategy
import com.up.ddm.strategy.nivel.NivelStrategy
import com.up.ddm.strategy.raca.RacaStrategy

@Entity(tableName = "personagem")
data class PersonagemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val classe: ClasseStrategy,
    val raca: RacaStrategy,
    val nivel: NivelStrategy,
    val habilidades: List<Habilidade>
)


