package com.woowa.kotestboilerplate.parser

import kotlin.reflect.KClass

data class KotlinType(
    var simpleName: String,
    val fqName: String
) {
    companion object {

        private const val InvalidFileExceptionMessage = "The File is Not Invalid File"

        fun of(type: KClass<out Any>): KotlinType {
            return KotlinType(
                simpleName = type.simpleName ?: throw IllegalAccessException(InvalidFileExceptionMessage),
                fqName = type.qualifiedName ?: throw IllegalAccessException(InvalidFileExceptionMessage)
            )
        }
    }
}
