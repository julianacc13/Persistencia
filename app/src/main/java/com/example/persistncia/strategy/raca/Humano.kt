package com.up.ddm.strategy.raca

class Humano : RacaStrategy {
    override val nome = "Humano"
    override fun calcularBonusRacial(): Map<String, Int> {
        return mapOf("Força" to 1, "Destreza" to 1, "Inteligência" to 1)
    }
}