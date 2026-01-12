package com.mtv.app.core.provider.utils

import kotlin.collections.get
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

@Suppress("UNCHECKED_CAST")
fun <T : Any> T.toMap(): Map<String, Any> {
    return this::class.memberProperties.associate { prop ->
        val kProp = prop as KProperty1<T, *>
        val value = kProp.get(this) ?: ""
        prop.name to value
    }
}

fun <T : Any> Map<String, Any?>.safeToDataClass(clazz: Class<T>): T {
    val kClass = clazz.kotlin
    val constructor = kClass.primaryConstructor!!

    val args = constructor.parameters.associateWith { param ->
        this[param.name]
    }

    return constructor.callBy(args)
}
