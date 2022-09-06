package com.woowa.kotestboilerplate.parser

import com.intellij.psi.PsiPrimitiveType
import com.intellij.psi.PsiType
import com.intellij.psi.impl.source.PsiClassReferenceType
import com.intellij.psi.util.PsiUtil
import org.jetbrains.kotlin.idea.refactoring.fqName.getKotlinFqName

data class KotlinType(
    val simpleName: String,
    val fqName: String,
    val wrappedType: KotlinType? = null
) {
    companion object {
        fun of(type: PsiType): KotlinType {
            val (fqName, simpleName) = convertKotlinType(type)
            val wrappedType = convertKotlinWrapperType(type)
            return KotlinType(
                simpleName = simpleName,
                fqName = fqName,
                wrappedType = wrappedType
            )
        }

        /**
         * PsiType to Kotlin Type
         */
        private fun convertKotlinType(psiType: PsiType): Pair<String, String> {
            return when (psiType) {
                is PsiPrimitiveType -> {
                    psiType.boxedTypeName.toString() to psiType.canonicalText
                }

                else -> {
                    val typeClass = PsiUtil.resolveClassInType(psiType) ?: throw IllegalArgumentException("")
                    typeClass.getKotlinFqName().toString() to typeClass.name.toString()
                }
            }
        }
        /**
         * 파일 이름을 주고 Import 해오는 방식으로 바꿔야함.
         */
        private fun convertKotlinWrapperType(psiType: PsiType): KotlinType? {
            return when (psiType) {
                is PsiClassReferenceType -> {
                    return if (psiType.parameters.isNotEmpty()) {
                        return of(psiType.parameters[0])
                    } else null
                }

                else -> null
            }
        }
    }
}
