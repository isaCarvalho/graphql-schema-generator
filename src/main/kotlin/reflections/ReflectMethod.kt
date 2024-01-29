package reflections

import annotations.GraphQLIgnore
import kotlin.reflect.KFunction
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.jvmErasure

class ReflectMethod(method: KFunction<*>) {
    val type = method.returnType.jvmErasure

    val name = method.name

    var graphQLIgnoreAnnotation: GraphQLIgnore? = null

    val parameters = method.valueParameters

    init {
        method.annotations.forEach {
            if (it is GraphQLIgnore) {
                graphQLIgnoreAnnotation = it
            }
        }
    }
}
