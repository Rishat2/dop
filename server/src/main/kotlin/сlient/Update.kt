package сlient

import Comanda
import commands.Information
import Console
import kotlinx.serialization.Serializable
import product.*

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

    override fun create(command: ArrayList<String>): Comanda {
        val console=Console()
        val o=Comanda(name)
        o.initP(getElement(command))
        o.initL(console.getArgs(commanda).toLong())
        return o
    }
    fun getElement(elem:ArrayList<String>):Product{
        val product=Product(elem[0],elem[4].toLong(), Coordinates(elem[1].toInt(),elem[2].toLong()),getUnitOfMeasure(elem[5]),
            Organization(elem[6],elem[7].toLong(),elem[8].toLong(),elem[10],getOrganizationType(elem[9])),elem[3])
        return product
    }
    fun getOrganisation(elem:ArrayList<String>): Organization {
        val org= Organization(elem[0],elem[1].toLong(),elem[2].toLong(),elem[4],getOrganizationType(elem[3]))
        return org
    }
    fun getUnitOfMeasure(unitOfMeasure:String): UnitOfMeasure {
        return when (unitOfMeasure){
            "1" -> UnitOfMeasure.PCS
            "2" -> UnitOfMeasure.LITERS
            else -> UnitOfMeasure.MILLIGRAMS
        }
    }
    fun getOrganizationType(organizationType:String): OrganisationType {
        return when (organizationType){
            "1" -> OrganisationType.COMMERCIAL
            "2" ->  OrganisationType.TRUST
            else ->  OrganisationType.PRIVATE_LIMITED_COMPANY
        }
    }
}