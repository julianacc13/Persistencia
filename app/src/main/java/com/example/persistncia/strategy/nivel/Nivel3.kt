package com.up.ddm.strategy.nivel

class Nivel3(override val nivelAtual: Int) : NivelStrategy {
    override val nivel: Int
        get() = nivelAtual

    override fun calcularBonusPorNivel(): Int {
        return 3
    }
}

