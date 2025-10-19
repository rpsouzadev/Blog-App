package com.rpsouza.blogapp.domain.model

import android.content.Intent
import android.graphics.drawable.Drawable

data class MapAppOption(
    val label: String,
    val icon: Drawable,
    val launchIntent: Intent
)
