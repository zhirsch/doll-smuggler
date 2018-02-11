= Doll Smuggler

See https://github.com/micahalles/doll-smuggler for the original README.

== Dependencies ==

The only dependency is [leiningen](https://github.com/technomancy/leiningen).

== Usage ==

To run the solver on an input, execute `lein run path/to/input.txt`.  The input
must be formated like:

```
max weight: 100

available dolls:

name    weight value
luke       10   150
anthony    30    35
candice    35   200
dorothy    25   160
```

The generated output will look like:

```
packed dolls:

name    weight value
dorothy    25   160
candice    35   200
anthony    30    35
luke       10   150
```

== Testing ==

To run the tests, execute `./test.sh`.  This will run both `lein test` and
`lein run` for every example in the `examples` directory.

To add more examples that will be executed by `test.sh`, simply drop them in
a subdirectory of the `examples` directory.  The input must be in a file named
`input.txt`, and the output (which is expected to exactly match what's output
by `lein run examples/new_subdirectory/input.txt`) must be in a file named
`output.txt`.
