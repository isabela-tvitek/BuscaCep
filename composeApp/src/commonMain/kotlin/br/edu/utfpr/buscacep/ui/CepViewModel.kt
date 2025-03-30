package br.edu.utfpr.buscacep.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.buscacep.repository.CepRepository
import br.edu.utfpr.buscacep.ui.CepFormState
import br.edu.utfpr.buscacep.ui.FormField
import br.edu.utfpr.buscacep.utils.extensions.formatarCep
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class CepViewModel(
    private val cepRepository: CepRepository
) : ViewModel() {

    var formState = mutableStateOf(CepFormState())
        private set

    private fun isCepValid(cep: String): Boolean {
        return cep.length == 8 && cep.all { it.isDigit() }
    }

    fun buscarCep(cep: String) {
        val cleanedCep = cep.replace("[^0-9]".toRegex(), "")

        if (formState.value.isLoading || !isCepValid(cleanedCep)) return

        formState.value = formState.value.copy(
            isLoading = true,
            hasErrorLoading = false,
            endereco = null
        )

        formState.value = formState.value.copy(isLoading = true, hasErrorLoading = false)

        viewModelScope.launch {
            delay(1500)
            try {
                val ocorreuErro = Random.nextBoolean()
                if (ocorreuErro) {
                    throw Exception("Erro gerado para teste")
                }

                val endereco = cepRepository.buscarCep(cleanedCep)
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
        val formattedCep = cleanedCep.formatarCep()

        formState.value = formState.value.copy(
            cep = FormField(value = formattedCep),
            isDataValid = isCepValid(cleanedCep)
        )
    }
}
