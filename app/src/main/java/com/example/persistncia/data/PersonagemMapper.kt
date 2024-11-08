import com.example.persistencia.data.entity.PersonagemEntity
import com.example.persistencia.strategy.Habilidade.Habilidade

fun PersonagemEntity.toDomainModel(): PersonagemStrategy {
    return PersonagemStrategy(
        id = id,
        nome = nome,
        classe = classe,
        raca = raca,
        nivel = nivel,
        habilidades = habilidades.map { Habilidade(it.nome, it.valor) }
    )
}

// Converte PersonagemStrategy para PersonagemEntity
fun PersonagemStrategy.toEntity(): PersonagemEntity {
    return PersonagemEntity(
        id = id,
        nome = nome,
        classe = classe,
        raca = raca,
        nivel = nivel,
        habilidades = habilidades.map { Habilidade(it.nome, it.valor) }
    )
}

