import java.util.Dictionary
import scala.io.Source
import scala.math._
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object AssignmentTwo {

  def main(args: Array[String]): Unit = {
    //println("This Works!")
    //println(sum_multiples_3_5(20))
    //println(patternCount("aaaaa", "aa"))
    //println(factorial(3))
    val names = Array("Tom", "Fred", "Harry")
    val mapNames = Map("Tom" -> 3,"Dick" -> 4, "Harry" -> 5)
    //mapValues(names, mapNames)
    //println(sampleNoRepeats((1 to 8), 3))
    smallestRandomSample()
  }

  //q1
  def sum_multiples_3_5(N: Int): Int = {
    return (1 to N).filter(x => x < N &&
                                x % 15 != 0 &&
                               (x % 3 == 0 ||
                                 x % 5 == 0))
                                .sum
  }

  //q2
  def patternCount(text: String, pattern: String): Int = {
    var count = 0
    text.sliding(pattern.length).foreach(i => { if ( i == pattern) count += 1})
    return count
  }

  //q3 factorial
  def factorial(num: Int): Int = {
    if (num < 0) {
      throw new IllegalArgumentException("num < 0");
    }

    return (1 to num).reduceLeft(_ * _)
  }

  //q4 map
  def mapValues(names: Array[String], nameMap: Map[String, Int]): ArrayBuffer[Int] = {
    var nameValues = ArrayBuffer[Int]()
    names.foreach(name => if(nameMap.contains(name)) {nameValues += nameMap(name)} )
    nameValues.foreach(println)
    return nameValues
  }

  //q5 randomNumbers
  def sampleNoRepeats(range: Range, n: Int): List[Int] = {
    //var sampleArray = ArrayBuffer[Int]()
    var arrayRange = range.toArray
    /**
    var randomNum = 0
    var i = 0
    while(i < n) {
      randomNum = arrayRange(scala.util.Random.nextInt(range.max))
      if(!sampleArray.contains(randomNum)) {
        sampleArray += randomNum
        i += 1
      }
    }
    **/
    // test
    // just use shuffle,
    return Random.shuffle(arrayRange.toList).take(n)
  }

  //from A1
  def randomInts(n: Int, range: Int): Array[Double] = {
    val randomInt = scala.util.Random
    return Array.fill[Double](n)(randomInt.nextInt(range))
  }

  //q6
  def smallestRandomSample(): Unit ={
    val populationArray = randomInts(100000, 50000)
    val M:Double = populationArray.sum / populationArray.length // expected mean M
    val sampleMean:Double = Random.shuffle(populationArray.toList).take(100).sum / 100
    val error = ((sampleMean - M).abs / M)
    val formatter = java.text.NumberFormat.getInstance
    println("The Mean for  M: " +  formatter.format(M))
    println("Mean for the sample: " +  formatter.format(sampleMean))
    println("The error is: " + error + "\n")

    // ================================== Question #7 ==========================================
    val oneHundredSampleMeanArray = Array.fill[Double](1000)(computeMean(populationArray, 100)).sorted
    val meanofOneHundredElements = oneHundredSampleMeanArray.sum / oneHundredSampleMeanArray.length
    val oneHundredElementsSampleStdDev = sampleStdDeviation(oneHundredSampleMeanArray, meanofOneHundredElements)
    val oneHundredElementsR = 1.96 * oneHundredElementsSampleStdDev

    var count100 = 0
    //oneHundredSampleMeanArray.sorted.foreach( mean => if(mean >= (M - boundR) && mean <= M + boundR) { println("The mean " + mean + " falls between " + (M - boundR) + " and " + (M + boundR) ) ; count+= 1 })
    oneHundredSampleMeanArray.sorted.foreach( mean => if(mean >= (M - oneHundredElementsR) && mean <= M + oneHundredElementsR) { count100+= 1 })
    println("For One Hundred Elements " +  oneHundredElementsR + " the Count is " + count100 + " for r value of " + oneHundredElementsR)

    // ============== question #8 ================
    val oneThousandElementsMeansArray = Array.fill[Double](1000)(computeMean(populationArray, 1000)).sorted
    val oneThousandElementsMeans = oneThousandElementsMeansArray.sum / oneThousandElementsMeansArray.length
    val oneThousandElementsSampleStdDev = sampleStdDeviation(oneThousandElementsMeansArray, oneThousandElementsMeans)
    val oneThousandElementsR = 1.96 * oneThousandElementsSampleStdDev
    var count1k = 0
    oneThousandElementsMeansArray.sorted.foreach( mean => if(mean >= (M - oneThousandElementsR) && mean <= M + oneThousandElementsR) { count1k += 1 })
    println("For One Hundred Elements " +  oneThousandElementsR + " the Count is " + count1k + " for r value of " + oneThousandElementsR)

    // =============== Question #9 =================
    val tenThousandElementsMeansArray = Array.fill[Double](1000)(computeMean(populationArray, 10000)).sorted
    val tenThousandElementsMeans = tenThousandElementsMeansArray.sum / tenThousandElementsMeansArray.length
    val tenThousandElementsSampleStdDev = sampleStdDeviation(tenThousandElementsMeansArray, tenThousandElementsMeans)
    val tenThousandElementsR = 1.96 * tenThousandElementsSampleStdDev
    var count10k = 0
    tenThousandElementsMeansArray.sorted.foreach( mean => if(mean >= (M - tenThousandElementsR) && mean <= M + tenThousandElementsR) { count10k += 1 })
    println("For One Hundred Elements " +  tenThousandElementsR + " the Count is " + count1k + " for r value of " + tenThousandElementsR)


    // ======== Population Calculation
    val populationStdDev = populationStdDeviation(populationArray, M)
    val populationStdError = populationStdErrorOfMean(populationStdDev, populationArray.length)
    val populationLowerBound = lowerBound(M, populationStdError, 1.96)
    val populationUpperBound = upperBound(M, populationStdError, 1.96)
    /**
    println("the population size is "+ populationArray.length)
    println("the population std dev is " + populationStdDev)
    println("The population upper and lower bounds are: " + populationLowerBound + " and " + populationUpperBound)
    println("The M -r is " + (M- populationLowerBound))
    println("The M +r is " + (populationUpperBound - M))

    // ======== Sample Mean Calculations
    val sampleStdDev = sampleStdDeviation(oneHundredSampleMeanArray, meanofOneHundredElements)
    val sampleStdError = sampleStdErrorOfMean(oneHundredElementsSampleStdDev, oneHundredSampleMeanArray.length)
    val sampleLowerBound = lowerBound(meanofOneHundredElements, sampleStdError, 1.962)
    val sampleUpperBound = upperBound(meanofOneHundredElements, sampleStdError, 1.962)
    val sampleR = meanofOneHundredElements - (meanofOneHundredElements - sampleLowerBound)

    println("The size of the sample is " + oneHundredSampleMeanArray.length)
    println("The mean of sample means is " + meanofOneHundredElements)
    println("The sample upper and lower bounds are: "+ sampleLowerBound + " and  " + sampleUpperBound)
    println("The M -r is " + (meanofOneHundredElements - sampleLowerBound))
    println("The M +r is " + (sampleUpperBound - meanofOneHundredElements))
    println(" ======== The meanofMeans less sampleR is " + sampleR)

    // ========== R Calculation
    //val boundR = 1.962 * populationStdDev / sqrt(populationArray.length)
    //println("))))))))))))))))) Custom R calc is " + boundR + " ((((((((((")
    // z = (sample mean - population mean) / standard error for population
    val zCalc = (meanofOneHundredElements - M) / populationStdError
    //println(" ******* Z CALC IS ******* : " + zCalc)
    // 1.96 = (meanofMeans - M) / populationStdError

    // r = population mean - 2(std deviation of entire population
    **/
    val boundR = 1.96  * oneHundredElementsSampleStdDev
    //println("final r is " + finalR)

    /**
    val confidenceIntervalStart = (meanofOneHundredElements - 1.96 * meanofOneHundredElements / Math.sqrt(oneHundredSampleMeanArray.length))
    val confidenceIntervalEnd = (meanofOneHundredElements + 1.96 * meanofOneHundredElements / Math.sqrt(oneHundredSampleMeanArray.length))
    println("The confidence interval is (" + formatter.format(confidenceIntervalStart) + " , " + formatter.format(confidenceIntervalEnd) + ")")
    **/

  }

  //q7ish
  // http://onlinestatbook.com/2/estimation/mean.html
  // https://www.mathsisfun.com/data/standard-deviation-formulas.html
  def computeMean(populationArray: Array[Double], numElements: Int): Double = {
    return Random.shuffle(populationArray.toList).take(numElements).sum / numElements.toDouble
  }

  def populationStdDeviation(populationArray: Array[Double], populationMean: Double ): Double = {
    return sqrt((populationArray.map( _ - populationMean).map(delta => delta * delta).sum )/ (populationArray.length).toFloat)
  }

  def sampleStdDeviation(sampleMeansArray: Array[Double], sampleMean: Double): Double = {
    return sqrt((sampleMeansArray.map( _ - sampleMean).map(delta => delta * delta).sum )/ (sampleMeansArray.length - 1).toFloat)
  }

  def populationStdErrorOfMean(populationStdDev: Double, populationSize: Int): Double = {
    return populationStdDev / (sqrt(populationSize).toDouble)
  }

  def sampleStdErrorOfMean(sampleStdDev: Double, sampleSize: Int): Double = {
    return sampleStdDev / (sqrt(sampleSize).toDouble)
  }

  def lowerBound(mean: Double, stdError: Double, confidenceZ: Double): Double = {
    return mean - confidenceZ * stdError
  }

  def upperBound(mean: Double, stdError: Double, confidenceZ: Double): Double = {
    return mean + confidenceZ * stdError
  }
}
