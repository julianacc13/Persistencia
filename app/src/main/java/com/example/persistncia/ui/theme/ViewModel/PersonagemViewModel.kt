package com.example.persistencia.ui.viewmodel

import PersonagemStrategy
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.persistencia.data.entity.AppDatabase
import com.example.persistencia.data.entity.PersonagemEntity
import com.example.persistencia.strategy.Habilidade.Habilidade
import com.up.ddm.strategy.Bruxo
import com.up.ddm.strategy.Feiticeiro
import com.up.ddm.strategy.Guerreiro
import com.up.ddm.strategy.Mago
import com.up.ddm.strategy.nivel.Nivel1
import com.up.ddm.strategy.nivel.Nivel2
import com.up.ddm.strategy.nivel.Nivel3
import com.up.ddm.strategy.raca.Anao
import com.up.ddm.strategy.raca.Elfo
import com.up.ddm.strategy.raca.Humano
import kotlinx.coroutines.launch
import toEntity

class PersonagemViewModel : ViewModel() {

    fun insertPersonagem(
        context: Context,
        nome: String,
        classe: String,
        raca: String,
        nivel: Int,
        habilidades: Map<String, Int>
    ) {

        val personagem = criarPersonagem(nome, classe, raca, nivel, habilidades)

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            db.personagemDao().insert(personagem.toEntity())
        }
    }

    // Função para obter todos os personagens do banco de dados
    fun getAllPersonagens(context: Context, callback: (List<PersonagemEntity>) -> Unit) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val personagens = db.personagemDao().getAllPersonagens()
            callback(personagens)
        }
    }

    fun updatePersonagem(
        context: Context,
        personagemId: Int,
        nome: String,
        classe: String,
        raca: String,
        nivel: Int,
        habilidades: Map<String, Int>
    ) {
        val personagem = criarPersonagem(nome, classe, raca, nivel, habilidades).apply {
            this.id = personagemId.toLong() // Atualiza o ID do personagem
        }

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)

            val personagemEntity = personagem.toEntity()

            if (personagemEntity.id != null) {
                db.personagemDao().update(personagemEntity)
            } else {
                Log.e("PersonagemViewModel", "ID do personagem é nulo ou inválido para atualização.")
            }
        }
    }

    // Função para excluir um personagem do banco de dados
    fun deletePersonagem(context: Context, personagem: PersonagemEntity) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)

            // Exclui o personagem apenas se o ID for válido
            if (personagem.id != null) {
                db.personagemDao().delete(personagem)
            } else {
                Log.e("PersonagemViewModel", "ID do personagem é nulo ou inválido para exclusão.")
            }
        }
    }


    // Função para criar um personagem a partir dos parâmetros
    private fun criarPersonagem(
        nome: String,
        classe: String,
        raca: String,
        nivel: Int,
        habilidades: Map<String, Int>  // Map de habilidades
    ): PersonagemStrategy {
        // Define a classe do personagem
        val classeStrategy = when (classe) {
            "Bruxo" -> Bruxo()
            "Feiticeiro" -> Feiticeiro()
            "Mago" -> Mago()
            "Guerreiro" -> Guerreiro()
            else -> throw IllegalArgumentException("Classe desconhecida")
        }

        // Define a raça do personagem
        val racaStrategy = when (raca) {
            "Anão" -> Anao()
            "Elfo" -> Elfo()
            "Humano" -> Humano()
            else -> throw IllegalArgumentException("Raça desconhecida")
        }

        // Define o nível do personagem
        val nivelStrategy = when (nivel) {
            1 -> Nivel1(1)
            2 -> Nivel2(2)
            3 -> Nivel3(3)
            else -> throw IllegalArgumentException("Nível inválido")
        }

        // Cria a lista de habilidades a partir do mapa
        val habilidadesList = habilidades.map { (nome, valor) ->
            Habilidade(nome, valor)
        }

        // Retorna o objeto PersonagemStrategy com as habilidades
        return PersonagemStrategy(
            nome = nome,
            classe = classeStrategy,
            raca = racaStrategy,
            nivel = nivelStrategy,
            habilidades = habilidadesList
        )
    }


}
