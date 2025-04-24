package com.sysbot32.robotmc.plugin.core.config

import com.zaxxer.hikari.HikariDataSource
import org.bukkit.configuration.Configuration
import javax.sql.DataSource

class DataSourceConfig(
    private val config: Configuration,
) {
    fun dataSource(): DataSource {
        return HikariDataSource().apply {
            driverClassName = config.getString("datasource.driverClassName", "com.mysql.cj.jdbc.Driver")
            jdbcUrl = config.getString("datasource.jdbcUrl")
            username = config.getString("datasource.username")
            password = config.getString("datasource.password")
        }
    }
}
