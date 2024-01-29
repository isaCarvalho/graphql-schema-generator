package generator

import annotations.GraphQLIgnore
import annotations.GraphQLMutation
import annotations.GraphQLQuery
import annotations.GraphQLSchema
import graphqlinterfaces.Mutation
import graphqlinterfaces.Query
import org.junit.jupiter.api.*
import java.io.File

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

    @AfterEach
    fun tearDown() {
        val file = File("./graphql/schema.graphql")
        if (file.exists()) {
            file.delete()
        }

        val directory = File("./graphql")
        if (directory.exists()) {
            directory.delete()
        }
    }

    @Nested
    @DisplayName("Given a kotlin class")
    inner class GivenAKotlinClass {


        @DisplayName("""
            It should successfully generate the whole schema
        """)
        @Test
        fun shouldGenerateSchemaWithSuccess() {
            assertDoesNotThrow {
                GraphQLGenerator.generate(User::class)
            }
        }

        @DisplayName("""
            It should successfully generate the whole query
        """)
        @Test
        fun shouldGenerateQueryWithSuccess() {
            assertDoesNotThrow {
                GraphQLGenerator.generate(UserQuery::class)
            }
        }

        @DisplayName("""
            It should successfully generate the whole mutation
        """)
        @Test
        fun shouldGenerateMutationWithSuccess() {
            assertDoesNotThrow {
                GraphQLGenerator.generate(UserMutation::class)
            }
        }
    }
}