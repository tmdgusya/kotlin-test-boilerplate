package com.woowa.kotestboilerplate.core.builder

import com.woowa.kotestboilerplate.parser.KotlinClassMetaData
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class KotlinClassBuilderTest : BehaviorSpec({

    given("given Class Name") {
        val mockPackageName = "com.woowa.kotestboilerplate"
        val mockClassName = "TestClass"
        val kotlinClassMetaData = mockk<KotlinClassMetaData> {
            every { className } returns mockClassName
            every { packageName } returns mockPackageName
        }
        val kotlinTestBuilder = KotlinPoetTestBuilder(kotlinClassMetaData)
        `when`("when execute buildClass() ") {
            val classContet = kotlinTestBuilder.buildClass()
            then("classContent is public class TestClass") {
                classContet shouldBe "package ${mockPackageName}\n" +
                        "\n" +
                        "public class ${mockClassName}Test\n"
            }
        }
    }

})