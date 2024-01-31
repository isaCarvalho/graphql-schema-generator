package generator

import annotations.GraphQLMutation
import annotations.GraphQLQuery
import annotations.GraphQLSchema
import exception.SaveGraphQLSchemaFileException
import filemanagement.GraphQLClassScanner
import java.io.File
import kotlin.reflect.KClass

object GraphQLGenerator {
    fun generate(packageName: String) {
        val graphQLScanner = GraphQLClassScanner(packageName)
        val classes = graphQLScanner.loadClasses()

        return generate(classes)
    }

    fun generate(vararg classes: KClass<*>) {
        generate(classes.toList())
    }

    fun generate(classes: List<KClass<*>>) {
        var fullQuery = ""

        classes.forEach {
            fullQuery += generateFullSchema(it)
        }

        saveToFile(fullQuery)
    }

    fun generate(clazz: KClass<*>) {
        val fullQuery = generateFullSchema(clazz)
        saveToFile(fullQuery)
    }

    private fun generateFullSchema(clazz: KClass<*>): String {
        var typeDef = ""
        var query = ""
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

        return typeDef + query
    }

    private fun saveToFile(schema: String) {
        try {
            val directory = File("./graphql")
            if (!directory.exists()) {
                directory.mkdir()
            }

            File("./graphql/schema.graphql").also {
                if (!it.exists()) {
                    it.createNewFile()
                }

                it.writeText(schema.trim())
            }
        } catch (ex: Exception) {
            throw SaveGraphQLSchemaFileException("Error saving file", ex)
        }
    }
}
