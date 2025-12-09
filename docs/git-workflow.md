# Git Cherry-Pick Guide

Simple guide for cherry-picking commits between branches in this repository.

## Branch Commits

- **feature/refactor**: `51437a8` - Code refactoring with clean code principles
- **feature/exceptions**: `45066ab` - Custom exception handling
- **testing**: `e481870` - Unit tests with JUnit 5

## Cherry-Pick Commands

### Apply Individual Features
```bash
# Switch to target main branch
git checkout main

# Apply refactoring
git cherry-pick 51437a8

# Apply exceptions
git cherry-pick 45066ab

# Apply testing
git cherry-pick e481870
```

### Apply All Features at Once
```bash
git checkout main
git cherry-pick 51437a8 45066ab e481870
```

### Handle Conflicts
```bash
# If conflicts occur
git add .
git cherry-pick --continue

# Or abort if needed
git cherry-pick --abort
```

### Verify After Cherry-Pick
```bash
# Test the application
mvn test
mvn compile exec:java
```