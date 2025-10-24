package com.github.rei0925.command

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.declaredMemberFunctions

class CommandManager() {
    private val commands = mutableMapOf<String, Pair<Any, (List<String>) -> Unit>>()
    private val defaultCommands = mutableMapOf<String, Pair<Any, (List<String>) -> Unit>>()
    private val lock = Any()

    fun registerCommand(instance: Any) {
        val aliasAnnotation = instance::class.findAnnotation<CommandAlias>() ?: return
        val alias = aliasAnnotation.value

        instance::class.declaredMemberFunctions.forEach { method ->
            val subAnnotation = method.findAnnotation<Subcommand>()
            val defaultAnnotation = method.findAnnotation<Default>()
            val key = if (subAnnotation != null && subAnnotation.value.isNotEmpty())
                "$alias ${subAnnotation.value}"
            else alias

            if (defaultAnnotation != null) {
                defaultCommands[alias] = instance to { args -> method.call(instance, args) }
            } else {
                commands[key] = instance to { args -> method.call(instance, args) }
            }
        }
    }

    fun runCommand(input: String): Boolean {
        val tokens = input.split(" ")
        val cmdKey = if (tokens.size > 1) "${tokens[0]} ${tokens[1]}" else tokens[0]
        val command = commands[cmdKey] ?: commands[tokens[0]]
        if (command != null) {
            val instance = command.first
            val method = instance::class.declaredMemberFunctions.find {
                val sub = it.findAnnotation<Subcommand>()
                val key = if (sub != null && sub.value.isNotEmpty())
                    "${instance::class.findAnnotation<CommandAlias>()?.value} ${sub.value}"
                else instance::class.findAnnotation<CommandAlias>()?.value
                key == cmdKey
            }

            // コマンド実行
            command.second.invoke(tokens.drop(1))

            // EndCommand が付いていたら true（＝終了シグナル）
            if (instance::class.findAnnotation<EndCommand>() != null) {
                return true
            }
        } else {
            val alias = tokens[0]
            val defaultCommand = defaultCommands[alias]
            if (defaultCommand != null) {
                defaultCommand.second.invoke(tokens.drop(1))

                if (defaultCommand.first::class.findAnnotation<EndCommand>() != null) {
                    return true
                }
            } else {
                println("不明なコマンド: $input")
            }
        }
        return false
    }

    fun startInteractive() {
        val reader = BufferedReader(InputStreamReader(System.`in`))

        while (true) {
            try {
                synchronized(lock) {
                    print("> ")
                    System.out.flush()
                }

                val input = reader.readLine()?.trim() ?: continue
                if (input.isEmpty()) continue

                // 終了コマンドが実行されたら break
                val shouldExit = runCommand(input)
                if (shouldExit) {
                    println("CLIを終了します。")
                    break
                }

            } catch (e: Exception) {
                println("CLI input error: ${e.message}")
            }
        }
    }
}