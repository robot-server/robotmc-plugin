package com.sysbot32.robotmc.plugin.coordinate

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object CoordinateTable : Table("robotmc_coordinate") {
    val id = long("id").autoIncrement()
    val uuid = uuid("uuid")
        .index()
    val name = varchar("name", 255)
    val world = varchar("world", 255)
    val environment = varchar("environment", 255)
    val x = double("x")
    val y = double("y")
    val z = double("z")
    val createdAt = datetime("created_at")
        .defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}
