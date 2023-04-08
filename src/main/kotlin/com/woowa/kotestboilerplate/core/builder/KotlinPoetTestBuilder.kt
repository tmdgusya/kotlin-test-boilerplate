package com.woowa.kotestboilerplate.core.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.woowa.kotestboilerplate.core.generator.TestCodeGenerator
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData
import com.woowa.kotestboilerplate.parser.KotlinType

open class KotlinPoetTestBuilder(
    private val kotlinClassMetaData: KotlinClassMetaData,
    private val testBuilderConfig: TestBuilderConfig,
    private val testCodeGenerator: TestCodeGenerator,
) : KotlinClassBuilder {

    override fun buildUnitTestClass(): KotlinClassContent {
        val fileBuilder =
            FileSpec.builder(kotlinClassMetaData.packageName, convertToTestFile())
                .addImport("io.mockk.mockk", "")
                .buildTestClass()
                .importProperties()

        return fileBuilder.build().toString()
    }

    private fun FileSpec.Builder.buildTestClass(): FileSpec.Builder {
        return addType(TypeSpec
            .classBuilder(convertClassName())
            .superclass(ClassName("io.kotest.core.spec.style", testBuilderConfig.spec.name))
            .addSuperclassConstructorParameter(
                CodeBlock.of(
                    """
                    {
                    ${addPropertiesToString()}
                    val sut: ${kotlinClassMetaData.className} = ${kotlinClassMetaData.className}(${stringConstructorArgs()})
                    ${testCodeGenerator.buildCodeBlock()}}
                    """.trimIndent()
                )
            )
            .build()
        )
    }

    /**
     * Import All Properties
     */
    private fun FileSpec.Builder.importProperties(): FileSpec.Builder {
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
                .initializeRelaxedMock(testBuilderConfig.isRelaxed)
                .build()
        }.joinToString("").trimIndent()
    }

    private fun PropertySpec.Builder.initializeRelaxedMock(isRelaxed: Boolean): PropertySpec.Builder {
        if (isRelaxed) this.initializer("mockk(relaxed = true)") else this.initializer("mockk()")
        return this
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

    fun stringConstructorArgs(): String {
        return kotlinClassMetaData.properties.joinToString(", ") { it.name }
    }
}
