package br.edu.utfpr.buscacep.ui

import br.edu.utfpr.buscacep.model.Endereco

data class CepFormState(
    val cep: String = "",
    val isDataValid: Boolean = false,
    val isLoading: Boolean = false,
    val hasErrorLoading: Boolean = false,
    val endereco: Endereco? = null
)
