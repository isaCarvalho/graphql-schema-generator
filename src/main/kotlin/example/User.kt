package example

import annotations.GraphQLIgnore
import annotations.GraphQLMutation
import annotations.GraphQLQuery
import annotations.GraphQLSchema
import graphqlinterfaces.Mutation
import graphqlinterfaces.Query
import java.util.UUID

@GraphQLSchema
data class User(
    val id: UUID,
    val name: String,
    val email: String,
    val age: Int?,
    @GraphQLIgnore
    val password: String,
    val person: Person,
)

@GraphQLSchema
data class Person(
    val name: String,
)

@GraphQLQuery(entity = User::class)
interface UserQuery: Query<UUID>{

    @GraphQLIgnore
    override fun getAll()
}

@GraphQLMutation(entity = User::class)
interface UserMutation: Mutation<User>
