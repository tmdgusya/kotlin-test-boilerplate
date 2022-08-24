package com.woowa.kotestboilerplate.utils

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.impl.source.PsiJavaFileImpl
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.kotlin.psi.KtFile


class FileDescriptorTest : BehaviorSpec({

    given("In Java Class") {
        val javaFiles = mockk<PsiJavaFileImpl>(relaxed = true)
        val actionMock = mockk<AnActionEvent>(relaxed = true) {
            every { getData(CommonDataKeys.PSI_FILE) } returns javaFiles
        }
        `when`("When execute create boiler template method") {
            val exception = shouldThrow<IllegalAccessException> {
                FileDescriptor.convertKotlinFile(actionMock.getData(CommonDataKeys.PSI_FILE)!!)
            }
            then("cause exception that contain message that 'You should use in kotlin file' ") {
                exception.message shouldBe "You should use in kotlin file"
            }
        }
    }

    given("In Kotlin Class") {
        val ktFiles = mockk<KtFile>(relaxed = true)
        val actionMock = mockk<AnActionEvent>(relaxed = true) {
            every { getData(CommonDataKeys.PSI_FILE) } returns ktFiles
        }
        `when`("When execute create boiler template method") {
            val ktFileClass = FileDescriptor.convertKotlinFile(actionMock.getData(CommonDataKeys.PSI_FILE)!!)
            then("return Object that is type of ktFile ") {
                ktFileClass::class shouldBe KtFile::class
            }
        }
    }

})