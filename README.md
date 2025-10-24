# KotlinCLI

**KotlinCLI** is a lightweight framework for building command-line interfaces (CLI) in Kotlin.  
It allows you to define commands easily with annotations, avoiding complex parsing or configurations.

---

## 🚀 Features

- ✅ **Simple syntax** — Define commands using annotations like `@CommandAlias`, `@Subcommand`, and `@Default`.
- ⚙️ **Customizable** — Extendable via `CommandManager` for advanced use cases.
- 🧩 **Modular design** — Register command classes and they are automatically recognized.
- 💬 **Consistent structure** — Each command extends `BaseCommand` for clean implementation.

---

## 🧠 Example Usage

### Command Definition

```kotlin
@CommandAlias("end")
class EndCommand : BaseCommand() {

    @Default
    fun execute(args: List<String>) {
        println("Executing shutdown process...")
    }

    @Subcommand("force")
    fun forceEnd(args: List<String>) {
        println("Forcing shutdown!")
    }
}
```

### Registering and Running Commands

```kotlin
fun main() {
    val manager = CommandManager()

    // Register command
    manager.registerCommand(EndCommand())

    // Execute examples
    manager.runCommand("end")         // => Executing shutdown process...
    manager.runCommand("end force")   // => Forcing shutdown!
}
```

---

## ⚡ BaseCommand

All command classes extend `BaseCommand` and implement the `execute()` method.

```kotlin
abstract class BaseCommand {
    abstract fun execute(args: List<String>)
}
```

This structure allows shared functionality such as `log()`, `help()`, or `context` handling to be easily added.

---

## 🧩 Use Cases

- CLI tools and development utilities  
- Admin consoles for bots or servers  
- Minecraft plugin command management  
- Lightweight debugging environments  

---

## 🛠️ Installation

**Gradle (Kotlin DSL)**

```kotlin
dependencies {
    implementation("com.github.rei0925:KotlinCLI:1.0.0")
}
```

**Maven**

```xml
<dependency>
    <groupId>com.github.rei0925</groupId>
    <artifactId>KotlinCLI</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!  
Feel free to open a pull request or submit an issue on GitHub.

---

## 📄 License

Commands is licensed under the [MIT License](https://tldrlegal.com/license/mit-license).  
See [LICENSE](LICENSE.txt) for details.
