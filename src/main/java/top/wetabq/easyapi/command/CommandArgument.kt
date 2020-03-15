package top.wetabq.easyapi.command

import cn.nukkit.command.data.CommandParamType

data class CommandArgument (
    val argName: String,
    val argType: CommandParamType,
    val optional: Boolean
)