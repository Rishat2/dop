package сlient

import Comanda
import commands.Information
import Console
import kotlinx.serialization.Serializable
import product.Organization
@Serializable
class RemoveAllByManufacturer():Client {
    override val name: String="remove_all_by_manufacturer"

    override fun check(command: String): Information {
        if (command==name){
            return Information("Ok",0)
        }
        return Information("У этой команды нет примитивных аргументов. Возможно, вы имели в виду \"remove_all_by_manufacturer\"",1)
    }

    override fun create(): Comanda {
        val console=Console()
        val o=Comanda(name)
        o.initM(console.getManufacturer())
        return o
    }
}