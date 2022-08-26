package com.woowa.kotestboilerplate.helper

class CreateTestFileHelper(
    private val kotlinClassAnalyzeHelper: KotlinClassAnalyzeHelper
) {
    fun getTestModuleURL(): String {
        val sourcePackagePath = kotlinClassAnalyzeHelper.getPackagePath()
        return "$TEST_PACKAGE_ROOT${sourcePackagePathToRealFilePath(sourcePackagePath)}"
    }

    private fun sourcePackagePathToRealFilePath(path: String): String {
        return path.replace(".", "/")
    }

    companion object {
        const val TEST_PACKAGE_ROOT = "test/kotlin/"
    }
}
