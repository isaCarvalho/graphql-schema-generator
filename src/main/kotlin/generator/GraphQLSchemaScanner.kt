package generator

import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.net.URL
import java.net.URLClassLoader
import java.util.Scanner

class GraphQLSchemaScanner(private val basePath: String) : ClassLoader() {

    fun loadClasses(): List<Class<*>> {
        val directory = File(basePath)
        if (!directory.exists() || !directory.isDirectory) {
            throw IllegalArgumentException("Invalid directory path: $basePath")
        }

        val urls: Array<URL> = arrayOf(directory.toURI().toURL())
        val classLoader = URLClassLoader.newInstance(urls)

        val classFiles = findClassFiles(directory)
        println("classes: $classFiles")
        return classFiles.mapNotNull { loadClass(classLoader, it) }
    }

    private fun findClassFiles(directory: File): List<File> {
        return directory.walkTopDown()
            .filter { it.isFile && it.name.endsWith(".kt") }
            .toList()
    }

    private fun loadClass(classLoader: ClassLoader, classFile: File): Class<*>? {
        val className = classFile.toRelativeString(File(".")).removeSuffix(".kt").replace(File.separator, ".")
        return try {
            println(className)
            classLoader.loadClass(className)
        } catch (e: ClassNotFoundException) {
            throw Exception("Error loading class $className", e)
        }
    }
}