package com.example.persistencia.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.persistencia.R
import com.example.persistencia.ui.viewmodel.PersonagemViewModel
import com.up.ddm.strategy.*
import com.up.ddm.strategy.nivel.*
import com.up.ddm.strategy.raca.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: PersonagemViewModel by viewModels()
    private lateinit var personagem: com.example.persistncia.strategy.personagem.PersonagemStrategy
    private var pontosRestantes = 27

    private lateinit var forcaTextView: TextView
    private lateinit var destrezaTextView: TextView
    private lateinit var constituicaoTextView: TextView
    private lateinit var inteligenciaTextView: TextView
    private lateinit var sabedoriaTextView: TextView
    private lateinit var carismaTextView: TextView
    private lateinit var pontosTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextNome = findViewById<EditText>(R.id.editTextNome)
        val buttonInsert = findViewById<Button>(R.id.buttonInsert)
        val buttonGetAll = findViewById<Button>(R.id.buttonGetAll)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        forcaTextView = findViewById(R.id.forcaTextView)
        destrezaTextView = findViewById(R.id.destrezaTextView)
        constituicaoTextView = findViewById(R.id.constituicaoTextView)
        inteligenciaTextView = findViewById(R.id.inteligenciaTextView)
        sabedoriaTextView = findViewById(R.id.sabedoriaTextView)
        carismaTextView = findViewById(R.id.carismaTextView)
        pontosTextView = findViewById(R.id.pontosRestantesTextView)

        criarPersonagem()

        buttonInsert.setOnClickListener {
            val nome = editTextNome.text.toString()
            if (nome.isNotBlank()) {
                personagem.nome = nome
                viewModel.insertPersonagem(this, personagem)
                Toast.makeText(this, "Personagem salvo com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Nome não pode estar vazio!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonGetAll.setOnClickListener {
            viewModel.getAllPersonagens(this) { personagens ->
                textViewResult.text = personagens.joinToString("\n") { it.toString() }
            }
        }

        setupSeekBar(R.id.forcaSeekBar, "Força")
        setupSeekBar(R.id.destrezaSeekBar, "Destreza")
        setupSeekBar(R.id.constituicaoSeekBar, "Constituição")
        setupSeekBar(R.id.inteligenciaSeekBar, "Inteligência")
        setupSeekBar(R.id.sabedoriaSeekBar, "Sabedoria")
        setupSeekBar(R.id.carismaSeekBar, "Carisma")
    }

    private fun criarPersonagem() {
        try {
            personagem = com.example.persistncia.strategy.personagem.PersonagemStrategy(
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
        val habilidades = personagem.habilidades
        forcaTextView.text = habilidades.find { it.nome == "Força" }?.valor.toString()
        destrezaTextView.text = habilidades.find { it.nome == "Destreza" }?.valor.toString()
        constituicaoTextView.text = habilidades.find { it.nome == "Constituição" }?.valor.toString()
        inteligenciaTextView.text = habilidades.find { it.nome == "Inteligência" }?.valor.toString()
        sabedoriaTextView.text = habilidades.find { it.nome == "Sabedoria" }?.valor.toString()
        carismaTextView.text = habilidades.find { it.nome == "Carisma" }?.valor.toString()
        pontosTextView.text = "Pontos restantes: ${personagem.pontosRestantes}"
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

                Log.d("SeekBar", "Pontos Restantes: $pontosRestantes, Progress: $progress, Habilidade: $habilidade")

                if (novosPontosRestantes >= 0 && progress <= 15) {
                    personagem.atualizarHabilidade(habilidade, progress)
                    pontosRestantes = novosPontosRestantes
                    pontosTextView.text = "Pontos restantes: $pontosRestantes"
                    atualizarHabilidades()
                } else {
                    seekBar?.progress = habilidadeValor // Corrige o progresso se insuficientes
                    Toast.makeText(this@MainActivity, "Pontos insuficientes ou valor máximo atingido!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
