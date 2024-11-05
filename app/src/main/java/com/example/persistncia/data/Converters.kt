package com.example.persistncia.data

import androidx.room.TypeConverter
import com.example.persistncia.strategy.Habilidade.Habilidade
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.up.ddm.strategy.ClasseStrategy
import com.up.ddm.strategy.Guerreiro
import com.up.ddm.strategy.Bruxo
import com.up.ddm.strategy.Feiticeiro
import com.up.ddm.strategy.Mago
import com.up.ddm.strategy.nivel.Nivel1
import com.up.ddm.strategy.nivel.Nivel2
import com.up.ddm.strategy.nivel.Nivel3
import com.up.ddm.strategy.nivel.NivelStrategy
import com.up.ddm.strategy.raca.Anao
import com.up.ddm.strategy.raca.Elfo
import com.up.ddm.strategy.raca.Humano
import com.up.ddm.strategy.raca.RacaStrategy

class Converters {

    private val gson = Gson()
    @TypeConverter
    fun fromClasseStrategy(classe: ClasseStrategy?): String? {
        return classe?.javaClass?.simpleName
    }

    @TypeConverter
    fun toClasseStrategy(classeName: String?): ClasseStrategy? {
        return when (classeName) {
            "Guerreiro" -> Guerreiro()
            "Bruxo" -> Bruxo()
            "Feiticeiro" -> Feiticeiro()
            "Mago" -> Mago()
            else -> null
        }
    }

    // RacaStrategy Converters
    @TypeConverter
    fun fromRacaStrategy(raca: RacaStrategy?): String? {
        return raca?.javaClass?.simpleName
    }

    @TypeConverter
    fun toRacaStrategy(racaName: String?): RacaStrategy? {
        return when (racaName) {
            "Anao" -> Anao()
            "Elfo" -> Elfo()
            "Humano" -> Humano()
            else -> null
        }
    }

    @TypeConverter
    fun fromNivelStrategy(nivel: NivelStrategy?): String? {
        return nivel?.javaClass?.simpleName
    }

    @TypeConverter
    fun toNivelStrategy(nivelName: String?): NivelStrategy? {
        return when (nivelName) {
            "Nivel1" -> Nivel1(1)
            "Nivel2" -> Nivel2(2)
            "Nivel3" -> Nivel3(3)
            else -> null
        }
    }
    @TypeConverter
    fun fromHabilidadeList(habilidades: List<Habilidade>?): String? {
        return gson.toJson(habilidades)
    }

    @TypeConverter
    fun toHabilidadeList(data: String?): List<Habilidade>? {
        val listType = object : TypeToken<List<Habilidade>>() {}.type
        return gson.fromJson(data, listType)
    }
}

