package com.yonce3.pactter.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey val userId: Int,
    @ColumnInfo(name = "name") val name: String?,
)