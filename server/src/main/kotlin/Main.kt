import commands.CollectionManager
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import product.Product
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.util.regex.Pattern

fun main(args: Array<String>) {
    println("Сервер запущен")
    //var arr=ByteArray(1000)
    //val arrMut:MutableList<ByteArray> = mutableListOf()
    //val arrT=ByteArray(1000)
    val port=8000
    //val maxSize=1500
    //val buffer=ByteArray(maxSize)
    val json = JsonFile()
    //val comWitnElem= arrayOf("add","update","remove_lower")
    //json.EncodeAndWrite(deque,"JSON")
    //var o=Comanda("")
    val deque1: MutableList<Product> = json.decodeAndRead("JSON")
    val socket = DatagramSocket(port)
    val cm=CollectionManager(deque1)
    val obje=InitMap(cm)
    val commands=obje.initialMap()
    /*try {
        val arr= ByteArray(1000)
        val packetFromClient= DatagramPacket(arr,arr.size)
        socket.receive(packetFromClient)
        val a=packetFromClient.data.toString(Charsets.UTF_8)
        var t=""
        for(i in 0..997){
            if (a[i]==a[i+1] && a[i]==a[i+2] && !(a[i].isDigit()) && !(a[i].isLetter()))
                break
            t+=a[i]
        }
        val s=t.split(Pattern.compile("\\s"),limit=3)
        println(s[1])
        val response: ByteArray = t.toByteArray()
        val packetToClient= DatagramPacket(response,response.size,packetFromClient.address,packetFromClient.port)
        socket.send(packetToClient)
    }catch (e:IOException){
        println("На сервере что-то пошло не так")
    }*/
    var status=""
    var login=""
    var password=""
    while (true){
        try {
            val arr= ByteArray(1000)
            val packetFromClient= DatagramPacket(arr,arr.size)
            socket.receive(packetFromClient)
            val a=packetFromClient.data.toString(Charsets.UTF_8)
            var response=ByteArray(15000)
            val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/studs","s367957","OQrRTz0bYbfH69dG")
            println("ok conn")
            var t = ""
            for (i in 0..997) {
                if (a[i] == a[i + 1] && a[i] == a[i + 2] && !(a[i].isDigit()) && !(a[i].isLetter()))
                    break
                t += a[i]
            }
            if (!(a[0].isLetter())) {
                val o = Json.decodeFromString<Comanda>(t)
                cm.initComanda(o)
                val information = commands.get(o.getName())!!.execute()
                response = Json.encodeToString(information).toByteArray()
            }
            else {
                val t=a.split(Pattern.compile("\\s"),limit=3)
                status=t[0]
                login=t[1]
                password=t[2]
                response = "o".toByteArray()
                if (a[0].toString()=="r") {
                    try {
                        val prep = conn.prepareStatement("SELECT count(user) FROM USERS WHERE password=\'$login\';")
                        println(prep)
                        val res = prep.executeQuery()
                        println(res)
                        while (res.next()) {
                            val user = res.getInt(1)
                            if (user!=0)
                                response="1".toByteArray()
                            else {
                                val prep=conn.prepareStatement("INSERT INTO USERS VALUES(\'$login\',\'$password\'" +
                                        ");")
                            }
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    //println(res)
                }
                else {
                    response = "o".toByteArray()
                    try {
                        val prep = conn.prepareStatement("SELECT count(user) FROM USERS WHERE password=\'$login\';")
                        println(prep)
                        val res = prep.executeQuery()
                        println(res)
                        while (res.next()) {
                            val user = res.getInt(1)
                            if (user==0)
                                response="1".toByteArray()
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

            }
            if (status=="")
                response="1".toByteArray()
            val packetToClient =
                DatagramPacket(response, response.size, packetFromClient.address, packetFromClient.port)

            socket.send(packetToClient)

        }catch (e: IOException){
            println("на сервере что-то пошло не так")
        }catch (e:Exception){
            println("Проблема в БД")
        }
    }
}