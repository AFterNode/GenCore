package cn.afternode.gencore.database

data class RemoteDBConfig(
    val host: String = "127.0.0.1",
    val port: Int = 3306,
    val user: String,
    val pass: String,
    val database: String
)
