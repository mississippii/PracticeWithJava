# Git - The Complete Command Reference & Problem Solver

> **For Software Engineers**: Practical Git commands, real-world scenarios, and solutions to common problems.

---

## Table of Contents
1. [Command Categories](#command-categories)
2. [The Confusing Trio: Reset vs Revert vs Restore](#the-confusing-trio-reset-vs-revert-vs-restore)
3. [Common Problems & Solutions](#common-problems--solutions)
4. [Branch Management](#branch-management)
5. [Stashing & Temporary Work](#stashing--temporary-work)
6. [Merge vs Rebase](#merge-vs-rebase)
7. [Undoing Mistakes](#undoing-mistakes)
8. [Conflict Resolution](#conflict-resolution)
9. [Dangerous Commands](#dangerous-commands)
10. [Team Collaboration](#team-collaboration)
11. [Git Internals](#git-internals)
12. [Advanced Workflows](#advanced-workflows)

---

## Command Categories

### 📊 Status & Information
| Command | Use When | Output |
|---------|----------|---------|
| `git status` | Check current state | Modified files, staged changes, branch info |
| `git log` | View commit history | All commits with hash, author, date |
| `git log --oneline` | Quick commit overview | Compact one-line per commit |
| `git log --graph --all` | Visual branch history | ASCII graph of branches |
| `git diff` | See unstaged changes | Line-by-line differences |
| `git diff --staged` | See staged changes | What will be committed |
| `git show <commit>` | View specific commit | Complete commit details |
| `git blame <file>` | See who changed what | Line-by-line authorship |
| `git reflog` | View all HEAD movements | Complete history including deleted commits |

---

## The Confusing Trio: Reset vs Revert vs Restore

### Quick Decision Tree:
```
Need to undo changes?
├─ Is it pushed to remote?
│  ├─ YES → Use git revert (safe, creates new commit)
│  └─ NO → Continue below
│
├─ Want to keep changes in working directory?
│  ├─ YES → git reset --soft (uncommit but keep changes staged)
│  └─ NO → Continue below
│
├─ Want to keep changes unstaged?
│  ├─ YES → git reset --mixed (default, uncommit and unstage)
│  └─ NO → git reset --hard (DANGER: delete everything)
│
└─ Just want to discard file changes?
   └─ git restore <file> (discard working directory changes)
```

---

## 1. `git reset` - Move Branch Pointer

**What it does**: Moves the current branch pointer (and optionally HEAD) to a different commit.

### Three Modes:

#### `git reset --soft <commit>`
**Use when**: You want to uncommit but keep changes staged.

```bash
# Scenario: Committed too early, want to add more files
git reset --soft HEAD~1

# What happens:
# ✓ Branch pointer moves back
# ✓ Changes stay in staging area
# ✗ Commit is gone (but changes preserved)
```

**Real-world example**:
```bash
# You committed but forgot to add a file
git add forgotten-file.txt
git reset --soft HEAD~1  # Uncommit
git add forgotten-file.txt
git commit -m "Complete feature with all files"
```

#### `git reset --mixed <commit>` (DEFAULT)
**Use when**: You want to uncommit AND unstage changes.

```bash
# Scenario: Want to reorganize commits or split into multiple commits
git reset HEAD~1  # Same as --mixed

# What happens:
# ✓ Branch pointer moves back
# ✓ Changes move to working directory (unstaged)
# ✗ Commit is gone
# ✗ Staging area cleared
```

**Real-world example**:
```bash
# You made one big commit but want to split into multiple
git reset HEAD~1
git add feature-part1.java
git commit -m "Part 1: Add feature structure"
git add feature-part2.java
git commit -m "Part 2: Implement feature logic"
```

#### `git reset --hard <commit>` ⚠️ DANGEROUS
**Use when**: You want to completely discard all changes.

```bash
# Scenario: Experimented with code, want to completely abandon it
git reset --hard HEAD~1

# What happens:
# ✓ Branch pointer moves back
# ✗ All changes DELETED (working directory + staging)
# ⚠️ IRREVERSIBLE (unless you find commit in reflog)
```

**Real-world example**:
```bash
# You tried a feature, it's terrible, delete everything
git reset --hard origin/main  # Match remote exactly

# Or abandon all local changes
git reset --hard HEAD
```

---

## 2. `git revert` - Create Inverse Commit

**What it does**: Creates a NEW commit that undoes a previous commit.

**Use when**:
- ✅ Changes are already pushed to remote
- ✅ Working in a team (preserves history)
- ✅ Need audit trail

```bash
# Scenario: Pushed a bad commit, need to undo it publicly
git revert <commit-hash>

# What happens:
# ✓ New commit created that reverses the changes
# ✓ History preserved (safe for team)
# ✓ Can be pushed without force
```

**Real-world examples**:

```bash
# Bad commit was pushed to main
git revert abc123
git push origin main  # No force needed!

# Revert multiple commits
git revert HEAD~3..HEAD  # Revert last 3 commits

# Revert a merge commit
git revert -m 1 <merge-commit>  # -m 1 means keep first parent
```

**When NOT to use revert**:
- Local commits not pushed yet (use reset instead)
- Want clean history (use reset before pushing)

---

## 3. `git restore` - Restore Files

**What it does**: Restore files in working directory or staging area.

### Two main uses:

#### Discard working directory changes
```bash
# Scenario: Modified file, want to discard changes
git restore <file>

# What happens:
# ✓ File restored to last commit state
# ✗ Unsaved changes lost
```

**Real-world example**:
```bash
# Accidentally modified config file
git restore config.properties

# Restore all modified files
git restore .
```

#### Unstage files
```bash
# Scenario: Accidentally staged wrong file
git restore --staged <file>

# What happens:
# ✓ File removed from staging area
# ✓ Changes still in working directory
```

**Real-world example**:
```bash
# Staged secret file by mistake
git restore --staged .env
# File still modified, just unstaged

# Add to .gitignore
echo ".env" >> .gitignore
git add .gitignore
```

---

## Common Problems & Solutions

### Problem 1: "I committed to the wrong branch"

```bash
# Current: feature/wrong-branch
# Want: feature/correct-branch

# Solution:
git log  # Note the commit hash
git checkout feature/correct-branch
git cherry-pick <commit-hash>
git checkout feature/wrong-branch
git reset --hard HEAD~1  # Remove from wrong branch
```

---

### Problem 2: "I need to change the last commit message"

```bash
# Not pushed yet
git commit --amend -m "Corrected message"

# Already pushed (use with caution)
git commit --amend -m "Corrected message"
git push --force-with-lease origin branch-name
```

**⚠️ Only amend if**: Commit is NOT on main/master or shared branch.

---

### Problem 3: "I committed secrets/passwords"

```bash
# If NOT pushed yet:
git reset --soft HEAD~1
# Remove secrets from files
git add .
git commit -m "Feature without secrets"

# If already pushed (more complex):
# Option 1: Revert and rotate secrets
git revert <commit-hash>
git push
# IMMEDIATELY rotate the exposed secrets!

# Option 2: BFG Repo-Cleaner (removes from history)
# https://rtyley.github.io/bfg-repo-cleaner/
bfg --replace-text passwords.txt
git push --force
# Still rotate secrets!
```

**Critical**: ALWAYS rotate exposed secrets regardless of Git actions.

---

### Problem 4: "I want to undo `git add`"

```bash
# Unstage specific file
git restore --staged <file>

# Unstage all files
git restore --staged .

# Old way (still works)
git reset HEAD <file>
```

---

### Problem 5: "Merge conflict hell"

```bash
# See which files have conflicts
git status

# See conflict markers in file
# <<<<<<< HEAD
# Your changes
# =======
# Their changes
# >>>>>>> branch-name

# Option 1: Manual resolution
# Edit files, remove markers
git add <resolved-files>
git commit

# Option 2: Accept theirs
git checkout --theirs <file>
git add <file>

# Option 3: Accept yours
git checkout --ours <file>
git add <file>

# Option 4: Abort merge
git merge --abort
```

**Pro tip**: Use a merge tool
```bash
git config --global merge.tool vimdiff
git mergetool
```

---

### Problem 6: "I need my changes from yesterday but reset --hard"

```bash
# Solution: reflog to the rescue!
git reflog
# Find the commit before reset
# reflog shows: abc123 HEAD@{1}: commit: My lost work

git cherry-pick abc123
# Or
git reset --hard abc123
```

**reflog is a lifesaver**: Keeps 90 days of HEAD movements.

---

### Problem 7: "Accidentally worked on main instead of feature branch"

```bash
# Current: main (with uncommitted changes)
# Want: feature/new-work

# Solution:
git stash
git checkout -b feature/new-work
git stash pop
git add .
git commit -m "Feature work"
```

---

### Problem 8: "Pull failed due to local changes"

```bash
# Error: "Please commit or stash them before you merge"

# Option 1: Stash and pull
git stash
git pull
git stash pop

# Option 2: Commit first
git add .
git commit -m "WIP"
git pull

# Option 3: Discard local changes (if not needed)
git restore .
git pull
```

---

## Branch Management

### Creating & Switching

```bash
# Create and switch
git checkout -b feature/new-feature

# New syntax (Git 2.23+)
git switch -c feature/new-feature

# Create from specific commit
git checkout -b hotfix/bug abc123

# Create from remote branch
git checkout -b feature/remote origin/feature/remote
```

### Deleting Branches

```bash
# Delete local branch (safe - prevents if not merged)
git branch -d feature/done

# Force delete (even if not merged)
git branch -D feature/abandoned

# Delete remote branch
git push origin --delete feature/done

# Prune deleted remote branches locally
git fetch --prune
```

### Renaming Branches

```bash
# Rename current branch
git branch -m new-name

# Rename other branch
git branch -m old-name new-name

# Update remote
git push origin :old-name new-name
git push origin -u new-name
```

---

## Stashing & Temporary Work

### Basic Stashing

```bash
# Stash current changes
git stash

# Stash with message
git stash save "WIP: feature implementation"

# Include untracked files
git stash -u

# Include ignored files too
git stash -a
```

### Retrieving Stashes

```bash
# List stashes
git stash list

# Apply most recent stash (keeps in stash list)
git stash apply

# Apply specific stash
git stash apply stash@{2}

# Apply and remove from stash list
git stash pop

# Apply to a new branch
git stash branch feature/stashed-work
```

### Managing Stashes

```bash
# Show stash contents
git stash show
git stash show -p  # With diff

# Drop specific stash
git stash drop stash@{1}

# Clear all stashes
git stash clear
```

**Real-world scenario**:
```bash
# Working on feature, urgent bug appears
git stash save "WIP: feature half done"
git checkout main
git checkout -b hotfix/urgent-bug
# Fix bug, commit, push
git checkout feature/original
git stash pop  # Continue where you left off
```

---

## Merge vs Rebase

### When to Merge

**Use merge when**:
- ✅ Working on public/shared branches
- ✅ Want to preserve complete history
- ✅ Feature branch completed
- ✅ Collaborating with team

```bash
# Merge feature into main
git checkout main
git merge feature/new-feature

# Create merge commit even if fast-forward possible
git merge --no-ff feature/new-feature
```

**Pros**:
- ✅ Safe, preserves history
- ✅ Shows when features were integrated
- ✅ Easy to revert entire feature

**Cons**:
- ❌ Creates merge commits (cluttered history)
- ❌ Non-linear history

---

### When to Rebase

**Use rebase when**:
- ✅ Want linear history
- ✅ Feature branch not pushed/shared
- ✅ Updating feature branch with main
- ✅ Cleaning up local commits

```bash
# Update feature branch with latest main
git checkout feature/my-feature
git rebase main

# Interactive rebase (clean up commits)
git rebase -i HEAD~5
```

**Pros**:
- ✅ Clean, linear history
- ✅ No merge commits
- ✅ Easier to understand history

**Cons**:
- ❌ Rewrites history (dangerous on shared branches)
- ❌ Can be complex if conflicts arise

---

### The Golden Rule of Rebase

**⚠️ NEVER REBASE PUBLIC/SHARED BRANCHES**

```bash
# ❌ DANGER - Don't do this!
git checkout main
git rebase feature/something

# ✅ SAFE - Do this instead
git checkout feature/something
git rebase main
```

---

### Interactive Rebase - Clean Up History

```bash
# Rebase last 5 commits interactively
git rebase -i HEAD~5

# Options:
# pick   = use commit
# reword = use commit, but edit message
# edit   = use commit, but stop to amend
# squash = merge with previous commit
# fixup  = like squash, but discard message
# drop   = remove commit
```

**Real-world example**: Squash WIP commits
```bash
# Before:
# abc123 WIP
# def456 WIP: still working
# ghi789 WIP: finally working
# jkl012 Feature complete

git rebase -i HEAD~4

# Change to:
# pick jkl012 Feature complete
# squash ghi789
# squash def456
# squash abc123

# Result: One clean commit!
```

---

## Undoing Mistakes

### Undo Last Commit (Not Pushed)

```bash
# Keep changes staged
git reset --soft HEAD~1

# Keep changes unstaged
git reset HEAD~1

# Discard changes completely
git reset --hard HEAD~1
```

---

### Undo Last Commit (Already Pushed)

```bash
# Safe way (public branch)
git revert HEAD
git push

# Dangerous way (own branch)
git reset --hard HEAD~1
git push --force-with-lease
```

---

### Undo Multiple Commits

```bash
# Reset back 3 commits
git reset --hard HEAD~3

# Revert last 3 commits (creates 3 revert commits)
git revert HEAD~2..HEAD

# Interactive rebase (clean way)
git rebase -i HEAD~3
# Mark commits as 'drop'
```

---

### Recover Deleted Commit

```bash
# Find lost commit
git reflog

# Cherry-pick it back
git cherry-pick <commit-hash>

# Or reset to it
git reset --hard <commit-hash>
```

---

### Undo Changes to Specific File

```bash
# Restore file to last commit
git restore <file>

# Restore file to specific commit
git restore --source=<commit> <file>

# Old syntax
git checkout <commit> -- <file>
```

---

## Conflict Resolution

### Understanding Conflict Markers

```java
<<<<<<< HEAD (Current Change)
int result = calculateTotal();
=======
int total = computeSum();
>>>>>>> feature/new-calculation (Incoming Change)
```

**What it means**:
- `<<<<<<< HEAD`: Your current branch changes
- `=======`: Separator
- `>>>>>>> branch`: Incoming branch changes

---

### Resolution Strategies

#### 1. Manual Resolution
```bash
# Edit file, remove markers, fix code
# After fixing:
git add <file>
git commit
```

#### 2. Accept Theirs (Incoming)
```bash
git checkout --theirs <file>
git add <file>
```

#### 3. Accept Ours (Current)
```bash
git checkout --ours <file>
git add <file>
```

#### 4. Use Merge Tool
```bash
git mergetool
```

---

### Abort Operations

```bash
# Abort merge
git merge --abort

# Abort rebase
git rebase --abort

# Abort cherry-pick
git cherry-pick --abort
```

---

## Dangerous Commands

### ⚠️ Commands That Rewrite History

| Command | Danger Level | Safe Alternative |
|---------|--------------|------------------|
| `git reset --hard` | 🔴 High | `git stash` or `git revert` |
| `git push --force` | 🔴 High | `git push --force-with-lease` |
| `git rebase` (on public) | 🔴 High | `git merge` |
| `git commit --amend` (pushed) | 🟡 Medium | Create new commit |
| `git filter-branch` | 🔴 High | Use BFG Repo-Cleaner |
| `git clean -fd` | 🟡 Medium | Check with `-n` first |

---

### `git push --force` vs `--force-with-lease`

```bash
# ❌ DANGEROUS - Overwrites remote regardless
git push --force

# ✅ SAFER - Only pushes if remote hasn't changed
git push --force-with-lease

# What can go wrong with --force:
# 1. Teammate pushed while you were working
# 2. You force push and delete their work
# 3. Data loss!

# --force-with-lease prevents this:
# It checks if remote matches your expectation
# Fails if someone else pushed
```

---

### `git clean` - Delete Untracked Files

```bash
# See what would be deleted (DRY RUN)
git clean -n

# Delete untracked files
git clean -f

# Delete untracked files and directories
git clean -fd

# Include ignored files
git clean -fdx
```

**⚠️ Warning**: `git clean` is IRREVERSIBLE!

---

## Team Collaboration

### Pull Request Workflow

```bash
# 1. Create feature branch
git checkout -b feature/new-feature

# 2. Make changes, commit
git add .
git commit -m "Implement new feature"

# 3. Push to remote
git push -u origin feature/new-feature

# 4. Create PR on GitHub/GitLab

# 5. Address review comments
git add .
git commit -m "Address review feedback"
git push

# 6. After PR merged, cleanup
git checkout main
git pull
git branch -d feature/new-feature
git push origin --delete feature/new-feature
```

---

### Keeping Feature Branch Updated

#### Option 1: Merge (Preserves history)
```bash
git checkout feature/my-feature
git merge main
# Resolve conflicts
git push
```

#### Option 2: Rebase (Clean history)
```bash
git checkout feature/my-feature
git rebase main
# Resolve conflicts
git push --force-with-lease
```

**Team convention**: Agree on one approach!

---

### Syncing Forked Repository

```bash
# Add upstream remote (one time)
git remote add upstream https://github.com/original/repo.git

# Fetch upstream changes
git fetch upstream

# Merge into your main
git checkout main
git merge upstream/main

# Push to your fork
git push origin main
```

---

### Cherry-Picking Commits

**Use when**: Need specific commit from another branch.

```bash
# Pick one commit
git cherry-pick <commit-hash>

# Pick multiple commits
git cherry-pick abc123 def456 ghi789

# Pick range of commits
git cherry-pick abc123..ghi789
```

**Real-world scenario**:
```bash
# Hotfix needed on main from feature branch
git checkout main
git cherry-pick <hotfix-commit-from-feature>
git push

# Feature branch keeps the commit too
```

---

## Git Internals

### Understanding the Three Trees

1. **Working Directory**: Your files
2. **Staging Area (Index)**: Prepared for commit
3. **Repository (.git)**: Committed history

```
Working Directory  →  Staging Area  →  Repository
   git add          git commit
   ←───────────────────────────────
   git restore         git reset
```

---

### Commits Are Snapshots, Not Diffs

```bash
# Each commit stores:
# - Tree (snapshot of files)
# - Parent commit(s)
# - Author & committer
# - Message
# - Timestamp

git cat-file -p <commit-hash>
# Shows commit object internals
```

---

### Branches Are Just Pointers

```bash
# A branch is a 41-byte file with commit hash
cat .git/refs/heads/main

# HEAD is a pointer to current branch
cat .git/HEAD
# ref: refs/heads/main
```

---

### Detached HEAD State

```bash
# Checkout specific commit
git checkout abc123

# You're in "detached HEAD" state
# HEAD points to commit, not branch

# To save work in detached HEAD:
git checkout -b new-branch
```

---

## Advanced Workflows

### Git Bisect - Find Bug-Introducing Commit

```bash
# Start bisect
git bisect start

# Mark current as bad
git bisect bad

# Mark known good commit
git bisect good abc123

# Git checks out middle commit
# Test it, then mark:
git bisect good  # or bad

# Repeat until Git finds the commit
# Shows: "abc123 is the first bad commit"

# Exit bisect
git bisect reset
```

**Automated bisect**:
```bash
git bisect start HEAD abc123
git bisect run npm test
# Runs tests automatically on each commit
```

---

### Git Worktree - Multiple Working Directories

```bash
# Work on multiple branches simultaneously
git worktree add ../hotfix main
git worktree add ../feature-2 feature/another

# Now you have:
# - ./repo (main worktree)
# - ../hotfix (linked worktree on main)
# - ../feature-2 (linked worktree on feature/another)

# List worktrees
git worktree list

# Remove worktree
git worktree remove ../hotfix
```

**Use case**: Review PR while working on feature.

---

### Git Hooks - Automate Workflows

```bash
# Hooks in .git/hooks/
# pre-commit: Run before commit
# pre-push: Run before push
# post-merge: Run after merge

# Example: pre-commit hook
# .git/hooks/pre-commit
#!/bin/sh
npm run lint
npm run test

# Make executable
chmod +x .git/hooks/pre-commit
```

**Share hooks with team**: Use Husky (npm package).

---

### Submodules - Repositories Within Repository

```bash
# Add submodule
git submodule add https://github.com/user/library.git libs/library

# Clone repo with submodules
git clone --recursive <repo-url>

# Or after cloning
git submodule init
git submodule update

# Update submodules
git submodule update --remote
```

**Alternative**: Git subtree (merges code directly).

---

## Git Aliases - Speed Up Workflow

```bash
# Set aliases in ~/.gitconfig
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status
git config --global alias.unstage 'restore --staged'
git config --global alias.last 'log -1 HEAD'
git config --global alias.visual 'log --graph --oneline --all'

# Now use:
git co main
git st
git visual
```

---

## Useful Commands Reference

### Search & Find

```bash
# Search commit messages
git log --grep="bug fix"

# Search code in history
git log -S "functionName"

# Find when line was changed
git blame <file>

# Find which commit deleted a file
git log --all --full-history -- <file>
```

---

### History Manipulation

```bash
# Squash last 3 commits
git reset --soft HEAD~3
git commit -m "Squashed commit"

# Edit commit in history
git rebase -i <commit>^
# Mark commit as 'edit'
# Make changes
git commit --amend
git rebase --continue

# Remove file from all history
git filter-branch --tree-filter 'rm -f password.txt' HEAD
# Better: Use BFG Repo-Cleaner
```

---

### Tags

```bash
# Create lightweight tag
git tag v1.0.0

# Create annotated tag (recommended)
git tag -a v1.0.0 -m "Release version 1.0.0"

# Tag specific commit
git tag -a v1.0.0 abc123

# Push tag to remote
git push origin v1.0.0

# Push all tags
git push --tags

# Delete tag locally
git tag -d v1.0.0

# Delete tag remotely
git push origin --delete v1.0.0

# Checkout tag
git checkout v1.0.0
```

---

## Best Practices

### Commit Messages

**Good commit format**:
```
<type>: <subject>

<body>

<footer>
```

**Types**: feat, fix, docs, style, refactor, test, chore

**Example**:
```
feat: Add user authentication

Implement JWT-based authentication with refresh tokens.
- Add login endpoint
- Add token validation middleware
- Add refresh token rotation

Closes #123
```

---

### Branch Naming

```bash
# Feature branches
feature/user-authentication
feature/payment-integration

# Bug fix branches
fix/login-error
bugfix/null-pointer-exception

# Hotfix branches (production issues)
hotfix/critical-security-patch

# Release branches
release/1.2.0

# Experimental branches
experiment/new-architecture
```

---

### .gitignore Best Practices

```bash
# Operating System
.DS_Store
Thumbs.db

# IDE
.idea/
.vscode/
*.swp

# Dependencies
node_modules/
vendor/

# Build outputs
dist/
build/
target/

# Environment
.env
.env.local

# Logs
*.log
logs/

# Sensitive files
secrets.yml
*.pem
```

**Global gitignore**:
```bash
git config --global core.excludesfile ~/.gitignore_global
```

---

## Emergency Recovery

### "I lost my work!"

```bash
# Step 1: Check reflog
git reflog

# Step 2: Find your work
# Look for commit or stash

# Step 3: Recover
git checkout <commit-hash>
git checkout -b recovered-work

# Or cherry-pick
git cherry-pick <commit-hash>
```

---

### "I force pushed and lost commits"

```bash
# If you still have local reflog
git reflog
git reset --hard <commit-before-force-push>

# If teammate has the commits
git fetch teammate
git cherry-pick <their-commit>

# Prevention: Use --force-with-lease
git push --force-with-lease
```

---

### "I committed to wrong repository"

```bash
# If not pushed yet
git log  # Copy commit hash
cd correct-repo
git cherry-pick <commit-hash>

# Back to wrong repo
git reset --hard HEAD~1
```

---

## Performance Tips

```bash
# Faster git status
git config --global core.fsmonitor true

# Garbage collection
git gc

# Clean up
git prune

# Shallow clone (faster for large repos)
git clone --depth 1 <repo-url>

# Partial clone (Git 2.19+)
git clone --filter=blob:none <repo-url>
```

---

## Troubleshooting

### "Permission denied (publickey)"
```bash
# Check SSH key
ssh -T git@github.com

# Generate new key
ssh-keygen -t ed25519 -C "your_email@example.com"

# Add to ssh-agent
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_ed25519
```

---

### "fatal: refusing to merge unrelated histories"
```bash
# Allow unrelated histories
git pull origin main --allow-unrelated-histories
```

---

### "Your branch and 'origin/main' have diverged"
```bash
# Option 1: Merge
git pull

# Option 2: Rebase
git pull --rebase

# Option 3: Force your version (careful!)
git push --force-with-lease
```

---

### "fatal: not a git repository"
```bash
# Initialize git
git init

# Or clone
git clone <repo-url>
```

---

## Summary Cheat Sheet

| Situation | Command |
|-----------|---------|
| Undo last commit (keep changes) | `git reset HEAD~1` |
| Undo last commit (discard changes) | `git reset --hard HEAD~1` |
| Undo pushed commit | `git revert HEAD` |
| Discard file changes | `git restore <file>` |
| Unstage file | `git restore --staged <file>` |
| Save work temporarily | `git stash` |
| Retrieve stashed work | `git stash pop` |
| Update feature branch | `git merge main` or `git rebase main` |
| Switch branches with changes | `git stash` then switch |
| Delete branch | `git branch -d <branch>` |
| Rename branch | `git branch -m <new-name>` |
| See what changed | `git diff` |
| Find bug-introducing commit | `git bisect` |
| Recover lost commit | `git reflog` |

---

## Dangerous Commands Checklist

Before running these, ask yourself:

- [ ] `git reset --hard` - Do I have backups? ✅ Check reflog after!
- [ ] `git push --force` - Is anyone else working on this branch? ✅ Use `--force-with-lease` instead!
- [ ] `git rebase` - Is this branch shared/public? ✅ Never rebase public branches!
- [ ] `git clean -fd` - Am I sure I want to delete untracked files? ✅ Run with `-n` first!
- [ ] `git commit --amend` - Is this pushed already? ✅ Only amend local commits!

---

**Remember**: Git never truly deletes data for ~90 days (reflog). If you mess up, `git reflog` is your friend!

**Golden Rule**: When in doubt, create a backup branch before destructive operations:
```bash
git branch backup-$(date +%Y%m%d-%H%M%S)
```
