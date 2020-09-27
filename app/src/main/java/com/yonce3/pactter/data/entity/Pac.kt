package com.yonce3.pactter.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pac (
    @PrimaryKey val pacId: Int
)