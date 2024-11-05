package com.up.ddm.strategy.nivel

class Nivel2(override val nivelAtual: Int) : NivelStrategy {
    override val nivel: Int
        get() = nivelAtual

    override fun calcularBonusPorNivel(): Int {
        return 2
    }
}

