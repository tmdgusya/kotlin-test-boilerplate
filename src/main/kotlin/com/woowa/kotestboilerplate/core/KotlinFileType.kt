package com.woowa.kotestboilerplate.core

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

class KotlinFileType : FileType {
    override fun getCharset(p0: VirtualFile, p1: ByteArray): String? {
        return null
    }

    override fun getDefaultExtension(): String {
        return ".kt"
    }

    override fun getIcon(): Icon? {
        return null
    }

    override fun getName(): String {
        return "Kotlin file"

    }

    override fun getDescription(): String {
        return "Kotlin source file"

    }

    override fun isBinary(): Boolean {
        return false
    }

    override fun isReadOnly(): Boolean {
        return false
    }
}