package com.up.ddm.strategy.nivel

interface NivelStrategy {
    val nivelAtual: Int
    val nivel: Int
    fun calcularBonusPorNivel(): Int
}
