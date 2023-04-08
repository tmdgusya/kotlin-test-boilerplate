package com.woowa.kotestboilerplate.core.builder

import com.woowa.kotestboilerplate.core.generator.BehaviourSpecGenerator
import com.woowa.kotestboilerplate.core.generator.TestCodeGenerator
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData
import com.woowa.kotestboilerplate.parser.KotlinField
import com.woowa.kotestboilerplate.parser.KotlinType
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class KotlinPoetTestBuilderTest : FunSpec({


    test("Give properties list that joined by comma") {
        // given
        val properties = listOf<KotlinField>(
            KotlinField("name", KotlinType("String", "kotlin")),
            KotlinField("age", KotlinType("Int", "kotlin")),
            KotlinField("address", KotlinType("String", "kotlin")),
        )
        val expected = properties.joinToString(", ") { it.name }
        val sut = createSutFactory(
            KotlinClassMetaData(
                properties = properties,
                className = "Test",
                packageName = "com.woowa.kotestboilerplate",
                methods = emptyArray(),
            )
        )

        // when && then
        sut.stringConstructorArgs() shouldBe expected
    }
})

private fun createSutFactory(with: KotlinClassMetaData): KotlinPoetTestBuilder {
    val testBuilderConfig: TestBuilderConfig = TestBuilderConfig(
        spec = SupportKotestSpec.BehaviorSpec,
        isNeedMethod = false,
        isRelaxed = true,
    )
    val testCodeGenerator: TestCodeGenerator = BehaviourSpecGenerator()
    return KotlinPoetTestBuilder(with, testBuilderConfig, testCodeGenerator)
}
