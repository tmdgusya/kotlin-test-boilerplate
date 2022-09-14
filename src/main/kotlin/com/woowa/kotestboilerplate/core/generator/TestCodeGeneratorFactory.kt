package com.woowa.kotestboilerplate.core.generator

import com.woowa.kotestboilerplate.core.builder.SupportKotestSpec
import com.woowa.kotestboilerplate.core.builder.TestBuilderConfig
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData

object TestCodeGeneratorFactory {
    fun create(
        supportSpec: SupportKotestSpec,
        kotlinClassMetaData: KotlinClassMetaData,
        testConfig: TestBuilderConfig
    ): TestCodeGenerator {
        return when (supportSpec) {
            SupportKotestSpec.BehaviorSpec -> BehaviourSpecGenerator()
            SupportKotestSpec.FreeSpec -> FreeSpecGenerator(kotlinClassMetaData, testConfig)
            SupportKotestSpec.FunSpec -> FunSpecGenerator()
        }
    }
}
