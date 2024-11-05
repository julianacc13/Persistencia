package com.up.ddm.strategy

interface ClasseStrategy {
    val nome: String
    fun calcularBonusHabilidade(): Map<String, Int>
}

