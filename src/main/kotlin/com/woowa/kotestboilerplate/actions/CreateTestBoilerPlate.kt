package com.woowa.kotestboilerplate.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.testIntegration.GotoTestOrCodeAction

class CreateTestBoilerPlate : GotoTestOrCodeAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
            ?: throw IllegalAccessException("The Plugin Only use in file class")
    }
}