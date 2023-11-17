package graphqlinterfaces

import annotations.GraphQLIgnore

interface Mutation<T> {

    fun create(entity: T)

    fun update(entity: T)

    fun delete(entity: T)

    fun deleteAll()

    @GraphQLIgnore
    override fun toString(): String

    @GraphQLIgnore
    override fun equals(other: Any?): Boolean

    @GraphQLIgnore
    override fun hashCode(): Int
}