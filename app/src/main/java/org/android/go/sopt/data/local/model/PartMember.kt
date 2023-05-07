package org.android.go.sopt.data.local.model

import androidx.annotation.DrawableRes

data class PartMember(
    @DrawableRes
    val imageResourceContent: Int,
    val stringContent: String,
)