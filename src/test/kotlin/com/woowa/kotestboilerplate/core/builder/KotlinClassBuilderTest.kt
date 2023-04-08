package com.woowa.kotestboilerplate.core.builder

import com.woowa.kotestboilerplate.core.generator.BehaviourSpecGenerator
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData
import com.woowa.kotestboilerplate.parser.KotlinField
import com.woowa.kotestboilerplate.parser.KotlinType
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class KotlinClassBuilderTest : BehaviorSpec({

    val behaviourSpecGenerator = mockk<BehaviourSpecGenerator>(relaxed = true)

    given("given Class Name") {
        val mockPackageName = "com.woowa.kotestboilerplate"
        val mockClassName = "TestClass"
        val kotlinClassMetaData = mockk<KotlinClassMetaData> {
            every { className } returns mockClassName
            every { packageName } returns mockPackageName
            every { properties } returns emptyList()
        }
        val testConfig = TestBuilderConfig()
        val kotlinTestBuilder = KotlinPoetTestBuilder(kotlinClassMetaData, testConfig, behaviourSpecGenerator)
        val expected = """
            package $mockPackageName
            
            import io.kotest.core.spec.style.BehaviorSpec
            import io.mockk.mockk
            
            public class ${mockClassName}Test : BehaviorSpec({
            
            val sut: $mockClassName = ${mockClassName}()
            })
            
        """.trimIndent()
        `when`("when execute buildClass() ") {
            val classContet = kotlinTestBuilder.buildUnitTestClass()
            then("classContent is public class TestClass") {
                classContet shouldBe expected
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
        val testConfig = TestBuilderConfig()
        val kotlinTestBuilder = KotlinPoetTestBuilder(
            kotlinClassMetaData =  kotlinClassMetaData,
            testBuilderConfig = testConfig,
            behaviourSpecGenerator
        )
        val expected = """
            package com.woowa.kotestboilerplate

            import io.kotest.core.spec.style.BehaviorSpec
            import io.mockk.mockk

            public class TestClassTest : BehaviorSpec({
            val age: Int = mockk(relaxed = true)
            val sut: TestClass = TestClass(age)
            })
            
        """.trimIndent()
        `when`("when execute buildClass() ") {
            val classContent = kotlinTestBuilder.buildUnitTestClass()
            then("classContent is public class TestClass") {
                classContent shouldBe expected
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
        val testConfig = TestBuilderConfig(isRelaxed = true)
        val kotlinTestBuilder = KotlinPoetTestBuilder(
            kotlinClassMetaData =  kotlinClassMetaData,
            testBuilderConfig = testConfig,
            behaviourSpecGenerator
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
                        "val ages: List<Long> = mockk(relaxed = true)\n" +
                        "val sut: TestClass = TestClass(ages)\n" +
                        "})\n"
            }
        }
    }

    given("if selected relaxedMock is falsy") {
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
        val testConfig = TestBuilderConfig(isRelaxed = false)
        val kotlinTestBuilder = KotlinPoetTestBuilder(
            kotlinClassMetaData =  kotlinClassMetaData,
            testBuilderConfig = testConfig,
            behaviourSpecGenerator
        )
        val expected = """
            package com.woowa.kotestboilerplate

            import io.kotest.core.spec.style.BehaviorSpec
            import io.mockk.mockk

            public class TestClassTest : BehaviorSpec({
            val age: Int = mockk()
            val sut: TestClass = TestClass(age)
            })
            
        """.trimIndent()
        `when`("when execute buildClass() ") {
            val classContent = kotlinTestBuilder.buildUnitTestClass()
            then("The content that (relaxed = true) does not create") {
                classContent shouldBe expected
            }
        }
    }

})
