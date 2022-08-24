package com.woowa.kotestboilerplate.helper

import com.intellij.psi.PsiClass
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

}
