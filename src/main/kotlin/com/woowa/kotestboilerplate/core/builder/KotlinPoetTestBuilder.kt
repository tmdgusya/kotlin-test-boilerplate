package com.woowa.kotestboilerplate.core.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData
import com.woowa.kotestboilerplate.parser.KotlinType

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
                    it.name,
                    type = propertyTypeBuild(it.type).addTypeParameterIfIsWrapperType(it.type)
                )
                .initializer("mockk<>(relaxed = true)")
                .build()
        }.joinToString("")
    }

    private fun propertyTypeBuild(type: KotlinType): ClassName {
        return ClassName("", type.simpleName)
    }

    private fun ClassName.addTypeParameterIfIsWrapperType(type: KotlinType): TypeName {
        if (type.wrappedType != null) {
            return this.plusParameter(ClassName("", type.wrappedType.simpleName))
        }
        return this
    }

    private fun convertToTestFile(): String {
        return "${convertClassName()}.kt"
    }

    private fun convertClassName(): String {
        return "${kotlinClassMetaData.className}Test"
    }
}
