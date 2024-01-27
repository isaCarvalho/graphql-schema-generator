
import generator.GraphQLSchemaScanner

fun main(args: Array<String>) {
    if (args.size <= 1) {
        println("Usage: <origin-directory> <destiny-directory>")
        return
    }

    val basePath = args[0]

    val classLoader = GraphQLSchemaScanner(basePath)
    val loadedClasses = classLoader.loadClasses()

    if (loadedClasses.isNotEmpty()) {
        println("Classes loaded:")
        loadedClasses.forEach { loadedClass ->
            println(loadedClass.name)
        }
    } else {
        println("No classes found or loaded.")
    }
}