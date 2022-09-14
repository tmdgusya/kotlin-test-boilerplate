package com.woowa.kotestboilerplate.parser

import com.intellij.psi.PsiMethod

data class KotlinClassMetaData(
    val properties: List<KotlinField>,
    val className: String,
    val packageName: String,
    val properTestSourceDir: String? = null,
    val methods: Array<PsiMethod>,
)
