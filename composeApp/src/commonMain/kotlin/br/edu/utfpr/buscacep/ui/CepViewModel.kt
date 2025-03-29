package br.edu.utfpr.buscacep.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.buscacep.repository.CepRepository
import br.edu.utfpr.buscacep.ui.CepFormState
import kotlinx.coroutines.launch


class CepViewModel(private val cepRepository: CepRepository) : ViewModel() {

    var formState = mutableStateOf(CepFormState())
        private set

    // Método público para atualizar o formState na Preview
    fun setFormState(state: CepFormState) {
        formState.value = state
    }

    private fun isCepValid(cep: String): Boolean {
        return cep.length == 8 && cep.all { it.isDigit() }
    }

    fun buscarCep(cep: String) {
        if (formState.value.isLoading || !isCepValid(cep)) return

        formState.value = formState.value.copy(isLoading = true, hasErrorLoading = false)

        viewModelScope.launch {
            try {
                val endereco = cepRepository.buscarCep(cep)
                formState.value = formState.value.copy(
                    isLoading = false,
                    endereco = endereco
                )
            } catch (ex: Exception) {
                println("Erro ao consultar o CEP $cep")
                ex.printStackTrace()
                formState.value = formState.value.copy(isLoading = false, hasErrorLoading = true)
            }
        }
    }

    fun onCepChanged(cep: String) {
        val cleanedCep = cep.replace("[^0-9]".toRegex(), "")
        val formattedCep = cleanedCep.chunked(5).joinToString("-")

        formState.value = formState.value.copy(
            cep = formattedCep,
            isDataValid = isCepValid(cleanedCep)
        )
    }
}
