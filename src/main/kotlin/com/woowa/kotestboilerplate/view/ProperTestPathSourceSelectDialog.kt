package com.woowa.kotestboilerplate.view

import com.intellij.openapi.ui.DialogWrapper
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class ProperTestPathSourceSelectDialog(
    private val properTestModuleURL: String
): DialogWrapper(true) {

    init {
        title = "TestSource"
        init()
    }

    override fun createCenterPanel(): JComponent {
        val dialogPanel = JPanel(BorderLayout())

        val label = JLabel(properTestModuleURL)
        dialogPanel.add(label)

        return dialogPanel
    }

}
