package com.woowa.kotestboilerplate.core

import com.intellij.psi.PsiField

data class KotlinMetaData(
    var className: String,
    val properties: Array<PsiField>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KotlinMetaData

        if (className != other.className) return false
        if (properties != null) {
            if (other.properties == null) return false
            if (!properties.contentEquals(other.properties)) return false
        } else if (other.properties != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = className.hashCode()
        result = 31 * result + (properties?.contentHashCode() ?: 0)
        return result
    }
}