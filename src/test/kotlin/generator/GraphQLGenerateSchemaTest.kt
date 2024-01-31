package generator

import annotations.GraphQLIgnore
import annotations.GraphQLSchema
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertEquals

class GraphQLGenerateSchemaTest {
    @GraphQLSchema
    data class TestClass(
        val id: UUID,
        val name: String,
        @GraphQLIgnore
        val email: String,
    )

    @Nested
    @DisplayName("Given a kotlin class")
    inner class GivenAKotlinClass {
        @DisplayName(
            """
            It should successfully generate the schema
        """,
        )
        @Test
        fun shouldGenerateSchemaWithSuccess() {
            val expected =
                "type TestClass {\n" +
                    "\tid: ID!,\n" +
                    "\tname: String!,\n" +
                    "}"

            val actual = GraphQLGenerateSchema.generate(TestClass::class)

            assertEquals(expected.trimIndent(), actual.trimIndent())
        }
    }
}
