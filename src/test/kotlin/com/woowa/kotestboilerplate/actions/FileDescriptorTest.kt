package com.woowa.kotestboilerplate.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.woowa.kotestboilerplate.stub.PsiFileStub
import com.woowa.kotestboilerplate.utils.FileDescriptor
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk


class FileDescriptorTest: BehaviorSpec({

    given("In Java Class") {
        val actionMock = mockk<AnActionEvent>(relaxed = true) {
            every { getData(CommonDataKeys.PSI_FILE) } returns PsiFileStub()
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

})