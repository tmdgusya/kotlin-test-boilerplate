package com.woowa.kotestboilerplate.parser

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiField
import com.intellij.psi.PsiType
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.kotlin.psi.KtFile

class KotlinClassParserImplTest : FunSpec({
    val ktFile: KtFile = mockk(relaxed = true)
    val sut = KotlinClassParserImpl(ktFile)

    test("return first class in the file") {
        val firstClass = mockk<PsiClass>(relaxed = true) {
            every { name } returns "FirstTestClass"
        }
        val secondInnerClass = mockk<PsiClass>(relaxed = true) {
            every { name } returns "SecondTestClass"
        }
        ktFile.let {
            every { it.classes } returns arrayOf(firstClass, secondInnerClass)
        }

        val result = sut.getClass()

        result shouldBe firstClass
        result shouldNotBe secondInnerClass
    }

    test("return all properties in the file") {
        val fieldName = "age"
        val psiField = mockk<PsiField>(relaxed = true) {
            every { type } returns PsiType.INT
            every { name } returns fieldName
        }
        val firstClass = mockk<PsiClass>(relaxed = true) {
            every { allFields } returns arrayOf(psiField)
        }
        ktFile.let {
            every { it.classes } returns arrayOf(firstClass)
        }

        val result = sut.getProperties()

        result.size shouldBe 1
        result[0].name shouldBe fieldName
        result[0].type.simpleName shouldBe "int"
        result[0].type.fqName shouldBe "java.lang.Integer"
    }

    test("return all properties that exclude companion object type in the file") {
        val fieldName = "age"
        val psiField = mockk<PsiField>(relaxed = true) {
            every { type } returns PsiType.INT
            every { name } returns fieldName
        }
        val psiCompanionType = mockk<PsiType>(relaxed = true) {
            every { canonicalText } returns "Companion"
        }
        val psiCompanionField = mockk<PsiField>(relaxed = true) {
            every { type } returns psiCompanionType
        }
        val firstClass = mockk<PsiClass>(relaxed = true) {
            every { allFields } returns arrayOf(psiField, psiCompanionField)
        }
        ktFile.let {
            every { it.classes } returns arrayOf(firstClass)
        }

        val result = sut.getProperties()

        result.size shouldNotBe 2
        result[0].name shouldBe fieldName
    }

    afterEach {
        clearAllMocks()
    }
})
