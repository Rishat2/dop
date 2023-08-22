package commands

import Comanda
import Console
import InitMap
import product.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class ExecuteScript (override val cm: CollectionManager): Command {
    private var coll= cm.collection.toMutableList()
    override val name: String = "execute_script"
    private val simpleCommand= arrayOf("help","info","print_ascending","show")
    private val noArgsCommand= arrayOf("clear","save","remove_last","sort")
    private val primitiveArgsCommand= arrayOf("filter_less_than_part_number","remove_by_id")
    private val commandsWithElement= arrayOf("add","remove_lower")
    private val comands= arrayOf("filter_less_than_part_number","remove_by_id","execute_script","add","remove_lower","clear","save","remove_last","sort","help","info","print_ascending","show","update","remove_all_by_manufacturer")
    //val scripts=ArrayList<String>()
    override fun execute(): Information {
        val fileName = cm.com.argumentS
        if (fileName in cm.scripts) {
            cm.scripts.clear()
            return Information("Обнаружено зацикливание", 1)
        }
        cm.scripts.add(fileName)
        try {
            val sc = Scanner(File(fileName))
            val cmES=CollectionManager(coll)
            cmES.scripts=cm.scripts
            val obj=InitMap(cmES)
            val commands = obj.initialMap()
            val client=obj.initialMapClient()
            var reply=""
            val console= Console()
            while (sc.hasNextLine()) {
                val line = sc.nextLine()
                if (!(console.getCommand(line) in comands)){
                    cm.scripts.clear()
                    return Information("Не корректно введена одна из команд",1)
                }
                if (line in simpleCommand || line in noArgsCommand || console.getCommand(line) in primitiveArgsCommand){
                    if (!(client.get(console.getCommand(line))!!.check(line).getStatus()==0)) {
                        cm.scripts.clear()
                        return Information("Некорекктно введен аргумент одной из команд", 1)
                    }
                    val o:Comanda=client.get(console.getCommand(line))!!.create(arrayListOf<String>(console.getArgs(line)))
                    cmES.initComanda(o)
                    reply+=commands.get(o.getName())!!.execute().getMessage()
                    continue
                }
                else if (console.getCommand(line)=="execute_script"){
                    if (!(client.get(console.getCommand(line))!!.check(line).getStatus()==0)) {
                        cm.scripts.clear()
                        return Information("Некорекктно введен аргумент одной из команд", 1)
                    }
                    cmES.initComanda(client.get(console.getCommand(line))!!.create(arrayListOf(console.getArgs(line))))
                    reply+=commands.get(console.getCommand(line))!!.execute().getMessage()
                    continue
                }
                else if (line in commandsWithElement){
                    val elem= arrayListOf<String>()
                    for (i in 1 ..11){
                        if (sc.hasNextLine())
                            elem.add(sc.nextLine())
                    }
                    if (elem.size!=11) {
                        cm.scripts.clear()
                        return Information("Полей элемента меньше, чем должно быть", 1)
                    }
                    if (!validElement(elem)) {
                        cm.scripts.clear()
                        return Information("Не корректно введено одно из полей", 1)
                    }
                    cmES.initComanda(client.get(line)!!.create(elem))
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
                        cm.scripts.clear()
                        return Information("Полей элемента меньше, чем должно быть", 1)
                    }
                    if (!validOrganisation(elem)) {
                        cm.scripts.clear()
                        return Information("Не корректно введено одно из полей", 1)
                    }
                    cmES.initComanda(client.get(line)!!.create(elem))
                    commands.get(line)!!.execute()
                    continue
                }
                else if (console.getCommand(line)=="update"){
                    if (!(client.get(console.getCommand(line))!!.check(line).getStatus()==0)) {
                        cm.scripts.clear()
                        return Information("Некорректно введен аргумент одной из команд", 1)
                    }
                    val elem= arrayListOf<String>()
                    for (i in 1 ..11){
                        if (sc.hasNextLine())
                            elem.add(sc.nextLine())
                    }
                    if (elem.size!=11) {
                        cm.scripts.clear()
                        return Information("Полей элемента меньше, чем должно быть", 1)
                    }
                    if (!validElement(elem)) {
                        cm.scripts.clear()
                        return Information("Не корректно введено одно из полей", 1)
                    }
                    cmES.initComanda(client.get(console.getCommand(line))!!.create(elem))
                    commands.get(console.getCommand(line))!!.execute()
                    continue
                }
                else if (line=="exit"){
                    assignment(coll)
                    if (reply!="") {
                        cm.scripts.clear()
                        return Information(reply, -1)
                    }
                    cm.scripts.clear()
                    return Information("Команда выполнена успешно",-1)
                }
            }
            if (cm.com.argumentS in cm.scripts)
            cm.scripts.removeAt(cm.scripts.size-1)
            assignment(cmES.collection)
            //if (reply!="")
                return Information(reply,0)
            //return Information("Команда выполнена успешно",0)
        } catch (e: IOException) {
            cm.scripts.clear()
            return Information("Что-то пошло не так.",1)
        }
        catch(e: FileNotFoundException){
            return Information("Файл найти не удалось",1)
        }
    }
    fun assignment(coll:MutableList<Product>){
        cm.collection.clear()
        for(o in coll){
            cm.collection.add(o)
        }
    }
    fun validElement(elem:ArrayList<String>): Boolean {
        val console=Console()
        if (console.validName(elem[0]) && console.validX(elem[1]) && console.validY(elem[2]) && console.validPrice(elem[4]) && console.validPartNumber(elem[3]) && console.validUnitOfMeasure(elem[5]) && console.validManufacturerName(elem[6]) && console.validAnnualTurnover(elem[7]) && console.validEmployeesCount(elem[8]) && console.validManufacturerType(elem[10]))
            return true
        return false
    }
    fun validOrganisation(elem:ArrayList<String>): Boolean {
        val console=Console()
        if (console.validManufacturerName(elem[0]) && console.validAnnualTurnover(elem[1]) && console.validEmployeesCount(elem[2]) && console.validManufacturerType(elem[3]))
            return true
        return false
    }
}