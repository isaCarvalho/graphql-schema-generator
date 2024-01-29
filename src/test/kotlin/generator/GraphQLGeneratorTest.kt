package generator

import annotations.GraphQLIgnore
import annotations.GraphQLMutation
import annotations.GraphQLQuery
import annotations.GraphQLSchema
import graphqlinterfaces.Mutation
import graphqlinterfaces.Query
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GraphQLGeneratorTest {
    @GraphQLSchema
    data class User(
        val name: String,
        @GraphQLIgnore
        val email: String
    )

    @GraphQLQuery(entity = User::class)
    interface UserQuery: Query<User>

    @GraphQLMutation(entity = User::class)
    interface UserMutation: Mutation<User>

    @Nested
    @DisplayName("Given a kotlin class")
    inner class GivenAKotlinClass {


        @DisplayName("""
            It should successfully generate the whole schema
        """)
        @Test
        fun shouldGenerateSchemaWithSuccess() {
            val expected = "type USER {\n"+
                    "\tname: STRING!,\n" +
                    "}"

            val actual = GraphQLGenerator.generate(User::class)

            assertEquals(expected.trimIndent(), actual.trimIndent())
        }

        @DisplayName("""
            It should successfully generate the whole query
        """)
        @Test
        fun shouldGenerateQueryWithSuccess() {
            val expected = "type Query {" +
                    "\n\tget(input: USER!): USER!\n" +
                    "\n\tgetAll: [USER!]!\n" +
                    "}\n\n"

            val actual = GraphQLGenerator.generate(UserQuery::class)

            assertEquals(expected.trimIndent(), actual.trimIndent())
        }

        @DisplayName("""
            It should successfully generate the whole mutation
        """)
        @Test
        fun shouldGenerateMutationWithSuccess() {
            val expected = "type Mutation {" +
                    "\n\tcreate(input: USER!): USER!\n" +
                    "\n\tdelete(input: USER!): USER!\n" +
                    "\n\tdeleteAll: [USER!]!\n" +
                    "\n\tupdate(input: USER!): USER!\n" +
                    "}\n\n"

            val actual = GraphQLGenerator.generate(UserMutation::class)

            assertEquals(expected.trimIndent(), actual.trimIndent())
        }
    }
}