package com.woowa.kotestboilerplate.actions

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.testIntegration.createTest.CreateTestAction
import com.woowa.kotestboilerplate.core.KotestCreator
import com.woowa.kotestboilerplate.core.UnitTestCreator

class CreateTestActionHandler(
    private val kotestCreator: KotestCreator = UnitTestCreator()
) : CreateTestAction() {

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        println("Invoke!!")
        kotestCreator.createTestClass(
            project = project,
            editor = editor ?: throw IllegalAccessException(),
            element = element
        )
    }
}
