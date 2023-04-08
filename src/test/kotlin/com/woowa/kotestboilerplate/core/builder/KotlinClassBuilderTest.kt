package com.woowa.kotestboilerplate.core.builder

import com.woowa.kotestboilerplate.core.generator.BehaviourSpecGenerator
import com.woowa.kotestboilerplate.fixture.Fixture
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData
import com.woowa.kotestboilerplate.parser.KotlinField
import com.woowa.kotestboilerplate.parser.KotlinType
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class KotlinClassBuilderTest : BehaviorSpec({

    val mockPackageName = "com.woowa.kotestboilerplate"
    val mockClassName = "TestClass"
    val behaviourSpecGenerator = mockk<BehaviourSpecGenerator>(relaxed = true)

    given("given Class Name") {

        val kotlinClassMetaData = KotlinClassMetaData(
            properties = listOf(),
            className = mockClassName,
            packageName = mockPackageName,
            properTestSourceDir = null,
            methods = emptyArray(),
        )
        val testConfig = TestBuilderConfig()
        val kotlinTestBuilder = Fixture.createSutFactory(
            metaData = kotlinClassMetaData,
            testBuilderConfig = testConfig,
            testCodeGenerator = behaviourSpecGenerator,
        )
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
        val mockType = "Int"
        val mockFieldName = "age"
        val kotlinClassMetaData = KotlinClassMetaData(
            properties = listOf(
                KotlinField(
                    name = mockFieldName,
                    type = KotlinType(
                        simpleName = mockType,
                        fqName = "",
                        wrappedType = null,
                    )
                )
            ),
            className = mockClassName,
            packageName = mockPackageName,
            properTestSourceDir = null,
            methods = emptyArray(),
        )
        val testConfig = TestBuilderConfig()
        val kotlinTestBuilder = Fixture.createSutFactory(
            metaData = kotlinClassMetaData,
            testBuilderConfig = testConfig,
            testCodeGenerator = behaviourSpecGenerator,
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
        val wrapperType = "List"
        val mockType = "Long"
        val mockFieldName = "ages"
        val kotlinClassMetaData = KotlinClassMetaData(
            properties = listOf(
                KotlinField(
                    name = mockFieldName,
                    type = KotlinType(
                        simpleName = wrapperType,
                        fqName = "",
                        wrappedType = KotlinType(simpleName = mockType, fqName = "", wrappedType = null),
                    )
                )
            ),
            className = mockClassName,
            packageName = mockPackageName,
            properTestSourceDir = null,
            methods = emptyArray(),
        )
        val testConfig = TestBuilderConfig(isRelaxed = true)
        val kotlinTestBuilder = Fixture.createSutFactory(
            metaData = kotlinClassMetaData,
            testBuilderConfig = testConfig,
            testCodeGenerator = behaviourSpecGenerator,
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
        val mockType = "Int"
        val mockFieldName = "age"
        val kotlinClassMetaData = KotlinClassMetaData(
            properties = listOf(
                KotlinField(
                    name = mockFieldName,
                    type = KotlinType(
                        simpleName = mockType,
                        fqName = "",
                        wrappedType = null,
                    )
                )
            ),
            className = mockClassName,
            packageName = mockPackageName,
            properTestSourceDir = null,
            methods = emptyArray(),
        )
        val testConfig = TestBuilderConfig(isRelaxed = false)
        val kotlinTestBuilder = Fixture.createSutFactory(
            metaData = kotlinClassMetaData,
            testBuilderConfig = testConfig,
            testCodeGenerator = behaviourSpecGenerator,
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
