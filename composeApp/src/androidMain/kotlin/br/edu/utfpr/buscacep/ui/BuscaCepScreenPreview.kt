package br.edu.utfpr.buscacep.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.edu.utfpr.buscacep.model.Endereco
import br.edu.utfpr.buscacep.repository.CepRepository
import br.edu.utfpr.buscacep.viewmodel.CepViewModel

@Preview(showBackground = true)
@Composable
private fun BuscaCepScreenEmptyPreview() {
    MaterialTheme {
        val mockViewModel = CepViewModel(cepRepository = CepRepository())

        mockViewModel.formState.value = CepFormState(
            cep = FormField(value = ""),
            isDataValid = false,
            isLoading = false,
            hasErrorLoading = false,
            endereco = null
        )

        BuscaCepScreen(cepViewModel = mockViewModel)
    }
}

@Preview(showBackground = true)
@Composable
private fun BuscaCepScreenLoadingPreview() {
    MaterialTheme {
        val mockViewModel = CepViewModel(cepRepository = CepRepository())

        mockViewModel.formState.value = CepFormState(
            cep = FormField(value = "85504-430"),
            isDataValid = true,
            isLoading = true,
            hasErrorLoading = false,
            endereco = Endereco(
                cep = "85504-430",
                logradouro = "Rua Itabira",
                bairro = "Bancários",
                localidade = "Pato Branco",
                uf = "PR"
            )
        )

        BuscaCepScreen(cepViewModel = mockViewModel)
    }
}

@Preview(showBackground = true)
@Composable
private fun BuscaCepScreenPreview() {
    MaterialTheme {
        val mockViewModel = CepViewModel(cepRepository = CepRepository())

        mockViewModel.formState.value = CepFormState(
            cep = FormField(value = "85504-430"),
            isDataValid = true,
            isLoading = false,
            hasErrorLoading = false,
            endereco = Endereco(
                cep = "85504-430",
                logradouro = "Rua Itabira",
                bairro = "Bancários",
                localidade = "Pato Branco",
                uf = "PR"
            )
        )

        BuscaCepScreen(cepViewModel = mockViewModel)
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun BuscaCepScreenErrorPreview() {
//    MaterialTheme {
//        val mockViewModel = CepViewModel(cepRepository = CepRepository())
//
//        mockViewModel.formState.value = CepFormState(
//            cep = FormField(value = "85504-430"),
//            isDataValid = false,
//            isLoading = false,
//            hasErrorLoading = true,
//            endereco = null
//        )
//
//        BuscaCepScreen(cepViewModel = mockViewModel)
//    }
//}
