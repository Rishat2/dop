package сlient

import Comanda
import commands.Information
import Console
import kotlinx.serialization.Serializable
import product.OrganisationType
import product.Organization
import product.UnitOfMeasure

@Serializable
class RemoveAllByManufacturer():Client {
    override val name: String="remove_all_by_manufacturer"

    override fun check(command: String): Information {
        if (command==name){
            return Information("Ok",0)
        }
        return Information("У этой команды нет примитивных аргументов. Возможно, вы имели в виду \"remove_all_by_manufacturer\"",1)
    }

    override fun create(command: ArrayList<String>): Comanda {
        val o=Comanda(name)
        o.initM(getOrganisation(command))
        return o
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