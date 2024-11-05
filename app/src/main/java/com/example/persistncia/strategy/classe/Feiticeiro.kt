package com.up.ddm.strategy

class Feiticeiro : ClasseStrategy {
    override val nome = "Feiticeiro"
    override fun calcularBonusHabilidade(): Map<String, Int> {
        return mapOf("Carisma" to 2, "Sabedoria" to 1)
    }
}