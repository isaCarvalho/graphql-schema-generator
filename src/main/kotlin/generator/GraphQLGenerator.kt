package generator

import annotations.GraphQLMutation
import annotations.GraphQLQuery
import annotations.GraphQLSchema
import com.sun.org.slf4j.internal.LoggerFactory
import exception.SaveGraphQLSchemaFileException
import java.io.File
import kotlin.reflect.KClass

object GraphQLGenerator {
    private val logger = LoggerFactory.getLogger(GraphQLGenerator::class.java)

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

            val file =
                File("./graphql/schema.graphql").also {
                    if (!it.exists()) {
                        it.createNewFile()
                    }

                    it.writeText(schema.trim())
                }

            logger.trace("Schema saved to ${file.absolutePath}")
        } catch (ex: Exception) {
            logger.error("Error saving file", ex)
            throw SaveGraphQLSchemaFileException("Error saving file", ex)
        }
    }
}
