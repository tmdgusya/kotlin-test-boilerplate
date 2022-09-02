package com.woowa.kotestboilerplate.helper

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiType
import com.woowa.kotestboilerplate.parser.KotlinClassParserImpl
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtFile
import java.lang.IllegalArgumentException


class KotlinClassParserTest : BehaviorSpec({

    given("given ktFile and className") {
        val className = "TestClass"
        val mockPsiClass = mockk<PsiClass>(relaxed = true) {
            every { name } returns className
        }
        val ktFiles = mockk<KtFile>(relaxed = true) {
            every { classes } returns arrayOf(mockPsiClass)
        }
        val kotlinClassParserImpl = KotlinClassParserImpl(ktFile = ktFiles)
        `when`("when execute getClass(className)") {
            val clazz = kotlinClassParserImpl.getClass(className = className)
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
        val kotlinClassParserImpl = KotlinClassParserImpl(ktFile = ktFiles)
        `when`("when execute getClass(className)") {
            val exception = shouldThrow<IllegalArgumentException>
                {  kotlinClassParserImpl.getClass(className = className) }
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
        val kotlinClassParserImpl = KotlinClassParserImpl(ktFile = ktFiles)
        `when`("when execute getClass(className)") {
            val method = kotlinClassParserImpl.getMethod(className = className, methodName = methodName)
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
        val kotlinClassParserImpl = KotlinClassParserImpl(ktFile = ktFiles)
        `when`("when execute getPackagePath()") {
            val packagePath = kotlinClassParserImpl.getPackagePath()
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
        val kotlinClassParserImpl = KotlinClassParserImpl(ktFile = ktFiles)
        `when`("when execute getClass(className)") {
            val exception = shouldThrow<IllegalArgumentException> {
                kotlinClassParserImpl.getMethod(className = className, methodName = methodName)
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
        val kotlinClassParserImpl = KotlinClassParserImpl(ktFile = ktFiles)
        `when`("when execute getProperties() method") {
            val properties = kotlinClassParserImpl.getProperties()
            then("then return properties list that contained class") {
                properties.size shouldBe 1

                val property = properties[0]
                property.name shouldBe "age"
                property.type shouldBe PsiType.INT
            }
        }
    }

    given("given kotlin File") {
        val className = "TestClass"
        val mockPsiClass = mockk<PsiClass>(relaxed = true) {
            every { name } returns className
        }
        val ktFile = mockk<KtFile>(relaxed = true) {
            every { classes } returns arrayOf(mockPsiClass)
        }
        `when`("execute getClassName()") {
            val kotlinClassParserImpl = KotlinClassParserImpl(ktFile = ktFile)
            then("should return first ClassName") {
                val _className = kotlinClassParserImpl.getClassName()

                _className shouldBe className
            }
        }
    }

    given("[Exception TEST] given kotlin File that not contained class") {
        val mockPsiClass = mockk<PsiClass>(relaxed = true) {
            every { name } returns null
        }
        val ktFile = mockk<KtFile>(relaxed = true) {
            every { classes } returns arrayOf(mockPsiClass)
        }
        `when`("execute getClassName()") {
            val kotlinClassParserImpl = KotlinClassParserImpl(ktFile = ktFile)
            then("should throw NoSuchElementException") {
                val exception = shouldThrow<NoSuchElementException> {
                    kotlinClassParserImpl.getClassName()
                }

                exception.message shouldBe "Not Exist Class in this File"
            }
        }
    }
})
