package com.up.ddm.strategy.raca

class Elfo : RacaStrategy {
    override val nome = "Elfo"
    override fun calcularBonusRacial(): Map<String, Int> {
        return mapOf("Destreza" to 2)
    }
}