package com.up.ddm.strategy.raca

class Anao : RacaStrategy {
    override val nome = "Anão"
    override fun calcularBonusRacial(): Map<String, Int> {
        return mapOf("Constituição" to 2)
    }
}
