package generator

import annotations.GraphQLIgnore
import annotations.GraphQLMutation
import annotations.GraphQLQuery
import annotations.GraphQLSchema
import graphqlinterfaces.Mutation
import graphqlinterfaces.Query
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@GraphQLSchema
data class User(
    val name: String,
    @GraphQLIgnore
    val email: String
)

@GraphQLQuery(entity = Person::class)
interface UserQuery: Query<Person>

@GraphQLMutation(entity = Person::class)
interface UserMutation: Mutation<Person>

class GraphQLGeneratorTest {

    @Nested
    @DisplayName("Given a kotlin class")
    inner class GivenAKotlinClass {

    }
}