package com.woowa.kotestboilerplate.core.builder

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData

class KotlinPoetTestBuilder(
    private val kotlinClassMetaData: KotlinClassMetaData
) : KotlinClassBuilder {

    override fun buildClass(): KotlinClassContent {
        val fileBuilder = FileSpec
            .builder(kotlinClassMetaData.packageName, convertToTestFile())
            .addType(TypeSpec.classBuilder(convertClassName()).build())

        return fileBuilder.build().toString()
    }

    private fun convertToTestFile(): String {
        return "${convertClassName()}.kt"
    }

    private fun convertClassName(): String {
        return "${kotlinClassMetaData.className}Test"
    }
}