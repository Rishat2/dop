import kotlinx.serialization.Serializable
import product.*

@Serializable
class Comanda(private var name:String) {
    var argumentL:Long=0
    var argumentS:String=""
    var argumentP: Product=
        Product("1",1, Coordinates(1,1), UnitOfMeasure.PCS, Organization("1",1,1,"1", OrganisationType.TRUST),"sdfghjmnbvcdfgdeececv")

    var argumentM: Organization = Organization("1",1,1,"1", OrganisationType.TRUST)

    fun getName():String{
        return name
    }
    fun setName(n:String){
        name=n
    }
    fun initS(s:String){
        argumentS=s
    }
    fun initP(p:Product){
        argumentP=p
    }
    fun initM(m:Organization){
        argumentM=m
    }
    fun initL(l:Long){
        argumentL=l
    }
}