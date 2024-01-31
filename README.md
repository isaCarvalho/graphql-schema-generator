# GraphQL Schema Gennerator

A library to generate GraphQL schema from Kotlin classes.

## Installation

Add the library dependency to your Kotlin project.

- Gradle
```gradle
dependencies {
    implementation 'com.github.isaCarvalho:graphql-schema-generator:0.0.1'
}
```

- Maven
```maven
<dependency>
    <groupId>com.github.isaCarvalho</groupId>
    <artifactId>graphql-schema-generator</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Usage

### Create a schema

```kotlin
package com.example

import java.util.Date
import com.github.isaCarvalho.graphql.schema.annotations.GraphQLIgnore
import com.github.isaCarvalho.graphql.schema.annotations.GraphQLSchema

@GraphQLSchema
data class User(
    val id: String,
    val name: String,
    val email: String,
    @GraphQLIgnore
    val createdAt: Date,
)
```

If you do not want a property to be generated in the schema, use the annotation `@GraphQLIgnore`.

### Create queries

```kotlin
package com.example

import com.github.isaCarvalho.graphql.schema.annotations.GraphQLIgnore
import com.github.isaCarvalho.graphql.schema.annotations.GraphQLQuery
import java.util.Date

@GraphQLQuery
interface UserQuery: Query<User> {
    
    @GraphQLIgnore
    fun getCreatedAt(): Date
}
```

The same way you can use `@GraphQLIgnore` to ignore a property, you can use it to ignore a method.

### Create mutations

```kotlin
package com.example

import com.github.isaCarvalho.graphql.schema.annotations.GraphQLIgnore
import com.github.isaCarvalho.graphql.schema.annotations.GraphQLMutation
import java.util.Date

@GraphQLMutation
interface UserMutation: Mutation<User> {
    
    @GraphQLIgnore
    override fun deleteAll()
}
```

You can also use `@GraphQLIgnore` to ignore a default method.

### Generate the schema

The schema can be generated using the `SchemaGenerator` class, and it will be save to `./graphql/schema.graphql` in the root of the project.

```kotlin
@SpringBootApplication
class Application {
    
    @Bean
    fun schemaGenerator(): SchemaGenerator {
        return SchemaGenerator()
    }
    
    @Bean
    fun schema(schemaGenerator: SchemaGenerator) {
        schemaGenerator.generate(User::class)
        schemaGenerator.generate(User::class, UserMutation::class)
        schemaGenerator.generate(listOf(UserQuery::class, UserMutation::class))
        schemaGenerator.generate("com.example") // make sure you've settled the name of the package correctly
    }
}

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}
```