package com.woowa.kotestboilerplate.core

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClassOwner
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.impl.source.codeStyle.JavaCodeStyleManagerImpl
import com.woowa.kotestboilerplate.core.builder.KotlinPoetTestBuilder
import com.woowa.kotestboilerplate.core.generator.TestDirectoryGenerator
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData
import com.woowa.kotestboilerplate.parser.KotlinClassParserImpl
import com.woowa.kotestboilerplate.utils.FileDescriptor

class UnitTestCreator : KotestCreator {
    override fun createTestClass(project: Project, editor: Editor, element: PsiElement) {
        val file = element.containingFile
        val kotlinClassParserImpl = KotlinClassParserImpl(
            ktFile = FileDescriptor.convertKotlinFile(file)
        )
        val containClass = kotlinClassParserImpl.getClass()
        val srcModule = ModuleUtilCore.findModuleForFile(element.containingFile)

        // build to testFile Structure
        val owner = file as PsiClassOwner
        val testFileResource = KotlinPoetTestBuilder(
            kotlinClassMetaData = KotlinClassMetaData(
                properties = kotlinClassParserImpl.getProperties(),
                className = containClass.name ?: throw IllegalArgumentException(""),
                packageName = owner.packageName,
            )
        ).buildUnitTestClass()

        val testDirectory = TestDirectoryGenerator(
            project = project,
            srcModule = srcModule ?: throw IllegalArgumentException(""),
            currentlyDirectoryInfo = kotlinClassParserImpl.getDirectoryAndPackage()
        ).createDirectoryIfNotExist()

        // createTestFile
        val psiFileFactory = PsiFileFactory.getInstance(project)
        val testFile = psiFileFactory.createFileFromText(
            "${containClass.name}Test.kt",
            KotlinFileType(),
            testFileResource
        )

        JavaCodeStyleManagerImpl(project).optimizeImports(testFile)

        testDirectory.add(testFile)
    }
}
