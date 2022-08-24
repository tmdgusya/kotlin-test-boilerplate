package com.woowa.kotestboilerplate.helper

import com.intellij.psi.PsiClass
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.kotlin.psi.KtFile



class KotlinClassAnalyzeHelperTest : BehaviorSpec({

    given("given ktFile") {
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
            then("return Class Name is given class Name")
            clazz.name shouldBe className
        }
    }
})
