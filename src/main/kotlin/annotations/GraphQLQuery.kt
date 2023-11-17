package annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class GraphQLQuery(
    val entity: KClass<*>
)
