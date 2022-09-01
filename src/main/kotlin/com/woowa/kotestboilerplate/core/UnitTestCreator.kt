package com.woowa.kotestboilerplate.core

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.psi.PsiClassOwner
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.testIntegration.createTest.CreateTestAction
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.woowa.kotestboilerplate.helper.CreateTestFileHelper
import com.woowa.kotestboilerplate.helper.KotlinClassAnalyzeHelper
import com.woowa.kotestboilerplate.utils.FileDescriptor
import org.jetbrains.kotlin.idea.caches.project.isTestModule
import java.io.File

class UnitTestCreator : KotestCreator {
    override fun createTestClass(project: Project, editor: Editor, element: PsiElement) {
        val file = element.containingFile
        val kotlinClassAnalyzeHelper = KotlinClassAnalyzeHelper(
            ktFile = FileDescriptor.convertKotlinFile(file)
        )
        val containClass = kotlinClassAnalyzeHelper.getClass()

        val srcModule = ModuleUtilCore.findModuleForFile(element.containingFile)

        val testModule = CreateTestAction.suggestModuleForTests(
            project,
            srcModule ?: throw IllegalArgumentException("")
        )

        // build to testFile Structure
        val owner = file as PsiClassOwner
        val result = FileSpec
            .builder(owner.packageName, "${containClass.name}Test.kt")
            .addType(TypeSpec.classBuilder("${containClass.name}Test").primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("name", String::class)
                    .build()
                )
                .addProperty(
                    PropertySpec.builder("name", String::class)
                        .initializer("name")
                        .build()
                )
                .addFunction(
                    FunSpec.builder("greet")
                        .addStatement("println(%P)", "Hello, \$name")
                        .build()
                )
                .build())
            .build()
            .toString()


        // the properties that need to create testFile
        val testRoot = ModuleRootManager.getInstance(testModule)
        val directory = testRoot.sourceRoots.firstNotNullOfOrNull { PsiManager.getInstance(project).findDirectory(it) }
        val psiFileFactory = PsiFileFactory.getInstance(project)

        // createTestFile
        val testFile = psiFileFactory.createFileFromText("${containClass.name}Test.kt", KotlinFileType(), result)
        directory?.add(testFile)
    }
}