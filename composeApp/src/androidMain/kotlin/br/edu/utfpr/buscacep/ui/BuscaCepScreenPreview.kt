package br.edu.utfpr.buscacep.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.edu.utfpr.buscacep.model.Endereco
import br.edu.utfpr.buscacep.repository.CepRepository
import br.edu.utfpr.buscacep.viewmodel.CepViewModel

@Preview(showBackground = true)
@Composable
private fun BuscaCepScreenPreview() {
    MaterialTheme {
        val mockViewModel = CepViewModel(cepRepository = CepRepository())

        mockViewModel.setFormState(
            CepFormState(
                cep = "85064-090",
                isDataValid = true,
                isLoading = false,
                hasErrorLoading = false,
                endereco = Endereco(
                    cep = "85064-090",
                    logradouro = "Rua dos Carpinteiros",
                    bairro = "Imóvel Morro Alto",
                    localidade = "Guarapuava",
                    uf = "PR"
                )
            )
        )

        BuscaCepScreen(cepViewModel = mockViewModel)
    }
}
