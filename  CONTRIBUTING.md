# Contributing Guide

This repository follows a simple Conventional Commits style for commit messages.

The goal is to make the project history readable and predictable. Each commit message should describe what kind of change was made and, optionally, which part of the project was affected.

## Commit Message Format

Use the following format:

```text
type(scope): short description
```

The `scope` is optional, but recommended when the change affects a specific language, module, or area of the repository.

Examples:

```text
feat(scala): add binary search example
fix(java): correct edge case in sorting algorithm
docs(readme): update project structure description
test(haskell): add tests for basic list functions
chore(repo): reorganize example directories
```

## Commit Types

Use the following commit types:

```text
feat:      new functionality or a new example
fix:       bug fix or correction of incorrect behavior
docs:      documentation changes
refactor:  code restructuring without changing external behavior
test:      adding, updating, or fixing tests
chore:     technical maintenance that does not affect source code behavior
build:     build files, dependencies, packaging, or project setup
ci:        CI/CD configuration and automation workflows
style:     formatting, whitespace, imports, or code style without logic changes
perf:      performance optimization without changing behavior
revert:    revert a previous commit
```

## How to Choose the Type

Use `feat` when adding a new algorithm, example, module, command, or feature.

```text
feat(go): add basic recursion examples
feat(scala): add immutable queue implementation
feat(haskell): add basic pattern matching example
```

Use `fix` when correcting incorrect behavior, broken logic, wrong output, or a bug.

```text
fix(java): correct off-by-one error in binary search
fix(scala): handle empty list in max function
fix(k8s): correct service target port
```

Use `docs` when changing Markdown files, comments that explain the project, learning notes, or documentation.

```text
docs(readme): clarify repository purpose
docs(contributing): add commit message convention
docs(k8s): add Kubernetes learning notes
docs(gcp): add deployment guide
```

Use `refactor` when changing the internal structure of code without changing what the code does.

```text
refactor(scala): simplify pattern matching example
refactor(java): extract validation logic into helper method
refactor(terraform): split infrastructure configuration into modules
```

Use `test` when adding, changing, or fixing tests.

```text
test(scala): add unit tests for list operations
test(java): cover sorting edge cases
```

Use `chore` for repository maintenance, helper files, cleanup, or technical changes that are not directly source code, documentation, build, or CI.

```text
chore(repo): clean up unused files
chore(scripts): rename helper script
chore(k8s): add basic deployment manifests
chore(infra): add initial infrastructure directory
```

Use `build` when changing build tools, dependencies, package configuration, Docker setup, Compose setup, or project setup files.

```text
build(sbt): add ScalaTest dependency
build(maven): configure Java compiler version
build(docker): add Dockerfile for Scala examples
build(compose): add local development compose setup
```

Use `ci` when changing GitHub Actions or other CI/CD configuration.

```text
ci(github-actions): add JVM test workflow
ci(github-actions): run tests on pull requests
ci(github-actions): add deployment workflow
```

Use `style` when only formatting code: spaces, indentation, line breaks, imports, or formatting tool changes. The logic must not change.

```text
style(scala): reformat examples
style(java): organize imports
```

Use `perf` when improving performance without changing the external behavior.

```text
perf(scala): reduce allocations in Fibonacci example
perf(java): optimize loop in search implementation
```

Use `revert` when undoing a previous commit.

```text
revert(repo): revert directory structure change
revert(scala): revert recursion example update
```

## Recommended Scopes

The `scope` describes the language, module, tool, or technical area affected by the change.

For this repository, useful scopes are:

```text
scala
java
haskell
go
docs
scripts
repo
readme
contributing
sbt
maven
```

For infrastructure, deployment, and cloud-related changes, useful scopes are:

```text
docker
compose
k8s
helm
terraform
aws
gcp
azure
infra
deployment
github-actions
```

Examples:

```text
feat(haskell): add basic pattern matching example
feat(scala): add immutable stack example
fix(java): correct edge case in binary search
docs(readme): describe polyglot repository structure
docs(contributing): add commit message convention
docs(k8s): add Kubernetes learning notes
chore(repo): add common directory layout
chore(k8s): add basic deployment manifests
build(sbt): configure test dependencies
build(docker): add Dockerfile for Scala examples
build(compose): add local development compose setup
ci(github-actions): add JVM test workflow
fix(k8s): correct service target port
docs(gcp): add deployment guide
feat(infra): add Kubernetes deployment configuration
```

Use the commit type to describe the kind of change. Use the scope to describe where the change was made.

For example:

```text
fix(k8s): correct service target port
```

Means:

* `fix` is the commit type because the change fixes a problem.
* `k8s` is the scope because the change affects Kubernetes configuration.
* `correct service target port` is the short description of the change.

Another example:

```text
build(docker): add Dockerfile for Scala examples
```

Means:

* `build` is the commit type because the change affects project setup and build/runtime configuration.
* `docker` is the scope because the change affects Docker configuration.
* `add Dockerfile for Scala examples` is the short description of the change.

## Subject Style

Write the subject in English.

Use the imperative form where possible:

```text
add binary search example
fix empty input handling
update README structure
remove unused script
```

Do not write long sentences. Keep the subject short and concrete.

Good:

```text
feat(scala): add binary search example
fix(java): handle empty array in max function
docs(readme): update project structure
```

Avoid:

```text
feat: I added a new binary search example to the Scala folder
fix: fixed some stuff
update: changes
```

## Practical Rule

A commit should answer one simple question:

```text
What kind of change is this, and where was it made?
```

For example:

```text
docs(readme): add repository overview
```

Means:

* type: documentation change
* scope: README
* action: repository overview was added
