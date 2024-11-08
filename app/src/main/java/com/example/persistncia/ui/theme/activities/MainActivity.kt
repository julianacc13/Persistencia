package com.example.persistencia.ui.activities

import com.example.persistencia.R
import com.example.persistencia.ui.viewmodel.PersonagemViewModel
import com.up.ddm.strategy.Guerreiro
import com.up.ddm.strategy.nivel.Nivel1
import com.up.ddm.strategy.raca.Anao
import PersonagemStrategy
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import toEntity

class MainActivity : AppCompatActivity() {

    private val viewModel: PersonagemViewModel by viewModels()
    private lateinit var personagem: PersonagemStrategy
    private var pontosRestantes = 27

    private lateinit var forcaTextView: TextView
    private lateinit var destrezaTextView: TextView
    private lateinit var constituicaoTextView: TextView
    private lateinit var inteligenciaTextView: TextView
    private lateinit var sabedoriaTextView: TextView
    private lateinit var carismaTextView: TextView
    private lateinit var pontosTextView: TextView

    private lateinit var selectedClasseTextView: TextView
    private lateinit var selectedRacaTextView: TextView
    private lateinit var selectedNivelTextView: TextView

    private var selectedClasse: String = ""
    private var selectedRaca: String = ""
    private var selectedNivel: Int = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextNome = findViewById<EditText>(R.id.editText_nome)
        val buttonInsert = findViewById<Button>(R.id.button_insert_character)
        val buttonGetAll = findViewById<Button>(R.id.button_get_all_characters)
        val buttonUpdate = findViewById<Button>(R.id.button_update_character)
        val buttonDelete = findViewById<Button>(R.id.button_delete_character)
        val textViewResult = findViewById<TextView>(R.id.textView_result)

        forcaTextView = findViewById(R.id.forca_text_view)
        destrezaTextView = findViewById(R.id.destreza_text_view)
        constituicaoTextView = findViewById(R.id.constituicao_text_view)
        inteligenciaTextView = findViewById(R.id.inteligencia_text_view)
        sabedoriaTextView = findViewById(R.id.sabedoria_text_view)
        carismaTextView = findViewById(R.id.carisma_text_view)
        pontosTextView = findViewById(R.id.pontos_restantes_text_view)

        selectedClasseTextView = findViewById(R.id.selected_classe_text_view)
        selectedRacaTextView = findViewById(R.id.selected_raca_text_view)
        selectedNivelTextView = findViewById(R.id.selected_nivel_text_view)

        criarPersonagem()

        val buttonClasse = findViewById<Button>(R.id.button_select_classe)
        val buttonRaca = findViewById<Button>(R.id.button_select_raca)
        val buttonNivel = findViewById<Button>(R.id.button_select_nivel)

        buttonClasse.setOnClickListener {
            val classes = arrayOf("Bruxo", "Feiticeiro", "Mago", "Guerreiro")
            showDialog("Escolha a classe", classes) { classeSelecionada ->
                selectedClasse = classeSelecionada
                selectedClasseTextView.text = "Classe: $selectedClasse"
            }
        }

        buttonRaca.setOnClickListener {
            val racas = arrayOf("Anão", "Elfo", "Humano")
            showDialog("Escolha a raça", racas) { racaSelecionada ->
                selectedRaca = racaSelecionada
                selectedRacaTextView.text = "Raça: $selectedRaca"
            }
        }

        buttonNivel.setOnClickListener {
            val niveis = arrayOf("1", "2", "3")
            showDialog("Escolha o nível", niveis) { nivelSelecionado ->
                selectedNivel = nivelSelecionado.toInt()
                selectedNivelTextView.text = "Nível: $selectedNivel"
            }
        }

