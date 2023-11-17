package graphqlinterfaces

import annotations.GraphQLIgnore

interface Query<T> {

    fun get(input: T)

    fun getAll()

    @GraphQLIgnore
    override fun toString(): String

    @GraphQLIgnore
    override fun equals(other: Any?): Boolean

    @GraphQLIgnore
    override fun hashCode(): Int
}