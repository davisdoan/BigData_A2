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
    //factorial(-1)
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
      throw new IllegalArgumentException("Your Input should be 0 or higher.")
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
    var arrayRange = range.toArray
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
    val meanOfOneHundredElements = computeMean(oneHundredSampleMeanArray, oneHundredSampleMeanArray.length)
    val oneHundredElementsSampleStdDev = sampleStdDeviation(oneHundredSampleMeanArray, meanOfOneHundredElements)
    val oneHundredElementsR = 1.96 * oneHundredElementsSampleStdDev
    var count100 = 0
    oneHundredSampleMeanArray.sorted.foreach( mean => if(mean >= (M - oneHundredElementsR) && mean <= M + oneHundredElementsR) { count100+= 1 })
    println("For One Hundred Elements, the Count is " + count100 + " for r value of " + oneHundredElementsR)

    // ============== question #8 ================
    val oneThousandElementsMeansArray = Array.fill[Double](1000)(computeMean(populationArray, 1000)).sorted
    val meanOfOneThousandElements = computeMean(oneThousandElementsMeansArray, oneThousandElementsMeansArray.length)
    val oneThousandElementsSampleStdDev = sampleStdDeviation(oneThousandElementsMeansArray, meanOfOneThousandElements)
    val oneThousandElementsR = 1.96 * oneThousandElementsSampleStdDev
    var count1k = 0
    oneThousandElementsMeansArray.sorted.foreach( mean => if(mean >= (M - oneThousandElementsR) && mean <= M + oneThousandElementsR) { count1k += 1 })
    println("For One Thousand Elements, the Count is " + count1k + " for r value of " + oneThousandElementsR)

    // =============== Question #9 =================
    val tenThousandElementsMeansArray = Array.fill[Double](1000)(computeMean(populationArray, 10000)).sorted
    val meanOfTenThousandElements = computeMean(tenThousandElementsMeansArray, tenThousandElementsMeansArray.length)
    val tenThousandElementsSampleStdDev = sampleStdDeviation(tenThousandElementsMeansArray, meanOfTenThousandElements)
    val tenThousandElementsR = 1.96 * tenThousandElementsSampleStdDev
    var count10k = 0
    tenThousandElementsMeansArray.sorted.foreach( mean => if(mean >= (M - tenThousandElementsR) && mean <= M + tenThousandElementsR) { count10k += 1 })
    println("For Ten Thousand Elements, the Count is " + count1k + " for r value of " + tenThousandElementsR)
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
}
