package generator

import graphqltypes.GraphQLNativeTypes
import reflections.ReflectClass
import reflections.ReflectProperty
import kotlin.reflect.KClass

object GraphQLGenerateSchema {

    fun generate(cls: KClass<*>): String {
        val reflectClass = ReflectClass(cls)

        val schemaName = reflectClass.schemaName
        val schemaProperties = reflectClass.properties

        return generateSchema(schemaName, schemaProperties)
    }

    private fun generateSchema(schemaName: String, schemaProperties: List<ReflectProperty>): String {
        var str = "type $schemaName {\n"

        schemaProperties.forEach {
            str += "\t${it.name}: ${GraphQLNativeTypes.convertType(it.type)}"

            if (!it.nullable) {
                str += "!"
            }

            str += ",\n"
        }

        return "$str}"
    }
}