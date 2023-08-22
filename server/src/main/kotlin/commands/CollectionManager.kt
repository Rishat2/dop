package commands

import Comanda
import product.Organization
import product.Product

class CollectionManager(val collection:MutableList<Product>) {
    /*fun getCom():Comanda{
        return com
    }*/
    lateinit var com:Comanda
    fun initComanda(com:Comanda){
        this.com=com
        this.com.argumentP.setId(collection)
    }
    var scripts= arrayListOf<String>()
    fun validatorLong(id:Long):Boolean{
        for (o in collection){
            if (o.getId()==id) {
                return true
            }
        }
        return false
    }
    fun validatorLast():Boolean{
        if (collection.isNotEmpty())
            return true
        return false
    }

    fun add(element:Product):Information {
        var status=0
        //val obj=Product("1",1, Coordinates(1,1),UnitofMeasure.PCS, Organization("1",1,1,"1",OrganisationType.TRUST),"sdfghjmnbvcdfgdeececv")
        try {
            collection.add(element)
            return Information("Команда была успешно выполнена",status)
        }catch (e: Exception){
            status=1
            return Information("Команду выполнить не удалось",status)
        }
    }
    fun clear():Information{
        var status = 0
        try {
            collection.clear()
            return Information("Команда выполнена успешно", status)
        } catch (e: Exception) {
            status = 1
            return Information("Команду выполнить не удалось", status)
        }
    }
    /*fun executeScript(filename:String):Information{
        val fileName = cm.com.argumentS
        if (fileName in scripts)
            return Information("Обнаружено зацикливание",1)
        scripts.add(fileName)
        try {
            val sc = Scanner(File(fileName))
            val obj=InitMap(cm)
            val commands = obj.initialMap()
            val client=obj.initialMapClient()
            var reply=""
            val console= Console()
            while (sc.hasNextLine()) {
                val line = sc.nextLine()
                if (!(console.getCommand(line) in comands)){
                    scripts.clear()
                    return Information("Не корректно введена одна из команд",1)
                }
                if (line in simpleCommand){
                    reply+=commands.get(line)!!.execute().getMessage()
                    continue
                }
                else if ( line in noArgsCommand){
                    commands.get(line)!!.execute()
                    continue
                }
                else if (console.getCommand(line) in primitiveArgsCommand){
                    if (!(client.get(console.getCommand(line))!!.check(line).getStatus()==0)) {
                        scripts.clear()
                        return Information("Некорекктно введен аргумент одной из команд", 1)
                    }
                    cm.com=client.get(console.getCommand(line))!!.create()
                    commands.get(console.getCommand(line))!!.execute()
                    continue
                }
                else if (console.getCommand(line)=="execute_script"){
                    if (!(client.get(console.getCommand(line))!!.check(line).getStatus()==0)) {
                        scripts.clear()
                        return Information("Некорекктно введен аргумент одной из команд", 1)
                    }
                    cm.com=client.get(console.getCommand(line))!!.create()
                    reply=commands.get(console.getCommand(line))!!.execute().getMessage()
                    continue
                }
                else if (line in commandsWithElement){
                    val elem= arrayListOf<String>()
                    for (i in 1 ..11){
                        if (sc.hasNextLine())
                            elem.add(sc.nextLine())
                    }
                    if (elem.size!=11) {
                        scripts.clear()
                        return Information("Полей элемента меньше, чем должно быть", 1)
                    }
                    if (!validElement(elem)) {
                        scripts.clear()
                        return Information("Не корректно введено одно из полей", 1)
                    }
                    cm.com.argumentP=getElement(elem)
                    commands.get(line)!!.execute()
                    continue
                }
                else if (line=="remove_all_by_manufacturer"){
                    val elem= arrayListOf<String>()
                    for (i in 1 ..5){
                        if (sc.hasNextLine())
                            elem.add(sc.nextLine())
                    }
                    if (elem.size!=5) {
                        scripts.clear()
                        return Information("Полей элемента меньше, чем должно быть", 1)
                    }
                    if (!validOrganisation(elem)) {
                        scripts.clear()
                        return Information("Не корректно введено одно из полей", 1)
                    }
                    cm.com.argumentM=getOrganisation(elem)
                    commands.get(line)!!.execute()
                    continue
                }
                else if (console.getCommand(line)=="update"){
                    if (!(client.get(console.getCommand(line))!!.check(line).getStatus()==0)) {
                        scripts.clear()
                        return Information("Некорректно введен аргумент одной из команд", 1)
                    }
                    val elem= arrayListOf<String>()
                    for (i in 1 ..11){
                        if (sc.hasNextLine())
                            elem.add(sc.nextLine())
                    }
                    if (elem.size!=11) {
                        scripts.clear()
                        return Information("Полей элемента меньше, чем должно быть", 1)
                    }
                    if (!validElement(elem)) {
                        scripts.clear()
                        return Information("Не корректно введено одно из полей", 1)
                    }
                    cm.com.argumentP=getElement(elem)
                    cm.com.argumentL=console.getArgs(line).toLong()
                    commands.get(line)!!.execute()
                    continue
                }
                else if (line=="exit"){
                    assignment(coll)
                    if (reply!="") {
                        scripts.clear()
                        return Information(reply, -1)
                    }
                    scripts.clear()
                    return Information("Команда выполнена успешно",-1)
                }
            }
            scripts.removeAt(scripts.size-1)
            assignment(coll)
            //if (reply!="")
                return Information(reply,0)
            //return Information("Команда выполнена успешно",0)
        } catch (e: IOException) {
            scripts.clear()
            return Information("Что-то пошло не так",1)
        }
        catch(e: FileNotFoundException){
            return Information("Файл найти не удалось",1)
        }
    }*/
    fun filterLessThanPartNumber(partNumber:String):Information{
        val status=0
        return try {
            var reply=""
            collection.stream().filter { s -> s.getPartNumber()<partNumber }.forEach { s -> reply+=s }
            /*for (o in collection){
                if (o.getPartNumber()< partNumber)
                    reply+=o.toString()
            }*/
            Information(reply,status)
        }catch (e: Exception) {
            Information("Команду выполнить не удалось",1)
        }
    }
    fun info():Information{
        return Information("Тип: Product.Product\n" +
                "Дата инициализации: ${Product.Date}\n" +
                "Количество элементов: ${collection.size}\n",0)
    }
    fun printAscending():Information{
        return try {
            if (collection.isEmpty())
                Information("Коллекция пустая",0)
            val coll=collection.sortedBy { it.getprice() } as MutableList<Product>
            var reply=""
            coll.stream().forEach { s -> reply+=s }
            /*for (obj in coll){
                reply+=obj.toString()
            }*/
            Information(reply,0)
        }catch (e: Exception){
            Information("Команду выполнить не удалось",1)
        }
    }
    fun removeAllByManufacturer(manufacturer:Organization):Information{
        return try {
            collection.removeIf { it.EqualsByManufacturer(manufacturer) }
            /*for (i in collection.indices.reversed()){
                if (collection[i].EqualsByManufacturer(manufacturer)){
                    collection[i].removeFrompN(collection[i].getPartNumber())
                    collection.remove(collection[i])
                }
            }*/
            Information("Команда выполнена успешно",0)
        }catch (e: Exception){
            Information("Команду выполнить не удалось",1)
        }
    }
    fun removeById(id:Long):Information{
        if (!(validatorLong(id)))
            return Information("Нет элемента с таким Id",1)
        return try {
            collection.removeIf{ it.getId() == id }
            Information("Команда выполнена успешно",0)
        }catch (e: Exception){
            Information("Команду выполнить не удалось",1)
        }
    }
    fun removeLast():Information{
        if (!validatorLast())
            return Information("Коллекция пустая. Команду выполнить не удалось",1)
        return try {
            collection.get(collection.size-1).removeFrompN(collection.get(collection.size-1).getPartNumber())
            collection.removeAt(collection.size - 1)
            Information("Команда выполнена успешно",0)
        }catch (e: Exception){
            Information("Команду выполнить не удалось",1)
        }
    }
    fun removeLower(element:Product):Information{
        return try {
            for (i in collection.indices.reversed()){
                if (collection[i].comparison(element)) {
                    collection[i].removeFrompN(collection[i].getPartNumber())
                    collection.remove(collection[i])
                }
            }
            Information("Команда выполнена успешно",0)
        }catch (e: Exception){
            Information("Команду выполнить не удалось",1)
        }
    }
    fun show():Information{
        return try {
            if (collection.isEmpty())
                return Information("Коллекция пустая",0)
            var reply=""
            for (obj in collection) {
                reply+=obj
            }
            Information(reply, 0)

        }catch (e: Exception){
            Information("Команду выполнить не удалось",1)
        }
    }
    fun sort():Information{
        return try {
            val list:ArrayList<Long> = arrayListOf()
            for(o in collection){
                list.add(o.getprice())
            }
            list.sort()
            var c=-1
            val coll= mutableListOf<Product>()
            for(i in list){
                ++c
                for(o in collection){
                    if (o.getprice()==i){
                        coll.add(o)
                        collection.remove(o)
                        break
                    }
                }
            }
            collection.clear()
            for (o in coll){
                collection.add(o)
            }
            Information("Команда выполнена успешно",0)
        }catch (e: Exception){
            Information("Команду выполнить не удалось",1)
        }
    }
    fun update(id:Long,element:Product):Information{
        if (!validatorLong(id))
            return Information("Элемент с таким значением id не найден",1)
        return try {
            var c=-1
            for (o in collection){
                c++
                if (o.getId()== id){
                    o.removeFrompN(o.getPartNumber())
                    collection.remove(o)
                    collection.add(c, element)
                }
            }
            Information("Команда выполнена успешно",0)
        }catch (e: Exception){
            Information("Команду выполнить не удалось",1)
        }
    }
}