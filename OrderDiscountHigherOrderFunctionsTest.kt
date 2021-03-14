
import org.junit.Test

/*
* this exapmle form eng-mohammed hammad channel
* https://www.youtube.com/watch?v=6uwRajbkaqI
* all waht i did that i wrote the example form C# to Kotlin
*
* created by Mohamed Dawood 
*/

class OrderDiscountHigherOrderFunctionsTest {

    /*
    * prepare list of orders
    * then loop on it to call the function of discount
    * note that each order should loop on the number of rules to get
    * if this order has discount or not
    * then print the result
    * */
    @Test
    fun testOrder() {
        arrayListOf(
            OrderItem(name = "Order One"),
            OrderItem(name = "Order Two"),
            OrderItem(name = "Order Three")
        ).map {
            getOrderWithDiscount(it, getDiscountRules())
        }.let {
            println(it.toString())
        }
    }

    /*
    * @order to set the new discount for it
    * @rules ech order may has many rules to calculate
    * so we should loop on @rules to check if it true
    * then sort to invoke the discountCalculator function with the selected order
    * then if the filtered rules more than one just take lowest three discounts
    * after that take the average for those filtered discounts
    * */
    private fun getOrderWithDiscount(
        order: OrderItem,
        rules: ArrayList<Pair<((order: OrderItem) -> Boolean), ((order: OrderItem) -> Long)>>
    ): OrderItem {
        val discounts = arrayListOf<Long>()
        rules.filter { item ->
            val (qualifyingCondition, _) = item
            qualifyingCondition.invoke(order)
        }.sortedBy {
            val (_, getDiscount) = it
            getDiscount.invoke(order)
        }.take(3).map {
            val (_, getDiscount) = it
            discounts.add(getDiscount.invoke(order))
        }

        order.discount = discounts.average();
        return order;
    }


    /*
    * each order has two steps to calculate the discount{
    *   - if it was qualified then call the rule# function to get the discount
    *   - so if you wanna add new rule add two functions
    *   ---> 1- qualified 2- discount value
    * }
    * 
    * @return list of all available rules 
    * @rule each rule contains two functions 
    *   1# - > is#Qualified  (method take order and return boolean)
    *   2# - > #ruleNum which return numerical value(method take order and return number)
    * why we return pair because each order we should apply both function on it
    * */
    private fun getDiscountRules(): ArrayList<Pair<((order: OrderItem) -> Boolean), ((order: OrderItem) -> Long)>> {
        return arrayListOf(
            Pair(::isRuleOneQualified, ::ruleOne),
            Pair(::isRuleTwoQualified, ::ruleTwo),
            Pair(::isRuleThreeQualified, ::ruleThree),
            Pair(::isRuleFourQualified, ::ruleFour)
        )
    }

    private fun isRuleOneQualified(r: OrderItem) = true
    private fun ruleOne(r: OrderItem) = 1L

    private fun isRuleTwoQualified(r: OrderItem) = true
    private fun ruleTwo(r: OrderItem) = 2L

    private fun isRuleThreeQualified(r: OrderItem) = false
    private fun ruleThree(r: OrderItem) = 3L

    private fun isRuleFourQualified(r: OrderItem) = true
    private fun ruleFour(r: OrderItem) = 9L


}

class OrderItem(val name: String) {
    var discount = 0.0
    override fun toString(): String {
        return "$name and has discount  $discount"
    }
}
