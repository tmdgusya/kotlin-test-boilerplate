package com.woowa.kotestboilerplate.parser

import com.intellij.psi.PsiType
import com.intellij.psi.impl.source.PsiClassReferenceType
import com.intellij.psi.util.PsiUtil
import org.jetbrains.kotlin.idea.refactoring.fqName.getKotlinFqName
import kotlin.reflect.KClass

data class KotlinType(
    val simpleName: String,
    val fqName: String,
    val wrappedType: KotlinType? = null
) {
    companion object {

        private const val InvalidFileExceptionMessage = "The File is Not Invalid File"

        fun of(type: PsiType): KotlinType {
            val ktClass = convertKotlinType(type)
            val wrappedType = convertKotlinWrapperType(type)
            return KotlinType(
                simpleName = ktClass.simpleName ?: throw IllegalAccessException(InvalidFileExceptionMessage),
                fqName = ktClass.qualifiedName ?: throw IllegalAccessException(InvalidFileExceptionMessage),
                wrappedType = wrappedType
            )
        }

        /**
         * PsiType to Kotlin Type
         */
        private fun convertKotlinType(psiType: PsiType): KClass<out Any> {
            val typeClass = PsiUtil.resolveClassInType(psiType) ?: throw IllegalArgumentException("")
            return Class.forName(typeClass.getKotlinFqName().toString()).kotlin
        }

        private fun convertKotlinWrapperType(psiType: PsiType): KotlinType? {
            if ((psiType as PsiClassReferenceType).parameters.isNotEmpty()) {
                return of(psiType.parameters[0])
            }
            return null
        }
    }
}
