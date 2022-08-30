package com.woowa.kotestboilerplate.core

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement

interface KotestCreator {

    fun createTestClass(project: Project, editor: Editor, element: PsiElement)

}
