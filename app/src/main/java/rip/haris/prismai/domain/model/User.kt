package rip.haris.prismai.domain.model

data class User(
    val id: Long = 0,
    val email: String,
    val fullName: String,
    val displayName: String,
    val isPro: Boolean,
    val hapticOn: Boolean,
    val preferences: String = "",
)
