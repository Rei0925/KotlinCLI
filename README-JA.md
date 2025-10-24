# KotlinCLI

**KotlinCLI** は、Kotlinでコマンドラインインターフェース（CLI）を構築するための軽量フレームワークです。  
アノテーションを使って簡単にコマンドを定義でき、複雑な解析や設定を避けられます。

---

## 🚀 特徴

- ✅ **シンプルな構文** — `@CommandAlias`、`@Subcommand`、`@Default` といったアノテーションでコマンドを定義可能。
- ⚙️ **カスタマイズ可能** — 高度なユースケースには `CommandManager` を拡張して対応可能。
- 🧩 **モジュール設計** — コマンドクラスを登録すると自動的に認識されます。
- 💬 **一貫した構造** — 各コマンドは `BaseCommand` を継承し、クリーンな実装が可能。

---

## 🧠 使用例

### コマンド定義

```kotlin
@CommandAlias("end")
class EndCommand : BaseCommand() {

    @Default
    fun execute(args: List<String>) {
        println("実行中: シャットダウン処理...")
    }

    @Subcommand("force")
    fun forceEnd(args: List<String>) {
        println("強制シャットダウン中！")
    }
}
```

### コマンドの登録と実行

```kotlin
fun main() {
    val manager = CommandManager()

    // コマンド登録
    manager.registerCommand(EndCommand())

    // 実行例
    manager.runCommand("end")         // => 実行中: シャットダウン処理...
    manager.runCommand("end force")   // => 強制シャットダウン中！
}
```

### インタラクティブモード

`startInteractive()` メソッドは、ユーザー入力を継続的に受け付け、コマンドの実行を自動で処理するCLIループを開始します。  
手動で入力管理を行わずにインタラクティブなコマンドラインアプリケーションを作成するのに便利です。

```kotlin
fun main() {
    val manager = CommandManager()
    manager.registerCommand(EndCommand())

    // インタラクティブCLIループ開始
    manager.startInteractive()
}
```

---

## ⚡ BaseCommand

すべてのコマンドクラスは `BaseCommand` を継承し、`execute()` メソッドを実装します。

```kotlin
abstract class BaseCommand {
    abstract fun execute(args: List<String>)
}
```

この構造により、`log()`、`help()`、`context` の処理など、共通機能を簡単に追加できます。

---

## 🧩 ユースケース

- CLIツールや開発ユーティリティ
- ボットやサーバーの管理コンソール
- Minecraftプラグインのコマンド管理
- 軽量なデバッグ環境

---

## 🛠️ インストール

**Gradle (Kotlin DSL)**

```kotlin
dependencies {
    implementation("com.github.Rei0925:KotlinCLI:v1.0.1")
}
```

**Maven**

```xml
<dependency>
    <groupId>com.github.Rei0925</groupId>
    <artifactId>KotlinCLI</artifactId>
    <version>v1.0.1</version>
</dependency>
```

---

## 🤝 貢献について

貢献、問題報告、機能リクエストは大歓迎です！  
GitHubでプルリクエストを送るか、Issueを提出してください。

---

## 📄 ライセンス

Commandsは[MITライセンス](https://tldrlegal.com/license/mit-license)のもとでライセンスされています。  
詳細は[LICENSE](LICENSE.txt)をご覧ください。
