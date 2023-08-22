package сlient

import Comanda
import commands.Information
import Console
import kotlinx.serialization.Serializable

@Serializable
class FilterLessThanPartNumber():Client {
    override val name: String="filter_less_than_part_number"
    private var commanda=""
    override fun check(command: String): Information {
        val console=Console()
        if(console.validPartNumber(console.getArgs(command))) {
            commanda=command
            return Information("Ok", 0)
        }
        return Information("В номере части должно не меньше 13 и не больше 58 символов",1)
    }

    override fun create(command: ArrayList<String>): Comanda {
        val console=Console()
        val o=Comanda(name)
        o.initS(command[0])
        return o
    }
}