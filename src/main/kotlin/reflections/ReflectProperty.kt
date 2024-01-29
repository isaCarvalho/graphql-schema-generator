package reflections

import annotations.GraphQLIgnore
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.jvmErasure

class ReflectProperty(property: KProperty1<*, *>) {
    val type = property.returnType.jvmErasure

    val name = property.name

    var graphQLIgnoreAnnotation: GraphQLIgnore? = null

    init {
        property.annotations.forEach {
            if (it is GraphQLIgnore) {
                graphQLIgnoreAnnotation = it
            }
        }
    }

    val nullable = property.returnType.isMarkedNullable

    fun getTypeConstructor() = type::class.constructors.first()
}
