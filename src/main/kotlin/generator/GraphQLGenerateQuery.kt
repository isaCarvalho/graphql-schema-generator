package generator

import annotations.GraphQLQuery
import graphqltypes.GraphQLNativeTypes
import reflections.ReflectClass
import reflections.ReflectMethod
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.jvmErasure

object GraphQLGenerateQuery {
    fun generate(
        queryInterface: KClass<*>,
        entity: KClass<*>,
    ): String {
        val reflectClass = ReflectClass(queryInterface)
        val reflectEntity = ReflectClass(entity)

        val isQuery = queryInterface.findAnnotation<GraphQLQuery>() != null

        var query =
            if (isQuery) {
                "type Query {"
            } else {
                "type Mutation {"
            }

        reflectClass.methods.forEach {
            query += generateSubQuery(it, reflectEntity)
        }

        return "$query}\n"
    }

    private fun generateSubQuery(
        method: ReflectMethod,
        entity: ReflectClass,
    ): String {
        val schemaName = "${entity.schemaName}!"
        var subQuery = "\n\t${method.name}"

        subQuery +=
            if (method.parameters.isNotEmpty()) {
                val inputClass = method.parameters[0].type.jvmErasure
                val inputType = GraphQLNativeTypes.convertType(inputClass)

                "(input: $inputType!): $schemaName\n"
            } else {
                ": [$schemaName]!\n"
            }

        return subQuery
    }
}
