# AWS - Complete Beginner Reference Guide

---

## Table of Contents

1. [Cloud Computing Basics](#1-cloud-computing-basics)
2. [AWS Global Infrastructure](#2-aws-global-infrastructure)
3. [IAM — Identity & Access Management](#3-iam--identity--access-management)
4. [EC2 — Elastic Compute Cloud](#4-ec2--elastic-compute-cloud)
5. [S3 — Simple Storage Service](#5-s3--simple-storage-service)
6. [VPC — Virtual Private Cloud](#6-vpc--virtual-private-cloud)
7. [RDS — Relational Database Service](#7-rds--relational-database-service)
8. [Elastic Load Balancing (ELB)](#8-elastic-load-balancing-elb)
9. [Auto Scaling](#9-auto-scaling)
10. [Route 53 — DNS Service](#10-route-53--dns-service)
11. [CloudWatch — Monitoring](#11-cloudwatch--monitoring)
12. [Lambda — Serverless Computing](#12-lambda--serverless-computing)
13. [Other Essential Services (Overview)](#13-other-essential-services-overview)
14. [Interview Questions](#14-interview-questions)

---

## 1. Cloud Computing Basics

### What is Cloud Computing?

Cloud computing means using **someone else's computers** (data centers) over the internet instead of buying and managing your own hardware.

```
On-Premises (Traditional):              Cloud (AWS):
══════════════════════════               ═══════════
You buy servers                          You rent servers
You maintain hardware                    AWS maintains hardware
You manage cooling, power               AWS handles everything
Upfront cost: $$$$$                      Pay-as-you-go: $
Scale: Buy more hardware (weeks)         Scale: Click a button (minutes)
```

### Cloud Service Models

```
┌──────────────────────────────────────────────────────────┐
│                   Cloud Service Models                     │
│                                                           │
│  On-Premises    IaaS           PaaS          SaaS        │
│  ──────────    ────           ────          ────         │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐   │
│  │ App     │  │ App     │  │ App     │  │ App ✓   │   │
│  │ Data    │  │ Data    │  │ Data    │  │ Data ✓  │   │
│  │ Runtime │  │ Runtime │  │ Runtime ✓│  │ Runtime ✓│   │
│  │ OS      │  │ OS      │  │ OS     ✓│  │ OS     ✓│   │
│  │ Infra   │  │ Infra  ✓│  │ Infra  ✓│  │ Infra  ✓│   │
│  └─────────┘  └─────────┘  └─────────┘  └─────────┘   │
│  You manage    AWS manages   AWS manages   AWS manages   │
│  everything    infrastructure infra+OS+     everything    │
│                               runtime                     │
│                                                           │
│  Example:      EC2            Elastic       Gmail         │
│  Your server   (VM)           Beanstalk     Office 365   │
└──────────────────────────────────────────────────────────┘

✓ = Managed by cloud provider
```

| Model | You Manage | AWS Manages | Example |
|-------|-----------|-------------|---------|
| **IaaS** | App, Data, OS | Hardware, Network | EC2, VPC |
| **PaaS** | App, Data | OS, Runtime, Infra | Elastic Beanstalk, RDS |
| **SaaS** | Nothing (just use it) | Everything | Gmail, Slack |

### Cloud Deployment Models

| Model | Description | Example |
|-------|-------------|---------|
| **Public Cloud** | Shared infrastructure, accessible over internet | AWS, Azure, GCP |
| **Private Cloud** | Dedicated to one organization | On-premise OpenStack |
| **Hybrid Cloud** | Mix of public + private | Sensitive data on-prem, rest on AWS |

---

## 2. AWS Global Infrastructure

```
AWS Global Infrastructure:
══════════════════════════

Region (ap-south-1 = Mumbai)
├── Availability Zone 1 (ap-south-1a) ─── Data Center(s)
├── Availability Zone 2 (ap-south-1b) ─── Data Center(s)
└── Availability Zone 3 (ap-south-1c) ─── Data Center(s)

Edge Locations (CDN endpoints for CloudFront)
── 400+ locations worldwide for caching content close to users
```

| Concept | What | Why |
|---------|------|-----|
| **Region** | Geographic area (e.g., Mumbai, Virginia) | Data residency, latency |
| **Availability Zone (AZ)** | 1+ data centers in a region | High availability (if one AZ fails, others work) |
| **Edge Location** | CDN cache point | Faster content delivery to users |

**Common Regions:**

| Region Code | Location |
|-------------|----------|
| `ap-south-1` | Mumbai, India |
| `us-east-1` | N. Virginia (oldest, most services) |
| `us-west-2` | Oregon |
| `eu-west-1` | Ireland |
| `ap-southeast-1` | Singapore |

**Best Practice:** Deploy in the region **closest to your users** for lower latency. Use **multiple AZs** for high availability.

---

## 3. IAM — Identity & Access Management

### What is IAM?

IAM controls **who** (authentication) can do **what** (authorization) in your AWS account.

```
IAM Hierarchy:
══════════════

AWS Account (Root User)
│
├── IAM User: "veer" (has own credentials)
│     └── Attached Policy: "EC2FullAccess"
│
├── IAM Group: "Developers"
│     ├── User: "john"
│     ├── User: "jane"
│     └── Attached Policy: "S3ReadOnly", "EC2FullAccess"
│
├── IAM Role: "EC2-S3-Access"
│     └── Attached Policy: "S3ReadWrite"
│     └── Trusted Entity: EC2 (any EC2 can assume this role)
│
└── IAM Policy: (JSON document defining permissions)
```

### IAM Components

| Component | What | Example |
|-----------|------|---------|
| **User** | Individual person or application | `veer`, `deploy-bot` |
| **Group** | Collection of users | `Developers`, `Admins` |
| **Role** | Temporary permissions for services | EC2 accessing S3 |
| **Policy** | JSON document defining permissions | Allow EC2 Start/Stop |

### IAM Policy (JSON)

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "AllowS3Read",
            "Effect": "Allow",
            "Action": [
                "s3:GetObject",
                "s3:ListBucket"
            ],
            "Resource": [
                "arn:aws:s3:::my-bucket",
                "arn:aws:s3:::my-bucket/*"
            ]
        },
        {
            "Sid": "DenyDeleteBucket",
            "Effect": "Deny",
            "Action": "s3:DeleteBucket",
            "Resource": "*"
        }
    ]
}
```

**Policy Evaluation Logic:**
```
1. By default → DENY everything
2. Check all policies:
   - Explicit DENY found?  → DENY  (Deny always wins)
   - Explicit ALLOW found? → ALLOW
   - Neither?              → DENY (default)
```

### IAM Best Practices

```
✓ Never use Root account for daily tasks
✓ Enable MFA (Multi-Factor Authentication) on Root
✓ Create individual IAM users (don't share credentials)
✓ Use Groups to assign permissions (not individual users)
✓ Use Roles for services (EC2, Lambda) — never embed keys
✓ Follow Least Privilege Principle — give minimum required permissions
✓ Rotate access keys regularly
✓ Use AWS managed policies when possible
```

---

## 4. EC2 — Elastic Compute Cloud

### What is EC2?

EC2 = **Virtual servers** (called instances) in the cloud. You can launch, configure, and terminate servers on demand.

### Launching an EC2 Instance — Key Decisions

```
When you launch an EC2, you choose:
═══════════════════════════════════

1. AMI (Amazon Machine Image)     → Which OS? (Amazon Linux, Ubuntu, Windows)
2. Instance Type                  → How powerful? (CPU, RAM)
3. Key Pair                       → SSH access (your-key.pem file)
4. VPC & Subnet                   → Which network?
5. Security Group                 → Firewall rules (which ports to open)
6. Storage (EBS Volume)           → Disk size and type
7. IAM Role                       → What AWS services can this EC2 access?
```

### Instance Types

```
Instance Type Naming:
  t2.micro
  │  │  │
  │  │  └── Size (nano < micro < small < medium < large < xlarge)
  │  └── Generation (higher = newer)
  └── Family (t = general, c = compute, r = memory, g = GPU)
```

| Family | Optimized For | Use Case |
|--------|-------------|----------|
| **t2/t3** | General purpose (burstable) | Web servers, dev environments |
| **m5/m6** | General purpose (steady) | Application servers |
| **c5/c6** | Compute | CPU-heavy tasks, batch processing |
| **r5/r6** | Memory | Databases, in-memory caching |
| **g4/p4** | GPU | Machine learning, video rendering |

### Instance Purchasing Options

| Option | How It Works | Savings | Best For |
|--------|-------------|---------|----------|
| **On-Demand** | Pay per hour/second, no commitment | 0% | Unpredictable workloads, testing |
| **Reserved** | 1 or 3 year commitment | Up to 72% | Steady-state workloads (production DBs) |
| **Spot** | Bid for unused capacity (can be interrupted) | Up to 90% | Batch jobs, fault-tolerant workloads |
| **Savings Plans** | Commit to $ amount per hour | Up to 72% | Flexible usage across instance types |

### Security Groups (Firewall)

```
Security Group = Virtual firewall for your EC2
═══════════════════════════════════════════════

INBOUND Rules (who can reach your EC2):
┌────────┬──────────┬──────────────┬────────────────┐
│  Type  │  Port    │  Source      │  Purpose        │
├────────┼──────────┼──────────────┼────────────────┤
│  SSH   │  22      │  My IP       │  Admin access   │
│  HTTP  │  80      │  0.0.0.0/0   │  Web traffic    │
│  HTTPS │  443     │  0.0.0.0/0   │  Secure web     │
│  Custom│  8080    │  10.0.0.0/16 │  App (VPC only) │
└────────┴──────────┴──────────────┴────────────────┘

OUTBOUND Rules: Allow all (default)

Key rules:
- Security Groups are STATEFUL (if inbound allowed, response auto-allowed)
- Default: all inbound DENIED, all outbound ALLOWED
- You can reference other security groups (e.g., "allow from web-sg")
```

### EBS (Elastic Block Store) — EC2 Storage

```
EBS = Virtual hard disk attached to EC2
═══════════════════════════════════════

EC2 Instance ─── attached to ──→ EBS Volume (/dev/xvda)

EBS Types:
┌─────────────────┬──────────┬───────────┬────────────────────────┐
│  Type           │  IOPS    │  Cost     │  Use Case              │
├─────────────────┼──────────┼───────────┼────────────────────────┤
│  gp3 (General)  │  3,000   │  Low      │  Boot volumes, dev     │
│  gp2 (General)  │  Burst   │  Low      │  Default boot volume   │
│  io2 (Provisioned)│ 64,000  │  High     │  Databases (critical)  │
│  st1 (Throughput)│  500     │  Very low │  Big data, logs        │
│  sc1 (Cold)     │  250     │  Lowest   │  Infrequent access     │
└─────────────────┴──────────┴───────────┴────────────────────────┘

Key Features:
- Snapshots → backup to S3 (can restore later)
- Encryption → enable at creation
- Persistent → data survives EC2 stop/start (NOT terminate by default)
```

### Connecting to EC2

```bash
# 1. Set key permission
chmod 400 my-key.pem

# 2. SSH into instance
ssh -i my-key.pem ec2-user@<public-ip>        # Amazon Linux
ssh -i my-key.pem ubuntu@<public-ip>           # Ubuntu

# 3. Common first steps on a fresh EC2
sudo yum update -y                              # Update packages (Amazon Linux)
sudo amazon-linux-extras install java-openjdk17  # Install Java 17
java -version                                    # Verify
```

---

## 5. S3 — Simple Storage Service

### What is S3?

S3 = **Object storage** (files) with unlimited capacity. Stores files as **objects** in **buckets**.

```
S3 Structure:
═════════════

Bucket: "my-company-app-assets"  (globally unique name)
│
├── images/
│   ├── logo.png          (Object: key = images/logo.png)
│   └── banner.jpg        (Object: key = images/banner.jpg)
│
├── config/
│   └── app.properties    (Object: key = config/app.properties)
│
└── backups/
    └── db-2024-01.sql    (Object: key = backups/db-2024-01.sql)

Each object can be up to 5TB
Bucket name must be globally unique across ALL AWS accounts
```

### S3 Storage Classes

| Class | Access Pattern | Cost | Durability | Use Case |
|-------|---------------|------|-----------|----------|
| **S3 Standard** | Frequent | $$$ | 99.999999999% (11 9s) | Active data, website |
| **S3 Standard-IA** | Infrequent | $$ | 11 9s | Backups, DR |
| **S3 One Zone-IA** | Infrequent, 1 AZ | $ | 11 9s (1 AZ) | Secondary backups |
| **S3 Glacier Instant** | Rare, instant access | $ | 11 9s | Archives (instant need) |
| **S3 Glacier Flexible** | Rare, mins-hours | ¢ | 11 9s | Long-term archives |
| **S3 Glacier Deep Archive** | Rarely (12-48hr retrieval) | ¢¢ | 11 9s | Compliance, 7yr retention |
| **S3 Intelligent-Tiering** | Unknown pattern | Auto | 11 9s | Let AWS optimize cost |

### S3 Key Features

```
VERSIONING:
═══════════
Enable versioning → keeps all versions of an object
  Upload logo.png (v1) → Upload logo.png (v2) → Both versions exist
  Accidental delete? → Just restore previous version!

LIFECYCLE POLICIES:
═══════════════════
Automatically transition objects between storage classes:
  Day 0 → S3 Standard (frequently accessed)
  Day 30 → S3 Standard-IA (less frequent)
  Day 90 → S3 Glacier (archive)
  Day 365 → Delete

BUCKET POLICIES (who can access):
═════════════════════════════════
{
    "Statement": [{
        "Effect": "Allow",
        "Principal": "*",
        "Action": "s3:GetObject",
        "Resource": "arn:aws:s3:::my-website/*"
    }]
}
→ Makes bucket publicly readable (for static website hosting)

STATIC WEBSITE HOSTING:
═══════════════════════
S3 can host static websites (HTML, CSS, JS)
→ Enable in bucket settings
→ Set index.html as default page
→ Access via: http://my-bucket.s3-website.ap-south-1.amazonaws.com
```

### AWS CLI — S3 Commands

```bash
# List buckets
aws s3 ls

# List objects in bucket
aws s3 ls s3://my-bucket/

# Upload file
aws s3 cp myfile.txt s3://my-bucket/

# Upload entire folder
aws s3 sync ./build/ s3://my-bucket/

# Download file
aws s3 cp s3://my-bucket/myfile.txt ./

# Delete object
aws s3 rm s3://my-bucket/myfile.txt

# Create bucket
aws s3 mb s3://my-new-bucket --region ap-south-1
```

---

## 6. VPC — Virtual Private Cloud

### What is VPC?

VPC = Your **own private network** inside AWS. It's like having your own data center in the cloud with complete control over networking.

### VPC Architecture

```
┌─────────────────── VPC (10.0.0.0/16) ─────────────────────┐
│                                                             │
│    ┌─── AZ-1 (ap-south-1a) ───┐  ┌─── AZ-2 (ap-south-1b) ───┐
│    │                            │  │                            │
│    │  ┌── Public Subnet ──┐    │  │  ┌── Public Subnet ──┐    │
│    │  │   10.0.1.0/24     │    │  │  │   10.0.3.0/24     │    │
│    │  │  ┌─────┐ ┌─────┐ │    │  │  │  ┌─────┐          │    │
│    │  │  │Nginx│ │ NAT │ │    │  │  │  │Nginx│          │    │
│    │  │  └─────┘ └─────┘ │    │  │  │  └─────┘          │    │
│    │  └───────────────────┘    │  │  └───────────────────┘    │
│    │                            │  │                            │
│    │  ┌── Private Subnet ─┐    │  │  ┌── Private Subnet ─┐    │
│    │  │   10.0.2.0/24     │    │  │  │   10.0.4.0/24     │    │
│    │  │  ┌─────┐ ┌─────┐ │    │  │  │  ┌─────┐ ┌─────┐ │    │
│    │  │  │ App │ │ DB  │ │    │  │  │  │ App │ │ DB  │ │    │
│    │  │  └─────┘ └─────┘ │    │  │  │  └─────┘ └─────┘ │    │
│    │  └───────────────────┘    │  │  └───────────────────┘    │
│    └────────────────────────────┘  └────────────────────────────┘
│                                                             │
│                     Internet Gateway                        │
└─────────────────────────┬───────────────────────────────────┘
                          │
                       Internet
```

### VPC Components

| Component | What | Analogy |
|-----------|------|---------|
| **VPC** | Your isolated network (e.g., 10.0.0.0/16) | Your building |
| **Subnet** | Segment of VPC (e.g., 10.0.1.0/24) | Floor in the building |
| **Internet Gateway (IGW)** | Connects VPC to internet | Main door |
| **NAT Gateway** | Lets private subnet access internet (outbound only) | One-way door |
| **Route Table** | Rules for traffic routing | Direction signs |
| **Security Group** | Instance-level firewall (stateful) | Room door lock |
| **NACL** | Subnet-level firewall (stateless) | Floor gate |

### Public vs Private Subnet

| Aspect | Public Subnet | Private Subnet |
|--------|--------------|----------------|
| Internet access | Direct (via IGW) | Outbound only (via NAT) |
| Public IP | Yes (auto-assign) | No |
| Route table | Route to IGW | Route to NAT Gateway |
| Use for | Load balancers, Nginx, bastion | App servers, databases |
| Security | Exposed to internet | Hidden from internet |

### Security Group vs NACL

| Feature | Security Group | NACL |
|---------|---------------|------|
| Level | Instance (EC2) | Subnet |
| State | **Stateful** (return traffic auto-allowed) | **Stateless** (must allow both directions) |
| Rules | Allow only | Allow + Deny |
| Default | Deny all inbound, Allow all outbound | Allow all |
| Evaluation | All rules checked | Rules processed in order (number) |

### CIDR Notation (IP Ranges)

```
CIDR = Classless Inter-Domain Routing
═══════════════════════════════════════

10.0.0.0/16  → 10.0.0.0 to 10.0.255.255  → 65,536 IPs (VPC level)
10.0.1.0/24  → 10.0.1.0 to 10.0.1.255    → 256 IPs (Subnet level)
10.0.1.0/28  → 10.0.1.0 to 10.0.1.15     → 16 IPs (small subnet)

Smaller number after / = MORE IPs
/16 = 65,536    /24 = 256    /28 = 16    /32 = 1 (single IP)

Common VPC setup:
VPC:            10.0.0.0/16
Public Subnet:  10.0.1.0/24  (256 IPs)
Private Subnet: 10.0.2.0/24  (256 IPs)
```

---

## 7. RDS — Relational Database Service

### What is RDS?

RDS = **Managed relational database** service. AWS handles backups, patching, scaling, and high availability — you just use the database.

### Supported Engines

| Engine | Description |
|--------|-------------|
| **Amazon Aurora** | AWS-built, MySQL/PostgreSQL compatible, 5x faster |
| **PostgreSQL** | Popular open-source |
| **MySQL** | Most widely used open-source |
| **MariaDB** | MySQL fork |
| **Oracle** | Enterprise |
| **SQL Server** | Microsoft |

### RDS vs Self-Managed DB on EC2

| Aspect | RDS (Managed) | DB on EC2 (Self-Managed) |
|--------|--------------|-------------------------|
| Patching | Automated | You do it |
| Backups | Automated (35-day retention) | You configure it |
| High Availability | Multi-AZ toggle | You set up replication |
| Scaling | Click to resize | Manual migration |
| OS access | No SSH | Full SSH access |
| Cost | Higher | Lower (but more effort) |
| Best for | Most use cases | Custom DB configs |

### Multi-AZ Deployment (High Availability)

```
Normal Operation:
═════════════════
  App ──→ Primary DB (AZ-1)
          │
          │ synchronous replication
          ▼
          Standby DB (AZ-2)  ← not accessible directly

Failover (Primary fails):
═════════════════════════
  App ──→ ✗ Primary DB (AZ-1)  ← DOWN
          │
          │ automatic failover (~60 seconds)
          ▼
  App ──→ Standby DB (AZ-2)  ← PROMOTED to Primary
```

### Read Replicas (Performance)

```
Without Read Replicas:              With Read Replicas:
══════════════════════              ═══════════════════
  App (all queries) ──→ Primary     App (writes) ──→ Primary
  Heavy load on one DB                                │ async replication
                                    App (reads) ──→ Read Replica 1
                                    App (reads) ──→ Read Replica 2
                                    (spread read load across replicas)
```

| Feature | Multi-AZ | Read Replica |
|---------|----------|-------------|
| Purpose | High Availability (disaster recovery) | Performance (read scaling) |
| Replication | Synchronous | Asynchronous |
| Failover | Automatic | Manual promotion |
| Access | Standby not readable | Readable |
| Cross-region | No | Yes |

### RDS Connection from Spring Boot

```yaml
# application.yml
spring:
  datasource:
    url: jdbc:postgresql://my-db.abc123.ap-south-1.rds.amazonaws.com:5432/mydb
    username: admin
    password: ${DB_PASSWORD}    # Use environment variable or Secrets Manager
  jpa:
    hibernate:
      ddl-auto: validate        # Never use create/update in production!
```

---

## 8. Elastic Load Balancing (ELB)

### What is a Load Balancer?

A load balancer **distributes incoming traffic** across multiple targets (EC2 instances) to ensure no single server is overwhelmed.

```
Without Load Balancer:                With Load Balancer:
══════════════════════                ════════════════════

Client → EC2 (overloaded!)           Client → Load Balancer → EC2-1 (33%)
                                                     │
                                                     ├──→ EC2-2 (33%)
                                                     │
                                                     └──→ EC2-3 (33%)
```

### Types of Load Balancers

| Type | Layer | Protocol | Best For |
|------|-------|----------|----------|
| **ALB** (Application) | Layer 7 | HTTP/HTTPS | Web apps, microservices, path-based routing |
| **NLB** (Network) | Layer 4 | TCP/UDP | High performance, gaming, IoT |
| **CLB** (Classic) | Layer 4/7 | HTTP/TCP | Legacy (avoid for new projects) |
| **GWLB** (Gateway) | Layer 3 | IP | Firewalls, intrusion detection |

### ALB (Application Load Balancer) — Most Common

```
ALB — Path-Based Routing:
═════════════════════════

Client                   ALB
  │                       │
  ├─ /api/users ─────────→├───→ User Service (EC2 group 1)
  │                       │
  ├─ /api/orders ────────→├───→ Order Service (EC2 group 2)
  │                       │
  └─ /static/* ──────────→├───→ Static Content (S3 or EC2)


ALB — Host-Based Routing:
═════════════════════════
  api.myapp.com  ────────→ API servers
  web.myapp.com  ────────→ Web servers
  admin.myapp.com ───────→ Admin servers
```

### Target Groups

```
ALB ──→ Target Group: "user-service-tg"
              │
              ├── EC2 Instance 1 (port 8080) — Healthy ✓
              ├── EC2 Instance 2 (port 8080) — Healthy ✓
              └── EC2 Instance 3 (port 8080) — Unhealthy ✗ (removed)

Health Check:
  Path: /actuator/health
  Interval: 30 seconds
  Healthy threshold: 3 consecutive passes
  Unhealthy threshold: 2 consecutive failures
```

---

## 9. Auto Scaling

### What is Auto Scaling?

Auto Scaling **automatically adjusts** the number of EC2 instances based on demand.

```
Low Traffic (night):     Normal Traffic:        High Traffic (sale):
════════════════════     ════════════════       ════════════════════
  ┌────┐                 ┌────┐ ┌────┐         ┌────┐ ┌────┐ ┌────┐ ┌────┐
  │EC2 │                 │EC2 │ │EC2 │         │EC2 │ │EC2 │ │EC2 │ │EC2 │
  └────┘                 └────┘ └────┘         └────┘ └────┘ └────┘ └────┘
  Min: 1                 Desired: 2             Max: 4

  Auto Scaling automatically scales IN and OUT based on your rules.
```

### Auto Scaling Components

```
┌── Launch Template ────────────────────────────────────────┐
│  "What to launch"                                         │
│  - AMI: Amazon Linux 2                                    │
│  - Instance Type: t3.medium                               │
│  - Key Pair: my-key                                       │
│  - Security Group: web-sg                                 │
│  - User Data: install Java, start app                     │
└───────────────────────────────────────────────────────────┘
                         │
                         ▼
┌── Auto Scaling Group (ASG) ───────────────────────────────┐
│  "How to manage"                                          │
│  - Min capacity: 1                                        │
│  - Desired capacity: 2                                    │
│  - Max capacity: 6                                        │
│  - Subnets: AZ-1a, AZ-1b (spread across AZs)            │
│  - Target Group: attached to ALB                          │
└───────────────────────────────────────────────────────────┘
                         │
                         ▼
┌── Scaling Policies ───────────────────────────────────────┐
│  "When to scale"                                          │
│  - Target Tracking: Keep CPU at 60%                       │
│  - Step Scaling: CPU > 80% → add 2, CPU < 30% → remove 1 │
│  - Scheduled: Scale to 4 every Friday 6 PM                │
└───────────────────────────────────────────────────────────┘
```

### Scaling Types

| Type | Trigger | Example |
|------|---------|---------|
| **Target Tracking** | Maintain a metric target | Keep average CPU at 60% |
| **Step Scaling** | Based on alarm thresholds | CPU > 80% → add 2 instances |
| **Scheduled** | Based on time | Scale up every Monday 9 AM |
| **Predictive** | ML-based forecasting | Predict traffic spikes |

### Auto Scaling + ALB Together

```
Internet → ALB → Target Group ← Auto Scaling Group
                     │                 │
                     ├── EC2-1         ├── Min: 2
                     ├── EC2-2         ├── Desired: 2
                     └── (EC2-3 added  └── Max: 5
                          when CPU > 70%)

Flow:
1. Traffic increases → CPU rises above 70%
2. Auto Scaling launches new EC2 from Launch Template
3. New EC2 registers with Target Group
4. ALB health checks new EC2
5. ALB starts routing traffic to new EC2
6. Traffic decreases → CPU drops below 30%
7. Auto Scaling terminates extra EC2
```

---

## 10. Route 53 — DNS Service

### What is Route 53?

Route 53 = AWS **DNS service** that translates domain names to IP addresses. Also handles **domain registration** and **health checking**.

```
User types: www.myapp.com
            │
            ▼
       Route 53 (DNS)
            │
            ├── A Record:     myapp.com → 52.1.2.3 (EC2 IP)
            ├── CNAME Record: www.myapp.com → myapp.com
            └── Alias Record: myapp.com → ALB DNS name
```

### Routing Policies

| Policy | What | Use Case |
|--------|------|----------|
| **Simple** | One record, one destination | Single server |
| **Weighted** | Split traffic by weight (70/30) | A/B testing, gradual migration |
| **Latency-based** | Route to lowest latency region | Multi-region apps |
| **Failover** | Primary/secondary (with health check) | Disaster recovery |
| **Geolocation** | Route based on user's location | Country-specific content |

```
Weighted Routing Example:
═════════════════════════
  myapp.com
     │
     ├── 80% ──→ ALB-1 (current version)
     └── 20% ──→ ALB-2 (new version — canary)

Failover Routing Example:
═════════════════════════
  myapp.com
     │
     ├── Primary ──→ ALB Mumbai     ← Health check: HEALTHY ✓
     └── Secondary ──→ ALB Singapore ← Used only if Mumbai is DOWN
```

---

## 11. CloudWatch — Monitoring

### What is CloudWatch?

CloudWatch = AWS **monitoring and observability** service. Collects metrics, logs, and triggers alarms.

```
CloudWatch Components:
══════════════════════

1. METRICS → Numbers over time
   - EC2: CPU, Network, Disk
   - RDS: Connections, Read/Write IOPS
   - ALB: Request count, latency, error rate
   - Custom: Your app metrics

2. ALARMS → Trigger when metrics cross threshold
   - CPU > 80% for 5 minutes → Send SNS notification
   - Error rate > 5% → Trigger Auto Scaling

3. LOGS → Centralized log storage
   - Application logs
   - VPC Flow Logs
   - CloudTrail logs (API activity)

4. DASHBOARDS → Visual graphs
   - Custom dashboards for your app
```

### CloudWatch Alarm Example

```
CloudWatch Alarm: "High-CPU-Alarm"
═══════════════════════════════════
  Metric:     AWS/EC2 → CPUUtilization
  Condition:  Average > 80%
  Period:     5 minutes
  Actions:
    - ALARM state → Send email via SNS
    - ALARM state → Trigger Auto Scaling (add instance)
    - OK state    → Send "recovered" email

States: OK → ALARM → INSUFFICIENT_DATA
```

---

## 12. Lambda — Serverless Computing

### What is Lambda?

Lambda = Run code **without managing servers**. You upload your code, AWS handles everything else. You pay only for actual execution time.

```
Traditional (EC2):                   Serverless (Lambda):
══════════════════                   ═══════════════════
You manage the server               No server to manage
Pay for 24/7 running                Pay only when code runs
Scale manually                      Auto-scales to millions of requests
Patch OS yourself                   AWS handles everything

EC2:    $$$$ (always running)        Lambda: $    (runs only when triggered)
        idle 90% of the time                 0 cost when not running
```

### How Lambda Works

```
Event Source ──→ Lambda Function ──→ Action
═════════════    ════════════════    ══════

API Gateway  ──→ processOrder()  ──→ Write to DynamoDB
S3 Upload    ──→ resizeImage()   ──→ Save thumbnail to S3
CloudWatch   ──→ cleanupLogs()   ──→ Delete old files
SQS Message  ──→ sendEmail()     ──→ Call SES API
Schedule     ──→ dailyReport()   ──→ Generate and email report
```

### Lambda Example (Java)

```java
public class OrderHandler implements RequestHandler<APIGatewayProxyRequestEvent,
                                                     APIGatewayProxyResponseEvent> {
    @Override
    public APIGatewayProxyResponseEvent handleRequest(
            APIGatewayProxyRequestEvent input, Context context) {

        String body = input.getBody();
        context.getLogger().log("Received order: " + body);

        // Process order logic here

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(200);
        response.setBody("{\"message\": \"Order processed\"}");
        return response;
    }
}
```

### Lambda Limits

| Limit | Value |
|-------|-------|
| Memory | 128 MB to 10,240 MB |
| Timeout | Max 15 minutes |
| Package size | 50 MB (zipped), 250 MB (unzipped) |
| Concurrent executions | 1,000 (default, can increase) |
| Temporary storage (`/tmp`) | 512 MB to 10,240 MB |

### When to Use Lambda vs EC2

| Use Lambda | Use EC2 |
|-----------|---------|
| Short tasks (< 15 min) | Long-running processes |
| Event-driven (API calls, S3 uploads) | Always-on applications |
| Unpredictable traffic | Steady, predictable load |
| Simple functions | Complex applications |
| Cost optimization (pay per use) | Need full OS control |

---

## 13. Other Essential Services (Overview)

| Service | What It Does | One-Line Summary |
|---------|-------------|-----------------|
| **Elastic Beanstalk** | PaaS — deploy apps without managing infra | Upload code → AWS deploys everything |
| **ECR** | Docker container registry | Store Docker images (like Docker Hub but private) |
| **ECS** | Container orchestration (AWS native) | Run Docker containers without Kubernetes |
| **EKS** | Managed Kubernetes | Kubernetes on AWS |
| **DynamoDB** | NoSQL database (key-value) | Fast, serverless database for simple queries |
| **ElastiCache** | Managed Redis / Memcached | In-memory caching |
| **SQS** | Message queue | Decouple services (like RabbitMQ) |
| **SNS** | Pub/Sub notifications | Send emails, SMS, push notifications |
| **CloudFront** | CDN | Cache content at edge locations globally |
| **CloudTrail** | API activity logging | Who did what in your AWS account |
| **Secrets Manager** | Store secrets (passwords, keys) | Secure secret storage with rotation |
| **CloudFormation** | IaC (AWS native) | Terraform but AWS-specific (YAML/JSON) |
| **CodePipeline** | CI/CD | AWS-native CI/CD pipeline |
| **CodeBuild** | Build service | Build and test code (like Jenkins build stage) |
| **CodeDeploy** | Deployment service | Deploy to EC2, Lambda, ECS |

### Choosing the Right Compute Service

```
Need a server with full control?
└── YES → EC2

Need to run containers?
├── Small scale / simple → ECS (Fargate)
└── Large scale / Kubernetes → EKS

Need to run code without servers?
└── Lambda

Just want to deploy a web app quickly?
└── Elastic Beanstalk
```

### Choosing the Right Database

```
Relational data (SQL)?
├── Managed → RDS (PostgreSQL, MySQL)
└── High performance → Aurora

Key-value / simple queries?
└── DynamoDB

In-memory caching?
└── ElastiCache (Redis)

Full-text search?
└── OpenSearch (Elasticsearch)
```

---

## 14. Interview Questions

---

### Q1. What is the difference between a Region and an Availability Zone?

| Aspect | Region | Availability Zone (AZ) |
|--------|--------|----------------------|
| What | Geographic area | 1+ data centers within a region |
| Example | `ap-south-1` (Mumbai) | `ap-south-1a`, `ap-south-1b` |
| Count | 30+ regions globally | 2-6 AZs per region |
| Network | Connected via high-speed network | < 2ms latency between AZs |
| Purpose | Data residency, reduce latency | High availability, fault tolerance |

**Why multiple AZs?** If one data center has a power outage or disaster, your app keeps running in another AZ. Always deploy across **at least 2 AZs** for production workloads.

---

### Q2. Explain IAM Users, Groups, Roles, and Policies with an example.

**Scenario:** A company has 3 developers and 2 EC2 instances that need S3 access.

```
Policy: "S3ReadWrite" (JSON — defines permissions)
   │
   ├── Attached to Group: "Developers"
   │       ├── User: john
   │       ├── User: jane
   │       └── User: veer
   │       (All 3 can read/write S3)
   │
   └── Attached to Role: "EC2-S3-Role"
           └── Assumed by: EC2 instances
           (EC2 can access S3 without storing credentials)
```

| Component | Purpose | Has Credentials? |
|-----------|---------|-----------------|
| **User** | Person or app needing AWS access | Yes (username/password or access keys) |
| **Group** | Batch-assign permissions to users | No (just a container for users) |
| **Role** | Grant permissions to AWS services | No (temporary credentials via STS) |
| **Policy** | JSON document defining what's allowed | No (attached to user/group/role) |

**Key rule:** Use **Roles** for EC2/Lambda (not access keys). Access keys in code = security risk.

---

### Q3. What is a VPC? Explain public and private subnets.

A **VPC (Virtual Private Cloud)** is your isolated private network in AWS. Think of it as your own data center in the cloud.

```
Internet
    │
    ▼
Internet Gateway ──→ Public Subnet  (10.0.1.0/24)
                       │              - Nginx, Load Balancer
                       │              - Has public IP
                       │              - Route to IGW
                       │
                    NAT Gateway
                       │
                       ▼
                     Private Subnet (10.0.2.0/24)
                       - App Server, Database
                       - NO public IP
                       - Can access internet via NAT (outbound only)
                       - NOT reachable from internet
```

**Why private subnet?** Your database and application servers should NOT be directly accessible from the internet. Only the load balancer/Nginx in the public subnet is exposed.

---

### Q4. What is the difference between Security Group and NACL?

| Feature | Security Group | NACL |
|---------|---------------|------|
| Level | Instance (EC2) | Subnet |
| Stateful/Stateless | **Stateful** | **Stateless** |
| Rules | **Allow only** | Allow + **Deny** |
| Default | Deny all inbound | Allow all |
| Evaluation | All rules evaluated | Rules evaluated **in order** (by number) |
| Use case | Fine-grained instance control | Broad subnet-level blocking |

**Stateful vs Stateless:**
- **Security Group (Stateful):** If you allow inbound on port 80, the response is **automatically** allowed out.
- **NACL (Stateless):** You must **explicitly** allow both inbound port 80 AND outbound ephemeral ports.

---

### Q5. Explain S3 storage classes. When would you use each?

| Class | Use When |
|-------|----------|
| **Standard** | Frequently accessed (website images, app config) |
| **Standard-IA** | Accessed < 1 time/month (backups, DR copies) |
| **One Zone-IA** | Non-critical infrequent data (secondary backups) |
| **Glacier Instant** | Archives needing immediate access (medical records) |
| **Glacier Flexible** | Archives accessed 1-2 times/year (audit logs) |
| **Deep Archive** | Data accessed < 1 time/year, 12-48hr wait OK (compliance) |
| **Intelligent-Tiering** | When you don't know access pattern (let AWS optimize) |

**Cost optimization tip:** Use **Lifecycle Policies** to automatically move objects:
- Day 0: Standard → Day 30: Standard-IA → Day 90: Glacier → Day 365: Delete

---

### Q6. How does an Application Load Balancer (ALB) work?

ALB operates at **Layer 7 (HTTP/HTTPS)** and distributes traffic based on content of the request.

```
Client Request                    ALB Routing
══════════════                    ═══════════

GET /api/users    ──────→ Target Group: user-service
                           ├── EC2-1 (port 8080)
                           └── EC2-2 (port 8080)

GET /api/orders   ──────→ Target Group: order-service
                           ├── EC2-3 (port 8081)
                           └── EC2-4 (port 8081)

GET /static/logo  ──────→ Target Group: static-content
                           └── S3 bucket
```

**Key features:**
- **Path-based routing:** `/api/users` → user service, `/api/orders` → order service
- **Host-based routing:** `api.myapp.com` → API servers, `web.myapp.com` → web servers
- **Health checks:** Removes unhealthy targets automatically
- **SSL termination:** HTTPS at ALB, HTTP internally
- **Sticky sessions:** Same client → same instance (for stateful apps)

---

### Q7. What is Auto Scaling? Explain with a real-world scenario.

**Scenario:** E-commerce app — normal traffic is 1000 req/sec, but during sale events it spikes to 10,000 req/sec.

```
Auto Scaling Configuration:
═══════════════════════════
  Min instances: 2  (always running, even at night)
  Desired:       4  (normal traffic)
  Max instances: 10 (peak traffic)

  Scaling Policy: Target Tracking
  → Keep average CPU utilization at 60%

Timeline:
  10 PM (low traffic):   2 instances running    (scale in)
  10 AM (normal):        4 instances running
  Sale starts:           CPU spikes to 90%
  Auto Scaling:          Launches 3 more → 7 instances
  Traffic keeps growing: Launches 3 more → 10 instances (max)
  Sale ends:             CPU drops → scales back to 4

  Cost: You only pay for instances while they're running!
```

---

### Q8. Explain the difference between RDS Multi-AZ and Read Replicas.

| Feature | Multi-AZ | Read Replica |
|---------|----------|-------------|
| **Purpose** | High Availability (HA) | Read Performance |
| **Replication** | Synchronous | Asynchronous |
| **Readable?** | Standby is **NOT** readable | Replica **IS** readable |
| **Failover** | Automatic (DNS switch, ~60s) | Manual promotion |
| **Cross-Region** | Same region only | Can be cross-region |
| **Use case** | Production DB must not go down | Heavy read workload (reports, analytics) |
| **Count** | 1 standby | Up to 5 replicas |

**In Spring Boot:**
```yaml
# Write operations → Primary endpoint
spring.datasource.url=jdbc:postgresql://primary.rds.amazonaws.com:5432/mydb

# Read operations → Read replica endpoint (configure read-only DataSource)
spring.read-datasource.url=jdbc:postgresql://replica.rds.amazonaws.com:5432/mydb
```

---

### Q9. What is the difference between EC2, ECS, EKS, and Lambda?

| Service | What | You Manage | AWS Manages | Best For |
|---------|------|-----------|-------------|----------|
| **EC2** | Virtual machine | Everything (OS, app, scaling) | Hardware | Full control needed |
| **ECS** | AWS container service | Task definitions, scaling rules | Container orchestration | Simple container workloads |
| **EKS** | Managed Kubernetes | K8s configs, pods, deployments | K8s control plane | Complex container workloads, multi-cloud |
| **Lambda** | Serverless functions | Just the code | Everything else | Event-driven, short tasks |

```
Decision tree:
══════════════
Need full OS access?          → EC2
Running containers?
  ├── Simple, AWS-native      → ECS (Fargate)
  └── Need Kubernetes         → EKS
Just run a function?          → Lambda
```

---

### Q10. How would you deploy a Spring Boot application on AWS?

**Simple approach (for beginners):**

```
Option 1: EC2 (Manual)
═══════════════════════
1. Launch EC2 (Amazon Linux 2, t3.medium)
2. SSH into EC2
3. Install Java 17
4. SCP your JAR file to EC2
5. Run: java -jar my-app.jar
6. Configure Security Group: open port 8080

Option 2: EC2 + Docker
═══════════════════════
1. Launch EC2
2. Install Docker
3. Build Docker image with your app
4. Run: docker run -d -p 8080:8080 my-app:1.0

Option 3: Elastic Beanstalk (Easiest)
══════════════════════════════════════
1. Package your JAR
2. Upload to Elastic Beanstalk console
3. EB creates EC2, ALB, Auto Scaling automatically
4. Done! Your app is running with auto-scaling.
```

**Production approach:**

```
Architecture:
═════════════
Route 53 (DNS: myapp.com)
    │
    ▼
ALB (HTTPS, SSL certificate from ACM)
    │
    ├── Target Group 1: User Service (port 8080)
    │     ├── EC2 in AZ-1 (Auto Scaling Group)
    │     └── EC2 in AZ-2
    │
    └── Target Group 2: Order Service (port 8081)
          ├── EC2 in AZ-1
          └── EC2 in AZ-2

RDS PostgreSQL (Multi-AZ) ← Private subnet
ElastiCache Redis         ← Private subnet
S3                        ← Static assets
CloudWatch                ← Monitoring + Alerts
```

---

### Q11. What is CloudFormation? How is it different from Terraform?

Both are **Infrastructure as Code (IaC)** tools.

| Feature | CloudFormation | Terraform |
|---------|---------------|-----------|
| Provider | AWS only | Multi-cloud (AWS, Azure, GCP) |
| Language | YAML / JSON | HCL |
| State | Managed by AWS | `.tfstate` file (you manage) |
| Cost | Free | Free (open source) |
| Rollback | Automatic on failure | Manual |
| Best for | AWS-only projects | Multi-cloud projects |

```yaml
# CloudFormation example (YAML)
AWSTemplateFormatVersion: '2010-09-09'
Resources:
  MyEC2Instance:
    Type: AWS::EC2::Instance
    Properties:
      ImageId: ami-0c55b159cbfafe1f0
      InstanceType: t2.micro
      Tags:
        - Key: Name
          Value: my-web-server
```

---

### Q12. Explain the AWS Shared Responsibility Model.

```
┌──────────────────────────────────────────────────────┐
│              SHARED RESPONSIBILITY MODEL               │
│                                                        │
│  ┌────────────────────────────────────────────────┐   │
│  │         CUSTOMER Responsibility                 │   │
│  │         "Security IN the cloud"                 │   │
│  │                                                  │   │
│  │  - Your data                                     │   │
│  │  - IAM (users, roles, permissions)               │   │
│  │  - OS patching (on EC2)                          │   │
│  │  - Network config (Security Groups, NACLs)       │   │
│  │  - Encryption (enable it!)                       │   │
│  │  - Application security                          │   │
│  └────────────────────────────────────────────────┘   │
│                                                        │
│  ┌────────────────────────────────────────────────┐   │
│  │         AWS Responsibility                       │   │
│  │         "Security OF the cloud"                  │   │
│  │                                                  │   │
│  │  - Physical data centers                         │   │
│  │  - Hardware maintenance                          │   │
│  │  - Network infrastructure                        │   │
│  │  - Hypervisor                                    │   │
│  │  - Managed service patching (RDS, Lambda, S3)    │   │
│  └────────────────────────────────────────────────┘   │
└──────────────────────────────────────────────────────┘
```

**Simple rule:**
- **EC2:** You patch the OS, you secure the app
- **RDS:** AWS patches the DB engine, you manage access
- **Lambda:** AWS manages everything, you write secure code
- **S3:** AWS secures the service, you control bucket policies

---

### Q13. What is Elastic Beanstalk? When would you use it?

**Elastic Beanstalk** is a PaaS that deploys your app and automatically provisions EC2, ALB, Auto Scaling, RDS, and CloudWatch.

```
You provide:                     EB creates automatically:
════════════                     ═══════════════════════
  my-app.jar (or Docker image)   ─→ EC2 instances
                                  ─→ Auto Scaling Group
                                  ─→ Load Balancer (ALB)
                                  ─→ Security Groups
                                  ─→ CloudWatch monitoring
                                  ─→ RDS (optional)

You still have FULL access to the underlying resources.
```

**Use Elastic Beanstalk when:**
- You want to deploy quickly without deep AWS knowledge
- Standard web application (Spring Boot, Node.js, Python)
- Don't need complex custom infrastructure

**Don't use when:**
- Complex microservices architecture → Use ECS/EKS
- Need fine-grained control → Use EC2 directly
- Serverless → Use Lambda

---

### Q14. How do you secure an AWS environment? (Best practices)

```
AWS Security Checklist:
═══════════════════════

IDENTITY:
  ✓ Never use Root account daily
  ✓ Enable MFA on Root and all IAM users
  ✓ Use IAM Roles (not access keys) for EC2/Lambda
  ✓ Least privilege: give minimum required permissions

NETWORK:
  ✓ Use VPC with public/private subnets
  ✓ Put DB and app servers in private subnets
  ✓ Security Groups: only open necessary ports
  ✓ Use NACLs for subnet-level blocking

DATA:
  ✓ Encrypt EBS volumes
  ✓ Encrypt S3 buckets (SSE-S3 or SSE-KMS)
  ✓ Encrypt RDS (enable at creation)
  ✓ Use Secrets Manager for passwords (not env variables)
  ✓ Enable S3 versioning (protect against accidental delete)

MONITORING:
  ✓ Enable CloudTrail (logs ALL API activity)
  ✓ Enable VPC Flow Logs (network traffic)
  ✓ Set up CloudWatch Alarms (CPU, errors, billing)
  ✓ Enable AWS Config (track config changes)

BILLING:
  ✓ Set up billing alerts
  ✓ Use AWS Budgets
  ✓ Tag resources for cost tracking
```

---

### Q15. What is the AWS Free Tier?

| Service | Free Tier Limit | Duration |
|---------|----------------|----------|
| **EC2** | 750 hours/month of t2.micro | 12 months |
| **S3** | 5 GB storage, 20,000 GET, 2,000 PUT | 12 months |
| **RDS** | 750 hours/month of db.t2.micro, 20 GB | 12 months |
| **Lambda** | 1 million requests, 400,000 GB-seconds | Always free |
| **DynamoDB** | 25 GB, 25 read/write capacity units | Always free |
| **CloudWatch** | 10 alarms, 10 custom metrics | Always free |
| **SNS** | 1 million publishes | Always free |
| **SQS** | 1 million requests | Always free |

**Tip:** Always set up **billing alerts** in CloudWatch to avoid surprise charges!

---
