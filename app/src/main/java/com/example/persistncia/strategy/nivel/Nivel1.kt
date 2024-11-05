package com.up.ddm.strategy.nivel

class Nivel1(override val nivelAtual: Int) : NivelStrategy {
    override val nivel: Int
        get() = nivelAtual

    override fun calcularBonusPorNivel(): Int {
        return 1
    }
}
