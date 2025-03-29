package br.edu.utfpr.buscacep.repository

import br.edu.utfpr.buscacep.model.Endereco
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class CepRepository {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun buscarCep(cep: String): Endereco {
        return client.get("https://viacep.com.br/ws/$cep/json/").body()
    }
}
