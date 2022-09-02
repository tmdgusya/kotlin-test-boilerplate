package com.woowa.kotestboilerplate.parser

interface KotlinClassParser {

    fun getProperties(): List<KotlinField>

    fun getClassName(): String

    fun getPackagePath(): String

}