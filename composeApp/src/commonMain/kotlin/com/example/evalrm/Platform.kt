package com.example.evalrm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform