package br.edu.utfpr.buscacep

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform