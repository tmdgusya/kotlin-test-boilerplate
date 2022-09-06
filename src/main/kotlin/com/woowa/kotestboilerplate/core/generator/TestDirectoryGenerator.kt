package com.woowa.kotestboilerplate.core.generator

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager
import com.intellij.testIntegration.createTest.CreateTestAction
import org.jetbrains.kotlin.idea.core.util.toPsiDirectory
import java.io.File

class TestDirectoryGenerator(
    private val project: Project,
    private val srcModule: Module,
    private val currentlyDirectoryInfo: String
) {

    fun createDirectoryIfNotExist(): PsiDirectory {
        val testModule = CreateTestAction.suggestModuleForTests(
            project,
            srcModule
        )
        // the properties that need to create testFile
        val testRoot = ModuleRootManager.getInstance(testModule)
        val psiManager = PsiManager.getInstance(project)

        val testModuleRoot = testRoot.sourceRoots
            .filter { it.name.contains("kotlin") }
            .firstNotNullOfOrNull {
            psiManager.findDirectory(it)
        } ?: throw IllegalArgumentException("Test Module doesn't exist")
        val testDirectoryPath = createTestDirectoryPath(testModuleRoot)

        val testDirectory = LocalFileSystem.getInstance().findFileByIoFile(File(testDirectoryPath))
            ?: VfsUtil.createDirectories(testDirectoryPath)

        return testDirectory.toPsiDirectory(project) ?:
            throw IllegalArgumentException("Test Directory can not convert psiDirectory Type")
    }

    private fun createTestDirectoryPath(testModuleRoot: PsiDirectory) =
        (testModuleRoot.virtualFile.path + "." + currentlyDirectoryInfo).replace(".", "/")
}
