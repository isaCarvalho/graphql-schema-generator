package graphqltypes

import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.javaType

object GraphQLNativeTypes {
    private val types = mapOf<KClass<*>, String>(
        Pair(UUID::class, "ID"),
        Pair(String::class, "String"),
        Pair(Int::class, "Int"),
        Pair(Float::class, "Float"),
        Pair(Boolean::class, "Boolean")
    )

    fun convertType(type: KClass<*>): String {
        val typeStr = if (types.containsKey(type)) {
            types[type] ?: "UNKNOWN"
        } else {
            type.simpleName ?: "UNKNOWN"
        }

        return typeStr.uppercase()
    }
}