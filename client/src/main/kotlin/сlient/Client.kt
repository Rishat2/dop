package сlient

import Comanda
import commands.Information

interface Client{
    val name:String
    fun check(command:String):Information
    fun create():Comanda{
        return Comanda(name)
    }
}