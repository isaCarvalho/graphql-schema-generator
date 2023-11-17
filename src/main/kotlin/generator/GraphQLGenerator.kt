package generator

import annotations.GraphQLMutation
import annotations.GraphQLQuery
import annotations.GraphQLSchema
import kotlin.reflect.KClass

object GraphQLGenerator {

    fun generate(classes: List<KClass<*>>): String {
        var typeDef = ""
        var query = ""
        classes.forEach { clazz ->
            if (clazz.annotations.find { it is GraphQLSchema } != null) {
                typeDef += GraphQLGenerateSchema.generate(clazz) + "\n"
            }

            val graphQLQueryAnnotation = clazz.annotations.find { it is GraphQLQuery } as GraphQLQuery?
            if (graphQLQueryAnnotation != null) {
                val entity = graphQLQueryAnnotation.entity

                query += GraphQLGenerateQuery.generate(clazz, entity) + "\n"
            }

            val graphQLMutationAnnotation = clazz.annotations.find { it is GraphQLMutation } as GraphQLMutation?
            if (graphQLMutationAnnotation != null) {
                val entity = graphQLMutationAnnotation.entity

                query += GraphQLGenerateQuery.generate(clazz, entity) + "\n"
            }
        }

        return typeDef + query
    }
}