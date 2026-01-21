# Git Guide

## Table of Contents
1. [Git Submodules](#git-submodules)
2. [Basic Git Commands](#basic-git-commands)

---

# Git Submodules

## What are Git Submodules?

A **Git submodule** is a repository embedded inside another repository. It allows you to keep a Git repository as a subdirectory of another Git repository while keeping their histories separate.

### Common Use Cases:
- Sharing common libraries across multiple projects
- Including third-party dependencies
- Managing microservices in a monorepo
- Separating frontend and backend repositories

### Structure Example:
```
main-project/
├── .git/
├── .gitmodules          # Submodule configuration file
├── README.md
├── src/
└── submodule-1/         # External repository (submodule)
    ├── .git/            # Points to external repo
    └── ...
└── submodule-2/         # Another external repository
    ├── .git/
    └── ...
```

---

## 1. Cloning a Repository with Submodules

### Method 1: Clone with Submodules (Recommended)

```bash
# Clone the main repo and initialize all submodules in one command
git clone --recurse-submodules <repository-url>

# Example:
git clone --recurse-submodules https://github.com/user/main-project.git
```

**What this does:**
- Clones the main repository
- Automatically initializes all submodules
- Checks out the correct commit for each submodule

---

### Method 2: Clone First, Then Initialize Submodules

If you already cloned without `--recurse-submodules`:

```bash
# 1. Clone the main repository (already done)
git clone https://github.com/user/main-project.git

# 2. Go into the project directory
cd main-project

# 3. Initialize submodules (registers submodules from .gitmodules)
git submodule init

# 4. Fetch and checkout submodule content
git submodule update

# OR combine steps 3 & 4:
git submodule update --init --recursive
```

**Explanation:**
- `git submodule init` - Copies submodule URLs from `.gitmodules` to `.git/config`
- `git submodule update` - Fetches the submodule data and checks out the specified commit
- `--recursive` - Also initializes nested submodules (submodules within submodules)

---

## 2. Checking Submodule Status

```bash
# Show submodule status
git submodule status

# Output example:
# 47d8d8f2b1e6c8c... submodule-1 (heads/main)
# -a1b2c3d4e5f6g7... submodule-2 (v1.2.3)
#  ^
#  │
#  └─ Status indicators:
#     - (dash) = submodule not initialized
#     + (plus) = currently checked out commit different from main repo's record
#     (space) = submodule is at correct commit
```

```bash
# Show detailed information
git submodule

# List all submodules
git config --file .gitmodules --get-regexp path
```

---

## 3. Pulling Updates

### Pull Main Repository + All Submodules

```bash
# Update main repo and all submodules
git pull --recurse-submodules

# If you want to fetch and update submodules automatically
git config --global submodule.recurse true
# Now 'git pull' will always update submodules
```

---

### Pull Main Repository Only

```bash
# Pull main repository
git pull origin main

# Then manually update submodules
git submodule update --remote --merge
```

**What `--remote` does:**
- Fetches the latest changes from the submodule's remote repository
- Without `--remote`, it just checks out the commit recorded in the main repo

---

### Pull Specific Submodule

```bash
# Go into the submodule directory
cd submodule-1

# Pull latest changes
git pull origin main

# Go back to main project
cd ..

# Update the main repo to track the new submodule commit (see section 4)
git add submodule-1
git commit -m "Update submodule-1 to latest"
```

---

## 4. Pushing Changes

### Understanding Submodule Workflow

**Important:** Submodules are **independent repositories**. You must commit and push in BOTH:
1. The submodule itself
2. The main repository (to update the submodule pointer)

---

### Scenario 1: You Modified Code in a Submodule

```bash
# Step 1: Go into the submodule
cd submodule-1

# Step 2: Check status
git status

# Step 3: Add changes
git add .

# Step 4: Commit changes
git commit -m "Fix bug in submodule-1"

# Step 5: Push to submodule's remote
git push origin main

# Step 6: Go back to main project
cd ..

# Step 7: Update main repo to point to new submodule commit
git add submodule-1
git commit -m "Update submodule-1 reference"

# Step 8: Push main repository
git push origin main
```

**Why Step 6-8?**
- The main repo stores a specific commit hash for each submodule
- You need to update that hash so others get your submodule changes

---

### Scenario 2: Push Everything at Once

```bash
# Push main repo and ensure submodules are pushed first
git push --recurse-submodules=on-demand

# What this does:
# 1. Pushes submodule changes first
# 2. Then pushes main repo changes
# 3. Fails if submodules haven't been pushed (safety check)
```

---

### Scenario 3: You Only Modified Main Repository

```bash
# Normal git workflow
git add .
git commit -m "Update main project"
git push origin main
```

---

## 5. Common Workflows

### Update All Submodules to Latest

```bash
# Fetch latest changes for all submodules
git submodule update --remote --merge

# Commit the updated references
git add .
git commit -m "Update all submodules to latest"
git push origin main
```

---

### Switch Branches in Main Repo

```bash
# Switch branch in main repo
git checkout feature-branch

# Update submodules to match this branch's recorded commits
git submodule update --init --recursive
```

**Why?** Different branches might reference different submodule commits.

---

### Working on a Feature Across Main Repo and Submodule

```bash
# 1. Create feature branch in main repo
git checkout -b feature/new-feature

# 2. Go into submodule
cd submodule-1

# 3. Create feature branch in submodule
git checkout -b feature/submodule-changes

# 4. Make changes in submodule
# ... edit files ...
git add .
git commit -m "Submodule changes for feature"
git push origin feature/submodule-changes

# 5. Go back to main repo
cd ..

# 6. Make changes in main repo
# ... edit files ...

# 7. Commit submodule reference update
git add submodule-1
git add <other-files>
git commit -m "Feature implementation with submodule updates"

# 8. Push main repo
git push origin feature/new-feature
```

---

## 6. Common Issues and Solutions

### Issue 1: Submodule Folder is Empty After Clone

**Cause:** Forgot to initialize submodules

**Solution:**
```bash
git submodule update --init --recursive
```

---

### Issue 2: Submodule in Detached HEAD State

**Cause:** Submodules checkout specific commits, not branches

**Solution:**
```bash
# Go into submodule
cd submodule-1

# Check current state
git status
# Output: HEAD detached at a1b2c3d

# Checkout a branch
git checkout main

# OR create a new branch from current commit
git checkout -b fix/my-changes
```

---

### Issue 3: Conflicts When Pulling Submodules

**Solution:**
```bash
# Go into submodule
cd submodule-1

# Resolve conflicts manually
git status
# ... fix conflicts in files ...

# Complete the merge
git add .
git commit -m "Resolve merge conflict"

# Go back and update main repo
cd ..
git add submodule-1
git commit -m "Update submodule after conflict resolution"
```

---

### Issue 4: Can't Push Submodule (Permission Denied)

**Cause:** You don't have write access to the submodule repository

**Solution:**
- Contact the submodule repository owner
- Fork the submodule and update `.gitmodules` to point to your fork
- Only commit changes to main repo, not submodule

---

### Issue 5: Accidentally Deleted Submodule Folder

**Solution:**
```bash
# Re-initialize submodule
git submodule update --init --recursive
```

---

## 7. Advanced Commands

### Add a New Submodule

```bash
# Add a new submodule
git submodule add <repository-url> <path>

# Example:
git submodule add https://github.com/user/library.git libs/library

# Commit the changes
git add .gitmodules libs/library
git commit -m "Add library submodule"
git push
```

---

### Remove a Submodule

```bash
# 1. Delete submodule section from .gitmodules
git config -f .gitmodules --remove-section submodule.path/to/submodule

# 2. Delete submodule section from .git/config
git config -f .git/config --remove-section submodule.path/to/submodule

# 3. Remove from index
git rm --cached path/to/submodule

# 4. Remove the actual files
rm -rf path/to/submodule
rm -rf .git/modules/path/to/submodule

# 5. Commit
git add .gitmodules
git commit -m "Remove submodule"
git push
```

---

### Update Submodule URL

```bash
# Edit .gitmodules file
git config -f .gitmodules submodule.path/to/submodule.url <new-url>

# Sync the new URL to .git/config
git submodule sync

# Update submodule
git submodule update --init --recursive

# Commit the change
git add .gitmodules
git commit -m "Update submodule URL"
git push
```

---

### View Submodule Diff

```bash
# See which commit the submodule changed to
git diff submodule-1

# See actual code changes in submodule
git diff --submodule=diff

# Or configure git to always show detailed diffs
git config --global diff.submodule diff
```

---

## 8. Best Practices

### 1. Always Use Specific Commits (Not Branches)
✅ **Good:** Main repo references specific commit hash
❌ **Bad:** Trying to reference a branch name

**Why?** Ensures reproducibility. Everyone gets the same code.

---

### 2. Push Submodules Before Main Repo
```bash
# Use this flag to ensure submodules are pushed first
git push --recurse-submodules=on-demand
```

---

### 3. Document Submodule Workflow in README
```markdown
## Setup

Clone with submodules:
\`\`\`bash
git clone --recurse-submodules <repo-url>
\`\`\`

Or if already cloned:
\`\`\`bash
git submodule update --init --recursive
\`\`\`
```

---

### 4. Use Git Hooks to Auto-Update Submodules

Create `.git/hooks/post-checkout`:
```bash
#!/bin/bash
git submodule update --init --recursive
```

Make it executable:
```bash
chmod +x .git/hooks/post-checkout
```

---

### 5. Check Submodule Status Before Committing
```bash
# Show if submodules have uncommitted changes
git status

# Show if submodules are at different commits than recorded
git submodule status
```

---

## 9. Quick Reference Cheatsheet

| Task | Command |
|------|---------|
| Clone with submodules | `git clone --recurse-submodules <url>` |
| Initialize submodules after clone | `git submodule update --init --recursive` |
| Pull main repo + submodules | `git pull --recurse-submodules` |
| Update submodules to latest | `git submodule update --remote --merge` |
| Check submodule status | `git submodule status` |
| Push main repo + submodules | `git push --recurse-submodules=on-demand` |
| Add new submodule | `git submodule add <url> <path>` |
| Go into submodule | `cd <submodule-path>` |
| Checkout branch in submodule | `cd <submodule> && git checkout main` |

---

## 10. Understanding .gitmodules File

The `.gitmodules` file tracks submodule configuration:

```ini
[submodule "submodule-1"]
    path = submodule-1
    url = https://github.com/user/submodule-1.git
    branch = main

[submodule "libs/external-lib"]
    path = libs/external-lib
    url = https://github.com/org/external-lib.git
    branch = stable
```

**Fields:**
- `path` - Where the submodule lives in your project
- `url` - Remote repository URL
- `branch` - (Optional) Which branch to track

---

## 11. Typical Daily Workflow

### Morning: Pull Latest Changes
```bash
# Pull main repo and all submodules
git pull --recurse-submodules

# OR
git pull
git submodule update --remote --merge
```

---

### During Work: Make Changes
```bash
# Work in main repo
git add .
git commit -m "Update feature"

# Work in submodule
cd submodule-1
git checkout -b my-feature
# ... make changes ...
git add .
git commit -m "Submodule changes"
git push origin my-feature
cd ..

# Update reference in main repo
git add submodule-1
git commit -m "Update submodule reference"
```

---

### End of Day: Push Everything
```bash
# Push submodules first, then main repo
git push --recurse-submodules=on-demand

# OR manually
cd submodule-1
git push origin my-feature
cd ..
git push origin main
```

---

## Summary

### Key Concepts:
1. **Submodules are separate repositories** - They have their own history
2. **Main repo tracks commits, not branches** - Points to specific commit hashes
3. **Two-step commit process** - Commit in submodule, then update reference in main repo
4. **Always initialize after clone** - Use `--recurse-submodules` or `git submodule update --init`

### Most Important Commands:
```bash
# Clone with submodules
git clone --recurse-submodules <url>

# Initialize after regular clone
git submodule update --init --recursive

# Pull updates
git pull --recurse-submodules

# Push (ensures submodules pushed first)
git push --recurse-submodules=on-demand
```

---

# Basic Git Commands

## Essential Git Commands

### Configuration
```bash
# Set username
git config --global user.name "Your Name"

# Set email
git config --global user.email "your.email@example.com"

# View configuration
git config --list
```

### Repository Setup
```bash
# Initialize new repository
git init

# Clone existing repository
git clone <url>
```

### Basic Workflow
```bash
# Check status
git status

# Add files to staging
git add <file>
git add .                    # Add all changes

# Commit changes
git commit -m "Commit message"

# Push to remote
git push origin main

# Pull from remote
git pull origin main
```

### Branching
```bash
# Create new branch
git checkout -b <branch-name>

# Switch branches
git checkout <branch-name>

# List branches
git branch

# Merge branch
git merge <branch-name>

# Delete branch
git branch -d <branch-name>
```

### History
```bash
# View commit history
git log

# View compact history
git log --oneline

# View specific file history
git log <file>
```

---

**End of Git Guide**
