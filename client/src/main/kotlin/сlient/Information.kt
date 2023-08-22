package commands

import kotlinx.serialization.Serializable

@Serializable
class Information(private val message:String,private val status:Int) {
    fun getStatus():Int{
        return status
    }
    fun getMessage():String{
        return message
    }
}