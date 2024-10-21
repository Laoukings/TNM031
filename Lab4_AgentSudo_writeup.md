# Agent Sudo Writeup

## Capture The Flag (CTF) Challenge

Welcome to the detailed and beginner-friendly writeup for the **Agent Sudo** CTF challenge. This document will guide you through the steps and methodologies used to solve the challenge.

![CTF Logo](https://assets.tryhackme.com/img/logo/tryhackme_logo_full.svg)

### Table of Contents

1. [Introduction](#introduction)
2. [Challenge Description](#challenge-description)
3. [Methodology](#methodology)
4. [Solution](#solution)
5. [Conclusion](#conclusion)

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

the file system can be accessed through the _file explorer_ similar to Windows and MacOS **OR** more commonly used the _terminal_. The commands to traverse and use the file system in the terminal is:

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

### Setting up the Linux Environment

There are two ways of doing the challenge. The first option is by using TryHackMe's provided virtual machine which is called AttackBox, which is free for 1 hour a day. The second option is to use your own install of Linux, such as Kali Linux, and use the VPN provided by TryHackMe to connect to the challenge.

##### Accessing the AttackBox on TryHackMe

To access the AttackBox on TryHackMe, navigate to the **Agent Sudo** room. Look for the "Start AttackBox" button, usually located on the left side of the interface. Click this button to initiate the AttackBox. The AttackBox is a virtual machine provided by TryHackMe for conducting your tasks. Once the AttackBox is running, you can interact with it directly through your browser, the tools required to complete the challenge are pre-installed.

##### Accessing the challenge with your own Linux Machine

To access the challenge with your own Linux machine, you need to connect to TryHackMe's network using a VPN. First, download the OpenVPN configuration file from the TryHackMe website. Then, use the `openvpn` command to connect: `sudo openvpn --config <path-to-config-file>`. Once connected, you can access the challenge environment as if you were using the AttackBox. The required tools might not be installed but are easily installed if needed.

#### Solution

### Task 1: **Connect to THM network**

Follow the steps in [setup](#Setting-up-the-Linux-Environment)

### Task 2: **Enumerate**

We are given the IP of the machine that we will attack in the THM network, it is usually `10.10.x.x`. It is recommended to document all your findings in a text file.

#### Question: How many open ports?

To find out how many ports are open we use Nmap.

<details>
<summary>What is Nmap?</summary>

**Nmap** (**N**etwork **Map**per) is a powerful open-source tool used for network discovery and security auditing. It is widely used in network security to discover hosts, services and what program version of the devices on the network, thus creating a "map" of the network. Nmap can be used to:

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
`bash
    nmap -sV <target-ip>
    `
This command will provide detailed information about the services running on the target machine.
</details>

In our case, we want to use the most basic port scanning and therefore use

```bash
nmap <target-ip>
```

Running this command gives us the following result:

![Nmap scan](image1.png)

We can see that there are **3 ports open**: FTP, SSH and HTTP.

#### How do you redirect yourself to a secret page?

When we scanned for open ports we noticed that there is a web server running on port 80. Opening the IP address in our web browser gives us:

![Web browser user agent](image2.png)

Agent R tells us to use our codename to access the website. We don't have a codename but we know how to impersonate Agent R with `user-agent`.
We see that there are 25 different agents but Agent R was not correct. there are 26 letters in the alphabet so we assume the agent names start at A. Therefore, We try again with Agent A but we get the same result as Agent R. We repeat until we find Agent C and we get:


<details>
  <summary>What is user-agent?</summary>

A **User-Agent** in a web browser is a string of text that the browser sends to a web server when making an HTTP request. It provides the server with essential information about the browser, the device, and the operating system being used by the client. This helps the server deliver a version of the website optimized for the specific browser or device.

## Structure of a User-Agent String

A typical user-agent string contains several components:

- **Browser name and version**: Identifies the web browser being used, such as Chrome, Firefox, or Safari.
- **Operating system**: Describes the platform, such as Windows, macOS, Linux, Android, or iOS.
- **Rendering engine**: Indicates the browser engine, like WebKit (Safari), Gecko (Firefox), or Blink (Chrome).
- **Device type**: Specifies the type of device being used, such as mobile, desktop, or tablet.
</details>

Therefore, the answer to the question "How do you redirect yourself to a secret page?" is **user-agent**.

#### What is the agent's name?

There are different ways we can change our `user-agent` to impersonate/spoof Agent R. We can use a browser extension, the terminal or change it manually. For this write-up, we will use the `curl` command in the Terminal.

<details>
<summary>What is curl?</summary>
    
`curl` (short for **c**lient **url**) is a command-line tool used to transfer data to or from a server, using various supported protocols like HTTP, HTTPS, FTP, and more. It is an effective tool that is often used for interacting with web servers, APIs, downloading files, and troubleshooting network issues.

The curl command:

```bash
curl <options> <target-ip>
```

The options needed:

1. **`-A <user-agent>`** Set a custom User-Agent string (e.g., `curl -A "Mozilla/5.0" https://youtube.com`).
2. **`-L`** Follow redirects (e.g., `curl -L https://youtube.com`).

</details>

We run the command with the user-agent `-A` as Agent **R** and redirects enabled `-L`:

```bash
curl -A "R" -L <target-ip>
```

![user-agent-res](image3.png)


Here we can see that our Agent is named: **Chris** which is our answer to the last question in Task 2.
We also note that the FTP password is weak which means that we could try to bruteforce this using hydra.

### Task 3: Hash cracking and brute-force

<details>
    <summary>What is Hydra?</summary>
Hydra is a powerful and versatile password-cracking tool used in hacking. It supports numerous protocols, including HTTP, FTP, SSH, and many more, making it a useful tool for brute force attacks. Hydra works by attempting to log in with various username and password combinations from a specified wordlist. Wordlists are essential tools in penetration testing and password cracking. One of the most popular wordlists is `rockyou.txt`, which contains millions of common passwords. This worldlist is often used with brute force attacks to guess the password on the attacked box. On the given AttackBox the wordlists can be accessed at `/usr/share/wordlists/rockyou.txt`. To use it with Hydra, you can specify the path in your command like so:

```bash
hydra -l <username> -P /usr/share/wordlists/rockyou.txt <target-ip> <protocol>
```

Replace `<username>`, `<target-ip>`, and `<protocol>` with the appropriate values for your attack.

</details>

#### Question: What is the FTP password?

By letting hydra run for som time it gives gives us the password: crystal which will be our answer to the first question of Task 3.


![hydra_crystal](image4.png)

Entering the ftp password crystal gives us:

![transfer_files](image5.png)

Now we would like to see the content of the FTP server. By entering the command ls in the current directory of the FTP server we list all files and directories and download them all. 
The content of the FTP server:

![downloading](image6.png)

We can see the listed files of the FTP server as:
To_agentJ.txt, 
cute-alien.jpg, 
cutie.png

Opening up the text file: To_agentJ.txt gives us the following information :

![To_agentJ_txt](image7.png)

Our next two question wants a Zip-file password which we do not have aswell as a steg password, this could mean that there is a hidden Zip-file in the files we downloaded. 
To extract data from binary files we could use the command `binwalk`. The text file To_agentJ above said that: "Your login password is somehow stored in the fake image". We start off using `binwalk`on cutie.png: 
<details>
<summary>What is Binwalk?</summary>

binwalk is a Linux command-line tool designed for analyzing and extracting data from binary files, particularly firmware images. It allows users to examine the contents of these binaries to identify file signatures, extract embedded files, and disassemble code.
The binwalk command:

```bash
binwalk <binwalk> <cutie.png>
```

</details>

![zipping](image8.png)

Listing the files in the extracted folder gives us: 

![to_agentR_txt](image9.png)

The Zip-file name is 8702.zip and it is encrypted. But we can get the password by using `zip2john`and then use `john`to crack the hash.
<details>
<summary>What does zip2john & john do?</summary>

zip2john is a utility that is part of the John the Ripper password cracking suite. It is used to convert ZIP archive files into a format that can be processed by John the Ripper. This tool extracts password hashes from ZIP files, allowing users to attempt to crack the passwords using John the Ripper.

```bash
zip2john <targeted-zipfile> > <zip.hash>
```

john, or John the Ripper, is a powerful password cracking tool that supports various hashing algorithms and formats. It is commonly used to recover lost passwords or to perform security audits by attempting to crack password hashes. John can use various attack modes, including dictionary attacks and brute-force attacks, to find the original passwords.

```bash
john <zip.hash>
```

</details>

![cutie_png_extracted](image10.png)

Extracting the zip with the use of 7-zip gives us the text file `To_agentR.txt`. The text file contains a word: `QXJIYTUx`

```bash
7z e <targeted-zipfile>
```

![password](image11.png)

#### Question: What is the Zip-file password?

`QXJIYTUx` is encoded. There are many ways of encoding a password. To find out which type of decoding method we need to use we could use [Cyberchef](https://gchq.github.io/CyberChef/) which suggests decoding by Base64. Decoding this by base64 gives us the following:

<details>
<summary>What is Base64</summary>
Base64 encoding is a method used to convert binary data into a text format using a specific set of 64 characters. This encoding is useful for transmitting data over mediums that may not handle binary data well, such as email or URLs. The main goal is to ensure that the data remains intact without modification during transport.
</details>

![base64_decode](image12.png)

This gives us our answer to our qustion: `area51`

#### Question: What is the Steg password?

Steghide is often used to hide information inside a jpg file. Testing this on cute-alien.jpg with the command steghide info will tell us if the jpg has something to hide: 

```bash
steghide <info> <targeted-imagefile>
```
<details>
<summary>What is steghide?</summary>

steghide is a command-line utility used for hiding data within various types of media files, such as images and audio files. It implements steganography, a technique that allows users to conceal information in plain sight, making it difficult to detect.
</details>


![steghide_cute_alien](image13.png)

With `steghide info` and entering the passphrase we get: embedded file "message.txt"

#### Question: Who is the other (agent in full name)?


![message_txt](image14.png)

Opening the contents of this textfile gives us:

![hi_james_txt](image15.png)

This gives us both the steg password `hackerrules` and the other agents full name `James` as answers to our questions above.

### Task 4: **Capture the flag**

What is the user flag?

We can here use the SSH information to get in to James machine and view the files :

![james](image16.png)

If we then use the command `cat` we can view the contents of `user_flag.txt` and get our answer to the question of **Task 4**.

![flag](image17.png)

### Task 5: **Privilage escalation**

![alien_autospy](image19.png)
![alienimage](image20.png)
![sudo-privilage](image21.png)
![mr.hacker](image22.png)



### Conclusion

Summarizing the key takeaways and lessons learned from the challenge.

---

_Happy Hacking!_
