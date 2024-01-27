import annotations.GraphQLIgnore
import annotations.GraphQLSchema
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
