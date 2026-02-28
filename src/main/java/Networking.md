# Networking - Complete Reference Guide

---

## Table of Contents

1. [OSI Model — 7 Layers](#1-osi-model--7-layers)
2. [Common Protocols](#2-common-protocols)
3. [HTTP vs HTTPS](#3-http-vs-https)
4. [DNS — Domain Name System](#4-dns--domain-name-system)
5. [Network Types — LAN, WAN, MAN, PAN](#5-network-types--lan-wan-man-pan)
6. [IPv4 vs IPv6](#6-ipv4-vs-ipv6)
7. [Ping and Traceroute](#7-ping-and-traceroute)
8. [IP Address vs MAC Address](#8-ip-address-vs-mac-address)
9. [TLS / SSL](#9-tls--ssl)
10. [Interview Questions](#10-interview-questions)

---

## 1. OSI Model — 7 Layers

The **OSI (Open Systems Interconnection)** model is a conceptual framework that describes how data travels from one computer to another across a network, divided into **7 layers**.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                          OSI MODEL (7 Layers)                           │
│                                                                         │
│   Layer   │  Name          │  What It Does            │  Example        │
│   ────────┼────────────────┼──────────────────────────┼─────────────────│
│   7       │  Application   │  User-facing services    │  HTTP, FTP,     │
│           │                │  (what you interact with)│  SMTP, DNS      │
│   ────────┼────────────────┼──────────────────────────┼─────────────────│
│   6       │  Presentation  │  Data formatting,        │  SSL/TLS,       │
│           │                │  encryption, compression │  JPEG, JSON     │
│   ────────┼────────────────┼──────────────────────────┼─────────────────│
│   5       │  Session       │  Opens/closes/manages    │  NetBIOS,       │
│           │                │  sessions between apps   │  RPC, PPTP      │
│   ────────┼────────────────┼──────────────────────────┼─────────────────│
│   4       │  Transport     │  Reliable delivery,      │  TCP, UDP       │
│           │                │  error checking, ports   │                 │
│   ────────┼────────────────┼──────────────────────────┼─────────────────│
│   3       │  Network       │  Routing, IP addressing  │  IP, ICMP,     │
│           │                │  (path selection)        │  Routers        │
│   ────────┼────────────────┼──────────────────────────┼─────────────────│
│   2       │  Data Link     │  MAC addressing, frames, │  Ethernet,      │
│           │                │  error detection         │  Wi-Fi, Switch  │
│   ────────┼────────────────┼──────────────────────────┼─────────────────│
│   1       │  Physical      │  Raw bits on the wire    │  Cables, Hubs,  │
│           │                │  (electrical signals)    │  Fiber optic    │
│                                                                         │
│   Data flows:  TOP → DOWN (sending)  |  BOTTOM → TOP (receiving)        │
└─────────────────────────────────────────────────────────────────────────┘
```

### How Data Travels Through the Layers

```
SENDER (You)                                      RECEIVER (Server)

Layer 7: Application    "GET /index.html"          Layer 7: Application
         ↓ adds HTTP header                                 ↑ removes HTTP header
Layer 6: Presentation   encrypts data (TLS)        Layer 6: Presentation
         ↓ adds encoding info                               ↑ decrypts data
Layer 5: Session        creates session ID          Layer 5: Session
         ↓ adds session info                                ↑ manages session
Layer 4: Transport      breaks into segments        Layer 4: Transport
         ↓ adds port numbers (src:5000, dst:443)            ↑ reassembles segments
Layer 3: Network        adds IP addresses           Layer 3: Network
         ↓ adds src/dst IP (192.168.1.5 → 8.8.8.8)         ↑ reads IP, routes
Layer 2: Data Link      adds MAC addresses          Layer 2: Data Link
         ↓ creates frames                                   ↑ reads MAC, removes frame
Layer 1: Physical       sends as electrical signals Layer 1: Physical
         ↓ bits → wire/radio                                ↑ signals → bits
```

### Data Units at Each Layer

| Layer | Data Unit | What's Added |
|-------|-----------|-------------|
| 7-5 Application/Presentation/Session | **Data** | Application headers |
| 4 Transport | **Segment** | Source port, Destination port |
| 3 Network | **Packet** | Source IP, Destination IP |
| 2 Data Link | **Frame** | Source MAC, Destination MAC |
| 1 Physical | **Bits** | Electrical signals (0s and 1s) |

### Easy Mnemonic to Remember

```
Top → Down (Layer 7 → 1):  "All People Seem To Need Data Processing"
Bottom → Up (Layer 1 → 7): "Please Do Not Throw Sausage Pizza Away"

A - Application
P - Presentation
S - Session
T - Transport
N - Network
D - Data Link
P - Physical
```

---

## 2. Common Protocols

### Protocol Overview

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        COMMON PROTOCOLS                                  │
├──────────┬──────────┬───────────────────────────────────────────────────┤
│ Protocol │  Port    │  Purpose                                          │
├──────────┼──────────┼───────────────────────────────────────────────────┤
│ HTTP     │  80      │  Web pages (unencrypted)                          │
│ HTTPS    │  443     │  Web pages (encrypted with TLS/SSL)               │
│ FTP      │  20, 21  │  File transfer (unencrypted)                      │
│ SFTP     │  22      │  Secure file transfer (over SSH)                  │
│ SMTP     │  25, 587 │  Sending emails                                   │
│ POP3     │  110, 995│  Receiving emails (downloads to device)           │
│ IMAP     │  143, 993│  Receiving emails (stays on server)               │
│ TCP      │  —       │  Reliable, ordered delivery (connection-based)    │
│ UDP      │  —       │  Fast, no guarantee (connectionless)              │
│ DNS      │  53      │  Domain name → IP address resolution              │
│ SSH      │  22      │  Secure remote login                              │
│ DHCP     │  67, 68  │  Automatic IP assignment                          │
└──────────┴──────────┴───────────────────────────────────────────────────┘
```

---

### HTTP (HyperText Transfer Protocol)

- **Port:** 80
- **Layer:** Application (Layer 7)
- **What:** Protocol for transferring web pages between browser and server
- **How:** Client sends request → Server sends response
- **Drawback:** Data sent in **plain text** — anyone can read it (no encryption)

```
Browser                              Server
  │                                    │
  │── GET /home HTTP/1.1 ────────────>│
  │   Host: www.example.com           │
  │                                    │
  │<──── HTTP/1.1 200 OK ────────────│
  │      Content-Type: text/html      │
  │      <html>...</html>             │
```

**HTTP Methods:**

| Method | Purpose | Idempotent | Body |
|--------|---------|-----------|------|
| GET | Retrieve data | Yes | No |
| POST | Create resource | No | Yes |
| PUT | Update/replace resource | Yes | Yes |
| PATCH | Partial update | No | Yes |
| DELETE | Remove resource | Yes | No |

---

### HTTPS (HTTP Secure)

- **Port:** 443
- **Layer:** Application + Presentation (Layer 7 + 6)
- **What:** HTTP + **TLS/SSL encryption**
- **How:** Establishes encrypted tunnel first (TLS handshake), then sends HTTP inside it
- **Why:** Protects data from eavesdropping, tampering, and impersonation

```
Browser                              Server
  │                                    │
  │── TLS Handshake ────────────────>│   (exchange keys, verify certificate)
  │<── TLS Handshake ────────────────│
  │                                    │
  │══ ENCRYPTED TUNNEL ESTABLISHED ══│
  │                                    │
  │── GET /home (encrypted) ────────>│   (nobody can read this)
  │<── 200 OK (encrypted) ──────────│
```

---

### FTP (File Transfer Protocol)

- **Port:** 20 (data), 21 (control/commands)
- **What:** Transfer files between client and server
- **Drawback:** Username, password, and files sent in **plain text**

```
Client                              FTP Server
  │── Connect to port 21 ─────────>│  (control connection)
  │── USER admin ──────────────────>│
  │── PASS secret123 ──────────────>│  (password in plain text!)
  │── RETR file.txt ───────────────>│
  │<── file.txt (via port 20) ─────│  (data connection)
```

---

### SFTP (SSH File Transfer Protocol)

- **Port:** 22
- **What:** Secure file transfer over **SSH tunnel**
- **Advantage:** Everything encrypted — credentials, commands, file data
- **Not the same as FTPS** (FTPS = FTP + SSL, SFTP = completely different protocol over SSH)

| Aspect | FTP | SFTP |
|--------|-----|------|
| Encryption | None | Full (SSH) |
| Ports | 20, 21 | 22 |
| Authentication | Username/password (plain text) | SSH keys or encrypted password |
| Firewall friendly | No (multiple ports) | Yes (single port) |
| Speed | Slightly faster | Slightly slower (encryption overhead) |

---

### SMTP (Simple Mail Transfer Protocol)

- **Port:** 25 (server-to-server), 587 (client-to-server with TLS)
- **What:** Protocol for **sending** emails
- **Only sends** — needs POP3/IMAP to receive

```
Your Email Client                   Email Server (Gmail)
  │                                    │
  │── HELO client.com ───────────────>│
  │── MAIL FROM: you@gmail.com ──────>│
  │── RCPT TO: friend@yahoo.com ─────>│
  │── DATA ──────────────────────────>│
  │   Subject: Hello!                  │
  │   Hi, how are you?                │
  │   .                               │
  │<── 250 OK Message sent ──────────│
  │                                    │
  │              Gmail Server ───────────────> Yahoo Server
  │                          (SMTP relay)
```

---

### POP3 (Post Office Protocol v3)

- **Port:** 110 (plain), 995 (with SSL)
- **What:** Protocol for **receiving/downloading** emails
- **Behavior:** Downloads emails to your device and **deletes from server** (by default)

| Aspect | POP3 | IMAP |
|--------|------|------|
| Emails stored | Downloaded to device | Remain on server |
| Multi-device | No (only on one device) | Yes (synced everywhere) |
| Offline access | Yes (downloaded) | Limited |
| Storage | Uses device storage | Uses server storage |
| Best for | Single device, limited server space | Multiple devices |

---

### TCP (Transmission Control Protocol)

- **Layer:** Transport (Layer 4)
- **Type:** Connection-oriented
- **What:** Reliable, ordered delivery of data with error checking
- **How:** Uses a **3-way handshake** to establish connection

```
3-Way Handshake:

Client                              Server
  │                                    │
  │── SYN (Can we connect?) ─────────>│
  │                                    │
  │<── SYN-ACK (Yes, let's!) ────────│
  │                                    │
  │── ACK (Great, connected!) ───────>│
  │                                    │
  │══════ Connection Established ═════│
  │                                    │
  │── Data ──────────────────────────>│
  │<── ACK (received) ───────────────│
  │── More Data ─────────────────────>│
  │<── ACK (received) ───────────────│
  │                                    │
  │── FIN (I'm done) ───────────────>│   4-Way Teardown
  │<── ACK ──────────────────────────│
  │<── FIN ──────────────────────────│
  │── ACK ──────────────────────────>│
```

---

### UDP (User Datagram Protocol)

- **Layer:** Transport (Layer 4)
- **Type:** Connectionless
- **What:** Fast, unreliable delivery — no handshake, no acknowledgment
- **When:** Speed matters more than reliability

```
Client                              Server
  │                                    │
  │── Data ──────────────────────────>│   (no handshake)
  │── Data ──────────────────────────>│   (no ACK)
  │── Data ──────────────────────────>│   (some may be lost)
  │── Data ──────────────────────────>│   (no ordering)
```

**TCP vs UDP:**

| Aspect | TCP | UDP |
|--------|-----|-----|
| **Connection** | Connection-oriented (handshake) | Connectionless |
| **Reliability** | Guaranteed delivery | No guarantee |
| **Ordering** | Data arrives in order | May arrive out of order |
| **Speed** | Slower (overhead) | Faster (no overhead) |
| **Error checking** | Yes + retransmission | Checksum only |
| **Header size** | 20 bytes | 8 bytes |
| **Use cases** | Web (HTTP), Email (SMTP), File transfer (FTP) | Video streaming, Gaming, DNS, VoIP |
| **Analogy** | Registered mail (tracked, confirmed) | Throwing postcards (hope they arrive) |

---

## 3. HTTP vs HTTPS

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         HTTP vs HTTPS                                    │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   HTTP (Insecure)                    HTTPS (Secure)                     │
│   ───────────────                    ──────────────                     │
│                                                                         │
│   Browser ──── plain text ───> Server   Browser ══ encrypted ══> Server │
│                                                                         │
│   Anyone on the network can:         Nobody can:                        │
│   ✗ Read your data                   ✓ Data is encrypted                │
│   ✗ Steal passwords                  ✓ Password protected               │
│   ✗ Modify data in transit           ✓ Data integrity verified          │
│   ✗ Impersonate the server           ✓ Server identity verified (cert)  │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

| Aspect | HTTP | HTTPS |
|--------|------|-------|
| **Full form** | HyperText Transfer Protocol | HyperText Transfer Protocol Secure |
| **Port** | 80 | 443 |
| **Encryption** | None — plain text | TLS/SSL encryption |
| **Certificate** | Not required | Requires SSL/TLS certificate |
| **URL** | `http://example.com` | `https://example.com` |
| **Speed** | Slightly faster | Slightly slower (encryption overhead, negligible today) |
| **SEO** | Lower ranking | Google gives ranking boost |
| **Data integrity** | Data can be modified in transit | Data cannot be tampered |
| **Authentication** | No server verification | Server verified via certificate |
| **Use case** | Never use for anything sensitive | Always use for everything |

**How HTTPS Works (Simplified):**

```
Step 1: Browser connects to https://bank.com

Step 2: TLS Handshake
  Browser                                Server
    │── "Hello, I support TLS 1.3" ────>│
    │<── Certificate + Public Key ──────│   (server proves identity)
    │                                    │
    │   Browser verifies certificate     │
    │   with Certificate Authority (CA)  │
    │                                    │
    │── Encrypted session key ─────────>│   (using server's public key)
    │                                    │
    │══ Both now share a session key ═══│

Step 3: All data encrypted with session key
    │══ GET /account (encrypted) ══════>│
    │<══ 200 OK + data (encrypted) ════│
```

---

## 4. DNS — Domain Name System

**DNS** is the **phone book of the internet**. It translates human-readable domain names into IP addresses.

```
You type: www.google.com
DNS returns: 142.250.190.46
Browser connects to: 142.250.190.46
```

### How DNS Resolution Works

```
┌─────────────────────────────────────────────────────────────────────────┐
│                      DNS RESOLUTION PROCESS                              │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  You type: www.google.com                                               │
│                                                                         │
│  Step 1: Browser Cache                                                  │
│  ──────────────────────                                                 │
│  Browser checks: "Do I already know this IP?"                           │
│  → Yes? Use it. Done!                                                   │
│  → No? Go to Step 2.                                                    │
│                                                                         │
│  Step 2: OS Cache                                                       │
│  ────────────────                                                       │
│  OS checks /etc/hosts and local DNS cache                               │
│  → Found? Use it. Done!                                                 │
│  → Not found? Go to Step 3.                                             │
│                                                                         │
│  Step 3: Recursive DNS Resolver (usually ISP)                           │
│  ────────────────────────────────────────────                           │
│  Resolver checks its cache.                                             │
│  → Found? Return it. Done!                                              │
│  → Not found? Start querying DNS hierarchy.                             │
│                                                                         │
│  Step 4: Root Name Server (.)                                           │
│  ────────────────────────────                                           │
│  Resolver asks: "Where is .com?"                                        │
│  Root says: "Ask the .com TLD server at 192.5.6.30"                     │
│                                                                         │
│  Step 5: TLD Name Server (.com)                                         │
│  ──────────────────────────────                                         │
│  Resolver asks: "Where is google.com?"                                  │
│  TLD says: "Ask Google's nameserver at ns1.google.com"                  │
│                                                                         │
│  Step 6: Authoritative Name Server (google.com)                         │
│  ──────────────────────────────────────────────                         │
│  Resolver asks: "What is the IP for www.google.com?"                    │
│  Authoritative says: "142.250.190.46"                                   │
│                                                                         │
│  Step 7: Resolver caches the result and returns it to your browser.     │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

```
Your Browser
    │
    ▼
┌──────────────────┐     ┌──────────────────┐
│ Recursive         │────>│ Root Server (.)  │  "Ask .com server"
│ Resolver (ISP)   │     └──────────────────┘
│                  │     ┌──────────────────┐
│                  │────>│ TLD Server (.com)│  "Ask google.com server"
│                  │     └──────────────────┘
│                  │     ┌──────────────────┐
│                  │────>│ Authoritative    │  "IP = 142.250.190.46"
│                  │     │ (google.com)     │
└──────────────────┘     └──────────────────┘
    │
    ▼
Browser connects to 142.250.190.46
```

### DNS Record Types

| Record | Purpose | Example |
|--------|---------|---------|
| **A** | Domain → IPv4 address | `google.com → 142.250.190.46` |
| **AAAA** | Domain → IPv6 address | `google.com → 2607:f8b0:4004::200e` |
| **CNAME** | Alias for another domain | `www.google.com → google.com` |
| **MX** | Mail server for domain | `google.com → mail.google.com` |
| **NS** | Nameserver for domain | `google.com → ns1.google.com` |
| **TXT** | Text info (verification, SPF) | SPF records for email auth |
| **PTR** | Reverse lookup (IP → Domain) | `142.250.190.46 → google.com` |
| **SOA** | Start of Authority (primary info) | Zone admin, serial number |

### DNS Commands

```bash
# Look up IP address for a domain
nslookup google.com

# Detailed DNS query
dig google.com

# Check specific record type
dig google.com MX          # Mail records
dig google.com AAAA        # IPv6 address
dig google.com NS          # Nameservers

# Reverse DNS lookup
dig -x 142.250.190.46

# Check local DNS file
cat /etc/hosts
# 127.0.0.1  localhost
# 192.168.1.5  myserver.local
```

---

## 5. Network Types — LAN, WAN, MAN, PAN

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        NETWORK TYPES BY AREA                             │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  PAN ──── LAN ──── MAN ──── WAN                                        │
│  (body)   (building) (city)  (world)                                    │
│                                                                         │
│  ┌─────┐  ┌──────────┐  ┌──────────────┐  ┌─────────────────────────┐  │
│  │ PAN │  │   LAN    │  │     MAN      │  │          WAN            │  │
│  │     │  │          │  │              │  │                         │  │
│  │ You │  │  Home /  │  │    City /    │  │  Country / World /     │  │
│  │ +   │  │  Office  │  │  Campus      │  │  Internet              │  │
│  │Phone│  │          │  │              │  │                         │  │
│  │ +   │  │ WiFi     │  │  ISP network │  │  Undersea cables       │  │
│  │Watch│  │ Ethernet │  │  City fiber  │  │  Satellites             │  │
│  │     │  │          │  │              │  │                         │  │
│  │~10m │  │ ~100m    │  │ ~50km        │  │ Unlimited               │  │
│  └─────┘  └──────────┘  └──────────────┘  └─────────────────────────┘  │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

| Aspect | PAN | LAN | MAN | WAN |
|--------|-----|-----|-----|-----|
| **Full form** | Personal Area Network | Local Area Network | Metropolitan Area Network | Wide Area Network |
| **Range** | ~10 meters | ~100m - 1km | ~5 - 50 km | Unlimited (worldwide) |
| **Coverage** | Around a person | Building / Office / Home | City / Campus | Country / Globe |
| **Speed** | Low-Medium | High (1-10 Gbps) | Medium-High | Varies (low to high) |
| **Ownership** | Individual | Private (organization) | ISP / Government | ISP / Telecom |
| **Technology** | Bluetooth, USB, NFC | Ethernet, Wi-Fi | Fiber, WiMAX | Fiber, Satellite, Leased lines |
| **Cost** | Very low | Low | Medium | High |
| **Example** | Phone + Smartwatch + Earbuds | Office network, Home Wi-Fi | City-wide cable TV network | The Internet |

### Real-World Examples

```
PAN:  Your phone connects to your Bluetooth earbuds and smartwatch
      ┌───────┐
      │ Phone ├──Bluetooth──► Earbuds
      │       ├──Bluetooth──► Smartwatch
      │       ├──USB────────► Laptop
      └───────┘

LAN:  Your home or office network
      ┌──────────┐
      │  Router  ├──WiFi──► Laptop
      │          ├──WiFi──► Phone
      │          ├──Ethernet──► Desktop
      │          ├──WiFi──► Smart TV
      └──────────┘

MAN:  Multiple LANs connected across a city
      ┌─────────┐         ┌─────────┐
      │ Office  │──Fiber──│ Office  │
      │ Branch1 │         │ Branch2 │
      └────┬────┘         └────┬────┘
           │                   │
           └──── City Fiber ───┘
                     │
              ┌──────┴──────┐
              │  Data Center │
              └─────────────┘

WAN:  The Internet connecting countries
      ┌─────────┐                    ┌─────────┐
      │  India  │──Undersea Cable──>│   USA   │
      │ Network │                    │ Network │
      └─────────┘                    └─────────┘
```

---

## 6. IPv4 vs IPv6

### IPv4

- **Internet Protocol version 4** — the most widely used IP addressing system
- **Format:** 4 groups of numbers separated by dots
- **Size:** 32 bits → ~4.3 billion addresses

```
IPv4 Address: 192.168.1.100

Binary: 11000000.10101000.00000001.01100100

Structure:
┌──────────────┬──────────────┐
│ Network Part │  Host Part   │
│  192.168.1   │    .100      │
└──────────────┴──────────────┘

Range: 0.0.0.0 to 255.255.255.255
Each octet: 0-255 (8 bits each × 4 = 32 bits)
```

**IPv4 Address Classes:**

| Class | Range | Default Mask | Purpose |
|-------|-------|-------------|---------|
| A | 1.0.0.0 – 126.255.255.255 | 255.0.0.0 (/8) | Large networks |
| B | 128.0.0.0 – 191.255.255.255 | 255.255.0.0 (/16) | Medium networks |
| C | 192.0.0.0 – 223.255.255.255 | 255.255.255.0 (/24) | Small networks |
| D | 224.0.0.0 – 239.255.255.255 | — | Multicasting |
| E | 240.0.0.0 – 255.255.255.255 | — | Reserved/Experimental |

**Private IP Ranges (used inside local networks):**

| Class | Range | Example |
|-------|-------|---------|
| A | 10.0.0.0 – 10.255.255.255 | Corporate networks |
| B | 172.16.0.0 – 172.31.255.255 | Medium organizations |
| C | 192.168.0.0 – 192.168.255.255 | Home networks |

---

### IPv6

- **Internet Protocol version 6** — successor to IPv4, solves address exhaustion
- **Format:** 8 groups of hexadecimal separated by colons
- **Size:** 128 bits → 340 undecillion addresses (3.4 × 10^38)

```
IPv6 Address: 2001:0db8:85a3:0000:0000:8a2e:0370:7334

Shortened:    2001:db8:85a3::8a2e:370:7334
              (leading zeros dropped, consecutive 0-groups replaced with ::)

Structure:
┌──────────────────────────────┬──────────────────────────────┐
│      Network Prefix (64 bits)│     Interface ID (64 bits)   │
│      2001:db8:85a3:0000      │     0000:8a2e:0370:7334      │
└──────────────────────────────┴──────────────────────────────┘
```

### IPv4 vs IPv6 Comparison

| Aspect | IPv4 | IPv6 |
|--------|------|------|
| **Address size** | 32 bits | 128 bits |
| **Format** | Decimal (192.168.1.1) | Hexadecimal (2001:db8::1) |
| **Total addresses** | ~4.3 billion | ~340 undecillion |
| **Notation** | Dotted decimal | Colon-separated hex |
| **Header size** | 20-60 bytes (variable) | 40 bytes (fixed) |
| **Checksum** | Yes (in header) | No (handled by upper layers) |
| **NAT required?** | Yes (address shortage) | No (enough addresses) |
| **IPSec** | Optional | Built-in |
| **Broadcast** | Yes | No (uses multicast/anycast) |
| **Auto-configuration** | DHCP required | SLAAC (stateless auto-config) |
| **Fragmentation** | By routers and sender | Only by sender |
| **Example** | `192.168.1.100` | `2001:db8:85a3::8a2e:370:7334` |

**Why do we need IPv6?**
- IPv4 ran out of addresses (4.3 billion isn't enough for all devices)
- No more NAT workarounds — every device gets a unique public IP
- Better security (built-in IPSec)
- Faster routing (simplified header)

---

## 7. Ping and Traceroute

### Ping

**Ping** tests whether a remote host is **reachable** and measures **round-trip time (latency)**. It uses the **ICMP (Internet Control Message Protocol)** at Layer 3.

```
Your Computer                        Target (google.com)
    │                                    │
    │── ICMP Echo Request ──────────────>│
    │                                    │
    │<── ICMP Echo Reply ───────────────│   (time measured)
    │                                    │
    │── ICMP Echo Request ──────────────>│
    │<── ICMP Echo Reply ───────────────│
    │                                    │
    │── ICMP Echo Request ──────────────>│
    │<── ICMP Echo Reply ───────────────│
```

```bash
# Basic ping
ping google.com

# Output:
# PING google.com (142.250.190.46): 56 bytes
# 64 bytes from 142.250.190.46: icmp_seq=0 ttl=117 time=12.3 ms
# 64 bytes from 142.250.190.46: icmp_seq=1 ttl=117 time=11.8 ms
# 64 bytes from 142.250.190.46: icmp_seq=2 ttl=117 time=12.1 ms
#
# --- google.com ping statistics ---
# 3 packets transmitted, 3 received, 0% packet loss
# round-trip min/avg/max = 11.8/12.1/12.3 ms

# Ping with limited count
ping -c 4 google.com       # Linux/Mac — send 4 pings
ping -n 4 google.com       # Windows — send 4 pings

# Ping with specific packet size
ping -s 1000 google.com    # Send 1000-byte packets
```

**What ping output tells you:**

| Field | Meaning |
|-------|---------|
| `bytes` | Size of ICMP packet |
| `icmp_seq` | Sequence number (to detect lost packets) |
| `ttl` | Time To Live — max hops before packet is discarded |
| `time` | Round-trip time in milliseconds (latency) |
| `packet loss` | % of packets that didn't return |

**Common ping results:**

| Result | Meaning |
|--------|---------|
| Reply with low time (< 50ms) | Good connection |
| Reply with high time (> 200ms) | Slow/distant connection |
| Request timed out | Host unreachable or firewall blocking |
| 100% packet loss | Host is down or ICMP blocked |
| Intermittent loss | Unstable network |

---

### Traceroute

**Traceroute** shows the **complete path** (each router/hop) that packets take from your computer to the destination. It reveals where delays or failures occur.

```
Your PC → Router1 → Router2 → Router3 → ... → Destination

traceroute google.com

 Hop  │ IP Address        │ Time
 ─────┼───────────────────┼──────
  1   │ 192.168.1.1       │ 1 ms    ← Your home router
  2   │ 10.0.0.1          │ 5 ms    ← ISP gateway
  3   │ 72.14.215.85      │ 12 ms   ← ISP backbone
  4   │ 108.170.250.33    │ 15 ms   ← Google edge
  5   │ 142.250.190.46    │ 18 ms   ← google.com (destination)
```

**How traceroute works:**

```
Uses TTL (Time To Live) — each router decrements TTL by 1.
When TTL reaches 0, router sends back "Time Exceeded" message.

Packet with TTL=1:
Your PC ──> Router1 (TTL=0, replies "I'm 192.168.1.1, 1ms")

Packet with TTL=2:
Your PC ──> Router1 ──> Router2 (TTL=0, replies "I'm 10.0.0.1, 5ms")

Packet with TTL=3:
Your PC ──> Router1 ──> Router2 ──> Router3 (TTL=0, replies)

... continues until destination reached.
```

```bash
# Linux/Mac
traceroute google.com

# Windows
tracert google.com

# With max hops
traceroute -m 20 google.com

# Using TCP instead of ICMP
traceroute -T google.com
```

**Ping vs Traceroute:**

| Aspect | Ping | Traceroute |
|--------|------|-----------|
| **Purpose** | Is the host reachable? How fast? | What path do packets take? |
| **Shows** | Latency + packet loss to destination | Every hop along the route |
| **Protocol** | ICMP Echo Request/Reply | ICMP with incrementing TTL |
| **Use when** | "Is the server up?" | "Where is the network slow/broken?" |
| **Output** | Round-trip time per packet | Each router + time per hop |

---

## 8. IP Address vs MAC Address

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     IP ADDRESS vs MAC ADDRESS                            │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  IP Address = "Where you live" (logical, changes when you move)         │
│  MAC Address = "Your fingerprint" (physical, permanent, unique to you)  │
│                                                                         │
│  IP:  192.168.1.100                                                     │
│  MAC: A4:83:E7:2F:5B:01                                                │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

| Aspect | IP Address | MAC Address |
|--------|-----------|-------------|
| **Full form** | Internet Protocol Address | Media Access Control Address |
| **OSI Layer** | Layer 3 (Network) | Layer 2 (Data Link) |
| **Purpose** | Identify device on a **network** (logical) | Identify device's **hardware** (physical) |
| **Format** | IPv4: `192.168.1.100` (decimal) | `A4:83:E7:2F:5B:01` (hexadecimal) |
| **Size** | 32 bits (IPv4) / 128 bits (IPv6) | 48 bits (6 bytes) |
| **Assigned by** | DHCP server / Network admin / ISP | Manufacturer (burned into NIC) |
| **Can change?** | Yes — changes with network/location | No — permanently assigned (can be spoofed) |
| **Scope** | Global (routable across internet) | Local (only within same LAN) |
| **Uniqueness** | Unique within a network | Globally unique (in theory) |
| **Example** | `192.168.1.100` or `2001:db8::1` | `A4:83:E7:2F:5B:01` |
| **Analogy** | Mailing address (123 Main St) | Fingerprint / Aadhaar number |

### How IP and MAC Work Together

```
You want to reach: 192.168.1.50 (another device on your LAN)

Step 1: Your PC knows the destination IP (192.168.1.50)
Step 2: But Ethernet needs a MAC address to deliver the frame
Step 3: ARP (Address Resolution Protocol) resolves IP → MAC

Your PC                           LAN
  │                                │
  │── ARP Request (broadcast): ───>│  "Who has 192.168.1.50?
  │   "Who has 192.168.1.50?       │   Tell A4:83:E7:2F:5B:01"
  │    Tell me your MAC!"          │
  │                                │
  │<── ARP Reply: ────────────────│  "192.168.1.50 is at
  │   "I'm B2:7C:D1:44:8A:03"    │   B2:7C:D1:44:8A:03"
  │                                │
  │   Now your PC builds the frame:│
  │   ┌─────────────┬─────────────┐│
  │   │ Dst MAC:    │ Src MAC:    ││
  │   │ B2:7C:D1:  │ A4:83:E7:  ││
  │   │ 44:8A:03   │ 2F:5B:01   ││
  │   ├─────────────┼─────────────┤│
  │   │ Dst IP:     │ Src IP:     ││
  │   │ 192.168.1.50│ 192.168.1.5││
  │   ├─────────────┴─────────────┤│
  │   │        DATA               ││
  │   └───────────────────────────┘│
```

### Useful Commands

```bash
# Find your IP address
ip addr show          # Linux
ifconfig              # Mac/Linux
ipconfig              # Windows

# Find your MAC address
ip link show          # Linux
ifconfig              # Mac — look for "ether"
ipconfig /all         # Windows — "Physical Address"

# View ARP table (IP → MAC mappings)
arp -a

# Find MAC of a remote device on LAN
ping 192.168.1.50 && arp -a | grep 192.168.1.50
```

**MAC Address Structure:**

```
A4:83:E7:2F:5B:01
└──┬──┘ └──┬──┘
   │       │
   │       └── Device-specific (unique per device)
   └────────── OUI (Organizationally Unique Identifier)
               Identifies manufacturer:
               A4:83:E7 = Intel
               00:1A:2B = Apple
               3C:5A:B4 = Samsung
```

---

## 9. TLS / SSL

### What is SSL/TLS?

**SSL (Secure Sockets Layer)** and **TLS (Transport Layer Security)** are cryptographic protocols that provide **secure communication** over a network.

- **SSL** — Original protocol (versions 1.0, 2.0, 3.0). Now **deprecated** and insecure.
- **TLS** — Successor to SSL (versions 1.0, 1.1, 1.2, 1.3). The **current standard**.

> People still say "SSL" but they almost always mean **TLS**. When you buy an "SSL certificate", it's actually used with TLS.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        SSL/TLS TIMELINE                                  │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  SSL 1.0 (never released, too insecure)                                │
│  SSL 2.0 (1995) ── DEPRECATED, full of vulnerabilities                 │
│  SSL 3.0 (1996) ── DEPRECATED (POODLE attack)                          │
│  TLS 1.0 (1999) ── DEPRECATED                                          │
│  TLS 1.1 (2006) ── DEPRECATED                                          │
│  TLS 1.2 (2008) ── WIDELY USED (still secure)                          │
│  TLS 1.3 (2018) ── CURRENT STANDARD (fastest, most secure)             │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### How TLS Handshake Works (TLS 1.2)

```
Client (Browser)                          Server (website)
    │                                        │
    │── 1. ClientHello ─────────────────────>│
    │   - TLS version (1.2)                  │
    │   - Supported cipher suites            │
    │   - Random number (client_random)      │
    │                                        │
    │<── 2. ServerHello ─────────────────────│
    │   - Chosen cipher suite                │
    │   - Random number (server_random)      │
    │   - SSL Certificate (with public key)  │
    │                                        │
    │   3. Client verifies certificate       │
    │   - Is it issued by trusted CA?        │
    │   - Is it expired?                     │
    │   - Does domain match?                 │
    │                                        │
    │── 4. Key Exchange ────────────────────>│
    │   - Client generates pre-master secret │
    │   - Encrypts with server's public key  │
    │   - Sends encrypted pre-master secret  │
    │                                        │
    │   5. Both sides derive session key     │
    │   - session_key = f(client_random,     │
    │     server_random, pre-master_secret)  │
    │                                        │
    │── 6. Client: "Change to encrypted" ──>│
    │<── 7. Server: "Change to encrypted" ──│
    │                                        │
    │══════ ALL DATA NOW ENCRYPTED ═════════│
    │══ GET /page (encrypted) ═════════════>│
    │<══ 200 OK (encrypted) ═══════════════│
```

### TLS 1.2 vs TLS 1.3

| Aspect | TLS 1.2 | TLS 1.3 |
|--------|---------|---------|
| **Handshake** | 2 round-trips | 1 round-trip (faster) |
| **0-RTT** | Not supported | Supported (resumption) |
| **Cipher suites** | Many (some weak) | Only strong ones |
| **Key exchange** | RSA or Diffie-Hellman | Diffie-Hellman only (forward secrecy) |
| **Speed** | Slower setup | ~100ms faster setup |
| **Security** | Good | Better (removed weak algorithms) |

### Key Concepts

**Symmetric vs Asymmetric Encryption:**

```
Asymmetric (Public/Private key) — used during handshake only
┌─────────┐                           ┌─────────┐
│ Public  │  Anyone can encrypt with  │ Private │  Only owner can decrypt
│  Key    │  this key                 │  Key    │  with this key
└─────────┘                           └─────────┘
Slow but secure for key exchange.

Symmetric (Session key) — used for actual data transfer
┌──────────────┐
│ Session Key  │  Same key encrypts AND decrypts
│ (shared)     │  Both client and server have it
└──────────────┘
Fast — used for all data after handshake.
```

**SSL/TLS Certificate Contents:**

| Field | Purpose |
|-------|---------|
| Domain name | Who the certificate is for (e.g., google.com) |
| Public key | Server's public key for encryption |
| Issuer | Certificate Authority that issued it (e.g., Let's Encrypt) |
| Validity period | Start and expiry dates |
| Signature | CA's digital signature (proves authenticity) |
| Serial number | Unique identifier |

**Certificate Authority (CA) Chain of Trust:**

```
Root CA (trusted by browsers/OS)
  │
  └── Intermediate CA
        │
        └── Your Website's Certificate (google.com)

Browser checks:
1. Is the certificate valid (not expired)?
2. Does the domain match?
3. Is the issuer (CA) trusted by my browser/OS?
4. Is the CA's signature valid?
→ All yes? Show 🔒 padlock in address bar.
```

---

## 10. Interview Questions

**Q1: Explain the OSI model and its 7 layers.**

**Answer:**

The OSI model has 7 layers (top to bottom): **Application** (HTTP, FTP — user services), **Presentation** (encryption, data format — SSL/TLS), **Session** (manages connections), **Transport** (TCP/UDP — reliable delivery via ports), **Network** (IP addressing, routing), **Data Link** (MAC addressing, frames, switches), **Physical** (cables, signals, bits).

Data flows top-down when sending (each layer adds a header) and bottom-up when receiving (each layer removes its header).

---

**Q2: What is the difference between TCP and UDP?**

**Answer:**

| TCP | UDP |
|-----|-----|
| Connection-oriented (3-way handshake) | Connectionless |
| Reliable (guaranteed delivery, retransmission) | Unreliable (no guarantee) |
| Ordered delivery | May arrive out of order |
| Slower (overhead) | Faster |
| Used for: HTTP, HTTPS, FTP, SMTP, SSH | Used for: DNS, Video streaming, Gaming, VoIP |

---

**Q3: What happens when you type a URL in the browser?**

**Answer:**

1. **DNS Resolution** — Browser resolves `www.google.com` → `142.250.190.46` (checks browser cache → OS cache → DNS resolver → Root → TLD → Authoritative server)
2. **TCP Connection** — 3-way handshake (SYN → SYN-ACK → ACK) with port 443
3. **TLS Handshake** — Exchange certificates, verify identity, establish encrypted session key
4. **HTTP Request** — Browser sends `GET /` request through encrypted tunnel
5. **Server Processing** — Server processes request, queries database if needed
6. **HTTP Response** — Server returns HTML/CSS/JS with status code (200 OK)
7. **Browser Rendering** — Parses HTML → builds DOM → applies CSS → executes JS → displays page

---

**Q4: What is the difference between HTTP and HTTPS?**

**Answer:**

| HTTP | HTTPS |
|------|-------|
| Port 80 | Port 443 |
| No encryption (plain text) | Encrypted (TLS/SSL) |
| No certificate needed | Requires SSL/TLS certificate |
| Data can be intercepted and read | Data is encrypted end-to-end |
| No server verification | Server identity verified via certificate |
| `http://` | `https://` with padlock icon |

HTTPS = HTTP + TLS. The TLS handshake establishes an encrypted channel before any HTTP data is exchanged.

---

**Q5: What is the difference between FTP and SFTP?**

**Answer:**

| FTP | SFTP |
|-----|------|
| Ports 20, 21 | Port 22 |
| No encryption | Full encryption (over SSH) |
| Password sent in plain text | Password encrypted |
| Multiple connections (control + data) | Single connection |
| Faster (no encryption overhead) | Slightly slower but secure |

---

**Q6: Explain IPv4 vs IPv6.**

**Answer:**

| IPv4 | IPv6 |
|------|------|
| 32-bit address | 128-bit address |
| ~4.3 billion addresses | ~340 undecillion addresses |
| Decimal notation: `192.168.1.1` | Hex notation: `2001:db8::1` |
| Requires NAT (address shortage) | No NAT needed |
| IPSec optional | IPSec built-in |
| Variable header (20-60 bytes) | Fixed header (40 bytes) |
| Supports broadcast | No broadcast (uses multicast) |

---

**Q7: What is DNS and how does it work?**

**Answer:**

DNS (Domain Name System) translates domain names to IP addresses. When you type `google.com`, the resolution follows: Browser cache → OS cache → Recursive Resolver (ISP) → Root Server (directs to `.com`) → TLD Server (directs to `google.com`) → Authoritative Server (returns the actual IP `142.250.190.46`).

Common record types: **A** (domain → IPv4), **AAAA** (domain → IPv6), **CNAME** (alias), **MX** (mail server), **NS** (nameserver).

---

**Q8: What is the difference between IP address and MAC address?**

**Answer:**

| IP Address | MAC Address |
|-----------|-------------|
| Layer 3 (Network) | Layer 2 (Data Link) |
| Logical address (can change) | Physical address (permanent) |
| Assigned by DHCP/admin | Assigned by manufacturer |
| Routable across internet | Only works within local LAN |
| `192.168.1.100` | `A4:83:E7:2F:5B:01` |
| Like a mailing address | Like a fingerprint |

ARP (Address Resolution Protocol) maps IP → MAC within a local network.

---

**Q9: Explain TLS/SSL. How does HTTPS use it?**

**Answer:**

TLS (Transport Layer Security) is a cryptographic protocol that provides encryption, authentication, and data integrity. SSL is the deprecated predecessor.

**TLS Handshake flow:**
1. Client sends supported TLS versions and cipher suites
2. Server responds with chosen cipher suite and its SSL certificate (containing public key)
3. Client verifies certificate with Certificate Authority
4. Client generates a pre-master secret, encrypts it with server's public key
5. Both derive a shared session key
6. All further communication is encrypted with the symmetric session key

TLS uses **asymmetric encryption** (slow, public/private keys) only for the handshake, then switches to **symmetric encryption** (fast, shared session key) for data transfer.

---

**Q10: What is the difference between LAN, WAN, MAN, and PAN?**

**Answer:**

| Type | Range | Example |
|------|-------|---------|
| PAN (Personal) | ~10 meters | Phone + Bluetooth earbuds |
| LAN (Local) | Building / Office | Home Wi-Fi, Office Ethernet |
| MAN (Metropolitan) | City (~50km) | City-wide ISP fiber network |
| WAN (Wide) | Country / Globe | The Internet |

LAN = high speed, low cost, private. WAN = lower speed, high cost, public/ISP-managed.

---

**End of Networking Reference Guide**
