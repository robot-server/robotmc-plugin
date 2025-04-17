package com.sysbot32.robotmc.plugin.core.players

import org.jetbrains.exposed.sql.Table

object RobotmcPlayer : Table("robotmc_player") {
    val uuid = uuid("uuid")
    val username = varchar("username", 16)
    override val primaryKey = PrimaryKey(uuid)
}
