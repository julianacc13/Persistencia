package com.example.persistncia.strategy.personagem

import com.example.persistncia.strategy.Habilidade.Habilidade
import com.up.ddm.strategy.ClasseStrategy
import com.up.ddm.strategy.nivel.NivelStrategy
import com.up.ddm.strategy.raca.RacaStrategy
import kotlin.math.floor

class PersonagemStrategy(
    val id: Long = 0,
    var nome: String = "",
    var classe: ClasseStrategy,
    var raca: RacaStrategy,
    var nivel: NivelStrategy,
    var habilidades: List<Habilidade> = listOf()
) {
    var pontosRestantes: Int = 27

    private fun habilidadesBase(): MutableMap<String, Int> {
        return habilidades.associate { it.nome to it.valor }.toMutableMap()
    }

    val forca: Int get() = habilidadesBase()["Força"] ?: 8
    val destreza: Int get() = habilidadesBase()["Destreza"] ?: 8
    val constituicao: Int get() = habilidadesBase()["Constituição"] ?: 8
    val inteligencia: Int get() = habilidadesBase()["Inteligência"] ?: 8
    val sabedoria: Int get() = habilidadesBase()["Sabedoria"] ?: 8
    val carisma: Int get() = habilidadesBase()["Carisma"] ?: 8

    fun distribuirPontos(atributo: String, valor: Int) {
        val habilidadesBase = habilidadesBase()
        if (atributo !in habilidadesBase) {
            throw IllegalArgumentException("Atributo inválido.")
        }

        val valorAtual = habilidadesBase[atributo] ?: 8

        if (valor < 8 || valor > 15) {
            throw IllegalArgumentException("O valor deve estar entre 8 e 15.")
        }

        val pontosUsados = valor - valorAtual
        val novaSomaHabilidades = habilidadesBase.values.sum() + pontosUsados

        if (novaSomaHabilidades > 27) {
            throw IllegalArgumentException("Distribuição de pontos inválida. O total de pontos não pode ultrapassar 27.")
        }

        habilidades = habilidades.map {
            if (it.nome == atributo) it.copy(valor = valor) else it
        }

        pontosRestantes -= pontosUsados
    }

    fun montarPersonagem(): Map<String, Int> {
        val habilidadesComBonus = habilidadesBase().toMutableMap()

        classe.calcularBonusHabilidade().forEach { (habilidade, bonus) ->
            habilidadesComBonus[habilidade] = (habilidadesComBonus[habilidade] ?: 8) + bonus
        }

        raca.calcularBonusRacial().forEach { (habilidade, bonus) ->
            habilidadesComBonus[habilidade] = (habilidadesComBonus[habilidade] ?: 8) + bonus
        }

        habilidadesComBonus["Força"] = (habilidadesComBonus["Força"] ?: 8) + nivel.calcularBonusPorNivel()

        habilidades = habilidades.map {
            it.copy(valor = habilidadesComBonus[it.nome] ?: it.valor)
        }

        return habilidadesBase()
    }

    fun forcaBonus(): Int = (forca - 10) / 2
    fun destrezaBonus(): Int = (destreza - 10) / 2
    fun constituicaoBonus(): Int = (constituicao - 10) / 2
    fun inteligenciaBonus(): Int = (inteligencia - 10) / 2
    fun sabedoriaBonus(): Int = (sabedoria - 10) / 2
    fun carismaBonus(): Int = (carisma - 10) / 2

    fun mudarClasse(novaClasse: ClasseStrategy) {
        classe = novaClasse
        montarPersonagem()
    }

    fun mudarRaca(novaRaca: RacaStrategy) {
        raca = novaRaca
        montarPersonagem()
    }

    fun mudarNivel(novoNivel: NivelStrategy) {
        nivel = novoNivel
        montarPersonagem()
    }

    fun calcularPontosDeVida(nivelAtual: Int): Int {
        val modificadorConstituicao = floor((constituicao - 10) / 2.0).toInt()
        return nivelAtual * (10 + modificadorConstituicao)
    }

    fun atualizarHabilidade(habilidade: String, progress: Int) {

        val habilidadeAtual = habilidades.find { it.nome == habilidade } ?: throw IllegalArgumentException("Habilidade inválida.")

        val novosPontosRestantes = pontosRestantes + (habilidadeAtual.valor - progress)

        if (progress in 8..15 && novosPontosRestantes >= 0) {
            habilidades = habilidades.map {
                if (it.nome == habilidade) it.copy(valor = progress) else it
            }
            pontosRestantes = novosPontosRestantes
        } else {
            throw IllegalArgumentException("Atualização de habilidade inválida ou valor fora dos limites.")
        }
    }


    override fun toString(): String {
        val habilidadesStr = habilidades.joinToString(", ") { "${it.nome}: ${it.valor}" }
        return "Personagem: $nome\nClasse: ${classe::class.simpleName}\nRaça: ${raca::class.simpleName}\nNível: ${nivel::class.simpleName}\nHabilidades: [$habilidadesStr]\nPontos Restantes: $pontosRestantes"
    }
}
