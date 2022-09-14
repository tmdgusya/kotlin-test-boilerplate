package com.woowa.kotestboilerplate.core.generator

import com.woowa.kotestboilerplate.core.builder.TestBuilderConfig
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData

class FreeSpecGenerator(
    private val kotlinClassMetaData: KotlinClassMetaData,
    private val testConfig: TestBuilderConfig
) : TestCodeGeneratorType(), TestCodeGenerator {
    override fun buildCodeBlock(): String {
        if (!testConfig.isNeedMethod) return """ "" - { }"""
        val methods = kotlinClassMetaData.methods
        val builder = StringBuilder()
        methods.forEach { builder.append("""
    "${it.name}" - { }
""") }

        return builder.toString()
    }
}
