package сlient

import commands.Information
import kotlinx.serialization.Serializable

@Serializable
class Help(override val name: String="help"):Client {
    override fun check(command: String): Information {
        if (command==name)
            return Information("Ok",0)
        return Information("У этой команды нет аргументов. Возможно вы имели в виду \"help\"",1)
    }
}