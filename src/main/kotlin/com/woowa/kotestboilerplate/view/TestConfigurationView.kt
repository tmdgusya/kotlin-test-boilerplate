package com.woowa.kotestboilerplate.view

import com.intellij.openapi.application.WriteActionAware
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiElement
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.panel
import com.woowa.kotestboilerplate.core.UnitTestCreator
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
            checkBox("generate with relaxed mock").applyToComponent {
                isSelected = true
                addItemListener {
                    if (it.stateChange == ItemEvent.SELECTED) {
                        configure.isRelaxed = it.stateChange.isSelected()
                    }
                }
            }.enabled(true)
        }
        row {
            isMethodNeedCheckBox = checkBox("method mock(only FreeSpec)").applyToComponent {
                addItemListener {
                    if (it.stateChange == ItemEvent.SELECTED) {
                        configure.isNeedMethod = !configure.isNeedMethod
                    }
                }
            }.enabled(false)
        }
    }
    return panel
}

private fun Int.isSelected(): Boolean {
    return this == 1
}

class TestUiDslDialog(
    private val project: Project?,
    private val editor: Editor?,
    private val element: PsiElement,
    private val testConfig: TestBuilderConfig
) : DialogWrapper(project, true), WriteActionAware {

    init {
        title = "KoTest Builder"
        init()
    }

    override fun startInWriteAction(): Boolean {
        return false
    }

    override fun doOKAction() {
        val kotestCreator = UnitTestCreator()
        kotestCreator.createTestClass(
            project = project!!,
            editor = editor ?: throw IllegalAccessException(),
            element = element,
            testConfig = testConfig
        )
        super.doOKAction()
    }

    override fun createCenterPanel(): JComponent {
        return testConfigurationViewer(testConfig)
    }
}
