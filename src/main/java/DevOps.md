# DevOps - Complete Reference Guide

---

## Table of Contents

1. [Linux Basics & Essential Commands](#1-linux-basics--essential-commands)
2. [Shell Scripting](#2-shell-scripting)
3. [Git & Version Control](#3-git--version-control)
4. [CI/CD — Continuous Integration & Delivery](#4-cicd--continuous-integration--delivery)
5. [Docker](#5-docker)
6. [Docker Compose](#6-docker-compose)
7. [Kubernetes (K8s)](#7-kubernetes-k8s)
8. [Nginx & Reverse Proxy](#8-nginx--reverse-proxy)
9. [Monitoring & Logging](#9-monitoring--logging)
10. [Infrastructure as Code (Terraform)](#10-infrastructure-as-code-terraform)
11. [Networking for DevOps](#11-networking-for-devops)
12. [Interview Questions](#12-interview-questions)

---

## 1. Linux Basics & Essential Commands

### File & Directory Operations

```bash
# Navigation
pwd                      # Print working directory
cd /var/log              # Change directory
cd ..                    # Go one level up
cd ~                     # Go to home directory

# Listing
ls -la                   # List all files with details (permissions, size, date)
ls -lh                   # Human-readable file sizes
ls -lt                   # Sort by modification time

# File Operations
cp file1.txt /backup/              # Copy file
cp -r dir1/ dir2/                  # Copy directory recursively
mv file1.txt /new/location/        # Move or rename
rm file.txt                        # Delete file
rm -rf directory/                  # Delete directory recursively (DANGEROUS)
mkdir -p /path/to/nested/dir       # Create nested directories
touch newfile.txt                  # Create empty file or update timestamp

# Viewing Files
cat file.txt             # Print entire file
less file.txt            # View with scroll (q to quit)
head -n 20 file.txt      # First 20 lines
tail -n 50 file.txt      # Last 50 lines
tail -f /var/log/app.log # Follow log in real-time (very useful!)
```

### User & Permission Management

```
File Permission Structure:
─────────────────────────
  -rwxrw-r--  1  veer  devs  4096  Jan 10  app.sh
  │├──┤├─┤├─┤     │     │
  │  │   │  │     │     └── Group
  │  │   │  │     └── Owner
  │  │   │  └── Others: read only (4)
  │  │   └── Group: read + write (6)
  │  └── Owner: read + write + execute (7)
  └── File type (- = file, d = directory, l = link)
```

```bash
# Permission numbers
# r = 4, w = 2, x = 1
chmod 755 script.sh      # Owner: rwx, Group: r-x, Others: r-x
chmod 644 config.txt     # Owner: rw-, Group: r--, Others: r--
chmod +x script.sh       # Add execute permission for all
chmod -R 755 /app/       # Recursive permission change

# Ownership
chown veer:devops file.txt        # Change owner and group
chown -R veer:devops /app/        # Recursive ownership change

# User Management
useradd -m -s /bin/bash john      # Create user with home dir and bash shell
passwd john                        # Set password
usermod -aG docker john            # Add user to docker group
su - john                          # Switch to user
```

### Process Management

```bash
ps aux                    # List all running processes
ps aux | grep java        # Find Java processes
top                       # Real-time process monitoring
htop                      # Better version of top (if installed)

kill PID                  # Graceful kill (SIGTERM)
kill -9 PID               # Force kill (SIGKILL)
killall java              # Kill all Java processes

# Background & Foreground
nohup java -jar app.jar &          # Run in background, survives logout
jobs                                # List background jobs
fg %1                               # Bring job 1 to foreground

# System Resources
df -h                     # Disk space (human readable)
du -sh /var/log/          # Directory size
free -h                   # Memory usage
uptime                    # System uptime and load
```

### Text Processing & Searching

```bash
# grep — Search inside files
grep "ERROR" app.log                  # Find lines containing "ERROR"
grep -r "password" /etc/              # Search recursively in directory
grep -i "error" app.log               # Case insensitive search
grep -n "Exception" app.log           # Show line numbers
grep -c "ERROR" app.log               # Count matching lines
grep -v "DEBUG" app.log               # Exclude lines with "DEBUG"

# find — Search for files
find / -name "*.log" -mtime -7        # Log files modified in last 7 days
find / -size +100M                    # Files larger than 100MB
find /tmp -type f -mtime +30 -delete  # Delete files older than 30 days

# awk — Column extraction
awk '{print $1, $4}' access.log       # Print 1st and 4th columns
awk -F: '{print $1}' /etc/passwd      # Print usernames (: delimiter)

# sed — Stream editor
sed 's/old/new/g' file.txt            # Replace all occurrences
sed -i 's/8080/9090/g' config.yml     # In-place replacement

# Piping and Redirection
cat app.log | grep "ERROR" | wc -l                 # Count errors
echo "backup done" >> /var/log/backup.log           # Append to file
command > output.txt 2>&1                           # Redirect stdout + stderr
cat app.log | sort | uniq -c | sort -rn | head -10  # Top 10 repeated lines
```

### Networking Commands

```bash
curl -X GET http://localhost:8080/api/health       # HTTP GET request
curl -X POST -H "Content-Type: application/json" \
     -d '{"name":"test"}' http://localhost:8080/api  # HTTP POST

wget https://example.com/file.zip     # Download file
netstat -tulnp                        # Show listening ports
ss -tulnp                             # Modern alternative to netstat
lsof -i :8080                         # Who is using port 8080?

# SSH
ssh user@192.168.1.10                 # Connect to remote server
ssh -i key.pem ec2-user@ip            # Connect with key file
scp file.txt user@server:/path/       # Copy file to remote
```

### Package Management

```bash
# Debian/Ubuntu (apt)
apt update                  # Update package list
apt install nginx           # Install package
apt remove nginx            # Remove package
apt upgrade                 # Upgrade all packages

# RedHat/CentOS (yum/dnf)
yum install httpd           # Install package
yum update                  # Update all packages
dnf install nginx           # Modern replacement for yum

# systemd — Service Management
systemctl start nginx       # Start service
systemctl stop nginx        # Stop service
systemctl restart nginx     # Restart service
systemctl enable nginx      # Start on boot
systemctl status nginx      # Check status
journalctl -u nginx -f      # Follow service logs
```

---

## 2. Shell Scripting

### Basic Script Structure

```bash
#!/bin/bash
# This is a comment

# Variables
APP_NAME="my-app"
VERSION="1.0.0"
PORT=8080

echo "Deploying $APP_NAME version $VERSION on port $PORT"
```

### Conditionals

```bash
#!/bin/bash

# if-else
if [ -f "/app/config.yml" ]; then
    echo "Config file exists"
elif [ -f "/app/config.yaml" ]; then
    echo "Found config.yaml instead"
else
    echo "No config file found!"
    exit 1
fi

# Comparison operators
# -eq (equal), -ne (not equal), -gt (greater), -lt (less)
# -f (file exists), -d (directory exists), -z (string empty)

if [ "$STATUS_CODE" -eq 200 ]; then
    echo "Service is healthy"
fi

if [ -z "$DATABASE_URL" ]; then
    echo "ERROR: DATABASE_URL is not set"
    exit 1
fi
```

### Loops

```bash
#!/bin/bash

# For loop — deploy to multiple servers
SERVERS=("web-01" "web-02" "web-03")
for server in "${SERVERS[@]}"; do
    echo "Deploying to $server..."
    ssh deploy@$server "cd /app && docker pull myapp:latest && docker restart myapp"
done

# While loop — wait for service to be ready
MAX_RETRIES=30
COUNT=0
while [ $COUNT -lt $MAX_RETRIES ]; do
    if curl -s http://localhost:8080/health > /dev/null 2>&1; then
        echo "Service is UP!"
        break
    fi
    echo "Waiting for service... ($COUNT/$MAX_RETRIES)"
    sleep 2
    COUNT=$((COUNT + 1))
done

if [ $COUNT -eq $MAX_RETRIES ]; then
    echo "Service failed to start!"
    exit 1
fi
```

### Functions

```bash
#!/bin/bash

# Function to check service health
check_health() {
    local SERVICE_URL=$1
    local SERVICE_NAME=$2

    STATUS=$(curl -s -o /dev/null -w "%{http_code}" "$SERVICE_URL/health")
    if [ "$STATUS" -eq 200 ]; then
        echo "✓ $SERVICE_NAME is healthy"
        return 0
    else
        echo "✗ $SERVICE_NAME is DOWN (status: $STATUS)"
        return 1
    fi
}

# Usage
check_health "http://localhost:8080" "User Service"
check_health "http://localhost:8081" "Order Service"
```

### Practical DevOps Script — Deployment

```bash
#!/bin/bash
set -euo pipefail    # Exit on error, undefined vars, pipe failures

APP_NAME="my-spring-app"
JAR_FILE="target/${APP_NAME}.jar"
LOG_FILE="/var/log/${APP_NAME}.log"
PID_FILE="/var/run/${APP_NAME}.pid"

deploy() {
    echo "=== Starting Deployment ==="

    # Step 1: Build
    echo "[1/4] Building application..."
    mvn clean package -DskipTests

    if [ ! -f "$JAR_FILE" ]; then
        echo "ERROR: Build failed — JAR not found"
        exit 1
    fi

    # Step 2: Stop old instance
    echo "[2/4] Stopping old instance..."
    if [ -f "$PID_FILE" ]; then
        OLD_PID=$(cat "$PID_FILE")
        kill "$OLD_PID" 2>/dev/null || true
        sleep 3
    fi

    # Step 3: Start new instance
    echo "[3/4] Starting new instance..."
    nohup java -jar "$JAR_FILE" \
        --server.port=8080 \
        --spring.profiles.active=prod \
        > "$LOG_FILE" 2>&1 &
    echo $! > "$PID_FILE"

    # Step 4: Health check
    echo "[4/4] Waiting for health check..."
    for i in $(seq 1 30); do
        if curl -s http://localhost:8080/actuator/health | grep -q "UP"; then
            echo "=== Deployment Successful! ==="
            return 0
        fi
        sleep 2
    done

    echo "ERROR: Health check failed"
    exit 1
}

deploy
```

---

## 3. Git & Version Control

### Git Architecture

```
Working Directory ──(git add)──→ Staging Area ──(git commit)──→ Local Repo ──(git push)──→ Remote Repo
         ↑                                                            │
         └────────────────────(git checkout / pull)────────────────────┘
```

### Essential Commands

```bash
# Repository Setup
git init                              # Initialize new repo
git clone https://github.com/user/repo.git   # Clone remote repo

# Daily Workflow
git status                            # Check current state
git add file.txt                      # Stage specific file
git add .                             # Stage all changes
git commit -m "feat: add login API"   # Commit with message
git push origin feature/login         # Push to remote branch
git pull origin main                  # Pull latest from main

# Branching
git branch                            # List branches
git branch feature/payment            # Create branch
git checkout feature/payment          # Switch branch
git checkout -b feature/payment       # Create + switch (shortcut)
git branch -d feature/payment         # Delete branch (if merged)

# Merging
git checkout main
git merge feature/payment             # Merge feature into main

# Viewing History
git log --oneline --graph             # Compact visual log
git diff                              # Unstaged changes
git diff --staged                     # Staged changes
git show abc1234                      # Show specific commit
```

### Branching Strategy — Git Flow

```
main (production)
 │
 ├── develop (integration)
 │     │
 │     ├── feature/login ──────── (merge back to develop)
 │     ├── feature/payment ─────── (merge back to develop)
 │     │
 │     └── release/1.0 ──────── (merge to main + develop)
 │
 └── hotfix/critical-bug ──────── (merge to main + develop)
```

| Branch | Purpose | Created From | Merges Into |
|--------|---------|-------------|-------------|
| `main` | Production-ready code | — | — |
| `develop` | Integration branch | `main` | `main` (via release) |
| `feature/*` | New features | `develop` | `develop` |
| `release/*` | Release preparation | `develop` | `main` + `develop` |
| `hotfix/*` | Critical production fix | `main` | `main` + `develop` |

### Merge vs Rebase

```
MERGE (preserves history):
     A──B──C  (main)
          \    \
           D──E──M  (feature)     ← M is the merge commit

REBASE (linear history):
     A──B──C  (main)
              \
               D'──E'  (feature)  ← commits re-applied on top of C
```

| Aspect | Merge | Rebase |
|--------|-------|--------|
| History | Preserves branching history | Creates linear history |
| Merge commit | Yes (extra commit) | No |
| Safety | Safe for shared branches | **NEVER rebase shared branches** |
| Use when | Merging feature → main | Updating feature with latest main |

### Merge Conflicts

```bash
# When conflict occurs
git merge feature/payment
# CONFLICT (content): Merge conflict in UserService.java

# 1. Open the file — you'll see:
<<<<<<< HEAD
    return userRepository.findById(id);      # Your version
=======
    return userRepository.findByIdOrThrow(id);  # Their version
>>>>>>> feature/payment

# 2. Resolve manually — keep what you want
# 3. Stage and commit
git add UserService.java
git commit -m "resolve merge conflict in UserService"
```

### Git Stash

```bash
# Save work temporarily (without committing)
git stash                             # Stash current changes
git stash save "WIP: payment logic"   # Stash with message
git stash list                        # List all stashes
git stash pop                         # Apply latest stash + remove it
git stash apply stash@{1}             # Apply specific stash (keep it)
git stash drop stash@{0}              # Delete specific stash
```

### Commit Message Convention

```
<type>(<scope>): <subject>

Types:
  feat     → New feature
  fix      → Bug fix
  docs     → Documentation
  style    → Formatting (no code change)
  refactor → Code restructuring
  test     → Adding tests
  chore    → Build scripts, CI config

Examples:
  feat(auth): add JWT token refresh endpoint
  fix(payment): handle null pointer in refund flow
  chore(ci): add SonarQube quality gate
```

---

## 4. CI/CD — Continuous Integration & Delivery

### What is CI/CD?

```
CI/CD Pipeline Flow:
════════════════════

  Developer         CI Server              CD Pipeline            Production
  ─────────         ─────────              ───────────            ──────────
  git push ───→ ┌──────────────┐     ┌──────────────────┐     ┌──────────────┐
                │  1. Checkout  │     │  4. Build Image   │     │  7. Deploy   │
                │  2. Build     │────→│  5. Push to       │────→│  8. Health   │
                │  3. Test      │     │     Registry      │     │     Check    │
                └──────────────┘     │  6. Update Config  │     │  9. Monitor  │
                                     └──────────────────┘     └──────────────┘

  ├──── Continuous Integration ────┤├──── Continuous Delivery ──────────────────┤
```

| Term | Meaning |
|------|---------|
| **CI** | Automatically build + test on every push |
| **CD (Delivery)** | Automatically deploy to staging; manual approval for prod |
| **CD (Deployment)** | Automatically deploy to prod (no manual step) |

### Jenkins Pipeline (Jenkinsfile)

```groovy
pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "myregistry/my-spring-app"
        DOCKER_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/user/my-spring-app.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.host.url=http://sonar:9000'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
            }
        }

        stage('Deploy to Staging') {
            steps {
                sh "kubectl set image deployment/my-app my-app=${DOCKER_IMAGE}:${DOCKER_TAG} -n staging"
            }
        }

        stage('Deploy to Production') {
            input {
                message "Deploy to production?"
                ok "Yes, deploy!"
            }
            steps {
                sh "kubectl set image deployment/my-app my-app=${DOCKER_IMAGE}:${DOCKER_TAG} -n production"
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
            // Send Slack notification
        }
    }
}
```

### GitHub Actions

```yaml
# .github/workflows/ci-cd.yml
name: CI/CD Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  build-and-test:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Cache Maven dependencies
        uses: actions/cache@v3
        with:
          path: ~/.m2
          key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}

      - name: Build and Test
        run: mvn clean verify

      - name: Build Docker Image
        run: docker build -t my-app:${{ github.sha }} .

      - name: Push to Docker Hub
        if: github.ref == 'refs/heads/main'
        run: |
          echo "${{ secrets.DOCKER_PASSWORD }}" | docker login -u "${{ secrets.DOCKER_USERNAME }}" --password-stdin
          docker tag my-app:${{ github.sha }} myregistry/my-app:latest
          docker push myregistry/my-app:latest

  deploy:
    needs: build-and-test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'

    steps:
      - name: Deploy to server
        uses: appleboy/ssh-action@v1
        with:
          host: ${{ secrets.SERVER_HOST }}
          username: ${{ secrets.SERVER_USER }}
          key: ${{ secrets.SSH_PRIVATE_KEY }}
          script: |
            docker pull myregistry/my-app:latest
            docker stop my-app || true
            docker rm my-app || true
            docker run -d --name my-app -p 8080:8080 myregistry/my-app:latest
```

### Jenkins vs GitHub Actions

| Feature | Jenkins | GitHub Actions |
|---------|---------|----------------|
| Hosting | Self-hosted (on your server) | Cloud-hosted by GitHub |
| Config | `Jenkinsfile` (Groovy) | `.yml` files in `.github/workflows/` |
| Plugins | 1800+ plugins | Marketplace actions |
| Cost | Free (you pay for server) | Free tier + paid minutes |
| Best for | Enterprise, complex pipelines | GitHub-hosted projects |
| UI | Web dashboard | GitHub tab |

---

## 5. Docker

### What is Docker?

```
Traditional Deployment vs Docker:
═════════════════════════════════

TRADITIONAL:                          DOCKER:
┌───────────────────┐                ┌───────────────────┐
│  "Works on my     │                │   Container 1     │
│   machine" 🤷     │                │  ┌─────────────┐  │
│                   │                │  │ App + Deps   │  │
│  App depends on:  │                │  │ (isolated)   │  │
│  - Java 17        │                │  └─────────────┘  │
│  - specific libs  │                │   Container 2     │
│  - OS config      │                │  ┌─────────────┐  │
│  - env variables  │                │  │ DB + Config  │  │
│                   │                │  │ (isolated)   │  │
└───────────────────┘                │  └─────────────┘  │
                                     │   Docker Engine   │
                                     │   Host OS         │
                                     └───────────────────┘
```

### Docker vs Virtual Machine

```
VM:                                    Docker:
┌──────────┐ ┌──────────┐            ┌──────────┐ ┌──────────┐
│   App A  │ │   App B  │            │   App A  │ │   App B  │
│  Bins/Lib│ │  Bins/Lib│            │  Bins/Lib│ │  Bins/Lib│
│ Guest OS │ │ Guest OS │            └────┬─────┘ └────┬─────┘
└────┬─────┘ └────┬─────┘                 │            │
     └──────┬─────┘                  ┌────┴────────────┴────┐
      ┌─────┴─────┐                  │    Docker Engine      │
      │ Hypervisor│                  └──────────┬────────────┘
      └─────┬─────┘                       ┌────┴────┐
      ┌─────┴─────┐                       │ Host OS │
      │  Host OS  │                       └─────────┘
      └───────────┘
 Heavy (GBs), slow startup              Light (MBs), fast startup
```

| Feature | VM | Docker Container |
|---------|-----|-----------------|
| Size | GBs | MBs |
| Startup | Minutes | Seconds |
| OS | Full guest OS | Shares host kernel |
| Isolation | Complete (hardware level) | Process level |
| Performance | Overhead from hypervisor | Near native |
| Use case | Different OS needed | Same OS, isolated apps |

### Dockerfile

```dockerfile
# Multi-stage build for Spring Boot application
# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline          # Cache dependencies
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run (smaller image)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder /app/target/*.jar app.jar

# Set ownership
RUN chown appuser:appgroup app.jar
USER appuser

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s \
    CMD wget -q --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Commands

```bash
# Image Management
docker build -t my-app:1.0 .              # Build image from Dockerfile
docker images                               # List all images
docker rmi my-app:1.0                       # Remove image
docker pull nginx:latest                    # Pull image from registry
docker push myregistry/my-app:1.0          # Push to registry

# Container Management
docker run -d --name my-app -p 8080:8080 my-app:1.0   # Run container
docker ps                                   # List running containers
docker ps -a                                # List all containers
docker stop my-app                          # Stop container
docker start my-app                         # Start stopped container
docker rm my-app                            # Remove container
docker restart my-app                       # Restart container

# Debugging
docker logs my-app                          # View logs
docker logs -f my-app                       # Follow logs (real-time)
docker exec -it my-app /bin/sh              # Shell into container
docker inspect my-app                       # Detailed container info
docker stats                                # Resource usage (CPU, RAM)

# Cleanup
docker system prune                         # Remove unused data
docker image prune -a                       # Remove unused images
docker volume prune                         # Remove unused volumes
```

### Docker Flags Explained

```bash
docker run \
    -d                          # Detached mode (background)
    --name my-app               # Container name
    -p 8080:8080                # Host port : Container port
    -e DB_HOST=postgres         # Environment variable
    -e DB_PORT=5432             #
    -v /host/data:/app/data     # Volume mount (persist data)
    --network my-network        # Connect to network
    --restart unless-stopped    # Restart policy
    --memory 512m               # Memory limit
    --cpus 1.5                  # CPU limit
    my-app:1.0                  # Image name:tag
```

### Docker Networking

```
Docker Network Types:
═════════════════════

1. bridge (default) — Containers on same host communicate
   ┌──────────────────────────────────┐
   │         bridge network           │
   │  ┌──────┐         ┌──────┐      │
   │  │App   │ ←─────→ │ DB   │      │
   │  │:8080 │         │:5432 │      │
   │  └──────┘         └──────┘      │
   └──────────────────────────────────┘

2. host — Container uses host's network directly
3. none — No networking (isolated)
```

```bash
# Create custom network
docker network create my-network

# Run containers on same network
docker run -d --name postgres --network my-network postgres:15
docker run -d --name my-app --network my-network -p 8080:8080 my-app:1.0

# Inside my-app, you can reach postgres by container name:
# jdbc:postgresql://postgres:5432/mydb
```

### Docker Volumes

```bash
# Named volume (Docker manages it)
docker volume create app-data
docker run -v app-data:/app/data my-app:1.0

# Bind mount (you specify host path)
docker run -v /home/veer/config:/app/config my-app:1.0

# Read-only mount
docker run -v /host/config:/app/config:ro my-app:1.0
```

---

## 6. Docker Compose

### What is Docker Compose?

Docker Compose lets you define and run **multi-container** applications with a single YAML file.

### docker-compose.yml — Full Example

```yaml
version: '3.8'

services:
  # Spring Boot Application
  app:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: my-spring-app
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: prod
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/mydb
      SPRING_DATASOURCE_USERNAME: admin
      SPRING_DATASOURCE_PASSWORD: secret
      SPRING_REDIS_HOST: redis
    depends_on:
      postgres:
        condition: service_healthy
      redis:
        condition: service_started
    networks:
      - app-network
    restart: unless-stopped

  # PostgreSQL Database
  postgres:
    image: postgres:15-alpine
    container_name: my-postgres
    environment:
      POSTGRES_DB: mydb
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: secret
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U admin -d mydb"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - app-network

  # Redis Cache
  redis:
    image: redis:7-alpine
    container_name: my-redis
    ports:
      - "6379:6379"
    volumes:
      - redis-data:/data
    networks:
      - app-network

  # Nginx Reverse Proxy
  nginx:
    image: nginx:alpine
    container_name: my-nginx
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf:ro
    depends_on:
      - app
    networks:
      - app-network

volumes:
  postgres-data:
  redis-data:

networks:
  app-network:
    driver: bridge
```

### Docker Compose Commands

```bash
docker-compose up -d                # Start all services (detached)
docker-compose down                 # Stop and remove all services
docker-compose down -v              # Stop + remove volumes too
docker-compose logs -f app          # Follow logs of 'app' service
docker-compose ps                   # List running services
docker-compose build                # Rebuild images
docker-compose restart app          # Restart specific service
docker-compose exec app /bin/sh     # Shell into running service
docker-compose pull                 # Pull latest images
```

---

## 7. Kubernetes (K8s)

### What is Kubernetes?

Kubernetes is a **container orchestration** platform that automates deploying, scaling, and managing containerized applications.

```
Why Kubernetes?
═══════════════
Docker alone:                         With Kubernetes:
- Manual scaling                      - Auto-scaling
- No self-healing                     - Self-healing (restarts failed pods)
- Manual load balancing               - Built-in load balancing
- Single host                         - Multi-host cluster
- Manual rollouts                     - Rolling updates + rollback
```

### K8s Architecture

```
┌──────────────────────────────────────────────────────────────────┐
│                        KUBERNETES CLUSTER                        │
│                                                                  │
│  ┌─────────────── Control Plane (Master) ──────────────────┐    │
│  │                                                          │    │
│  │  ┌──────────┐  ┌───────────┐  ┌────────┐  ┌─────────┐  │    │
│  │  │ API      │  │ Scheduler │  │ etcd   │  │Controller│  │    │
│  │  │ Server   │  │           │  │(store) │  │ Manager  │  │    │
│  │  └──────────┘  └───────────┘  └────────┘  └─────────┘  │    │
│  └──────────────────────────────────────────────────────────┘    │
│                                                                  │
│  ┌──── Worker Node 1 ────┐    ┌──── Worker Node 2 ────┐        │
│  │                        │    │                        │        │
│  │  ┌─Pod──┐  ┌─Pod──┐   │    │  ┌─Pod──┐  ┌─Pod──┐   │        │
│  │  │ App  │  │ App  │   │    │  │ App  │  │ DB   │   │        │
│  │  └──────┘  └──────┘   │    │  └──────┘  └──────┘   │        │
│  │                        │    │                        │        │
│  │  ┌────────┐ ┌───────┐ │    │  ┌────────┐ ┌───────┐ │        │
│  │  │ Kubelet│ │ kube- │ │    │  │ Kubelet│ │ kube- │ │        │
│  │  │        │ │ proxy │ │    │  │        │ │ proxy │ │        │
│  │  └────────┘ └───────┘ │    │  └────────┘ └───────┘ │        │
│  └────────────────────────┘    └────────────────────────┘        │
└──────────────────────────────────────────────────────────────────┘
```

| Component | Role |
|-----------|------|
| **API Server** | Front door — all communication goes through it |
| **etcd** | Key-value store — stores cluster state |
| **Scheduler** | Decides which node to place a new pod on |
| **Controller Manager** | Ensures desired state matches actual state |
| **Kubelet** | Agent on each node — manages pods |
| **kube-proxy** | Handles networking and load balancing on each node |

### Key Concepts

```
Pod  →  smallest unit, 1+ containers
         │
ReplicaSet  →  ensures N copies of a pod are running
         │
Deployment  →  manages ReplicaSets, handles rolling updates
         │
Service  →  stable network endpoint to reach pods
         │
Ingress  →  external HTTP routing (like API Gateway)
```

### K8s YAML Files

**Deployment**
```yaml
# deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-spring-app
  labels:
    app: my-spring-app
spec:
  replicas: 3                           # Run 3 instances
  selector:
    matchLabels:
      app: my-spring-app
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxUnavailable: 1                 # Max 1 pod down during update
      maxSurge: 1                       # Max 1 extra pod during update
  template:
    metadata:
      labels:
        app: my-spring-app
    spec:
      containers:
        - name: my-spring-app
          image: myregistry/my-spring-app:1.0
          ports:
            - containerPort: 8080
          env:
            - name: SPRING_PROFILES_ACTIVE
              value: "prod"
            - name: DB_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: db-secret
                  key: password
          resources:
            requests:
              memory: "256Mi"
              cpu: "250m"
            limits:
              memory: "512Mi"
              cpu: "500m"
          readinessProbe:
            httpGet:
              path: /actuator/health
              port: 8080
            initialDelaySeconds: 15
            periodSeconds: 10
          livenessProbe:
            httpGet:
              path: /actuator/health
              port: 8080
            initialDelaySeconds: 30
            periodSeconds: 15
```

**Service**
```yaml
# service.yaml
apiVersion: v1
kind: Service
metadata:
  name: my-spring-app-service
spec:
  type: ClusterIP                       # Internal only (default)
  selector:
    app: my-spring-app
  ports:
    - port: 80                          # Service port
      targetPort: 8080                  # Container port
```

**Service Types:**

| Type | Access | Use Case |
|------|--------|----------|
| `ClusterIP` | Internal only | Service-to-service communication |
| `NodePort` | External via `NodeIP:Port` | Development, testing |
| `LoadBalancer` | External via cloud LB | Production (cloud only) |

**ConfigMap & Secret**
```yaml
# configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  SPRING_PROFILES_ACTIVE: "prod"
  SERVER_PORT: "8080"

---
# secret.yaml
apiVersion: v1
kind: Secret
metadata:
  name: db-secret
type: Opaque
data:
  username: YWRtaW4=                    # base64 encoded "admin"
  password: c2VjcmV0                    # base64 encoded "secret"
```

### Essential kubectl Commands

```bash
# Cluster Info
kubectl cluster-info                    # Cluster details
kubectl get nodes                       # List nodes

# Deployments
kubectl apply -f deployment.yaml        # Create/update from file
kubectl get deployments                 # List deployments
kubectl rollout status deployment/my-app # Check rollout progress
kubectl rollout undo deployment/my-app  # Rollback to previous version
kubectl scale deployment/my-app --replicas=5  # Scale manually

# Pods
kubectl get pods                        # List pods
kubectl get pods -o wide                # Show node + IP
kubectl describe pod my-app-xyz         # Detailed pod info
kubectl logs my-app-xyz                 # Pod logs
kubectl logs -f my-app-xyz             # Follow logs
kubectl exec -it my-app-xyz -- /bin/sh  # Shell into pod
kubectl delete pod my-app-xyz           # Delete pod (recreated by deployment)

# Services
kubectl get svc                         # List services
kubectl describe svc my-app-service     # Service details

# Apply all files in directory
kubectl apply -f k8s/                   # Apply all YAML in folder

# Namespaces
kubectl get pods -n staging             # List pods in staging namespace
kubectl create namespace staging        # Create namespace
```

---

## 8. Nginx & Reverse Proxy

### What is a Reverse Proxy?

```
Without Reverse Proxy:                 With Reverse Proxy (Nginx):
═══════════════════════                ═══════════════════════════

Client ──→ App:8080                    Client ──→ Nginx:80/443 ──→ App:8080
Client ──→ App:8080                              │
Client ──→ App:8080                              ├──→ App-1:8080
                                                 ├──→ App-2:8080   (Load Balancing)
                                                 └──→ App-3:8080

Benefits:
- SSL termination (HTTPS at Nginx, HTTP internally)
- Load balancing across multiple app instances
- Static file serving
- Rate limiting and security
- Caching
```

### Nginx Configuration

```nginx
# /etc/nginx/nginx.conf
events {
    worker_connections 1024;
}

http {
    # Upstream — define backend servers
    upstream spring-app {
        server app-1:8080;
        server app-2:8080;
        server app-3:8080;
    }

    server {
        listen 80;
        server_name myapp.com;

        # Redirect HTTP to HTTPS
        return 301 https://$server_name$request_uri;
    }

    server {
        listen 443 ssl;
        server_name myapp.com;

        ssl_certificate     /etc/nginx/ssl/cert.pem;
        ssl_certificate_key /etc/nginx/ssl/key.pem;

        # API requests → Spring Boot
        location /api/ {
            proxy_pass http://spring-app;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
        }

        # Static files → serve directly
        location /static/ {
            root /var/www/html;
            expires 30d;
        }

        # Health check endpoint
        location /health {
            proxy_pass http://spring-app/actuator/health;
        }
    }
}
```

---

## 9. Monitoring & Logging

### Monitoring Stack Overview

```
┌──────────────────────────────────────────────────────────┐
│                    Monitoring Architecture                 │
│                                                          │
│   ┌─────────┐      ┌────────────┐      ┌────────────┐  │
│   │ App     │─────→│ Prometheus │─────→│  Grafana   │  │
│   │ /metrics│      │ (collect)  │      │ (visualize)│  │
│   └─────────┘      └────────────┘      └────────────┘  │
│                                                          │
│   ┌─────────┐      ┌────────────┐      ┌────────────┐  │
│   │ App     │─────→│   ELK /    │─────→│  Kibana    │  │
│   │ (logs)  │      │  Loki      │      │ (search)   │  │
│   └─────────┘      └────────────┘      └────────────┘  │
│                                                          │
│   ┌─────────┐                          ┌────────────┐  │
│   │AlertMgr │─────────────────────────→│Slack/Email │  │
│   └─────────┘                          └────────────┘  │
└──────────────────────────────────────────────────────────┘
```

### Prometheus + Grafana (Metrics)

| Tool | Purpose |
|------|---------|
| **Prometheus** | Collects and stores time-series metrics (CPU, memory, request count) |
| **Grafana** | Dashboard UI — creates graphs and charts from Prometheus data |
| **AlertManager** | Sends alerts when metrics exceed thresholds |

**Spring Boot Integration:**
```xml
<!-- pom.xml -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```
```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health, prometheus, metrics
  metrics:
    tags:
      application: my-spring-app
```

Your app exposes metrics at `/actuator/prometheus` — Prometheus scrapes this endpoint.

### ELK Stack (Logging)

```
ELK = Elasticsearch + Logstash + Kibana

App Logs ──→ Logstash (parse/transform) ──→ Elasticsearch (store/index) ──→ Kibana (search/visualize)

Alternatives:
- EFK: Elasticsearch + Fluentd + Kibana
- PLG: Promtail + Loki + Grafana (lightweight, growing popular)
```

| Tool | Purpose |
|------|---------|
| **Elasticsearch** | Full-text search engine for log storage |
| **Logstash** | Collects, parses, and transforms logs |
| **Kibana** | Web UI to search and visualize logs |
| **Loki** | Lightweight alternative to Elasticsearch (by Grafana) |

### Key Metrics to Monitor

| Category | Metrics |
|----------|---------|
| **Application** | Request rate, error rate, response time (latency) |
| **System** | CPU usage, memory usage, disk I/O |
| **JVM** | Heap usage, GC pauses, thread count |
| **Database** | Connection pool, query time, deadlocks |
| **Container** | Restart count, resource limits, OOM kills |

### The Four Golden Signals (Google SRE)

| Signal | What it measures |
|--------|-----------------|
| **Latency** | How long requests take |
| **Traffic** | How many requests per second |
| **Errors** | Rate of failed requests |
| **Saturation** | How full is the system (CPU, memory, disk) |

---

## 10. Infrastructure as Code (Terraform)

### What is IaC?

```
Manual Infrastructure:                   Infrastructure as Code:
══════════════════════                   ═══════════════════════
1. Login to AWS console                  1. Write code (.tf files)
2. Click "Create EC2"                    2. Run: terraform apply
3. Manually configure                    3. Infrastructure is created
4. Hope you remember settings            4. Code IS the documentation
5. Hard to replicate                     5. Easily replicate in any env

Key Benefits:
- Version controlled (git)
- Reproducible
- Self-documenting
- Peer-reviewable
```

### Terraform Basics

```hcl
# main.tf — Create an EC2 instance

# 1. Provider — which cloud?
provider "aws" {
  region = "ap-south-1"
}

# 2. Resource — what to create?
resource "aws_instance" "web_server" {
  ami           = "ami-0c55b159cbfafe1f0"   # Amazon Linux 2
  instance_type = "t2.micro"

  tags = {
    Name        = "web-server"
    Environment = "production"
  }
}

# 3. Variable — parameterize
variable "instance_type" {
  description = "EC2 instance type"
  default     = "t2.micro"
}

# 4. Output — show result
output "server_ip" {
  value = aws_instance.web_server.public_ip
}
```

### Terraform Commands

```bash
terraform init        # Download provider plugins
terraform plan        # Preview what will be created/changed/destroyed
terraform apply       # Apply changes (creates infrastructure)
terraform destroy     # Destroy all managed infrastructure
terraform fmt         # Format .tf files
terraform validate    # Check syntax
terraform state list  # List managed resources
```

### Terraform Workflow

```
Write .tf files  →  terraform init  →  terraform plan  →  terraform apply
                          │                    │                  │
                    Download plugins     Preview changes    Create resources
                                                                  │
                                                          terraform destroy
                                                          (cleanup when done)
```

### Terraform vs Ansible

| Feature | Terraform | Ansible |
|---------|-----------|---------|
| Purpose | **Provision** infrastructure | **Configure** servers |
| Approach | Declarative ("I want 3 servers") | Procedural ("Do these steps") |
| State | Maintains state file | Stateless |
| Best for | Creating cloud resources | Installing software, config |
| Language | HCL (.tf) | YAML (.yml) |
| Example | Create EC2, VPC, S3 | Install Java, deploy JAR, setup Nginx |

> Typical combo: **Terraform** creates the servers → **Ansible** configures them

---

## 11. Networking for DevOps

### Essential Concepts

```
Port Ranges:
════════════
0 - 1023     → Well-known ports (HTTP:80, HTTPS:443, SSH:22, DNS:53)
1024 - 49151 → Registered ports (MySQL:3306, PostgreSQL:5432, Redis:6379)
49152 - 65535→ Dynamic/Ephemeral ports

Common Ports for DevOps:
  22    → SSH
  80    → HTTP
  443   → HTTPS
  3000  → Grafana
  3306  → MySQL
  5432  → PostgreSQL
  5672  → RabbitMQ
  6379  → Redis
  8080  → Tomcat / Spring Boot
  8443  → HTTPS alt
  9090  → Prometheus
  9092  → Kafka
  9200  → Elasticsearch
  27017 → MongoDB
```

### Firewall & Security Groups

```
Firewall Rules (iptables / Security Groups):
════════════════════════════════════════════

INBOUND (who can reach your server):
  ┌─────────────────────────────────────────────┐
  │ Port │ Protocol │  Source     │ Purpose      │
  │──────┼──────────┼────────────┼──────────────│
  │ 22   │ TCP      │ My IP only │ SSH access   │
  │ 80   │ TCP      │ 0.0.0.0/0  │ HTTP         │
  │ 443  │ TCP      │ 0.0.0.0/0  │ HTTPS        │
  │ 8080 │ TCP      │ VPC only   │ App (internal)│
  └─────────────────────────────────────────────┘

OUTBOUND (where your server can go):
  Usually: Allow all outbound (0.0.0.0/0)
```

### DNS Records for DevOps

```bash
# Common DNS record types you'll work with
A     Record → Domain → IPv4        (myapp.com → 52.1.2.3)
AAAA  Record → Domain → IPv6        (myapp.com → 2001:db8::1)
CNAME Record → Domain → Domain      (www.myapp.com → myapp.com)
TXT   Record → Verification         (SPF, DKIM for email)
MX    Record → Mail servers          (myapp.com → mail.myapp.com)

# DNS debugging
nslookup myapp.com                   # Quick DNS lookup
dig myapp.com                        # Detailed DNS info
dig +short myapp.com A               # Just the IP
```

---

## 12. Interview Questions

---

### Q1. What is DevOps? How is it different from traditional development?

**DevOps** is a culture and set of practices that combines **Development (Dev)** and **Operations (Ops)** to shorten the development lifecycle and deliver high-quality software continuously.

```
Traditional (Siloed):                  DevOps (Collaborative):
═══════════════════                    ═══════════════════════
Dev Team → "Here's the code"           Dev + Ops → Work together
    ↓                                       ↓
Ops Team → "Let me figure out             Automated pipeline
            how to deploy this"             ↓
    ↓                                  Fast, reliable releases
Slow, error-prone releases
```

| Aspect | Traditional | DevOps |
|--------|-------------|--------|
| Teams | Separate Dev & Ops | Unified team |
| Releases | Monthly/Quarterly | Daily/Weekly |
| Deployment | Manual | Automated (CI/CD) |
| Feedback | Slow | Fast (monitoring, alerts) |
| Failure response | Blame game | Blameless postmortem |

---

### Q2. Explain CI/CD and why it is important.

**CI (Continuous Integration):** Developers merge code into a shared branch frequently. Each merge triggers an automated build + test. Catches bugs early.

**CD (Continuous Delivery):** After CI passes, the code is automatically deployed to staging. Production deployment requires manual approval.

**CD (Continuous Deployment):** Everything is automated — code goes straight to production after all tests pass.

```
Developer pushes code
        │
        ▼
  ┌──────────┐     ┌──────────┐     ┌──────────┐     ┌──────────┐
  │  Build    │────→│  Test    │────→│  Stage   │────→│  Prod    │
  │  (Maven)  │     │  (JUnit) │     │  Deploy  │     │  Deploy  │
  └──────────┘     └──────────┘     └──────────┘     └──────────┘
  ├───────── CI ─────────────┤├── CD (Delivery: manual gate) ──┤
                              ├── CD (Deployment: auto) ───────┤
```

**Why important?**
- Detect bugs early (before they reach production)
- Faster release cycles
- Consistent, repeatable deployments
- Reduced human error

---

### Q3. What is the difference between Docker image and Docker container?

| Aspect | Docker Image | Docker Container |
|--------|-------------|-----------------|
| What | Blueprint/template | Running instance of an image |
| State | Read-only | Read-write (has runtime state) |
| Analogy | Java Class | Java Object (instance) |
| Storage | Stored in registry | Runs on host |
| Created by | `docker build` | `docker run` |

```bash
# Image = Blueprint
docker build -t my-app:1.0 .      # Create image from Dockerfile

# Container = Running instance
docker run -d my-app:1.0           # Create container from image
docker run -d my-app:1.0           # Another container from SAME image
docker run -d my-app:1.0           # And another — all independent
```

---

### Q4. Explain Docker multi-stage build. Why use it?

Multi-stage builds use multiple `FROM` statements to create smaller, more secure production images.

```dockerfile
# Stage 1: BUILD (heavy — includes Maven, JDK, source code)
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
# Image size: ~800MB (includes Maven, source, build tools)

# Stage 2: RUN (lightweight — only JRE + JAR)
FROM eclipse-temurin:17-jre-alpine
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
# Final image size: ~200MB (only what's needed to run)
```

**Benefits:**
- Smaller image size (no build tools in production)
- More secure (fewer packages = smaller attack surface)
- Faster deployment (smaller image to push/pull)

---

### Q5. What happens when you run `docker run -d -p 8080:8080 my-app`?

Step-by-step:

1. **Docker checks locally** for image `my-app`. If not found, pulls from registry.
2. **Creates a new container** from the image with a read-write layer.
3. **`-d` (detached):** Runs the container in the background.
4. **`-p 8080:8080`:** Maps host port 8080 → container port 8080.
5. **Executes the `ENTRYPOINT`/`CMD`** defined in the Dockerfile.
6. **Returns the container ID** to the terminal.

```
  Host Machine                     Docker Container
  ────────────                     ────────────────
  Port 8080  ◄──── mapped ────►   Port 8080
                                   │
  curl localhost:8080              App listening on 8080
```

---

### Q6. What is Kubernetes and why do we need it?

**Kubernetes (K8s)** is a container orchestration platform. Docker runs containers — Kubernetes **manages** them at scale.

**Without K8s (Docker only):**
- You manually start/stop containers
- If a container crashes, you manually restart it
- Scaling means manually running more containers
- No built-in load balancing

**With K8s:**
| Feature | How K8s Helps |
|---------|--------------|
| Self-healing | Pod crashes → K8s restarts it automatically |
| Scaling | `kubectl scale --replicas=10` → instant horizontal scaling |
| Load balancing | Service distributes traffic across pods |
| Rolling updates | Zero-downtime deployments |
| Rollback | One command to revert to previous version |
| Secret management | Securely store passwords, keys |
| Resource limits | Prevent one app from consuming all CPU/RAM |

---

### Q7. Explain Pod, Deployment, and Service in Kubernetes.

```
┌─── Deployment ──────────────────────────────────────────┐
│  "I want 3 replicas of my-app, using image v2.0"       │
│                                                          │
│  ┌─── ReplicaSet ─────────────────────────────────────┐ │
│  │  "Ensure exactly 3 pods are always running"        │ │
│  │                                                     │ │
│  │   ┌─ Pod ──┐    ┌─ Pod ──┐    ┌─ Pod ──┐         │ │
│  │   │ my-app │    │ my-app │    │ my-app │         │ │
│  │   │ (v2.0) │    │ (v2.0) │    │ (v2.0) │         │ │
│  │   └────────┘    └────────┘    └────────┘         │ │
│  └─────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────┘
               ▲
               │  selector: app=my-app
               │
┌─── Service ──┴──────────────────────────────────────────┐
│  "Route traffic to any pod labeled app=my-app"          │
│   ClusterIP: 10.96.0.1:80  →  Pod:8080                 │
└──────────────────────────────────────────────────────────┘
```

| Concept | What | Analogy |
|---------|------|---------|
| **Pod** | Smallest deployable unit (1+ containers) | A single process/app |
| **ReplicaSet** | Ensures N pods are always running | Supervisor |
| **Deployment** | Manages ReplicaSets + rolling updates | Manager |
| **Service** | Stable network endpoint for pods | Load balancer / DNS entry |

---

### Q8. What is a Jenkinsfile? Explain declarative vs scripted pipeline.

A **Jenkinsfile** defines your CI/CD pipeline as code, stored in your repo alongside your source code.

**Declarative Pipeline** (Recommended — structured, easier):
```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}
```

**Scripted Pipeline** (Flexible — full Groovy power):
```groovy
node {
    stage('Build') {
        sh 'mvn clean package'
    }
    stage('Test') {
        try {
            sh 'mvn test'
        } catch (Exception e) {
            echo "Tests failed: ${e.message}"
            currentBuild.result = 'FAILURE'
        }
    }
}
```

| Aspect | Declarative | Scripted |
|--------|-------------|----------|
| Syntax | Structured (`pipeline {}`) | Free-form Groovy |
| Ease | Easier to learn | More complex |
| Flexibility | Limited | Full Groovy |
| Error handling | `post { failure {} }` | `try/catch` |
| Recommended | Yes (for most cases) | Only for complex logic |

---

### Q9. What is Infrastructure as Code? Terraform vs Ansible.

**IaC** = Managing infrastructure (servers, networks, databases) through **code files** instead of manual clicks.

```
Manual:                         IaC:
═══════                         ════
Login AWS Console               Write main.tf
Click "Create EC2"              Run: terraform apply
Configure manually              ✓ Created in seconds
Can't reproduce easily          ✓ Version controlled
No audit trail                  ✓ git log shows who changed what
```

| Aspect | Terraform | Ansible |
|--------|-----------|---------|
| What it does | **Creates** infrastructure | **Configures** servers |
| Approach | Declarative ("desired state") | Procedural ("steps to execute") |
| State | Tracks state in `.tfstate` | Stateless |
| Language | HCL (`.tf`) | YAML (`.yml`) playbooks |
| Example | Create EC2, VPC, S3 bucket | Install Java, deploy JAR |
| Idempotent | Yes (no duplicate resources) | Yes (skips if already done) |
| Agent | Agentless (API calls) | Agentless (SSH) |

**Together:** Terraform creates the servers → Ansible configures them.

---

### Q10. Explain the difference between `docker-compose up` and `docker stack deploy`.

| Feature | docker-compose | docker stack deploy |
|---------|---------------|-------------------|
| Mode | Single host | Docker Swarm (multi-host cluster) |
| Scaling | Limited (`scale: 3`) | Full orchestration |
| Use case | Development, testing | Production |
| File | `docker-compose.yml` | Same file format |
| Networking | Bridge network | Overlay network (across hosts) |
| Command | `docker-compose up -d` | `docker stack deploy -c docker-compose.yml myapp` |

---

### Q11. How do you handle secrets in DevOps?

**NEVER** hardcode secrets in code or Docker images.

| Tool/Method | How | Best For |
|-------------|-----|----------|
| **Environment Variables** | `-e DB_PASS=secret` in Docker | Development |
| **Docker Secrets** | `docker secret create` | Swarm mode |
| **K8s Secrets** | `kubectl create secret` | Kubernetes |
| **HashiCorp Vault** | Central secret management API | Enterprise |
| **AWS Secrets Manager** | Cloud-managed secrets | AWS projects |
| **`.env` file** | `env_file: .env` in compose | Local dev (NEVER commit!) |

```yaml
# Kubernetes Secret example
apiVersion: v1
kind: Secret
metadata:
  name: db-credentials
type: Opaque
data:
  username: YWRtaW4=        # echo -n "admin" | base64
  password: cEBzc3dvcmQ=    # echo -n "p@ssword" | base64
```

```yaml
# Using in a Pod
env:
  - name: DB_USERNAME
    valueFrom:
      secretKeyRef:
        name: db-credentials
        key: username
```

---

### Q12. What is a reverse proxy? Why use Nginx in front of your application?

A **reverse proxy** sits between clients and your backend servers, forwarding requests.

```
Without Nginx:                    With Nginx:
Client → App:8080                 Client → Nginx:443 → App:8080

Problems:                         Solutions:
- No HTTPS                        ✓ SSL termination at Nginx
- Single instance                 ✓ Load balancing (round-robin)
- App serves static files         ✓ Nginx serves static files efficiently
- No rate limiting                ✓ Rate limiting, caching
- App port exposed                ✓ Only 80/443 exposed
```

---

### Q13. Explain rolling update vs blue-green vs canary deployment.

```
ROLLING UPDATE (Kubernetes default):
═══════════════════════════════════
  v1 v1 v1 v1  →  v2 v1 v1 v1  →  v2 v2 v1 v1  →  v2 v2 v2 v2
  (gradually replace pods one by one)

BLUE-GREEN:
═══════════
  Blue (v1) ← Traffic          Blue (v1)
                         →                         ← Switch
  Green (v2)  (standby)        Green (v2) ← Traffic

CANARY:
═══════
  v1 v1 v1 v1 ← 90% traffic
  v2           ← 10% traffic  (test with small % first)
```

| Strategy | Downtime | Rollback | Resource Cost | Risk |
|----------|----------|----------|--------------|------|
| Rolling Update | Zero | Slow (re-roll) | Low (gradual) | Medium |
| Blue-Green | Zero | Instant (switch back) | High (2x infra) | Low |
| Canary | Zero | Instant (remove canary) | Low | Lowest |

---

### Q14. A deployment fails in production. Walk through your troubleshooting process.

```
Step-by-step approach:
══════════════════════

1. CHECK MONITORING DASHBOARDS
   → Grafana: CPU spike? Memory OOM? Error rate jump?
   → What changed? Recent deployment? Config change?

2. CHECK APPLICATION LOGS
   → kubectl logs <pod-name>               # K8s
   → docker logs <container-name>          # Docker
   → tail -f /var/log/app.log             # VM
   → Look for: Exceptions, stack traces, connection refused

3. CHECK INFRASTRUCTURE
   → kubectl get pods (CrashLoopBackOff? ImagePullBackOff?)
   → kubectl describe pod <name> (events, resource limits)
   → df -h (disk full?)
   → free -h (memory exhausted?)

4. CHECK RECENT CHANGES
   → git log --oneline -10 (what was deployed?)
   → kubectl rollout history deployment/my-app

5. ROLLBACK IF CRITICAL
   → kubectl rollout undo deployment/my-app
   → Deploy previous Docker image tag

6. ROOT CAUSE ANALYSIS
   → Fix the issue
   → Add monitoring/alerts to prevent recurrence
   → Blameless postmortem
```

---

### Q15. What are Docker volumes? Why are they needed?

Containers are **ephemeral** — when a container is deleted, all data inside is lost. Volumes persist data outside the container lifecycle.

```
Without Volume:                    With Volume:
Container → data → 💀 LOST        Container → Volume → 💾 PERSISTED
(on container rm)                  (data survives container rm)
```

| Type | Syntax | Managed By | Use Case |
|------|--------|-----------|----------|
| Named Volume | `-v mydata:/app/data` | Docker | Database storage |
| Bind Mount | `-v /host/path:/app/data` | You | Config files, dev |
| tmpfs | `--tmpfs /app/tmp` | RAM | Temporary data |

---

### Q16. How does a Kubernetes liveness probe differ from a readiness probe?

| Probe | Purpose | On Failure |
|-------|---------|-----------|
| **Liveness** | "Is the app alive?" | **Restart** the pod |
| **Readiness** | "Is the app ready to serve traffic?" | **Remove** from service (stop sending traffic) |
| **Startup** | "Has the app finished starting?" | **Restart** (gives slow apps more time) |

```yaml
# Real-world example:
livenessProbe:                    # App is deadlocked? Restart it.
  httpGet:
    path: /actuator/health
    port: 8080
  initialDelaySeconds: 30         # Wait 30s before first check
  periodSeconds: 15               # Check every 15s

readinessProbe:                   # DB connection lost? Stop sending traffic.
  httpGet:
    path: /actuator/health/readiness
    port: 8080
  initialDelaySeconds: 10
  periodSeconds: 10
```

**Scenario:** App starts → startup probe waits → readiness probe passes → receives traffic → DB goes down → readiness fails → traffic stopped → DB recovers → readiness passes → traffic resumes. If app itself hangs → liveness fails → pod restarted.

---

### Q17. What is GitOps?

**GitOps** = Using Git as the **single source of truth** for both application code AND infrastructure.

```
Traditional CI/CD:                    GitOps:
  Push code → CI builds → CD deploys   Push code → CI builds → Push to Git
                                        → ArgoCD detects change → Deploys

  Developer ──→ Jenkins ──→ kubectl     Developer ──→ Git ──→ ArgoCD ──→ K8s

Key difference: No one runs kubectl manually.
               Git repo = desired state of cluster.
```

**Tools:** ArgoCD, Flux

**Benefits:**
- Audit trail (git log = deployment history)
- Easy rollback (`git revert`)
- Pull-based (more secure — cluster pulls from git)

---

### Q18. Explain the concept of "shift-left" in DevOps.

**Shift-left** = Move testing, security, and quality checks **earlier** (to the left) in the development lifecycle.

```
Traditional (problems found late):
  Code → Build → Test → Stage → SECURITY SCAN → Prod
                                      ↑
                                 Found vulnerability
                                 (expensive to fix)

Shift-Left (problems found early):
  Code → LINT + SECURITY SCAN → Build → Test → Stage → Prod
              ↑
         Found vulnerability
         (cheap to fix)
```

| Practice | What | Tools |
|----------|------|-------|
| Static Analysis | Scan code for bugs/style | SonarQube, ESLint |
| SAST | Security scan of source code | SonarQube, Checkmarx |
| Pre-commit hooks | Check before commit | Husky, pre-commit |
| Unit tests | Test early, test often | JUnit, Mockito |
| Container scanning | Scan Docker images | Trivy, Snyk |

---
