package com.woowa.kotestboilerplate.utils

import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.psi.KtFile

object FileDescriptor {
    /**
     * @param psiFile is extract by psiManager when executed method
     *
     * @throws IllegalAccessException if file type is not kotlin file type
     * @return KtFile (The file Type must be Kotlin File)
     */
    fun convertKotlinFile(psiFile: PsiFile): KtFile {
        if (psiFile::class != KtFile::class) {
            throw IllegalAccessException("You should use in kotlin file")
        }

        return psiFile as KtFile
    }
}
