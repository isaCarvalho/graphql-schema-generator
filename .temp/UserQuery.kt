import annotations.GraphQLIgnore
import annotations.GraphQLQuery
import graphqlinterfaces.Query
import java.util.UUID

@GraphQLQuery(entity = User::class)
interface UserQuery: Query<UUID>{

    @GraphQLIgnore
    override fun getAll()
}