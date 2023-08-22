package сlient

import Comanda
import commands.Information
import Console
import kotlinx.serialization.Serializable
import product.Product
@Serializable
class Add():Client {
    override val name: String="add"
    override fun check(command: String): Information {
        if (command==name){
            return Information("Ok",0)
        }
        return Information("У этой команды нет примитивных аргументов. Возможно вы имели в виду \"add\"",1)
    }

    override fun create(): Comanda {
        val console=Console()
        val o=Comanda(name)
        o.initP(console.getElement())
        return o
    }
}