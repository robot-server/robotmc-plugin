package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.config.DataSourceConfig
import com.sysbot32.robotmc.plugin.core.coordinates.Coordinate
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class CoordinatePlugin : JavaPlugin() {
    override fun onEnable() {
        Database.connect(DataSourceConfig().dataSource())
        transaction {
            SchemaUtils.create(CoordinateTable)
        }
        server.commandMap.register(name.lowercase(), CoordinateCommand())
        server.pluginManager.registerEvents(CoordinateListener(), this)
        server.servicesManager.register(Coordinate::class.java, CoordinateImpl(), this, ServicePriority.Normal)
    }
}
