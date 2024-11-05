package com.up.ddm.strategy.raca

interface RacaStrategy {
    val nome: String
    fun calcularBonusRacial(): Map<String, Int>
}
