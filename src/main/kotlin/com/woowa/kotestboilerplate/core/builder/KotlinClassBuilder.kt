package com.woowa.kotestboilerplate.core.builder

typealias KotlinClassContent = String

interface KotlinClassBuilder {

    fun buildUnitTestClass(): KotlinClassContent

}
