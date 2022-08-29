package com.woowa.kotestboilerplate.actions

import com.intellij.codeInsight.CodeInsightActionHandler
import com.intellij.testIntegration.GotoTestOrCodeAction

class GotoTestOrCodeActionExtenstion : GotoTestOrCodeAction() {
    override fun getHandler(): CodeInsightActionHandler {
        return GotoTestBoilerPlateHandler()
    }
}
