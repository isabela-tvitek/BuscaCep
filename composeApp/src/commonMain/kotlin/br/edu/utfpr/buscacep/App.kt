package br.edu.utfpr.buscacep

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import br.edu.utfpr.buscacep.repository.CepRepository
import br.edu.utfpr.buscacep.ui.BuscaCepScreen
import br.edu.utfpr.buscacep.viewmodel.CepViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val cepViewModel = CepViewModel(cepRepository = CepRepository())
        BuscaCepScreen(cepViewModel = cepViewModel)
    }
}