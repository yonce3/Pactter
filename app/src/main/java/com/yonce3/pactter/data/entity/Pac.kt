package com.yonce3.pactter.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pac (
    @PrimaryKey(autoGenerate = true) val pacId: Int,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "image_file_path") val imageFilePath: String?,
)