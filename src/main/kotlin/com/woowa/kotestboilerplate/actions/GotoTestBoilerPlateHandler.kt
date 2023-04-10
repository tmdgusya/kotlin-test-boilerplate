package com.woowa.kotestboilerplate.actions

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.IconLoader
import com.intellij.psi.PsiFile
import com.intellij.testIntegration.GotoTestOrCodeHandler
import javax.swing.Icon

class GotoTestBoilerPlateHandler(
    private val createTestActionHandler: CreateTestActionHandler = CreateTestActionHandler()
) : GotoTestOrCodeHandler() {
    override fun getSourceAndTargetElements(editor: Editor?, file: PsiFile?): GotoData? {
        val sourceAndTargetElements = super.getSourceAndTargetElements(editor, file) ?: return null
        sourceAndTargetElements.additionalActions.add(0, object : AdditionalAction {
            override fun getText(): String {
                return "Create KoTest BoilerTemplate"
            }

            override fun getIcon(): Icon {
                return IconLoader.getIcon("/icons/kotest.png", this::class.java)
            }

            override fun execute() {
                createTestActionHandler.invoke(
                    file!!.project,
                    editor,
                    file
                )
            }
        })

        return sourceAndTargetElements
    }

}
