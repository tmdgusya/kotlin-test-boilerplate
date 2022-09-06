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
    fun getClass(className: String): PsiClass {
        val clazz = ktFile.classes.filter { it.name == className }

        require(clazz.isNotEmpty()) { "Not Exist Class($className)" }

        return clazz.first()
    }

    fun getClass(): PsiClass {
        if (ktFile.classes.isEmpty()) throw IllegalAccessException("")
        return ktFile.classes.first()
    }

    fun getMethod(className: String, methodName: String): PsiMethod {
        val clazz = getClass(className)

        val methods = clazz.allMethods.filter { it.name == methodName }
        require(methods.isNotEmpty()) { "Is not exist method in class" }

        return methods.first()
    }

    override fun getProperties(): List<KotlinField> {
        val clazz = getClass()

        return clazz.allFields
            .filter { it.type.isExcludeType() }
            .map { KotlinField.of(it) }
    }

    override fun getDirectoryAndPackage(): String {
        return ktFile.packageFqName.toString()
    }

    override fun getClassName(): String {
        return getClass().name ?: throw NoSuchElementException("Not Exist Class in this File")
    }

    private fun PsiType.isExcludeType(): Boolean {
        if (this.canonicalText.contains("Companion")) {
            return false
        }
        return true
    }

}
