package com.woowa.kotestboilerplate.helper

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiType
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtFile
import java.lang.IllegalArgumentException


class KotlinClassAnalyzeHelperTest : BehaviorSpec({

    given("given ktFile and className") {
        val className = "TestClass"
        val mockPsiClass = mockk<PsiClass>(relaxed = true) {
            every { name } returns className
        }
        val ktFiles = mockk<KtFile>(relaxed = true) {
            every { classes } returns arrayOf(mockPsiClass)
        }
        val kotlinClassAnalyzeHelper = KotlinClassAnalyzeHelper(ktFile = ktFiles)
        `when`("when execute getClass(className)") {
            val clazz = kotlinClassAnalyzeHelper.getClass(className = className)
            then("return Class Name is given class Name") {
                clazz.name shouldBe className
            }
        }
    }

    given("given ktFile and wrong className") {
        val className = "TestClass"
        val mockPsiClass = mockk<PsiClass>(relaxed = true) {
            every { name } returns "WrongClass"
        }
        val ktFiles = mockk<KtFile>(relaxed = true) {
            every { classes } returns arrayOf(mockPsiClass)
        }
        val kotlinClassAnalyzeHelper = KotlinClassAnalyzeHelper(ktFile = ktFiles)
        `when`("when execute getClass(className)") {
            val exception = shouldThrow<IllegalArgumentException>
                {  kotlinClassAnalyzeHelper.getClass(className = className) }
            then("throw IllegalArgumentException('Not Exist Class($className)')") {
                exception.message shouldBe "Not Exist Class($className)"
            }
        }
    }

    given("given ktFile and ClassName and specific methodName") {
        val className = "TestClass"
        val methodName = "testMethod"
        val mockMethod = mockk<PsiMethod>(relaxed = true) {
            every { name } returns methodName
        }
        val mockPsiClass = mockk<PsiClass>(relaxed = true) {
            every { name } returns className
            every { allMethods } returns arrayOf(mockMethod)
        }
        val ktFiles = mockk<KtFile>(relaxed = true) {
            every { classes } returns arrayOf(mockPsiClass)
        }
        val kotlinClassAnalyzeHelper = KotlinClassAnalyzeHelper(ktFile = ktFiles)
        `when`("when execute getClass(className)") {
            val method = kotlinClassAnalyzeHelper.getMethod(className = className, methodName = methodName)
            then("return Class Name is given class Name") {
                method.name shouldBe methodName
            }
        }
    }

    given("[FindPackagePath Test] given ktFile and className") {
        val fqName = mockk<FqName>(relaxed = true) {
            every { asString() } returns "com.woowa.kotestboilerplate.helper"
        }
        val ktFiles = mockk<KtFile>(relaxed = true) {
            every { packageFqName } returns fqName
        }
        val kotlinClassAnalyzeHelper = KotlinClassAnalyzeHelper(ktFile = ktFiles)
        `when`("when execute getPackagePath()") {
            val packagePath = kotlinClassAnalyzeHelper.getPackagePath()
            then("then return packagePath() is com.woowa.kotestboilerplate.helper") {
                packagePath shouldBe "com.woowa.kotestboilerplate.helper"
            }
        }
    }

    given("given ktFile and ClassName and wrong specific methodName") {
        val className = "TestClass"
        val methodName = "wrongMethodName"
        val mockMethod = mockk<PsiMethod>(relaxed = true) {
            every { name } returns "xxx"
        }
        val mockPsiClass = mockk<PsiClass>(relaxed = true) {
            every { name } returns className
            every { allMethods } returns arrayOf(mockMethod)
        }
        val ktFiles = mockk<KtFile>(relaxed = true) {
            every { classes } returns arrayOf(mockPsiClass)
        }
        val kotlinClassAnalyzeHelper = KotlinClassAnalyzeHelper(ktFile = ktFiles)
        `when`("when execute getClass(className)") {
            val exception = shouldThrow<IllegalArgumentException> {
                kotlinClassAnalyzeHelper.getMethod(className = className, methodName = methodName)
            }
            then("thow IllegalArgumentException that contain message is 'Is not exist method in class'") {
                exception.message shouldBe "Is not exist method in class"
            }
        }
    }

    given("given ktFile and ClassName") {
        val className = "TestClass"
        val mockPsiField = mockk<PsiField>(relaxed = true) {
            every { name } returns "age"
            every { type } returns PsiType.INT
        }
        val mockPsiClass = mockk<PsiClass>(relaxed = true) {
            every { name } returns className
            every { allFields } returns arrayOf(mockPsiField)
        }
        val ktFiles = mockk<KtFile>(relaxed = true) {
            every { classes } returns arrayOf(mockPsiClass)
        }
        val kotlinClassAnalyzeHelper = KotlinClassAnalyzeHelper(ktFile = ktFiles)
        `when`("when execute getProperties() method") {
            val properties = kotlinClassAnalyzeHelper.getProperties(className = className)
            then("then return properties list that contained class") {
                properties.size shouldBe 1

                val property = properties[0]
                property.name shouldBe "age"
                property.type shouldBe PsiType.INT
            }
        }
    }
})
