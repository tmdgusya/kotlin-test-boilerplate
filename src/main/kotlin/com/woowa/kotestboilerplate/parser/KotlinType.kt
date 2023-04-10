package com.woowa.kotestboilerplate.parser

import com.intellij.psi.PsiPrimitiveType
import com.intellij.psi.PsiType
import com.intellij.psi.PsiWildcardType
import com.intellij.psi.impl.source.PsiClassReferenceType
import com.intellij.psi.util.PsiUtil
import org.jetbrains.kotlin.idea.base.psi.kotlinFqName

data class KotlinType(
    val simpleName: String,
    val fqName: String,
    val wrappedType: KotlinType? = null
) {
    companion object {
        fun of(type: PsiType): KotlinType = convertKotlinType(type).let {
            KotlinType(
                simpleName = it.second,
                fqName = it.first,
                wrappedType = convertKotlinWrapperType(type)
            )
        }

        /**
         * PsiType to Kotlin Type
         */
        private fun convertKotlinType(psiType: PsiType): Pair<String, String> = when (psiType) {
            is PsiPrimitiveType -> {
                psiType.boxedTypeName.toString() to psiType.canonicalText
            }

            is PsiWildcardType -> { psiType.presentableText to psiType.getCanonicalText() }

            else -> {
                val typeClass = PsiUtil.resolveClassInType(psiType) ?: throw IllegalArgumentException("")
                typeClass.kotlinFqName.toString() to typeClass.name.toString()
            }
        }
        /**
         * 파일 이름을 주고 Import 해오는 방식으로 바꿔야함.
         */
        private fun convertKotlinWrapperType(psiType: PsiType): KotlinType? = when (psiType) {
            is PsiClassReferenceType -> {
                if (psiType.parameters.isNotEmpty()) { of(psiType.parameters[0]) } else null
            }

            else -> null
        }
    }
}
