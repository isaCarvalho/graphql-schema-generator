package filemanagement

import org.reflections.Reflections
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import kotlin.reflect.KClass

class GraphQLClassScanner(private val packageName: String) {
    private val reflections =
        Reflections(
            ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(packageName)),
        )

    fun loadClasses(): List<KClass<*>> {
        return loadClassesAnnotatedWithGraphQLSchema() + loadClassesAnnotatedWithGraphQLQuery() + loadClassesAnnotatedWithGraphQLMutation()
    }

    private fun loadClassesAnnotatedWithGraphQLSchema(): List<KClass<*>> {
        return reflections.getTypesAnnotatedWith(annotations.GraphQLSchema::class.java)
            .filter { it.`package`.name == packageName }
            .map { it.kotlin }
    }

    private fun loadClassesAnnotatedWithGraphQLQuery(): List<KClass<*>> {
        return reflections.getTypesAnnotatedWith(annotations.GraphQLQuery::class.java)
            .filter { it.`package`.name == packageName }
            .map { it.kotlin }
    }

    private fun loadClassesAnnotatedWithGraphQLMutation(): List<KClass<*>> {
        return reflections.getTypesAnnotatedWith(annotations.GraphQLQuery::class.java)
            .filter { it.`package`.name == packageName }
            .map { it.kotlin }
    }

    fun loadClassesThatExtendsGraphQLInterface(interfaceClass: KClass<*>): List<KClass<*>> {
        return reflections.getSubTypesOf(interfaceClass.java)
            .filter { it.`package`.name == packageName }
            .map { it.kotlin }
    }
}
