package generator

import annotations.GraphQLMutation
import annotations.GraphQLQuery
import graphqlinterfaces.Mutation
import graphqlinterfaces.Query
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GraphQLGenerateQueryTest {
    data class Person(
        val name: String,
    )

    @GraphQLQuery(entity = Person::class)
    interface PersonQuery : Query<Person>

    @GraphQLMutation(entity = Person::class)
    interface PersonMutation : Mutation<Person>

    @Nested
    @DisplayName(
        """Given a Kotlin class
        And a interface that implements a GraphQL interface
    """,
    )
    inner class GivenAKotlinClass {
        @Test
        @DisplayName(
            "When the interface is Query" +
                "It should generate the query",
        )
        fun shouldGenerateQueryCorrectly() {
            val query = GraphQLGenerateQuery.generate(PersonQuery::class, Person::class)

            val expected =
                "type Query {" +
                    "\n\tget(input: Person!): Person!\n" +
                    "\n\tgetAll: [Person!]!\n" +
                    "}\n"

            assertEquals(expected.trim(), query.trim())
        }

        @Test
        @DisplayName(
            "When the interface is Mutation" +
                "It should generate the mutation",
        )
        fun shouldGenerateMutationCorrectly() {
            val query = GraphQLGenerateQuery.generate(PersonMutation::class, Person::class)

            val expected =
                "type Mutation {" +
                    "\n\tcreate(input: Person!): Person!\n" +
                    "\n\tdelete(input: Person!): Person!\n" +
                    "\n\tdeleteAll: [Person!]!\n" +
                    "\n\tupdate(input: Person!): Person!\n" +
                    "}\n"

            assertEquals(expected.trim(), query.trim())
        }
    }
}
