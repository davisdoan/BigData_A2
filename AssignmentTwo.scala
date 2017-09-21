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
    val names = Array("Tom", "Fred", " Harry")
    val mapNames = ("Tom" -> 3,"Dick" -> 4, "Harry" -> 5)
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
    return (1 to num).reduceLeft(_ * _)
  }

  //q4 map
  def mapValues(names: Array[String], nameMap: Map[String, Int]): Int = {
    // array given is Array("Tom", "Fred", "Harry")
    // Map is Map(Tom->3, Dick->4, Harry->5)
    //check if map contains array[i], if it does, append that map[key] to the return array
    var nameValues = ArrayBuffer[Int]()
    var count =0
    nameMap.keys.foreach((name) =>
      if(nameMap.contains(name)) {
        nameValues += 1
      }
    )

    nameValues.foreach(println)
    // Returns Array(3,5)

    return 1

    //return names.foreach(i => if(nameMap(i)) {})
  }

  //q5 randomNumbers
  def sampleNoRepeats(range: Range, n: Int): ArrayBuffer[Int] = {
    var sampleArray = ArrayBuffer[Int]()
    var arrayRange = range.toArray
    var randomNum = 0
    var i = 0
    while(i < n) {
      randomNum = arrayRange(scala.util.Random.nextInt(range.max))
      if(!sampleArray.contains(randomNum)) {
        sampleArray += randomNum
        i += 1
      }
    }
    return sampleArray
  }

  //from A1
  def randomInts(n: Int, range: Int): Array[BigInt] = {
    val randomInt = scala.util.Random
    return Array.fill[BigInt](n)(randomInt.nextInt(range))
  }

  //q6
  def smallestRandomSample(): Unit ={
    // generate array
    val sampleArray = randomInts(100000, 50000)
    // compute mean M
    val M:BigInt = sampleArray.sum / sampleArray.length // expected
    // take random sample of 100 diff elements(no replacement) and compute Mean
    val sampleMean:BigInt = Random.shuffle(sampleArray.toList).take(100).sum / 100
    // whats the error?
    val formatter = java.text.NumberFormat.getInstance
    println("Median for array mean M: " +  formatter.format(M))
    println("Median for array mean Sample: " +  formatter.format(sampleMean))
    println("The error is: " + ((sampleMean - M).abs / M) + "\n")
    // what is the error?
  }

}
