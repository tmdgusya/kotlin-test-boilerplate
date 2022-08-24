package com.woowa.kotestboilerplate.helper

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
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
})
