import commands.Information
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress as InetAddress1

fun initUser():String{
    var status=""
    while (true) {
        print("Если вы хотите авторизоваться введите \'a\', если вы хотите зарегистрироваться введите \'r\' >>> ")
        val input1 = readLine()
        if (input1 == "a" || input1 == "r") {
            status = input1
            break
        }
    }
    print("Введите имя пользователя >>> ")
    val login = readLine()
    print("Введите пароль >>> ")
    val password= readLine()
    return status+" "+login+" "+password
}
fun main() {
    println("Клиент запущен")
    val port=8000
    var arr: ByteArray
    val socket= DatagramSocket()
    val adress= InetAddress1.getByName("localhost")
    val console = Console()
    //val orga=Organization("1",1,1,"1", OrganisationType.TRUST)
    //val obje= Product("1",1, Coordinates(1,1), UnitofMeasure.PCS, orga,"sdfghjmnbvcdfgdeececv")
    val obj = InitMap()
    val client = obj.initialMapClient()
    var s=0
    while (true){
        if (s==0){
            while (true) {
                try {
                    arr = initUser().toByteArray()
                    val packetToServer = DatagramPacket(arr, arr.size, adress, port)
                    socket.soTimeout = 2000
                    socket.send(packetToServer)
                    val arrT = ByteArray(30000)
                    val packetFromServer = DatagramPacket(arrT, arrT.size)
                    socket.receive(packetFromServer)
                    val a = packetFromServer.data.toString(Charsets.UTF_8)
                    if (a[0].toString()=="o") {
                        s=1
                        break
                    }
                } catch (e: IOException) {
                    println("Сервер временно недоступен")
                }
            }
        }
        try{
            print(">>> ")
            val input= readLine()
            if (!(console.check(input!!))) {
                println("Нет такой команды, чтобы узнать какие команды есть введите help")
                continue
            }
            val information = client.get(console.getCommand(input))!!.check(input)
            if (information.getStatus()==-1)
                break
            if (information.getStatus() == 1) {
                println(information.getMessage())
                continue
            }
            val o=client.get(console.getCommand(input))!!.create()
            //o.initUser(status,login!!,password!!)
            arr= Json.encodeToString(o).toByteArray()
            val packetToServer = DatagramPacket(arr, arr.size, adress, port)
            socket.soTimeout=2000
            socket.send(packetToServer)
            val arrT=ByteArray(30000)
            val packetFromServer = DatagramPacket(arrT, arrT.size)
            socket.receive(packetFromServer)
            val a = packetFromServer.data.toString(Charsets.UTF_8)
            if (a[0].toString()=="1"){
                s=1
                continue
            }
            /*if (a[0].toString()=="1"){
                arr=initUser().toByteArray()
                socket.send(DatagramPacket(arr,arr.size,adress,port))
            }*/
            var t=""
            for(i in 0..29997){
                if (!(a[i].isLetter()) && a[i]==a[i+1] && a[i]==a[i+2] && !(a[i].isDigit()))
                    break
                t+=a[i]
            }
            val informat=Json.decodeFromString<Information>(t)
            println(informat.getMessage())
        }catch (e: IOException){
            s=0
            println("Не удается подключиться к серверу")
        }
    }

    /*while (true) {
        println( client.push(mess))
    }*/

    /*val obje= Product("1",1, Coordinates(1,1), UnitofMeasure.PCS, Organization("1",1,1,"1", OrganisationType.TRUST),"sdfghjmnbvcdfgdeececv")
    val obj = InitMap(obje)
    //val deque = mutableListOf(
    //Product("bread",30,Coordinates(1,2),UnitofMeasure.MILLIGRAMS,Organization("Karlaman",350,200,null,OrganisationType.TRUST),"aaaaaaaaaaaaaa"),Product("milk",70,Coordinates(2,3),UnitofMeasure.LITERS,Organization("Ufa",700,400,"full",null),"bbbbbbbbbbbbbb"))
    val json = JsonFile()
    //json.EncodeAndWrite(deque,"JSON")
    val deque1: MutableList<Product> = json.decodeAndRead("JSON")
    //deque1=json.sort(deque1)
    //deque1.add(obje)
    val commands = obj.initialMap(deque1)
    val console = Console()
    val client = obj.initialMapClient()
    while (true) {
        print(">>> ")
        val input = readLine()
        if (!(console.check(input!!))) {
            println("Нет такой команды, чтобы узнать какие команды есть введите help")
            continue
        }
        var information = client.get(console.getCommand(input))!!.check(input)
        if (information.getStatus() == 1) {
            println(information.getMessage())
            continue
        }
        information = commands.get(
            console.getCommand(input))!!.execute()
        if (information.getMessage()=="")
            println("Команда выполнена успешно")
        println(information.getMessage())
        if (information.getStatus() == -1) {
            break
        }
        /*val inf: Information = when (console.Check(input)) {
            0 -> commands.get(input)!!.execute()
            1 -> commands.get(input)!!.execute(console.getElement())
            2 -> commands.get(input)!!.execute(console.getManufacturer())
            3 -> commands.get(console.getCommand(input))!!.execute(console.getArgs(input))
            5 -> commands.get(console.getCommand(input))!!.execute(console.getId(input))
            -31 -> Information("Номер части должен быть не меньше 13 и не больше 58", 1)
            -32 -> Information("Id должен быть целым положительным числом", 1)
            4 -> commands.get(input)!!.execute(console.getId(input), console.getElement())
            else -> {
                Information("Нет такой команды повторите ввод", 1)
            }
        }
        if (inf.status == -1) {
            println(inf.message)
            break
        }
        println(inf.message)*/

        /*when (commands.get(console.getCommand(input!!))!!.Check(input)){
        "0" -> commands.get(console.getCommand(input!!))!!.Execute(null,deque1)
        "-1" -> commands.get(console.getCommand(input!!))!!.Execute(console.getElement(),deque1)
        "-2" -> commands.get(console.getCommand(input!!))!!.Execute(console.getManufacturer(),deque1)
        "break" -> break
        else -> println(commands.get(console.getCommand(input!!))!!.Check(input))
    }
    print("\n")
    }*/
        /*val input2="sort"
    if(!console.Check(input)){
        println("Нет такой команды. Повторите ввод")
        //continue
    }
    when (commands.get(console.getCommand(input2!!))!!.Check(input2)){
        "0" -> commands.get(console.getCommand(input2!!))!!.Execute(null,deque1)
        "-1" -> commands.get(console.getCommand(input2!!))!!.Execute(console.getElement(),deque1)
        "-2" -> commands.get(console.getCommand(input2!!))!!.Execute(console.getManufacturer(),deque1)
        //"break" -> break
        else -> println(commands.get(console.getCommand(input2!!))!!.Check(input2))
    }
    print(deque1)
    // }*/
    }*/
}