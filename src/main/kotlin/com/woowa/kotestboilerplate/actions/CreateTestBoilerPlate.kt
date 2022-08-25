package com.woowa.kotestboilerplate.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.testIntegration.GotoTestOrCodeAction
import com.woowa.kotestboilerplate.helper.KotlinClassAnalyzeHelper
import com.woowa.kotestboilerplate.utils.FileDescriptor

class CreateTestBoilerPlate : GotoTestOrCodeAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
            ?: throw IllegalAccessException("The Plugin Only use in file class")

        val editor = e.getData(CommonDataKeys.EDITOR)

        val className = editor?.caretModel?.currentCaret?.selectedText
            ?: throw IllegalArgumentException("You must select class Text")

        val krFile = FileDescriptor.convertKotlinFile(psiFile)
        val kotlinClassAnalyzeHelper = KotlinClassAnalyzeHelper(krFile)

        val properties = kotlinClassAnalyzeHelper.getProperties(className)
    }
}