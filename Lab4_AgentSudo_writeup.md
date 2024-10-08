# Agent Sudo Writeup

## Capture The Flag (CTF) Challenge

Welcome to the detailed and beginner-friendly writeup for the **Agent Sudo** CTF challenge. This document will guide you through the steps and methodologies used to solve the challenge.

![CTF Logo](https://assets.tryhackme.com/img/logo/tryhackme_logo_full.svg)

### Table of Contents
1. [Introduction](#introduction)
2. [Challenge Description](#challenge-description)
3. [Methodology](#methodology)
4. [Conclusion](#conclusion)

### Introduction
This writeup will explore the **Agent Sudo** challenge, breaking down each step to uncover the solution. This guide is intended for complete beginners and will explain the approach and techniques used.

### Challenge Description
The **Agent Sudo** challenge is designed to test your skills in various areas of cybersecurity, including cryptography, steganography, and web exploitation. "You found a secret server located under the deep sea. Your task is to hack inside the server and reveal the truth."

**Disclaimer-Do we want this???**
This write-up is done for educational purpose. While understanding network vulnerabilities is crucial for improving security measures, 
it is imperative to emphasize that exploiting these vulnerabilities without permission is unethical and illegal. 
Ethical hacking, or penetration testing, is conducted with the consent of the system owner and focuses on enhancing security, 
protecting data, and preventing malicious attacks.

### Methodology
Our approach to solving the challenge involves:
- Analyzing the provided clues and hints
- Applying relevant tools and techniques
- Documenting each step for clarity

#### Introduction to Linux
If you never used Linux before it is important to understand the file structure of the operating system. 
Linux has a powerful networking stack, enabling it to function as a client, server, or router. Key features include:

- Networking Tools: Utilities like ipconfig, ip, and ping are used for network configuration and diagnostics.
- Protocols: Linux supports various network protocols, including TCP/IP, which is fundamental for internet communication.
- Firewalls and Security: Tools like iptables and ufw allow users to configure firewalls to protect the system from unauthorized access.  

##### Understanding the Filesystem
The Linux filesystem is hierarchical (like Windows), starting from the root directory, denoted by `/`. Here are some key directories:

- `/bin`: Essential command binaries
- `/etc`: Configuration files
- `/home`: User home directories
- `/var`: Variable data like logs
- `/usr`: User utilities and applications
- `/root`: The home directory for the root user

The root user, also known as the superuser, has administrative privileges and can perform any task on the system.

##### Traversing the Filesystem
the file system can be accessed through the *file explorer* similar to Windows and MacOS **OR** more commonly used the *terminal*. The commands to traverse and use the file system in the terminal is:
- `ls`: Lists the contents of a directory.
- `cd`: **C**hanges the current **d**irectory.
- `pwd`: **P**rints the current **w**orking **d**irectory.
- `mkdir`: **C**reates a new **d**irectory.
- `rmdir`: **R**e**m**oves an empty **d**irectory.
- `rm -r`: **R**e**m**oves a directory and its contents **r**ecursively.
- `cp`: **C**o**p**ies files or directories.
- `mv`: **M**o**v**es or renames files or directories.
- `find`: Searches for files and directories.

`cd` and `ls` are the most commonly used commands. `cd` 

#### Setting up Linux the Enviroment (#setup)
There are two ways of doing the challange. The first option is by using TryHackMe's provided virtual machine that is called AttackBox, which is free for 1 hour a day. The second option is to use your own install of Linux, such as Kali Linux, and use the VPN provided by TryHackMe to connect to the challage.

##### Accessing the AttackBox on TryHackMe
To access the AttackBox on TryHackMe, navigate to the **Agent Sudo** room. Look for the "Start AttackBox" button, usually located on the left side of the interface. Click this button to initiate the AttackBox. The AttackBox is a virtual machine provided by TryHackMe for conducting your tasks. Once the AttackBox is running, you can interact with it directly through your browser, the tools required to complete the challage are pre-installed.

##### Accessing the Challage with your own Linux Machine
To access the challenge with your own Linux machine, you need to connect to TryHackMe's network using a VPN. First, download the OpenVPN configuration file from the TryHackMe website. Then, use the `openvpn` command to connect: `sudo openvpn --config <path-to-config-file>`. Once connected, you can access the challenge environment as if you were using the AttackBox. The required tools might possibly not be installed but are easily installed if needed. 

#### Solution

##### Task 1

Task 1 requires the user to connect to the THM network using the steps in [setup]


1. First, we are given the IP of the machine that we will attack in the THM network.  

**Nmap** (**N**etwork **Map**per) is a powerful open-source tool used for network discovery and security auditing. It is widely used in network security to discover hosts, services and what program version of the devices on the network, thus creating a "map" (often called fingerprint) of the network. Nmap can be used to:

- Discover live hosts on a network.
- Identify open ports on a target host.
- Detect the operating system and services running on a host.
- Perform vulnerability scanning.

To use Nmap in the terminal, you can follow these basic steps:

1. **Basic Host Discovery**: To find live hosts on a network, you can use the following command:
    ```bash
    nmap -sn <target-ip-range>
    ```
    Replace `<target-ip-range>` with the IP range you want to scan, e.g., `10.10.10.10`.

2. **Port Scanning**: To scan for open ports on a specific host, use:
    ```bash
    nmap <target-ip>
    ```
    Replace `<target-ip>` with the IP address of the target machine.

3. **Service and Version Detection**: To detect services and their versions running on open ports, use:
    ```bash
    nmap -sV <target-ip>
    ```
    This command will provide detailed information about the services running on the target machine.



Hydra is a powerful and versatile password-cracking tool used in hacking. It supports numerous protocols, including HTTP, FTP, SSH, and many more, making it a usefull tool for brute force attacks. Hydra works by attempting to log in with various username and password combinations from a specified wordlist. Wordlists are essential tools in penetration testing and password cracking. One of the most popular wordlists is `rockyou.txt`, which contains millions of common passwords. This worldlist is often used with brute force attacks to guess the password on the attacked box. On the given AttackBox the wordlists can be accessed at `/usr/share/wordlists/rockyou.txt`. To use it with Hydra, you can specify the path in your command like so:

```bash
hydra -l <username> -P /usr/share/wordlists/rockyou.txt <target-ip> <protocol>
```

Replace `<username>`, `<target-ip>`, and `<protocol>` with the appropriate values for your attack.

2. With the ip-adress of the machine given we can scan the network for additional information with Nmap. 

Nmap is an open source Linux command network scanning tool used for network exploration, host discovery, and security auditing.

By letting nmap run by entering: 
    ```bash
    nmap -sC -sV <ip-adress>
    ```
in the terminal window we get the following output:

//Resultimages

The open ports are: 
- 21/tcp open ftp
- 22/tcp open ssh
- 80/tcp open http

The CTF Challenge consists of three topics with questions regarding each topic. The topics are:
-Enumarate
-Hash-cracking and brute force
-Capture the flag


**Enumarate**

How many open ports?

How you redirect yourself to a secret page?

What is the agent name?

**Hash-cracking and brute force**

What is the FTP password?

What is the Zip-file password?

What is the Steg password?

Who is the other (agent in full name)?

What is the SSH Password?

**Capture the user flag**

What is the user flag?


### Conclusion
Summarizing the key takeaways and lessons learned from the challenge.

---

*Happy Hacking!*
