package com.woowa.kotestboilerplate.fixture

import com.woowa.kotestboilerplate.core.builder.KotlinPoetTestBuilder
import com.woowa.kotestboilerplate.core.builder.SupportKotestSpec
import com.woowa.kotestboilerplate.core.builder.TestBuilderConfig
import com.woowa.kotestboilerplate.core.generator.BehaviourSpecGenerator
import com.woowa.kotestboilerplate.core.generator.TestCodeGenerator
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData

object Fixture {
    fun createSutFactory(
        metaData: KotlinClassMetaData,
        testBuilderConfig: TestBuilderConfig = TestBuilderConfig(
            spec = SupportKotestSpec.BehaviorSpec,
            isNeedMethod = false,
            isRelaxed = true,
        ),
        testCodeGenerator: TestCodeGenerator = BehaviourSpecGenerator()
    ): KotlinPoetTestBuilder {
        return KotlinPoetTestBuilder(
            kotlinClassMetaData = metaData,
            testBuilderConfig = testBuilderConfig,
            testCodeGenerator = testCodeGenerator
        )
    }
}
