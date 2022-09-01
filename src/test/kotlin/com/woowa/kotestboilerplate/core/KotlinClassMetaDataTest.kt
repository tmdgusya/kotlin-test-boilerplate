package com.woowa.kotestboilerplate.core

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiField
import com.intellij.psi.PsiType
import com.woowa.kotestboilerplate.helper.KotlinClassAnalyzeHelper
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.kotlin.psi.KtFile
import kotlin.test.assertNotNull

class KotlinClassMetaDataTest: FunSpec({
    test("KotlinMetaData contains className") {
        val kotlinMetaData = KotlinMetaData(
            className = "HelloWorld"
        )

        kotlinMetaData.className shouldBe "HelloWorld"
    }

    test("KotlinMetaData contains class Properties") {
        val className = "HelloWorld"
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
        val helper = KotlinClassAnalyzeHelper(ktFiles)
        val kotlinMetaData = KotlinMetaData(
            className = "HelloWorld",
            properties = helper.getProperties("HelloWorld")
        )

        assertNotNull(kotlinMetaData.properties)
        kotlinMetaData.properties!![0].name shouldBe "age"
    }
})