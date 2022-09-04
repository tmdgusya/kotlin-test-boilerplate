package com.woowa.kotestboilerplate.core.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData

open class KotlinPoetTestBuilder(
    private val kotlinClassMetaData: KotlinClassMetaData,
) : KotlinClassBuilder {

    override fun buildUnitTestClass(): KotlinClassContent {
        val fileBuilder =
            FileSpec.builder(kotlinClassMetaData.packageName, convertToTestFile())
                .addImport("io.mockk.mockk", "")
                .buildTestClass()
                .doImportProperties()

        return fileBuilder.build().toString()
    }

    private fun FileSpec.Builder.buildTestClass(): FileSpec.Builder {
        return addType(TypeSpec
            .classBuilder(convertClassName())
            .superclass(ClassName("io.kotest.core.spec.style", "BehaviorSpec"))
            .addSuperclassConstructorParameter(CodeBlock.of("{\n${addPropertiesToString()}}"))
            .build()
        )
    }

    /**
     * Import All Properties
     */
    private fun FileSpec.Builder.doImportProperties(): FileSpec.Builder {
        kotlinClassMetaData.properties.forEach {
            if (it.type.fqName.isNotEmpty()) addImport(it.type.fqName, "")
        }
        return this
    }

    private fun addPropertiesToString(): String {
        return kotlinClassMetaData.properties.map {
                PropertySpec.builder(
                    it.name, type = ClassName("", it.type.simpleName)
                )
                .initializer("mockk<${it.type.simpleName}>(relaxed = true)")
                .build()
        }.joinToString("")
    }
    private fun convertToTestFile(): String {
        return "${convertClassName()}.kt"
    }

    private fun convertClassName(): String {
        return "${kotlinClassMetaData.className}Test"
    }
}
