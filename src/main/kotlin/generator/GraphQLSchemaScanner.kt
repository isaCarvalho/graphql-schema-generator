package generator

import annotations.GraphQLMutation
import annotations.GraphQLQuery
import annotations.GraphQLSchema
import java.io.File
import java.net.URL
import java.util.*
import kotlin.reflect.KClass

object GraphQLSchemaScanner {

    fun getClassesWithGraphQLSchemaAnnotation(packageName: String): List<KClass<*>> {
        val path = packageName.replace('.', '/')
        val classLoader = Thread.currentThread().contextClassLoader
        val resources: Enumeration<URL> = classLoader.getResources(path)

        val directories = mutableListOf<File>()
        while (resources.hasMoreElements()) {
            directories.add(File(resources.nextElement().file))
        }

        val classes = mutableListOf<Class<*>>()
        for (directory in directories) {
            classes.addAll(findClasses(directory, packageName))
        }

        val classesWithAnnotation = mutableListOf<KClass<*>>()
        for (clazz in classes) {
            if (clazz.isAnnotationPresent(GraphQLSchema::class.java) ||
                clazz.isAnnotationPresent(GraphQLQuery::class.java) ||
                clazz.isAnnotationPresent(GraphQLMutation::class.java)) {
                classesWithAnnotation.add(clazz::kotlin.get())
            }
        }

        return classesWithAnnotation
    }

    private fun findClasses(directory: File, packageName: String): List<Class<*>> {
        val classes = mutableListOf<Class<*>>()
        if (!directory.exists()) {
            return classes
        }

        val files = directory.listFiles()
        files?.forEach { file ->
            if (file.isDirectory) {
                assert(!file.name.contains("."))
                classes.addAll(findClasses(file, "$packageName.${file.name}"))
            } else if (file.name.endsWith(".class")) {
                val className = "$packageName.${file.name.substring(0, file.name.length - 6)}"
                classes.add(Class.forName(className))
            }
        }

        return classes
    }
}