package сlient

import Comanda
import commands.Information
import Console
import kotlinx.serialization.Serializable
import product.Product
@Serializable
class Update():Client {
    override val name: String="update"
    private var commanda=""
    override fun check(command: String): Information {
        val console=Console()
        if (console.checkId(console.getArgs(command))){
            commanda=command
            return Information("Ok",0)
        }
        return Information("Id должно быть натуральным числом",1)
    }

    override fun create(): Comanda {
        val console=Console()
        val o=Comanda(name)
        o.initL(console.getArgs(commanda).toLong())
        o.initP(console.getElement())
        return o
    }
}