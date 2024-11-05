package com.up.ddm.strategy

class Bruxo : ClasseStrategy {
    override val nome = "Bruxo"
    override fun calcularBonusHabilidade(): Map<String, Int> {
        return mapOf("Inteligência" to 2, "Carisma" to 1)
    }
}