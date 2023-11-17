
import generator.GraphQLGenerator
import generator.GraphQLSchemaScanner
import java.io.File

fun main(args: Array<String>) {
    try {
        val classes = GraphQLSchemaScanner.getClassesWithGraphQLSchemaAnnotation("example")
        for (clazz in classes) {
            println("Class with @GraphQLSchema annotation: ${clazz}")
        }

        val shcema = GraphQLGenerator.generate(classes)
        File("./schema.graphql").writeText(shcema)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}