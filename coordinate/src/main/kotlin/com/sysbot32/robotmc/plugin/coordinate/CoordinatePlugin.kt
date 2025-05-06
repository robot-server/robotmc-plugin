package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.config.DataSourceConfig
import com.sysbot32.robotmc.plugin.core.services.Coordinate
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class CoordinatePlugin : JavaPlugin() {
    override fun onEnable() {
        config.options().copyDefaults(true)
        saveConfig()
        Database.connect(DataSourceConfig(config).dataSource())
        transaction {
            SchemaUtils.create(CoordinateTable)
        }
        server.commandMap.register(name.lowercase(), CoordinateCommand())
        server.commandMap.register(name.lowercase(), CoordinateListCommand())
        server.commandMap.register(name.lowercase(), ToNetherCommand())
        server.commandMap.register(name.lowercase(), ToOverworldCommand())
        server.pluginManager.registerEvents(CoordinateListener(), this)
        server.servicesManager.register(Coordinate::class.java, CoordinateImpl(), this, ServicePriority.Normal)
    }
}
