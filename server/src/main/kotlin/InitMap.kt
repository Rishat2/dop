import commands.*
import product.Product


class InitMap(val cm:CollectionManager) {
    /*private val id:Array<Long> = arrayOf(0)
    private val product:Array<Product> = arrayOf(product)
    private val manufacturer= arrayOf(Organization("",1,1,null,null))
    private val partNumber= arrayOf("")
    private val filename= arrayOf("")
    fun getId(): Array<Long> {
        return id
    }
    fun getProduct(): Array<Product> {
        return product
    }
    fun getManufacturer(): Array<Organization> {
        return manufacturer
    }*/
    fun createCommand(): MutableList<Command> {
        val add = Add(cm)
        val clear = Clear(cm)
        val exit=Exit(cm)
        val filterLessThanPartNumber=FilterLessThanPartNumber(cm)
        val help=Help(cm)
        val info=Info(cm)
        val printAscending=PrintAscending(cm)
        val removeAllByManufacturer=RemoveAllByManufacturer(cm)
        val removeById=RemoveById(cm)
        val removeLast=RemoveLast(cm)
        val removeLower=RemoveLower(cm)
        val show=Show(cm)
        val sort=Sort(cm)
        val executeScript=ExecuteScript(cm)
        val update=Update(cm)
        val list: MutableList<Command> = mutableListOf(add,executeScript, clear,exit,filterLessThanPartNumber,help,info,printAscending,removeAllByManufacturer,removeLast,removeLower,removeById,show,sort,update)
        return list
    }

    fun initialMap(): MutableMap<String, Command> {
        val map: MutableMap<String, Command> = mutableMapOf()
        val list = createCommand()
        for (i in 1..list.size) {
            map.put(list[i-1].name, list[i-1])
        }
        return map
    }
    fun createCommandClient(): MutableList<сlient.Client> {
        val add = сlient.Add()
        val clear = сlient.Clear()
        val executeScript=сlient.ExecuteScript()
        val exit=сlient.Exit()
        val filterLessThanPartNumber=сlient.FilterLessThanPartNumber()
        val help=сlient.Help()
        val info=сlient.Info()
        val printAscending=сlient.PrintAscending()
        val removeAllByManufacturer=сlient.RemoveAllByManufacturer()
        val removeById=сlient.RemoveById()
        val removeLast=сlient.RemoveLast()
        val removeLower=сlient.RemoveLower()
        val save=сlient.Save()
        val show=сlient.Show()
        val sort=сlient.Sort()
        val update=сlient.Update()
        val list: MutableList<сlient.Client> = mutableListOf(add, clear,executeScript,exit,filterLessThanPartNumber,help,info,printAscending,removeAllByManufacturer,removeLast,removeLower,removeById,save,show,sort,update)
        return list
    }

    fun initialMapClient(): MutableMap<String, сlient.Client> {
        val map: MutableMap<String, сlient.Client> = mutableMapOf()
        val list = createCommandClient()
        for (i in 1..list.size) {
            map.put(list[i-1].name, list[i-1])
        }
        return map
    }
}