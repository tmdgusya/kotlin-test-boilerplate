package com.woowa.kotestboilerplate.helper

import com.woowa.kotestboilerplate.parser.KotlinClassParser

class CreateTestFileHelper(
    private val kotlinClassParser: KotlinClassParser
) {
    fun getTestModuleURL(): String {
        val sourcePackagePath = kotlinClassParser.getPackagePath()
        return "$TEST_PACKAGE_ROOT${sourcePackagePathToRealFilePath(sourcePackagePath)}"
    }

    private fun sourcePackagePathToRealFilePath(path: String): String {
        return path.replace(".", "/")
    }

    companion object {
        const val TEST_PACKAGE_ROOT = "test/kotlin/"
    }
}
