package cn.afternode.gencore.database

import com.mysql.cj.jdbc.ConnectionImpl
import com.mysql.cj.jdbc.JdbcConnection
import org.bukkit.plugin.Plugin
import org.sqlite.SQLiteConnection
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class DataManager(val plugin: Plugin) {
    var connection: Connection? = null

    /**
     * Connect to H2 with local file
     */
    fun connectH2(name: String) {
        if (connection != null && !connection!!.isClosed) throw IllegalStateException("Connection already created and not closed")

        Class.forName("org.h2.Driver")
        connection = DriverManager.getConnection("jdbc:h2:./plugins/${plugin.name}/$name")
    }

    /**
     * Connect to MySQL remote database
     */
    fun connectMySql(config: RemoteDBConfig) {
        if (connection != null && !connection!!.isClosed) throw IllegalStateException("Connection already created and not closed")

        Class.forName("com.mysql.cj.jdbc.Driver")
        connection = DriverManager.getConnection("jdbc:mysql://${config.host}:${config.port}/${config.database}", config.user, config.pass)
    }

    /**
     * Connect to SQLite with local file
     */
    fun connectSqlite(name: String) {
        if (connection != null && !connection!!.isClosed) throw IllegalStateException("Connection already created and not closed")

        Class.forName("org.sqlite.JDBC")
        connection = DriverManager.getConnection("jdbc:sqlite:./plugins/${plugin.name}/$name")
    }

    fun end() {
        if (connection != null && !connection!!.isClosed) connection!!.close()
    }

    fun statement(): Statement = connection!!.createStatement()

    fun execute(vararg sql: String) {
        val stmt = statement()
        for (s in sql) stmt.execute(s)
        stmt.close()
    }

    fun asSqlite(): SQLiteConnection? {
        return if (connection is SQLiteConnection) connection as SQLiteConnection else null
    }

    fun asMySql(): ConnectionImpl? {
        return if (connection is ConnectionImpl) connection as ConnectionImpl else null
    }

    fun asH2(): JdbcConnection? {
        return if (connection is JdbcConnection) connection as JdbcConnection else null
    }
}