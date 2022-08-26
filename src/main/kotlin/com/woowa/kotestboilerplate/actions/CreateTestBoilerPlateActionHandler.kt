package com.woowa.kotestboilerplate.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.testIntegration.GotoTestOrCodeAction
import com.woowa.kotestboilerplate.helper.CreateTestFileHelper
import com.woowa.kotestboilerplate.helper.KotlinClassAnalyzeHelper
import com.woowa.kotestboilerplate.utils.FileDescriptor
import com.woowa.kotestboilerplate.view.ProperTestPathSourceSelectDialog

class CreateTestBoilerPlateActionHandler : GotoTestOrCodeAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
            ?: throw IllegalAccessException("The Plugin Only use in file class")

        val editor = e.getData(CommonDataKeys.EDITOR)

        val className = editor?.caretModel?.currentCaret?.selectedText
            ?: throw IllegalArgumentException("You must select class Text")

        println("User Select Class Name : $className")

        val ktFile = FileDescriptor.convertKotlinFile(psiFile)
        val kotlinClassAnalyzeHelper = KotlinClassAnalyzeHelper(ktFile)
        val createTestFileHelper = CreateTestFileHelper(kotlinClassAnalyzeHelper)

        val properTestModuleURL = createTestFileHelper.getTestModuleURL()

        val panel = ProperTestPathSourceSelectDialog(properTestModuleURL)
        panel.showAndGet()

        if (panel.isOK) {
            println("User Clicked Ok Button")
        } else {
            println("User Clicked Cancel Button")
        }
    }
}
