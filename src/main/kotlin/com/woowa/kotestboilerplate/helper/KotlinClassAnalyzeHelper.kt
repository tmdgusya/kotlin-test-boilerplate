package com.woowa.kotestboilerplate.helper

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.psi.KtFile

/**
 * Kotlin Class Analyze Class
 * Given KtFile Then can extract Class And Methods
 */
class KotlinClassAnalyzeHelper(
    private val ktFile: KtFile
) {
    fun getClass(className: String): PsiClass {
        return ktFile.classes.first { it.name == className }
    }

    fun getMethod(className: String, methodName: String): PsiMethod {
        val clazz = getClass(className)

        return clazz.allMethods.first { it.name == methodName }
    }

}
