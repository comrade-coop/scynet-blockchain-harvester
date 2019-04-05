package harvester.common.processor

import java.math.BigDecimal

class FeatureCalculator {
    companion object {

        private val WEI_IN_ETH = "1000000000000000000"

        fun weiToEth(wei: String): String {
            return (BigDecimal(wei) / BigDecimal(WEI_IN_ETH)).setScale(2).toString()
        }

        fun sum(firstNum: String, secondNum: String): String {
            val result = BigDecimal(firstNum).add(BigDecimal(secondNum))
            // uncomment when starting from block 0
//          if(result < BigInteger.ZERO){
//            result = BigInteger.ZERO
//          }
            return result.toString()
        }

        fun increaseByOne(number: String): String {
            return BigDecimal(number).add(BigDecimal.ONE).toString()
        }

        fun subtract(firstNum: String, secondNum: String): String {
            return BigDecimal(firstNum).subtract(BigDecimal(secondNum)).toString()
        }

        fun multiply(firstNum: String, secondNum: String): String {
            return (BigDecimal(firstNum) * BigDecimal(secondNum)).toString()
        }
    }
}