package org.android.go.sopt.data.model

import androidx.annotation.DrawableRes

data class PartMember(
    @DrawableRes
    val memberImage: Int,
    val memberName: String,
)