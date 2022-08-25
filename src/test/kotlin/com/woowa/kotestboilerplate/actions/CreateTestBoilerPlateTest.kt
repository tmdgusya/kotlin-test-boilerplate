package com.woowa.kotestboilerplate.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.CaretModel
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiEditorUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.lang.IllegalArgumentException

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

    given("if user didn't drag anything") {

        val templateAction = CreateTestBoilerPlate()

        val mockPsiFile = mockk<PsiFile>(relaxed = true)
        val mockCaret = mockk<Caret>(relaxed = true) {
            every { selectedText } returns null
        }
        val mockCaretModel = mockk<CaretModel>(relaxed = true) {
            every { currentCaret } returns mockCaret
        }
        val mockEditor = mockk<Editor>(relaxed = true) {
            every { caretModel } returns mockCaretModel
        }
        val actionMock = mockk<AnActionEvent>(relaxed = true) {
            every { getData(CommonDataKeys.PSI_FILE) } returns mockPsiFile
            every { getData(CommonDataKeys.EDITOR) } returns mockEditor
        }

        `when`("when execute plugin") {
            val exception = shouldThrow<IllegalArgumentException> {
                templateAction.actionPerformed(actionMock)
            }
            then("throw IllegalArgumentException('You must select class Text')") {
                exception.message shouldBe "You must select class Text"
            }
        }
    }

})