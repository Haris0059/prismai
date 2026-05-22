package rip.haris.prismai.domain.model

enum class GreetingCategory { NORMAL, FUNNY, SERIOUS }

data class Greeting(
    val id: Long = 0,
    val text: String,
    val category: GreetingCategory,
)
