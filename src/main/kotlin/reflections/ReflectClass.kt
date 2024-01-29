package reflections

import annotations.GraphQLIgnore
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.functions

class ReflectClass(cls: KClass<*>) {
    val properties: List<ReflectProperty>

    val methods: List<ReflectMethod>

    var schemaName: String

    init {
        this.schemaName = cls.simpleName!!.uppercase()

        // setting the properties
        properties =
            cls.declaredMemberProperties.filter { property ->
                property.annotations.find { it is GraphQLIgnore } == null
            }.map { ReflectProperty(it) }

        methods =
            cls.functions.filter { method ->
                method.annotations.find { it is GraphQLIgnore } == null
            }.map { ReflectMethod(it) }
    }
}
