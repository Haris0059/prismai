package rip.haris.prismai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val email: String,
    val password: String,
    val fullName: String,
    val displayName: String,
    val isPro: Boolean,
    val hapticOn: Boolean,
)
