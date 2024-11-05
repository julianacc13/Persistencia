package com.up.ddm.strategy

class Guerreiro : ClasseStrategy {
    override val nome = "Guerreiro"
    override fun calcularBonusHabilidade(): Map<String, Int> {
        return mapOf("Força" to 2, "Constituição" to 1)
    }
}