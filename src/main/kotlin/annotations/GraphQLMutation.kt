package annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class GraphQLMutation(
    val entity: KClass<*>,
)
