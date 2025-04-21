package com.sysbot32.robotmc.plugin.core.config

import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

class DataSourceConfig(
    private val dataClassName: String = "com.mysql.cj.jdbc.Driver",
    private val jdbcUrl: String,
    private val username: String? = null,
    private val password: String? = null,
) {
    fun dataSource(): DataSource {
        return HikariDataSource().apply {
            driverClassName = this.driverClassName
            jdbcUrl = this.jdbcUrl
            username = this.username
            password = this.password
        }
    }
}
