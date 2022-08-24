package com.woowa.kotestboilerplate.utils

import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.psi.KtFile

object FileDescriptor {
    fun convertKotlinFile(psiFile: PsiFile): KtFile {
        if (psiFile::class != KtFile::class) {
            throw IllegalAccessException("You should use in kotlin file")
        }

        return psiFile as KtFile
    }
}