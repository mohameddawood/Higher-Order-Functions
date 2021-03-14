
import org.junit.Test
/*
* this exapmle form eng-mohammed hammad channel
* https://www.youtube.com/watch?v=oCb63QjJhoo
* all waht i did that i wrote the example form C# to Kotlin
*
* created by Mohamed Dawood 
*/
class HigherOrderFunctions {

    var dOne: ((d: Double) -> Double) = ::testOne
    var dTwo: ((d: Double) -> Double) = ::testTwo
    var list = arrayListOf<((d: Double) -> Double)>()

    var a: ((i: Int) -> Pair<Double, Double>) =
        ::productParametersFood
    var b: ((i: Int) -> Pair<Double, Double>) =
        ::productParametersBeverage
    var c: ((i: Int) -> Pair<Double, Double>) =
        ::productParametersRawMaterial

    private val order = Order().apply {
        OrderID = 10
        ProductIndex = 10
        Quantity = 20.0
        UnitPrice = 4.0
    }

    @Test
    fun testValues() {
        println("/*************Start Test Values**********************/")
        list.add(dOne)
        list.add(dTwo)
        println(testTwo(testOne(5.0)))
        println(testOne(testTwo(5.0)))
        println(list[0](5.0))
        println(list[1](5.0))
        println(testThree(list[0], 5.0))
        println(testThree(list[1], 5.0))
        println("/*************End Test Values**********************/")
    }

    @Test
    fun testDiscount() {
        println("/*************Start Test Discount**********************/")
        val p = when (ProductType.Beverage) {
            ProductType.Food -> a
            ProductType.Beverage -> b
            ProductType.RawMaterial -> c
        }
        print("Discount Is = ")
        println(calculateDiscount(p, order))
        println("/*************End Test Discount**********************/")
    }

    private fun testThree(f: (d: Double) -> Double, value: Double) = f(value) + value;
    private fun testOne(x: Double) = x / 2
    private fun testTwo(x: Double) = x / 4 + 1


    private fun calculateDiscount(
        productParameterCalc: (i: Int) -> Pair<Double, Double>,
        order: Order
    ): Double {
        val (x1, x2) = productParameterCalc(order.ProductIndex)
        return x1 * order.Quantity + x2 * order.UnitPrice;
    }

    private fun productParametersFood(productIndex: Int) = Pair(
        productIndex / (productIndex + 100).toDouble(),
        productIndex / (productIndex + 300).toDouble()
    )


    private fun productParametersBeverage(productIndex: Int) = Pair(
        productIndex / (productIndex + 300).toDouble(),
        productIndex / (productIndex + 400).toDouble()
    )

    private fun productParametersRawMaterial(productIndex: Int) =
        Pair(
            productIndex / (productIndex + 400).toDouble(),
            productIndex / (productIndex + 700).toDouble()
        )


}

enum class ProductType {
    Food, Beverage, RawMaterial
}

class Order {
    var OrderID = 0
    var ProductIndex = 0
    var Quantity = 0.0
    var UnitPrice = 0.0
}
