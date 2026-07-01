package rip.haris.prismai.ui.common

sealed interface LoadStatus {
    data object Init : LoadStatus
    data object Loading : LoadStatus
    data object Success : LoadStatus
    data class Error(val message: String) : LoadStatus
}
