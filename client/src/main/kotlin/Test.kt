import commands.Information
import kotlinx.serialization.Serializable

@Serializable
abstract class Test {
    abstract val name:String
    abstract fun check(s:String):Information
}