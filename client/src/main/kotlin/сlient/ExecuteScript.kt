package сlient

import Comanda
import commands.Information
import Console
import kotlinx.serialization.Serializable

@Serializable
class ExecuteScript():Client {
    override val name: String="execute_script"
    private var commanda=""
    override fun check(command: String): Information {
        val console=Console()
        if (console.checkExecuteScript(console.getCommand(command))) {
            commanda=command
            return Information("Ok", 0)
        }
        return Information("Такого файла не существует, повторите ввод",1)
    }

    override fun create(): Comanda {
        val console=Console()
        val o=Comanda(name)
        o.initS(console.getArgs(commanda))
        return o
    }
}