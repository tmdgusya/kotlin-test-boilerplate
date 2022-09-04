package com.woowa.kotestboilerplate.core.builder

import com.woowa.kotestboilerplate.parser.KotlinClassMetaData
import com.woowa.kotestboilerplate.parser.KotlinField
import com.woowa.kotestboilerplate.parser.KotlinType
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
            every { properties } returns emptyList()
        }
        val kotlinTestBuilder = KotlinPoetTestBuilder(kotlinClassMetaData)
        `when`("when execute buildClass() ") {
            val classContet = kotlinTestBuilder.buildUnitTestClass()
            then("classContent is public class TestClass") {
                classContet shouldBe "package ${mockPackageName}\n" +
                        "\n" +
                        "import io.kotest.core.spec.style.BehaviorSpec\n" +
                        "import io.mockk.mockk\n" +
                        "\n" +
                        "public class ${mockClassName}Test : BehaviorSpec({\n})\n" +
                        ""
            }
        }
    }

    given("given Class Name And Property") {
        val mockPackageName = "com.woowa.kotestboilerplate"
        val mockClassName = "TestClass"
        val mockType = "Int"
        val mockFieldName = "age"
        val mockKotlinType = mockk<KotlinType>(relaxed = true) {
            every { simpleName } returns mockType
            every { fqName } returns ""
            every { wrappedType } returns null
        }
        val mockKotlinField = mockk<KotlinField>(relaxed = true) {
            every { name } returns mockFieldName
            every { type } returns mockKotlinType
        }
        val kotlinClassMetaData = mockk<KotlinClassMetaData> {
            every { className } returns mockClassName
            every { packageName } returns mockPackageName
            every { properties } returns listOf(mockKotlinField)
        }
        val kotlinTestBuilder = KotlinPoetTestBuilder(
            kotlinClassMetaData =  kotlinClassMetaData,
        )
        `when`("when execute buildClass() ") {
            val classContent = kotlinTestBuilder.buildUnitTestClass()
            then("classContent is public class TestClass") {
                classContent shouldBe "package ${mockPackageName}\n" +
                        "\n" +
                        "import io.kotest.core.spec.style.BehaviorSpec\n" +
                        "import io.mockk.mockk\n" +
                        "\n" +
                        "public class ${mockClassName}Test : " +
                        "BehaviorSpec({\nval $mockFieldName: $mockType = mockk<>(relaxed = true)\n" +
                        "})\n"
            }
        }
    }

    given("given Class Name And Parameter with TypeParameter") {
        val mockPackageName = "com.woowa.kotestboilerplate"
        val mockClassName = "TestClass"
        val mockType = "List"

        val wrappertType = "Long"
        val mockFieldName = "ages"
        val mockWrappetKotlinType = mockk<KotlinType>(relaxed = true) {
            every { simpleName } returns wrappertType
            every { fqName } returns ""
        }
        val mockKotlinType = mockk<KotlinType>(relaxed = true) {
            every { simpleName } returns mockType
            every { fqName } returns ""
            every { wrappedType } returns mockWrappetKotlinType
        }
        val mockKotlinField = mockk<KotlinField>(relaxed = true) {
            every { name } returns mockFieldName
            every { type } returns mockKotlinType
        }
        val kotlinClassMetaData = mockk<KotlinClassMetaData> {
            every { className } returns mockClassName
            every { packageName } returns mockPackageName
            every { properties } returns listOf(mockKotlinField)
        }
        val kotlinTestBuilder = KotlinPoetTestBuilder(
            kotlinClassMetaData =  kotlinClassMetaData,
        )
        `when`("when execute buildClass() ") {
            val classContent = kotlinTestBuilder.buildUnitTestClass()
            then("classContent is public class TestClass") {
                classContent shouldBe "package com.woowa.kotestboilerplate\n" +
                        "\n" +
                        "import io.kotest.core.spec.style.BehaviorSpec\n" +
                        "import io.mockk.mockk\n" +
                        "\n" +
                        "public class TestClassTest : BehaviorSpec({\n" +
                        "val ages: List<Long> = mockk<>(relaxed = true)\n" +
                        "})\n"
            }
        }
    }

})
