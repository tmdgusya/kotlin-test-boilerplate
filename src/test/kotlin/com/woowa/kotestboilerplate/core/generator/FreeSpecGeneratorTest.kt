package com.woowa.kotestboilerplate.core.generator

import com.intellij.psi.PsiMethod
import com.woowa.kotestboilerplate.core.builder.TestBuilderConfig
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

internal class FreeSpecGeneratorTest : FunSpec({

    val mockTestConfig = mockk<TestBuilderConfig>(relaxed = true)

    test("correct-draw if is Not Needed drawing method") {
        // given
        every { mockTestConfig.isNeedMethod } returns false
        val methodName = "getAttr"
        val method = mockk<PsiMethod>(relaxed = true) {
            every { name } returns methodName
        }
        val mockMetaData = mockk<KotlinClassMetaData>(relaxed = true) {
            every { methods } returns arrayOf(method)
        }
        val sut = FreeSpecGenerator(mockMetaData, mockTestConfig)
        val expected = """ "" - { }"""

        // when
        val block = sut.buildCodeBlock()

        // then
        block shouldBe expected
    }

    test("correct-draw if contain only one method") {
        // given
        every { mockTestConfig.isNeedMethod } returns true
        val methodName = "getAttr"
        val method = mockk<PsiMethod>(relaxed = true) {
            every { name } returns methodName
        }
        val mockMetaData = mockk<KotlinClassMetaData>(relaxed = true) {
            every { methods } returns arrayOf(method)
        }
        val sut = FreeSpecGenerator(mockMetaData, mockTestConfig)
        val expected = """
    "$methodName" - { }
"""

        // when
        val block = sut.buildCodeBlock()

        // then
        block shouldBe expected
    }

    test("correct-draw if contain multiple(2) method") {
        // given
        every { mockTestConfig.isNeedMethod } returns true
        val methodName1 = "getAttr"
        val methodName2 = "getName"
        val method1 = mockk<PsiMethod>(relaxed = true) {
            every { name } returns methodName1
        }
        val method2 = mockk<PsiMethod>(relaxed = true) {
            every { name } returns methodName2
        }
        val mockMetaData = mockk<KotlinClassMetaData>(relaxed = true) {
            every { methods } returns arrayOf(method1, method2)
        }
        val sut = FreeSpecGenerator(mockMetaData, mockTestConfig)
        val expected = """
    "$methodName1" - { }

    "$methodName2" - { }
"""

        // when
        val block = sut.buildCodeBlock()

        // then
        block shouldBe expected
    }

    test("correct-draw if contain multiple(3) method") {
        // given
        every { mockTestConfig.isNeedMethod } returns true
        val methodName1 = "getAttr"
        val methodName2 = "getName"
        val methodName3 = "testName"
        val method1 = mockk<PsiMethod>(relaxed = true) {
            every { name } returns methodName1
        }
        val method2 = mockk<PsiMethod>(relaxed = true) {
            every { name } returns methodName2
        }
        val method3 = mockk<PsiMethod>(relaxed = true) {
            every { name } returns methodName3
        }
        val mockMetaData = mockk<KotlinClassMetaData>(relaxed = true) {
            every { methods } returns arrayOf(method1, method2, method3)
        }
        val sut = FreeSpecGenerator(mockMetaData, mockTestConfig)
        val expected = """
    "$methodName1" - { }

    "$methodName2" - { }

    "$methodName3" - { }
"""

        // when
        val block = sut.buildCodeBlock()

        // then
        block shouldBe expected
    }

    afterEach {
        clearAllMocks()
    }

})
