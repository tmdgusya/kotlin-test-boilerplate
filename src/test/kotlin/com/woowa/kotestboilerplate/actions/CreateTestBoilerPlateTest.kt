package com.woowa.kotestboilerplate.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class CreateTestBoilerPlateTest : BehaviorSpec({

    given("Not in a file") {
        val templateAction = CreateTestBoilerPlate()

        val actionMock = mockk<AnActionEvent>(relaxed = true) {
            every { getData(CommonDataKeys.PSI_FILE) } returns null
        }
        `when`("execute plugin that i made") {
            val exception = shouldThrow<IllegalAccessException> { templateAction.actionPerformed(actionMock) }
            then("throw IllegalAccessException that contain message is 'The Plugin Only use in file class'") {
                exception.message shouldBe "The Plugin Only use in file class"
            }
        }
    }

})