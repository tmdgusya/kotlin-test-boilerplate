package com.woowa.kotestboilerplate.core.generator

import com.woowa.kotestboilerplate.core.builder.SupportKotestSpec
import com.woowa.kotestboilerplate.core.builder.TestBuilderConfig
import com.woowa.kotestboilerplate.parser.KotlinClassMetaData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class TestCodeGeneratorTest : FunSpec({
    val mockMetaData = mockk<KotlinClassMetaData>()
    val mockTestConfig = mockk<TestBuilderConfig>()

    test("FreeSpec 이 들어왔을때 FreeSpecGenerator Class 를 리턴한다.") {
        // given
        val spec = SupportKotestSpec.FreeSpec

        // when
        val result = TestCodeGeneratorFactory.create(spec, mockMetaData, mockTestConfig)

        // then
        result::class shouldBe FreeSpecGenerator::class
    }

    test("FunSpec 이 들어왔을때 FunSpecGenerator Class 를 리턴한다.") {
        // given
        val spec = SupportKotestSpec.FunSpec

        // when
        val result = TestCodeGeneratorFactory.create(spec, mockMetaData, mockTestConfig)

        // then
        result::class shouldBe FunSpecGenerator::class
    }

    test("BehaviorSpec 이 들어왔을때 BehaviourSpecGenerator Class 를 리턴한다.") {
        // given
        val spec = SupportKotestSpec.BehaviorSpec

        // when
        val result = TestCodeGeneratorFactory.create(spec, mockMetaData, mockTestConfig)

        // then
        result::class shouldBe BehaviourSpecGenerator::class
    }

})
