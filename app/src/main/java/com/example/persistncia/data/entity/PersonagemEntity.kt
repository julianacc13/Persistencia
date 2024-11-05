package com.example.persistncia.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.persistncia.strategy.Habilidade.Habilidade
import com.up.ddm.strategy.ClasseStrategy
import com.up.ddm.strategy.nivel.NivelStrategy
import com.up.ddm.strategy.raca.RacaStrategy

@Entity(tableName = "personagem")
data class PersonagemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var nome: String = "",
    var classe: ClasseStrategy,
    var raca: RacaStrategy,
    var nivel: NivelStrategy,
    val habilidades: List<Habilidade>

)
