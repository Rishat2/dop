package сlient

import Comanda
import commands.Information
import Console
import kotlinx.serialization.Serializable
import product.Product
@Serializable
class RemoveLower():Client {
    override val name: String="remove_lower"
    override fun check(command: String): Information {
        if (command==name){
            return Information("Ok",0)
        }
        return Information("У этой команды нет примитивных аргументов. Возможно вы имели в виду \"$name\"",1)
    }

    override fun create(): Comanda {
        val console=Console()
        val o=Comanda(name)
        o.initP(console.getElement())
        return o
    }
}