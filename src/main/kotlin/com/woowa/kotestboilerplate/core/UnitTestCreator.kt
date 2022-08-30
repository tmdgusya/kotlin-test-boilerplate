package com.woowa.kotestboilerplate.core

import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.ide.fileTemplates.FileTemplateUtil
import com.intellij.lang.Language
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.openapi.project.Project
import com.intellij.psi.JavaDirectoryService
import com.intellij.psi.PsiElement
import com.woowa.kotestboilerplate.helper.KotlinClassAnalyzeHelper
import com.woowa.kotestboilerplate.utils.FileDescriptor

class UnitTestCreator : KotestCreator {
    override fun createTestClass(project: Project, editor: Editor, element: PsiElement) {
        val file = element.containingFile
        val kotlinClassAnalyzeHelper = KotlinClassAnalyzeHelper(
            ktFile = FileDescriptor.convertKotlinFile(file)
        )
        val containClass = kotlinClassAnalyzeHelper.getClass()
        println(containClass.name)

        val srcDir = element.containingFile.containingDirectory
        println(srcDir)

        val srcModule = ModuleUtilCore.findModuleForFile(element.containingFile)

        val srcPackage = JavaDirectoryService.getInstance().getPackage(srcDir)
        println(srcPackage)

        JavaDirectoryService.getInstance().createClass(
                srcDir,
            "${containClass.name}Test"
        )

        val kotlinTemplate = FileTemplateManager.getInstance().getTemplate("Kotlin Class")
        println(kotlinTemplate.name)
        FileTemplateUtil.createFromTemplate(kotlinTemplate, "${containClass.name}Test", null, srcDir)
    }

}