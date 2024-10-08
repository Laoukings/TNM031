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
The **Agent Sudo** challenge is designed to test your skills in various areas of cybersecurity, including cryptography, steganography, and web exploitation.

### Methodology
Our approach to solving the challenge involves:
- Analyzing the provided clues and hints
- Applying relevant tools and techniques
- Documenting each step for clarity

#### Introduction to Linux
If you never used Linux before it is important to understand the file structure of the operating system.  

##### Understanding and Traversing the Filesystem
The Linux filesystem is hierarchical (like Windows), starting from the root directory, denoted by `/`. Here are some key directories:

- `/bin`: Essential command binaries
- `/etc`: Configuration files
- `/home`: User home directories
- `/var`: Variable data like logs
- `/usr`: User utilities and applications
- `/root`: The home directory for the root user

The root user, also known as the superuser, has administrative privileges and can perform any task on the system.

the file system can be accessed through the file explorer similar to Windows and MacOS **OR** more commonly used the *terminal*. The commands to traverse and use the file system in the terminal is:
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


### Conclusion
Summarizing the key takeaways and lessons learned from the challenge.

---

*Happy Hacking!*
