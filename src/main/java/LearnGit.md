# LearnGit - Comprehensive Git Guide for Industry Standards

## Table of Contents
1. [Introduction to Git](#introduction-to-git)
2. [Git Fundamentals](#git-fundamentals)
3. [Basic Commands](#basic-commands)
4. [Branching Strategies](#branching-strategies)
5. [Merging and Rebasing](#merging-and-rebasing)
6. [Collaboration Workflows](#collaboration-workflows)
7. [Remote Repositories](#remote-repositories)
8. [Common Real-World Fixes](#common-real-world-fixes)
9. [Advanced Topics](#advanced-topics)
10. [Industry Best Practices](#industry-best-practices)
11. [Interview Questions](#interview-questions)

---

## Introduction to Git

### What is Git?
Git is a distributed version control system (DVCS) designed to handle everything from small to very large projects with speed and efficiency. It was created by Linus Torvalds in 2005 and has become the industry standard for version control in software development.

### Why Git?
- **Distributed Architecture**: Every developer has a complete copy of the repository
- **Non-linear Development**: Supports multiple branches for parallel development
- **Performance**: Fast operations due to local storage and efficient compression
- **Security**: Cryptographic history to prevent tampering
- **Flexibility**: Accommodates various workflows and team sizes

### Git vs Other VCS
| Feature | Git | SVN | Mercurial |
|---------|-----|-----|-----------|
| Distributed | Yes | No | Yes |
| Speed | Fast | Slower | Fast |
| Learning Curve | Moderate | Easy | Moderate |
| Industry Adoption | 90%+ | <5% | <5% |
| Branching | Lightweight | Expensive | Lightweight |

---

## Git Fundamentals

### Core Concepts

#### Repository (Repo)
A repository is a directory that contains:
- **Working Directory**: Current file state
- **Staging Area (Index)**: Files staged for commit
- **Local Repository**: Complete history stored in .git folder
- **Remote Repository**: Central server (GitHub, GitLab, Bitbucket)

#### Three States of Files
```
1. Modified  → Changes in working directory but not staged
2. Staged    → Changes added to staging area, ready to commit
3. Committed → Changes saved in local repository
```

#### Commit
A commit is a snapshot of your project at a specific point in time. Each commit contains:
- A unique SHA-1 hash (40 characters)
- Author information
- Commit message
- Parent commit reference(s)
- Complete file snapshot

Example Commit Structure:
```
commit 3a2f8d9c4e5b1a7f9e2d3c4b5a6f7e8d9c0a1b2
Author: John Doe <john@example.com>
Date:   Mon Jan 20 10:30:45 2025 +0530

    Add user authentication module
    
    - Implement JWT token generation
    - Add password hashing with bcrypt
    - Create login/logout endpoints
```

#### Branch
A branch is a pointer to a commit. It allows parallel development without affecting the main codebase.
- **HEAD**: Points to the current branch/commit
- **Master/Main**: Default branch (industry now uses "main")
- **Feature Branches**: Created for specific features

#### Tag
A tag is a fixed reference to a commit, typically used for marking releases.
```
v1.0.0 - First production release
v1.0.1 - Bug fix release
v2.0.0 - Major version release
```

---

## Basic Commands

### Initialization and Configuration

#### Initialize Repository
```bash
git init                          # Initialize local repository
git clone <url>                   # Clone remote repository
git clone <url> <directory>       # Clone to specific directory
```

#### Configuration
```bash
git config --global user.name "Your Name"
git config --global user.email "your@email.com"
git config --list                 # View all configurations
git config user.name              # View specific config
```

### Staging and Committing

#### Check Status
```bash
git status                        # Show working tree status
git status -s                     # Short format
git status --porcelain           # Machine-readable format
```

#### Staging Changes
```bash
git add <file>                    # Stage specific file
git add .                         # Stage all changes
git add -A                        # Stage all (including deletions)
git add -p                        # Interactive staging (patch mode)
```

#### Unstaging Changes
```bash
git reset <file>                  # Unstage file
git reset                         # Unstage all files
git restore --staged <file>       # Modern alternative (Git 2.23+)
```

#### Committing
```bash
git commit -m "message"           # Commit staged changes
git commit -am "message"          # Stage and commit tracked files
git commit --amend                # Modify last commit
git commit --amend --no-edit      # Amend without changing message
```

### Viewing History

#### View Commits
```bash
git log                           # Show commit history
git log --oneline                 # One line per commit
git log --graph --all --oneline   # Visual branch representation
git log --author="John"           # Filter by author
git log --since="2025-01-01"      # Filter by date
git log -p                        # Show actual changes (patch)
git log -n 5                      # Show last 5 commits
```

#### View Changes
```bash
git diff                          # Show unstaged changes
git diff --staged                 # Show staged changes
git diff <branch1> <branch2>      # Compare branches
git diff HEAD~1 HEAD              # Compare with previous commit
git show <commit>                 # Show specific commit details
```

### Reverting Changes

#### Discard Changes
```bash
git checkout <file>               # Discard changes in working directory
git checkout .                    # Discard all changes
git restore <file>                # Modern alternative (Git 2.23+)
```

#### Undo Commits
```bash
git reset --soft HEAD~1           # Keep changes in staging area
git reset --mixed HEAD~1          # Keep changes in working directory
git reset --hard HEAD~1           # Discard all changes
git revert <commit>               # Create new commit that undoes changes
```

---

## Branching Strategies

### Creating and Managing Branches

#### Branch Operations
```bash
git branch                        # List local branches
git branch -a                     # List all branches (local and remote)
git branch <branch-name>          # Create new branch
git checkout <branch-name>        # Switch to branch
git checkout -b <branch-name>     # Create and switch to branch
git branch -d <branch-name>       # Delete branch (safe)
git branch -D <branch-name>       # Force delete branch
git branch -m <old-name> <new-name>  # Rename branch
git branch -m master main         # Rename current default branch to main
```

#### Branch Deletion Remote
```bash
git push origin --delete <branch-name>  # Delete remote branch
git push origin :<branch-name>          # Alternative syntax
git branch -dr origin/<branch-name>     # Delete remote tracking branch
```

### Popular Branching Models

#### 1. Git Flow (Gitflow)
A robust branching model for larger teams and releases.

**Main Branches:**
- **main/master**: Production-ready code
- **develop**: Integration branch for features

**Supporting Branches:**
- **feature/**: New features (branched from develop)
- **release/**: Release preparation (branched from develop)
- **hotfix/**: Emergency fixes (branched from main)

```
Workflow:
1. Create feature branch: git checkout -b feature/user-auth develop
2. Work on feature
3. Create Pull Request for code review
4. Merge to develop after approval
5. Create release branch when ready: git checkout -b release/v1.0 develop
6. Test and fix bugs in release branch
7. Merge to main and tag: git tag v1.0
8. Merge back to develop
```

**Advantages:**
- Clear separation of concerns
- Organized release management
- Support for hotfixes

**Disadvantages:**
- Complex for small teams
- Multiple branch integrations

#### 2. GitHub Flow
Simpler model ideal for continuous deployment.

**Branches:**
- **main**: Always deployable
- **feature branches**: One feature per branch

```
Workflow:
1. Create feature branch: git checkout -b feature/add-login
2. Commit changes regularly
3. Push branch to remote
4. Create Pull Request
5. Code review and discussion
6. Merge to main (auto-deploys)
7. Delete feature branch
```

**Advantages:**
- Simple and easy to understand
- Promotes frequent deployments
- Clear code review process

**Disadvantages:**
- Doesn't handle multiple versions
- Not suitable for scheduled releases

#### 3. Trunk-Based Development
Developers commit to main branch frequently with short-lived branches.

```
Workflow:
1. Create short-lived branch (1-2 days)
2. Commit frequently (daily)
3. Create minimal Pull Request
4. Quick review and merge to main
5. Deploy immediately
6. Delete branch
```

**Advantages:**
- Reduces merge conflicts
- Promotes continuous integration
- Faster development cycle

**Disadvantages:**
- Requires automated testing
- Strong discipline needed

---

## Merging and Rebasing

### Merge Operations

#### Fast-Forward Merge
When the current branch hasn't diverged from the target branch.

```bash
git checkout main
git merge feature/login
# Result: main moves to feature/login commit
```

**Visual:**
```
Before:          After:
main ──→         main
  ↗                ↗
feature/login → feature/login
```

#### Three-Way Merge
When branches have diverged, a merge commit is created.

```bash
git checkout main
git merge feature/login
# Git creates new commit combining both branches
```

**Visual:**
```
Before:          After:
main ──→          main
  ↗                ╲ (merge commit)
feature/login →   feature/login
```

#### Merge Strategies
```bash
git merge --strategy=recursive feature/branch    # Default (usually)
git merge --strategy=ours feature/branch         # Keep our changes
git merge -X theirs feature/branch               # Prefer their changes on conflicts
git merge --no-ff feature/branch                 # Always create merge commit
git merge --squash feature/branch                # Combine commits into one
```

#### Handling Merge Conflicts
When Git cannot automatically merge:

```bash
# Status shows conflicts
git status

# Open conflicted file - see markers:
<<<<<<< HEAD
  our changes
=======
  their changes
>>>>>>> feature/branch

# After resolving:
git add <resolved-file>
git commit -m "Resolve merge conflicts"

# Or abort merge:
git merge --abort
```

### Rebasing

Rebasing rewrites commit history by replaying commits on top of another branch.

#### Simple Rebase
```bash
git checkout feature/login
git rebase main
# Replays feature/login commits on top of main
```

**Visual:**
```
Before:          After:
main ──→         main ──→
  ↗                        ↗
feature/login →          feature/login
```

#### Interactive Rebase
Reorder, squash, or edit commits.

```bash
git rebase -i HEAD~3              # Interactive rebase last 3 commits
git rebase -i --root              # Rebase entire history
```

**Available Commands in Interactive Rebase:**
```
pick   - Use commit
reword - Use commit but edit message
squash - Use commit but combine with previous
fixup  - Like squash but discard log message
drop   - Remove commit
exec   - Execute command
```

Example workflow:
```bash
pick a1b2c3d Add authentication
squash d4e5f6g Fix password validation
fixup h7i8j9k Add error handling
reword l0m1n2o Update documentation
```

#### Rebase vs Merge

| Aspect | Rebase | Merge |
|--------|--------|-------|
| History | Linear, clean | Non-linear, shows branching |
| Readability | Better | More complex |
| Debugging | Harder with bisect | Easier to track merges |
| Safety | Rewrites history | Preserves history |
| Team Size | Better for small teams | Better for large teams |
| Use Case | Feature branches | Main branch integration |

#### Golden Rules of Rebasing
```
1. Never rebase commits that have been pushed publicly
2. Never rebase the main branch
3. Rebase feature branches locally before merging
4. Use merge for shared branches
```

---

## Collaboration Workflows

### Pull Requests (GitHub/GitLab)

A Pull Request (PR) is a method to propose changes and request code review.

#### Creating a Pull Request
```bash
# 1. Create feature branch
git checkout -b feature/user-profile

# 2. Make changes and commit
git add .
git commit -m "Add user profile page"

# 3. Push to remote
git push origin feature/user-profile

# 4. Open PR on GitHub/GitLab (via web interface)
```

#### PR Best Practices
- **Clear Title**: Describes what the PR does
- **Description**: Explains why and how the change was made
- **Linkable**: References related issues
- **Small PRs**: Keep changes focused (under 400 lines)
- **Clear Commits**: Logical, atomic commits
- **Branch Protection**: Require review before merge

#### Code Review Process
```
1. Author submits PR
2. Reviewers request changes or approve
3. Address review comments
4. Rebase and force push if needed
5. Final approval from maintainers
6. Merge using squash, rebase, or merge strategy
7. Delete branch after merge
```

### Collaborative Workflow Example

**Scenario**: Team of 3 developers, using GitHub Flow

```bash
# Developer 1: Starting new feature
git checkout -b feature/add-notifications develop
# ... make changes ...
git push origin feature/add-notifications
# Create PR, get reviewed, merge to develop

# Developer 2: Updating local repo
git fetch origin                  # Update remote tracking branches
git pull origin develop           # Get latest develop
# ... starts own feature ...

# Conflict Resolution:
git fetch origin
git rebase origin/develop         # Get latest and rebase
# Fix conflicts...
git add .
git rebase --continue
git push origin feature/name -f   # Force push after rebase
```

### Forking and Contributing

For open-source projects:

```bash
# 1. Fork repository on GitHub
# 2. Clone your fork
git clone https://github.com/your-username/project.git

# 3. Add upstream remote
git remote add upstream https://github.com/original-owner/project.git

# 4. Create feature branch
git checkout -b feature/improvement

# 5. Make changes and push to your fork
git push origin feature/improvement

# 6. Create Pull Request from your fork to upstream

# 7. Keep fork updated
git fetch upstream
git rebase upstream/main
git push origin main
```

---

## Remote Repositories

### Remote Configuration

#### Add and Remove Remotes
```bash
git remote add <name> <url>       # Add remote repository
git remote remove <name>          # Remove remote
git remote rename <old> <new>     # Rename remote
git remote -v                     # List remotes with URLs
git remote show <name>            # Show remote details
```

#### Common Remote Names
```
origin     - Your fork or main repository
upstream   - Original project repository (open-source)
backup     - Backup repository
```

### Fetching and Pulling

#### Fetch
Downloads changes from remote without modifying working directory.

```bash
git fetch                         # Fetch from all remotes
git fetch origin                  # Fetch from specific remote
git fetch origin main             # Fetch specific branch
git fetch --all                   # Fetch from all remotes
```

#### Pull
Combination of fetch and merge/rebase.

```bash
git pull                          # Fetch and merge (default)
git pull --rebase                 # Fetch and rebase
git pull origin main              # Pull specific branch
git pull --ff-only                # Only allow fast-forward
```

#### Setting Default Pull Behavior
```bash
git config --global pull.rebase true  # Default to rebase
git config --global pull.ff only      # Only fast-forward
```

### Pushing Changes

#### Push Operations
```bash
git push                          # Push current branch
git push origin main              # Push to specific remote and branch
git push -u origin feature/login  # Set upstream and push
git push --all                    # Push all branches
git push --tags                   # Push all tags
git push origin --delete <branch> # Delete remote branch
```

#### Force Push (Use Carefully!)
```bash
git push --force                  # Forcefully overwrite remote
git push --force-with-lease       # Safer alternative (checks remote)
```

**When to force push:**
- Squashing commits locally before first push
- Correcting commit history on feature branch
- Never on shared/main branch

### Tracking Branches

```bash
git branch -vv                    # Show tracking branches
git branch --set-upstream-to=origin/main main  # Set tracking
git branch -u origin/feature feature           # Set for feature branch
```

---

## Common Real-World Fixes

### Rename `master` to `main` Correctly

Many older repositories still use `master`. Modern GitHub projects usually use
`main`. Renaming locally is only the first step; the remote branch and upstream
tracking must also be updated.

```bash
# 1. Rename local branch
git branch -m master main

# 2. Push new main branch and set upstream
git push -u origin main

# 3. Change the default branch on GitHub:
# GitHub repository -> Settings -> Branches -> Default branch -> main

# 4. Delete old remote branch after default branch is changed
git push origin --delete master
```

If `git push` shows this error:

```text
fatal: The upstream branch of your current branch does not match
the name of your current branch
```

it means your local branch was renamed, but it still tracks the old remote
branch. Fix it with:

```bash
git push -u origin main
```

Check the tracking branch:

```bash
git branch -vv
```

Expected result:

```text
* main abc1234 [origin/main] your commit message
```

### Remove Old Remote Tracking References

Sometimes Git still shows deleted remote branches locally.

```bash
git fetch --prune
git branch -r
```

Set pruning as the default:

```bash
git config --global fetch.prune true
```

### Safer Daily Sync Workflow

Before starting work:

```bash
git checkout main
git pull --ff-only
git checkout -b feature/clear-name
```

While working:

```bash
git status
git add -p
git commit -m "feat(scope): describe change"
```

Before opening a PR:

```bash
git fetch origin
git rebase origin/main
git push -u origin feature/clear-name
```

After merge:

```bash
git checkout main
git pull --ff-only
git branch -d feature/clear-name
git fetch --prune
```

### Useful Git Aliases

Aliases reduce typing and make daily Git easier.

```bash
git config --global alias.st status
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.cm "commit -m"
git config --global alias.lg "log --oneline --graph --decorate --all"
```

Usage:

```bash
git st
git lg
```

### When to Use `merge`, `rebase`, and `revert`

| Situation | Recommended Command | Why |
|-----------|---------------------|-----|
| Update local `main` | `git pull --ff-only` | Avoids accidental merge commits |
| Update feature branch with latest `main` | `git rebase origin/main` | Keeps feature history clean |
| Combine approved PR into `main` | GitHub merge/squash/rebase button | Keeps review history visible |
| Undo a pushed commit | `git revert <commit>` | Does not rewrite shared history |
| Clean local unpushed commits | `git rebase -i HEAD~n` | Safe before sharing |

### Quick Safety Rules

1. Run `git status` before destructive commands.
2. Prefer `git restore` over old `git checkout -- <file>`.
3. Prefer `git revert` for commits already pushed to shared branches.
4. Prefer `git push --force-with-lease` over `git push --force`.
5. Never rewrite `main` history unless the whole team agrees.

---

## Advanced Topics

### Stashing

Save uncommitted changes without committing.

```bash
git stash                         # Stash current changes
git stash save "message"          # Stash with description
git stash list                    # List all stashes
git stash apply                   # Apply latest stash (keep it)
git stash apply stash@{0}         # Apply specific stash
git stash pop                     # Apply and remove latest stash
git stash drop stash@{0}          # Delete specific stash
git stash clear                   # Delete all stashes
```

**Use Case:**
```bash
# Working on feature, need to switch branches urgently
git stash
git checkout hotfix/critical-bug
# Fix bug, commit, merge
git checkout feature/my-feature
git stash pop
```

### Cherry-Pick

Apply specific commits from one branch to another.

```bash
git cherry-pick <commit>          # Apply single commit
git cherry-pick <commit1> <commit2> <commit3>  # Multiple commits
git cherry-pick <commit1>..<commit2>  # Range of commits
git cherry-pick --continue        # Continue after resolving conflicts
git cherry-pick --abort           # Abort cherry-pick
```

**Use Case:**
```bash
# Bug fix on develop needs to be in main immediately
git checkout main
git cherry-pick abc123def456      # Apply the fix commit
git push origin main
```

### Bisect

Find which commit introduced a bug using binary search.

```bash
git bisect start                  # Start bisect
git bisect bad                    # Mark current commit as bad
git checkout <old-commit>
git bisect good                   # Mark old commit as good
# Git automatically checks out middle commit
git bisect good                   # or git bisect bad (based on testing)
# Repeat until found...
git bisect reset                  # End bisect
```

### Reflog

Reference logs show recent HEAD movements.

```bash
git reflog                        # Show recent operations
git reflog show <branch>          # Show specific branch reflog
git checkout HEAD@{1}             # Recover deleted branch
git reset --hard HEAD@{n}         # Reset to previous state
```

### Submodules

Include one repository within another.

```bash
git submodule add <url> <path>    # Add submodule
git clone --recurse-submodules <url>  # Clone with submodules
git submodule update --init --recursive  # Initialize submodules
git submodule foreach git pull origin main  # Update all submodules
```

### Tags

Create named references to commits.

```bash
git tag <tag-name>                # Create lightweight tag
git tag -a v1.0 -m "Version 1.0"  # Create annotated tag
git tag -l                        # List tags
git show v1.0                     # Show tag details
git push origin v1.0              # Push specific tag
git push origin --tags            # Push all tags
git tag -d v1.0                   # Delete local tag
git push origin --delete v1.0     # Delete remote tag
```

**Semantic Versioning:**
```
v1.2.3
↑ ↑ ↑
│ │ └─ Patch (bug fixes)
│ └─── Minor (new features, backward compatible)
└───── Major (breaking changes)
```

---

## Industry Best Practices

### Commit Message Standards

#### Conventional Commits Format
```
<type>(<scope>): <subject>

<body>

<footer>

Example:
feat(auth): add JWT token generation

Implement JWT token creation with configurable expiration.
Add password hashing using bcrypt for security.

Closes #123
Breaking-change: API returns token instead of session
```

#### Types (Conventional Commits)
```
feat      - New feature
fix       - Bug fix
docs      - Documentation changes
style     - Code style changes (not affecting logic)
refactor  - Code refactoring
perf      - Performance improvements
test      - Test additions/modifications
chore     - Build, dependencies, configuration
ci        - CI/CD configuration
```

#### Commit Message Guidelines
1. **First Line**: Imperative mood, present tense ("add" not "added")
2. **Length**: Keep subject under 50 characters
3. **Body**: Wrap at 72 characters, explain what and why
4. **References**: Link to issues (#123)
5. **Scope**: Optional but helpful for large projects
6. **Footer**: Breaking changes, co-authors, relates to

**Bad Example:**
```
fixed stuff
```

**Good Example:**
```
fix(payment): handle null payment method gracefully

When processing payment without a selected method,
the system now returns 400 Bad Request with clear error message
instead of crashing with NullPointerException.

Fixes #456
```

### Code Review Standards

#### Pre-Review Checklist
- [ ] Code compiles/builds successfully
- [ ] All tests pass locally
- [ ] No merge conflicts
- [ ] Commits are atomic and well-message
- [ ] Self-reviewed before requesting review
- [ ] Related documentation updated

#### Reviewer Checklist
- [ ] Code follows project style guide
- [ ] Logic is correct and efficient
- [ ] Tests cover edge cases
- [ ] No security vulnerabilities
- [ ] Documentation is accurate
- [ ] Performance impact considered

#### Review Comments Best Practices
```
Good:
"This SQL query could have N+1 problem. Consider using JOIN instead."

Avoid:
"This is wrong"
```

### .gitignore Management

```gitignore
# Java
*.class
*.jar
target/
.classpath

# IDE
.idea/
.vscode/
*.iml

# OS
.DS_Store
Thumbs.db

# Environment
.env
.env.local

# Logs
*.log
logs/

# Dependencies
node_modules/
__pycache__/
```

### Large File Handling

#### Git LFS (Large File Storage)
```bash
git lfs install                   # Initialize Git LFS
git lfs track "*.psd"             # Track large file type
git add .gitattributes
git add design.psd
git commit -m "Add design file"
```

### Workflows and Git Hooks

#### Pre-commit Hook
```bash
#!/bin/bash
# .git/hooks/pre-commit

# Run linter
npm run lint
if [ $? -ne 0 ]; then
    echo "Linting failed. Commit aborted."
    exit 1
fi
```

#### Commit Message Validation
```bash
#!/bin/bash
# .git/hooks/commit-msg

MESSAGE=$(cat $1)
if ! echo "$MESSAGE" | grep -qE '^(feat|fix|docs|style|refactor|perf|test|chore)'; then
    echo "Invalid commit message format"
    exit 1
fi
```

### Security Best Practices

#### Never Commit Secrets
```bash
# BAD - NEVER DO THIS
password = "mySecretPassword123"
API_KEY = "sk_live_xxx"

# GOOD - Use environment variables
password = os.getenv('DB_PASSWORD')
api_key = os.getenv('API_KEY')
```

#### Remove Secrets If Accidentally Committed
```bash
# First: rotate/revoke the exposed secret immediately.
# Removing it from Git history does not make the leaked value safe again.

# Option 1: Using BFG Repo Cleaner
bfg --delete-files secrets.txt

# Option 2: Using git filter-repo (modern Git history rewrite tool)
git filter-repo --path secrets.txt --invert-paths

# Option 3: Latest local commit only, before pushing
git reset --soft HEAD~1
git rm secrets.txt
git commit --amend -m "Remove secrets"
```

#### Signing Commits
```bash
# Generate GPG key
gpg --gen-key

# Configure Git
git config --global user.signingkey <KEY_ID>
git config --global commit.gpgsign true

# Sign specific commit
git commit -S -m "Important security fix"

# Verify commits
git log --show-signature
```

---

## Interview Questions

### Beginner Level

**1. What is Git and why is it important?**
- Git is a distributed version control system
- Allows tracking changes, collaboration, and maintaining history
- Industry standard for source code management

**2. What's the difference between Git and GitHub?**
- Git: Version control system (local or self-hosted)
- GitHub: Cloud hosting platform for Git repositories

**3. Explain the Git workflow (stages of a file)?**
- Untracked → Staged → Committed
- Modified → Staged → Committed

**4. What's the difference between `git pull` and `git fetch`?**
- `git fetch`: Downloads changes without modifying working directory
- `git pull`: Fetches and automatically merges changes

**5. How do you undo the last commit?**
```bash
git reset --soft HEAD~1    # Keep changes
git reset --hard HEAD~1    # Discard changes
git revert HEAD            # Create new commit undoing changes
```

### Intermediate Level

**6. Explain merge conflicts and how to resolve them?**
- Occur when Git cannot automatically merge changes
- Happens when both branches modify same lines
- Resolved by manually editing files and marking as resolved

**7. What are the advantages of rebasing over merging?**
- Linear history
- Cleaner commit history
- Easier to understand changes
- Downside: rewrites commit history (risky for shared branches)

**8. Describe Git Flow branching model?**
- Main branch for production
- Develop branch for integration
- Feature/release/hotfix branches
- Complex but good for managed releases

**9. How do you handle a large file in Git?**
- Use Git LFS (Large File Storage)
- Store file pointer instead of actual file
- Useful for binary files, images, videos

**10. What is cherry-pick and when to use it?**
- Apply specific commits from one branch to another
- Use for backporting fixes, hotfixes to multiple branches

### Advanced Level

**11. Explain the differences between hard reset, soft reset, and mixed reset?**
```
git reset --hard   : Discards all changes
git reset --soft   : Keeps changes in staging area
git reset --mixed  : Keeps changes in working directory (default)
```

**12. How would you recover deleted commits?**
- Use `git reflog` to find lost commits
- `git checkout HEAD@{n}` to recover
- Can reset to any point in reflog

**13. Describe interactive rebase and its use cases?**
- Allows reordering, squashing, editing commits
- Use case: Clean up feature branch history before merging
- Example: `git rebase -i HEAD~5`

**14. What are the security risks with force push?**
- Overwrites remote history
- Can cause other developers to lose work
- Use `--force-with-lease` as safer alternative
- Never on shared/main branch

**15. How do you handle submodules in Git?**
- Include external repository within project
- Use `git submodule add <url>`
- Clone with `--recurse-submodules`
- Update with `git submodule update --init --recursive`

---

## Common Git Commands Reference

### Daily Usage
```bash
git status                        # Check status
git add .                         # Stage all changes
git commit -m "message"           # Commit changes
git push                          # Push to remote
git pull                          # Pull from remote
git log --oneline -n 10          # View recent commits
```

### Branching
```bash
git branch                        # List branches
git checkout -b feature/name      # Create and switch branch
git push -u origin feature/name   # Push new branch
git merge main                    # Merge main into current
git branch -d feature/name        # Delete branch
```

### Undoing
```bash
git restore <file>                # Discard file changes
git reset --soft HEAD~1           # Undo last commit (keep changes)
git revert HEAD                   # Create undo commit
git clean -fd                     # Remove untracked files
```

### Troubleshooting
```bash
git reflog                        # Find lost commits
git status --porcelain            # Machine-readable status
git diff --no-index file1 file2   # Compare files
git log --grep="keyword"          # Search commits
```

---

## Conclusion

Git is a powerful tool that requires practice to master. Focus on:
1. Understanding fundamental concepts (commits, branches, remotes)
2. Following industry best practices (meaningful commits, code review)
3. Choosing appropriate branching strategy for your team
4. Regular practice with common operations
5. Learning advanced features as needed

**Key Takeaway**: Git is not just about version control; it's about effective collaboration and maintaining code quality through proper workflows and discipline.

---

## References and Resources

- Official Git Documentation: https://git-scm.com/doc
- GitHub Learning Lab: https://lab.github.com/
- Atlassian Git Tutorials: https://www.atlassian.com/git/tutorials
- Pro Git Book: https://git-scm.com/book/en/v2
- GitHub Guides: https://guides.github.com/
