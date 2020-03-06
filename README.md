# EasyAPI-Nukkit

> Make creating plugin easier

[简体中文](README_zh.md)

## How to use? 

### Modularize Plugin

- Support hot plug
- Modules can be removed and installed at any time, and help modularize plugins
- Reference practice：In anti-cheating，we need to modularize all of cheating check module, such as 'Speed' checking，we need to add MoveEvent listener in my plugin，add record a lot of params，and it also includes custom config. If you don't use EasyAPI to be modularize, all of these will be complicated. And when some server no longer used some of check pattern. If you don't use EasyAPI to be modularize, it is very difficult to be meticulous and modularize.

### Directly use API

- It convenient to debug, test or some simple plugin, that can reduce some unnecessary progress

## Features

### EasyAPI !

### Async Event Provider

- Kotlin Coroutine

	- Use Kotlin Coroutine to complete the high efficiency and laconic async event provider.

- AsyncTask

	- Use Nukkit AsyncTask distribute each event trigger as a task，Nukkit don't need any return. So, just use, and enjoy the high efficiency

### Listener Pipeline

- The event processing is modularized into the listening pipeline of each step, and the processed data of each pipeline can be passed to the next-level listening pipeline, and the processing is modular, supports hot plugging, and supports async (also sync).

### Integrated Interface & Free Modular Enable

- Permission Groups

- Command

- Config

- Rsweapon

- Economy

- Database - DbLibX
	- Auto async

- GUI

- Bottom Tip Management

- Chat Format Management

- Top BossBar Management

- Right Scoreboard Management

- More····
    - Support any plugin to be EasyAPI interface module and integrated development

### Integrated Module & Free Modular Enable

- Bottom Tip Management
- Chat Format Management
- Top BossBar Management
- Right Scoreboard Management
- More···
	- Support any plugin to be EasyAPI module and integrated development