import com.example.persistncia.data.entity.PersonagemEntity
import com.example.persistncia.strategy.personagem.PersonagemStrategy
import com.example.persistncia.strategy.Habilidade.Habilidade

fun PersonagemEntity.toDomainModel(): PersonagemStrategy {
    return PersonagemStrategy(
        id = id,
        nome = nome,
        classe = classe,
        raca = raca,
        nivel = nivel,
        habilidades = habilidades
    )
}

fun PersonagemStrategy.toEntity(): PersonagemEntity {
    return PersonagemEntity(
        id = id,
        nome = nome,
        classe = classe,
        raca = raca,
        nivel = nivel,
        habilidades = habilidades
    )
}
