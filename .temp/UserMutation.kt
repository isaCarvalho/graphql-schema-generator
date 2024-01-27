import annotations.GraphQLMutation

@GraphQLMutation(entity = User::class)
interface UserMutation: Mutation<User>