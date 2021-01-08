# Scala metaprogramming exercise

### Usage

This is a normal sbt project, you can compile code with `sbt compile` and run the tests
with `sbt test`.

### Exercise

Implement optimizations for a simple dot product on arrays where the arrays may be statically known.
`src/main/scala/Vectors` already implments the a `dot` product using a macro. The implmentation is in the method `dotImpl` which already optimizes for two statically known empty vectors. Your task is to add more optimizations
based on statically known information that can be recovered through the quote pattern or the reflect API.
