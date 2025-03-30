package br.edu.utfpr.buscacep.ui

import br.edu.utfpr.buscacep.model.Endereco
import org.jetbrains.compose.resources.StringResource

data class FormField(
    val value: String = "",
    val errorStringResource: StringResource? = null
) {
    val hasError: Boolean get() = errorStringResource != null
}

data class CepFormState(
    val cep: FormField = FormField(),
    val logradouro: String = "",
    val numero: String = "",
    val complemento: String = "",
    val bairro: String = "",
    val cidade: String = "",
    val buscandoCep: Boolean = false,
    val isDataValid: Boolean = false,
    val isLoading: Boolean = false,
    val hasErrorLoading: Boolean = false,
    val endereco: Endereco? = null
) {
    val isValid: Boolean
        get() = cep.value.isNotBlank() && cep.errorStringResource == null
                && logradouro.isNotBlank()
                && numero.isNotBlank()
                && bairro.isNotBlank()
                && cidade.isNotBlank()

    val hasError: Boolean get() = hasErrorLoading || cep.hasError

    val formErrors: List<String> get() = listOfNotNull(
        if (hasError) "Erro ao carregar Cep" else null
    )

    val hasFormErrors: Boolean get() = formErrors.isNotEmpty()
}
