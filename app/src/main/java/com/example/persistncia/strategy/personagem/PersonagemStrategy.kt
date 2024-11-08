import com.example.persistencia.strategy.Habilidade.Habilidade
import com.up.ddm.strategy.ClasseStrategy
import com.up.ddm.strategy.nivel.NivelStrategy
import com.up.ddm.strategy.raca.RacaStrategy


class PersonagemStrategy(
    var id: Long = 0,
    var nome: String = "",
    var classe: ClasseStrategy,
    var raca: RacaStrategy,
    var nivel: NivelStrategy,
    var habilidades: List<Habilidade> = mutableListOf( // Lista de habilidades mutáveis
        Habilidade("Força", 8),
        Habilidade("Destreza", 8),
        Habilidade("Constituição", 8),
        Habilidade("Inteligência", 8),
        Habilidade("Sabedoria", 8),
        Habilidade("Carisma", 8)
    )
) {
    var pontosRestantes: Int = 27

    fun atualizarHabilidade(habilidade: String, progress: Int) {
        if (Habilidade.atualizarHabilidade(habilidades, habilidade, progress)) {
            pontosRestantes -= (progress - habilidades.find { it.nome == habilidade }?.valor!! ?: 0)
        } else {
            throw IllegalArgumentException("Atualização de habilidade inválida ou valor fora dos limites.")
        }
    }
}

