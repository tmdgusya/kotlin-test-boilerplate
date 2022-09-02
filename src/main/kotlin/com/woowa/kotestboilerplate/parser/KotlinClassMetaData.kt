package com.woowa.kotestboilerplate.parser

data class KotlinClassMetaData(
    val properties: List<KotlinField>,
    val className: String,
    val packageName: String,
    val properTestSourceDir: String,
)