## Main
Scripts run and working on Linux Ubuntu 18.04

## Contents
### `12.2020`<br/>
└─» [Svn_To_Git_Transformation](#Svn_To_Git_Transformation) `Version Control`<br/>
### `10.2020`<br/>
└─» [Folder_Search](#Folder_Search) `Basic`<br/>
### `06.2020`<br/>
├─» [Ping_Pong](#Ping_Pong) `Game`<br/>
├─» [Processes](#Processes) `Basic`<br/>
├─» [System_Stats](#System_Stats) `Basic`<br/>
├─» [Site_Checker](#Site_Checker) `Basic`<br/>
├─» [Image_to_ANSI](#Image_to_ANSI) `Basic`<br/>
└─» [Lowercase_Files_Names](#Lowercase_Files_Names) `Basic`<br/>
### `04.2020`<br/>
└─» [Check_IPs](#Check_IPs) `Natwork`<br/>

## Topics
### Check_IPS
More info in [folder](Check_IPs).

### Ping_Pong
A Ping-Pong game for console. This is pretty self-explanatory.
![](imgs/ping-pong.png)

### Folder_Search
Script design to collect data of words collected in directory subtree. With four different options, allows to (for each file) count all occurences in all files in folder, count amount of files in which occures and list all lines in which appears, along with filename and line number. Additionally allows aist all lines with word duplications in them, from all files in directory subtree.

### Image_to_ANSI
Script to download a photo of kitten from [API](https://api.thecatapi.com/v1/images/search) and display it in console using img2txt. Additionally, it displays a random Chuck Norris quote downloaded from [API](http://api.icndb.com/jokes/random).

### Lowercase_Files_Names
Script to change names of all files in given folder to lowercase.

### Processes
Script showing basic data about current working processes, such as process id (PID), parent process id (PPID), name, state and count.

### Site_Checker
Script checking site `$1` for changes every `$2` seconds and displaying message in console when change found.

### Svn_To_Git_Transformation
Script taking as arguments 2 revisions numbers (from, to) and url to svn repository. Creates a local git repository, with each commit copies as is from svn (copies commit message and file changes).

### System_Stats
Sript showing basic data about PC, such as current download speed, how long is system up, on what percentage battery is and average system usage over 1-5-15 min.
