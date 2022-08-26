package com.woowa.kotestboilerplate.helper

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtFile

class CreateTestFileHelperTest: BehaviorSpec({

    given("If given className, and Fields that contains in class") {
        val fqName = mockk<FqName>(relaxed = true) {
            every { asString() } returns "com.woowa.kotestboilerplate.helper"
        }
        val ktFiles = mockk<KtFile>(relaxed = true) {
            every { packageFqName } returns fqName
        }
        val kotlinClassHelper = KotlinClassAnalyzeHelper(ktFiles)
        val helper = CreateTestFileHelper(kotlinClassHelper)
        `when`("execute getTestModuleURL()") {
            val testFileUrl = helper.getTestModuleURL()
            then("return test/kotlin/com/woowa/kotesetboilerplate/helper") {
                testFileUrl shouldBe "test/kotlin/com/woowa/kotestboilerplate/helper"
            }
        }
    }

})
