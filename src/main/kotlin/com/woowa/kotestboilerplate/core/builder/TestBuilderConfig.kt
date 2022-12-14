package com.woowa.kotestboilerplate.core.builder

data class TestBuilderConfig(
    var spec: SupportKotestSpec = SupportKotestSpec.BehaviorSpec,
    var isNeedMethod: Boolean = false,
    var isRelaxed: Boolean = true,
)

enum class SupportKotestSpec {
    FunSpec,
    FreeSpec,
    BehaviorSpec,
    ;
}
