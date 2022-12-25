package com.woowa.kotestboilerplate.parser

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiType
import org.jetbrains.kotlin.psi.KtFile
import java.util.NoSuchElementException

/**
 * Kotlin Class Analyze Class
 * Given KtFile Then can extract Class And Methods
 */
class KotlinClassParserImpl(
    private val ktFile: KtFile
) : KotlinClassParser {
    fun getClass(className: String): PsiClass = ktFile.classes
        .filter { it.name == className }
        .also { require(it.isNotEmpty()) { "Not Exist Class($className)" } }
        .first()

    fun getClass(): PsiClass = ktFile.classes.also { require(it.isNotEmpty()) { "Not Exist Class" } }.first()

    fun getMethod(className: String, methodName: String): PsiMethod = getClass(className)
        .let { it.allMethods.filter { it.name == methodName } }
        .also { require(it.isNotEmpty()) { "Is not exist method in class" } }
        .first()

    override fun getProperties(): List<KotlinField> = getClass().allFields
        .filter { it.type.isExcludeType() }
        .map { KotlinField.of(it) }

    override fun getDirectoryAndPackage(): String = ktFile.packageFqName.toString()

    override fun getClassName(): String = getClass().name
        ?: throw NoSuchElementException("Not Exist Class in this File")

    private fun PsiType.isExcludeType(): Boolean = !this.canonicalText.contains("Companion")

    fun getMethods(): Array<PsiMethod> = getClass().methods

}
