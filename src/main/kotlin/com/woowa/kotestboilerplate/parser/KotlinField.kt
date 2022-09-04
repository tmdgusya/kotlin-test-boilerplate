package com.woowa.kotestboilerplate.parser

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiField
import com.intellij.psi.PsiType
import com.intellij.psi.util.PsiUtil
import org.jetbrains.kotlin.idea.refactoring.fqName.getKotlinFqName
import kotlin.reflect.KClass

data class KotlinField(
    val name: String,
    val type: KotlinType,
    val annotation: Array<PsiAnnotation>? = null
) {

    companion object {
        fun of(psiField: PsiField): KotlinField {
            return KotlinField(
                name = psiField.name,
                type = KotlinType.of(convertKotlinType(psiField.type)),
                annotation = psiField.annotations
            )
        }

        /**
         * PsiType to Kotlin Type
         */
        private fun convertKotlinType(psiType: PsiType): KClass<out Any> {
            val typeClass = PsiUtil.resolveClassInType(psiType) ?: throw IllegalArgumentException("")
            return Class.forName(typeClass.getKotlinFqName().toString()).kotlin
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KotlinField

        if (name != other.name) return false
        if (type != other.type) return false
        if (annotation != null) {
            if (other.annotation == null) return false
            if (!annotation.contentEquals(other.annotation)) return false
        } else if (other.annotation != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (annotation?.contentHashCode() ?: 0)
        return result
    }

}
