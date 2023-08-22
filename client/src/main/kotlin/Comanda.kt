import kotlinx.serialization.Serializable
import product.*

@Serializable
class Comanda(private val name:String) {
    //var status=0
    //var login=""
    //var password=""
    var argumentL:Long=0
    var argumentS:String=""
    var argumentP: Product=Product("1",1, Coordinates(1,1), UnitofMeasure.PCS, Organization("1",1,1,"1", OrganisationType.TRUST),"sdfghjmnbvcdfgdeececv")
    var argumentM:Organization=Organization("1",1,1,"1", OrganisationType.TRUST)
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
    /*fun initUser(status:String,login:String,password:String){
        if (status=="a")
            this.status=0
        else {
            this.status=1
        }
        this.login=login
        this.password=password
    }*/
}