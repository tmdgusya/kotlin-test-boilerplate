package com.woowa.kotestboilerplate.core

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.woowa.kotestboilerplate.core.builder.TestBuilderConfig

interface KotestCreator {

    fun createTestClass(project: Project, editor: Editor, element: PsiElement, testConfig: TestBuilderConfig)

}
