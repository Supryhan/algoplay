#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

echo "Checking AlgoPlay labs..."
echo

echo "JVM / SBT:"
if command -v sbt >/dev/null 2>&1; then
  (cd "$ROOT_DIR" && sbt compile)
else
  echo "sbt is not installed. Skipping JVM check."
fi

echo
echo "Haskell:"
if command -v runghc >/dev/null 2>&1; then
  runghc "$ROOT_DIR/haskell/hello/Main.hs"
else
  echo "runghc is not installed. Skipping Haskell check."
fi

echo
echo "Go:"
if command -v go >/dev/null 2>&1; then
  (cd "$ROOT_DIR/go" && go run ./hello)
else
  echo "go is not installed. Skipping Go check."
fi

echo
echo "Done."
