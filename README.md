# AlgoPlay — Programming Laboratory

AlgoPlay is a personal programming laboratory for collecting small examples, experiments, exercises, and interview-oriented notes across several programming languages.

The repository started as a Scala and Java playground and is gradually evolving into a structured polyglot workspace. It is not intended to represent a single production application. Instead, it is a practical reference space for revisiting language features, libraries, algorithms, concurrency models, and functional programming concepts.

## Purpose

This repository is used for:

* experimenting with Scala, Java, Haskell, Go, and TypeScript;
* collecting small but meaningful programming examples;
* revisiting language features before interviews;
* testing ideas related to functional programming, concurrency, algorithms, and backend development;
* keeping personal reference implementations and notes in a structured form.

## Main Areas

### Scala

The Scala part contains examples related to the Scala language, functional programming, collections, type system features, pattern matching, concurrency, effects, and popular libraries.

Typical topics include:

* core Scala syntax and language features;
* collections and transformations;
* pattern matching;
* implicits and type classes;
* variance and type system experiments;
* Futures and asynchronous programming;
* Cats and functional abstractions;
* ZIO examples;
* Akka-related experiments;
* algorithms and coding interview tasks.

### Java

The Java part contains examples and exercises focused on core Java programming, collections, object-oriented design, concurrency, and problem-solving.

Typical topics include:

* Java language basics;
* collections;
* generics;
* multithreading;
* streams;
* reactive programming experiments;
* algorithms and interview-style exercises.

### Haskell

The `haskell` directory contains small Haskell examples used for learning, experimentation, and gradual practice with functional programming from the Haskell perspective.

The current structure is intentionally minimal:

```text
haskell/
└── examples/
    └── basics/
        └── Main.hs
```

Possible future topics:

* pure functions;
* algebraic data types;
* pattern matching;
* recursion;
* type classes;
* monads and effects.

### Go

The `go` directory contains small Go examples used for learning, experimentation, and gradual practice with the Go ecosystem.

The current structure is intentionally minimal:

```text
go/
├── go.mod
└── examples/
    └── basics/
        └── main.go
```

Possible future topics:

* basic Go syntax;
* packages and modules;
* structs and interfaces;
* goroutines and channels;
* error handling;
* small backend utilities.

### TypeScript 
The `typescript` directory contains small TypeScript examples used for learning, experimentation, and interview preparation. 

The current structure is intentionally minimal:

```text 
typescript/
└── examples/
    └── basics.ts
```
Possible future topics include:

* basic and advanced types;
* interfaces and type aliases;
* generics;
* union and intersection types;
* type narrowing;
* asynchronous programming.

## Repository Structure

```text
algoplay/
├── src/
│   ├── main/
│   │   ├── scala/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│       ├── scala/
│       ├── java/
│       └── resources/
├── haskell/
│   ├── README.md
│   └── examples/
│       └── basics/
│           └── Main.hs
├── go/
│   ├── README.md
│   ├── go.mod
│   └── examples/
│       └── basics/
│           └── main.go
├── docs/
│   └── index.md
├── scripts/
│   └── check-labs.sh
├── build.sbt
└── README.md
```

## Build and Run

### JVM / Scala / Java

The main JVM part of the repository is managed with SBT.

Compile the JVM examples:

```bash
sbt compile
```

Run tests:

```bash
sbt test
```

### Haskell

If `runghc` is installed, the minimal Haskell example can be executed from the repository root:

```bash
runghc haskell/examples/basics/Main.hs
```

### Go

If Go is installed, the minimal Go example can be executed from the repository root:

```bash
cd go
go run ./examples/basics
```

### Check All Labs

The repository contains a helper script:

```bash
./scripts/check-labs.sh
```

The script attempts to check the JVM, Haskell, and Go areas. If a required tool is not installed locally, the corresponding check is skipped.

## Notes

This repository is intentionally practical and exploratory. Some examples are small and focused on a single language feature, while others may demonstrate broader ideas such as concurrency, functional abstractions, backend-oriented programming patterns, or algorithmic problem solving.

The structure may evolve over time as new languages, libraries, and experiments are added.
