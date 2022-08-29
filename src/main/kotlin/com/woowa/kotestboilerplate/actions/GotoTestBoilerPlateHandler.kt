package com.woowa.kotestboilerplate.actions

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.IconLoader
import com.intellij.psi.PsiFile
import com.intellij.testIntegration.GotoTestOrCodeHandler
import javax.swing.Icon

class GotoTestBoilerPlateHandler : GotoTestOrCodeHandler() {
    override fun getSourceAndTargetElements(editor: Editor?, file: PsiFile?): GotoData? {
        val sourceAndTargetElements = super.getSourceAndTargetElements(editor, file) ?: return null
        sourceAndTargetElements.additionalActions.add(0, object : AdditionalAction {
            override fun getText(): String {
                return "Create KoTest BoilerTemplate"
            }

            override fun getIcon(): Icon {
                return IconLoader.getIcon("/icons/kotest.png")
            }

            override fun execute() {
                println("Hello")
                println("Execute Create Test")
            }
        })

        return sourceAndTargetElements
    }

}
