package org.ryan.app.soc.common

import kotlin.reflect.full.memberProperties

// Natural Transformation 함수 정의
fun <A, B> A.transform(transform: (A) -> B): B = transform(this)

// 리플렉션을 사용하여 자동으로 필드를 매핑하는 함수
inline fun <reified A : Any, reified B : Any> A.convertTo(): B {
    val propertiesByName = A::class.memberProperties.associateBy { it.name }
    val constructor = B::class.constructors.first()

    val args = constructor.parameters.map { parameter ->
        val property = propertiesByName[parameter.name]
        property?.get(this)
    }
    return constructor.call(*args.toTypedArray())
}
