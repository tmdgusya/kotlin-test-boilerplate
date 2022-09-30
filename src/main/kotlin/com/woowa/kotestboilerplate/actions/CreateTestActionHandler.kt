package com.woowa.kotestboilerplate.actions

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.testIntegration.createTest.CreateTestAction
import com.woowa.kotestboilerplate.core.builder.TestBuilderConfig
import com.woowa.kotestboilerplate.view.TestUiDslDialog

class CreateTestActionHandler : CreateTestAction() {

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        val testConfig = TestBuilderConfig()
        TestUiDslDialog(project, editor, element, testConfig).showAndGet()
    }
}