        buttonInsert.setOnClickListener {
            val nome = editTextNome.text.toString()
            if (nome.isNotBlank() && selectedClasse.isNotBlank() && selectedRaca.isNotBlank()) {
                val habilidades = personagem.habilidades.associate { it.nome to it.valor }

                viewModel.insertPersonagem(
                    this,
                    nome,
                    selectedClasse,
                    selectedRaca,
                    selectedNivel,
                    habilidades
                )

                Toast.makeText(this, "Personagem salvo com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonGetAll.setOnClickListener {
            viewModel.getAllPersonagens(this) { personagens ->
                textViewResult.text = personagens.joinToString("\n") { it.toString() }
            }
        }

        buttonUpdate.setOnClickListener {
            val nome = editTextNome.text.toString()
            if (nome.isNotBlank() && selectedClasse.isNotBlank() && selectedRaca.isNotBlank()) {
                val habilidades = personagem.habilidades.associate { it.nome to it.valor }

                viewModel.updatePersonagem(
                    this,
                    personagem.id.toInt(),
                    nome,
                    selectedClasse,
                    selectedRaca,
                    selectedNivel,
                    habilidades
                )

                Toast.makeText(this, "Personagem atualizado com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDelete.setOnClickListener {
            viewModel.deletePersonagem(this, personagem.toEntity())
            Toast.makeText(this, "Personagem excluído com sucesso!", Toast.LENGTH_SHORT).show()
        }

        setupSeekBar(R.id.forca_seek_bar, "Força")
        setupSeekBar(R.id.destreza_seek_bar, "Destreza")
        setupSeekBar(R.id.constituicao_seek_bar, "Constituição")
        setupSeekBar(R.id.inteligencia_seek_bar, "Inteligência")
        setupSeekBar(R.id.sabedoria_seek_bar, "Sabedoria")
        setupSeekBar(R.id.carisma_seek_bar, "Carisma")
    }

    private fun showDialog(title: String, options: Array<String>, onSelect: (String) -> Unit) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setItems(options) { _, which ->
            onSelect(options[which])
        }
        builder.create().show()
    }

    private fun criarPersonagem() {
        try {
            personagem = PersonagemStrategy(
                id = 1,
                nome = "Ana",
                classe = Guerreiro(),
                raca = Anao(),
                nivel = Nivel1(1)
            )
            atualizarHabilidades()
        } catch (e: Exception) {
            Log.e("MainActivity", "Erro ao inicializar personagem: ${e.message}")
            Toast.makeText(this, "Erro ao inicializar personagem", Toast.LENGTH_SHORT).show()
        }
    }

    private fun atualizarHabilidades() {
        personagem.habilidades.forEach { habilidade ->
            when (habilidade.nome) {
                "Força" -> forcaTextView.text = habilidade.valor.toString()
                "Destreza" -> destrezaTextView.text = habilidade.valor.toString()
                "Constituição" -> constituicaoTextView.text = habilidade.valor.toString()
                "Inteligência" -> inteligenciaTextView.text = habilidade.valor.toString()
                "Sabedoria" -> sabedoriaTextView.text = habilidade.valor.toString()
                "Carisma" -> carismaTextView.text = habilidade.valor.toString()
            }
        }
        pontosTextView.text = "Pontos restantes: $pontosRestantes"
    }

    private fun setupSeekBar(seekBarId: Int, habilidade: String) {
        val seekBar: SeekBar = findViewById(seekBarId)
        val habilidadeValor = personagem.habilidades.find { it.nome == habilidade }?.valor ?: 8
        seekBar.max = 15
        seekBar.progress = habilidadeValor

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val pontosGastados = habilidadeValor - 8
                val novosPontosRestantes = pontosRestantes + pontosGastados - (progress - 8)

                if (novosPontosRestantes >= 0 && progress <= 15) {
                    personagem.atualizarHabilidade(habilidade, progress)
                    pontosRestantes = novosPontosRestantes
                    pontosTextView.text = "Pontos restantes: $pontosRestantes"
                    atualizarHabilidades()
                } else {
                    seekBar?.progress = habilidadeValor
                    Toast.makeText(this@MainActivity, "Pontos insuficientes ou valor máximo atingido!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
