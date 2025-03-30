package br.edu.utfpr.buscacep.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.edu.utfpr.buscacep.model.Endereco
import br.edu.utfpr.buscacep.viewmodel.CepViewModel
import br.edu.utfpr.buscacep.ui.visualtransformation.CepVisualTransformation
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscaCepScreen(
    modifier: Modifier = Modifier,
    cepViewModel: CepViewModel
) {
    val formState = cepViewModel.formState.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(formState.hasErrorLoading) {
        if (formState.hasErrorLoading) {
            snackbarHostState.showSnackbar(
                message = "Ocorreu um erro ao consultar o CEP. " +
                        "Aguarde um momento e tente novamente."
            )
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = formState.cep.value,
                onValueChange = { cepViewModel.onCepChanged(it) },
                label = { Text("Digite o CEP") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                visualTransformation = CepVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                isError = formState.cep.hasError
            )
            formState.cep.errorStringResource?.let {
                Text(
                    text = stringResource(it),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedButton(
                onClick = { cepViewModel.buscarCep(formState.cep.value) },
                enabled = formState.isDataValid && !formState.isLoading,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = if (formState.isDataValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (formState.isDataValid) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            ) {
                if (formState.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Buscar",
                        color = if (formState.isDataValid)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            ResultadoCep(formState.endereco, formState)
        }
    }
}

@Composable
fun ResultadoCep(endereco: Endereco?, formState: CepFormState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (!formState.isLoading && !formState.hasErrorLoading && endereco != null) {
            endereco?.let {
                Text("CEP: ${it.cep}")
                Text("Logradouro: ${it.logradouro}")
                Text("Bairro: ${it.bairro}")
                Text("Localidade: ${it.localidade}")
                Text("UF: ${it.uf}")
            }
        } else {
            Text("CEP:")
            Text("Logradouro:")
            Text("Bairro:")
            Text("Localidade:")
            Text("UF:")
        }
    }
}
