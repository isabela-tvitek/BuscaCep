package br.edu.utfpr.buscacep.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.edu.utfpr.buscacep.model.Endereco
import br.edu.utfpr.buscacep.viewmodel.CepViewModel

class CepVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val input = text.text
        val masked = if (input.length <= 5) {
            input
        } else {
            input.substring(0, 5) + "-" + input.substring(5).take(3)
        }
        return TransformedText(AnnotatedString(masked), OffsetMapping.Identity)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscaCepScreen(
    modifier: Modifier = Modifier,
    cepViewModel: CepViewModel,
) {
    val formState = cepViewModel.formState.value

    Scaffold (
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = formState.cep,
                onValueChange = { cepViewModel.onCepChanged(it) },
                label = { Text("Digite o CEP") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                visualTransformation = CepVisualTransformation(),  // Aplicando a máscara
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            ElevatedButton(
                onClick = { cepViewModel.buscarCep(formState.cep) },
                enabled = formState.isDataValid && !formState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (formState.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Buscar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!formState.isLoading && !formState.hasErrorLoading) {
                ResultadoCep(formState.endereco)
            }

            if (formState.hasErrorLoading) {
                Snackbar(
                    action = {
                        TextButton(onClick = { cepViewModel.buscarCep(formState.cep) }) {
                            Text("Tentar Novamente")
                        }
                    },
                    content = { Text("Ocorreu um erro ao consultar o CEP. Tente novamente.") }
                )
            }
        }
    }
}

@Composable
fun ResultadoCep(endereco: Endereco?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        endereco?.let {
            Text("CEP: ${it.cep}")
            Text("Logradouro: ${it.logradouro}")
            Text("Bairro: ${it.bairro}")
            Text("Localidade: ${it.localidade}")
            Text("UF: ${it.uf}")
        }
    }
}