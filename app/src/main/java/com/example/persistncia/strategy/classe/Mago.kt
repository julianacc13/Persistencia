package com.up.ddm.strategy

class Mago : ClasseStrategy {
    override val nome = "Mago"
    override fun calcularBonusHabilidade(): Map<String, Int> {
        return mapOf("Inteligência" to 2, "Sabedoria" to 1)
    }
}