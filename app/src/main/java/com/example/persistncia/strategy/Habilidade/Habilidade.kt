package com.example.persistencia.strategy.Habilidade
import com.example.persistencia.strategy.Habilidade.Habilidade


data class Habilidade(
    val nome: String,
    var valor: Int
) {

    companion object {

        fun atualizarHabilidade(habilidades: List<Habilidade>, nome: String, novoValor: Int): Boolean {
            val habilidade = habilidades.find { it.nome == nome }
            return if (habilidade != null && novoValor in 8..15) {
                habilidade.valor = novoValor
                true
            } else {
                false
            }
        }


        fun adicionarHabilidade(habilidades: MutableList<Habilidade>, novaHabilidade: Habilidade): Boolean {
            if (habilidades.any { it.nome == novaHabilidade.nome }) {
                return false // A habilidade já existe
            }
            habilidades.add(novaHabilidade)
            return true
        }


        fun removerHabilidade(habilidades: MutableList<Habilidade>, nome: String): Boolean {
            val habilidade = habilidades.find { it.nome == nome }
            return if (habilidade != null) {
                habilidades.remove(habilidade)
                true
            } else {
                false
            }
        }
    }
}

