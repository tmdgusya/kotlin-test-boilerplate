package com.woowa.kotestboilerplate.view

import com.intellij.openapi.application.WriteActionAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.panel
import com.woowa.kotestboilerplate.core.builder.SupportKotestSpec
import com.woowa.kotestboilerplate.core.builder.TestBuilderConfig
import java.awt.event.ItemEvent
import javax.swing.JCheckBox
import javax.swing.JComponent

fun testConfigurationViewer(configure: TestBuilderConfig): DialogPanel {
    lateinit var isMethodNeedCheckBox: Cell<JCheckBox>
    val panel = panel {
        row {
            dropDownLink(
                item = configure.spec,
                items = listOf(
                    SupportKotestSpec.FunSpec,
                    SupportKotestSpec.FreeSpec,
                    SupportKotestSpec.BehaviorSpec
                ),
                onSelected = {
                    configure.spec = it

                    if (it == SupportKotestSpec.FreeSpec) {
                        isMethodNeedCheckBox.enabled(true)
                    } else {
                        isMethodNeedCheckBox.enabled(false)
                    }
                },
                updateText = true
            )
        }
        row {
            isMethodNeedCheckBox = checkBox("method mock(only FreeSpec)").applyToComponent {
                addItemListener {
                    if (it.stateChange == ItemEvent.SELECTED) {
                        configure.isNeedMethod = true
                    }
                }
            }.enabled(false)
        }
    }
    return panel
}

class TestUiDslDialog(
    project: Project?,
    private val configure: TestBuilderConfig
) : DialogWrapper(project, true), WriteActionAware {

    init {
        title = "KoTest Builder"
        init()
    }

    override fun startInWriteAction(): Boolean {
        return false
    }

    override fun createCenterPanel(): JComponent {
        return testConfigurationViewer(configure)
    }
}
