// ======= Chapter 12 Higher Order Functions
/**
Scala mixes object orientation with functional features. In a functional programming language, functions are first-class citizens that can be passed around and manipulated just like any other data types. This is very useful whenever you want to pass some action detail to an algorithm. In a functional language, you just wrap that detail into a function that you pass as a parameter. In this chapter, you will see how to be productive with functions that use or return functions.

Highlights of the chapter include:
• Functions are “first-class citizens” in Scala, just like numbers.
• You can create anonymous functions, usually to give them to other functions.
• A function argument specifies behavior that should be executed later.
• Many collection methods take function parameters, applying a function to the values of the collection.
• There are syntax shortcuts that allow you to express function parameters in a way that is short and easy to read.
• You can create functions that operate on blocks of code and look much like the built-in control statements.

  */


// ======== 12.1 Functions as Values
import scala.math._
val num = 3.14
val fun = ceil _

// the _ behind ceil indicates that you really meant the function
// fun is reported as (double) -> double, that is a funct receiving and returning a double

// ***** Note:the _ turns the ceil method into a funciton
// in scala you cannot manipulate methods, only functions
// the type of function is a (Double) => Double
// the type of the ceil method is (Double) Double

// ***** The ceil method is a method of the scala.math package obj
// if you have a method from a class the syntax to turn it into a func is diff:

val g = (_: String).charAt(_:Int)
 // a function (String, Int) => Char

// alernatively you can specify the type of the function instead of the param types:
val h:(String, Int) => Char = _.charAt(_)

// what can you do with a function?
// call it
// pass it around by storing it in a var or giving it to a func as a param
fun(num)
Array(3.14,1.42,2.0).map(fun) // Array (4.0,2.0,2.0)

// map method accepts a function and applies it to all vlaues of an array
// returns an array with the function vals



// ======== 12.2 Anon Functions
// in scala you dont have to give a name to each function
val triple = (x: Double) => 3 * x

// the same as if you did:
def triple(x: Double) = 3 * x

// no need to name, just pass it
Array(3.14, 1.42, 2.0).map((x: Double)=> 3 * x)
// Array(9.42, 4.26, 6.0) tell map to mult each elem by 3


// ***** NOTE: you can enclose the function arg in braces instead of parens:
Array(3,14, 1.42, 2.0).map{ (x: Double) => 3 * x }

// ***** NOTE: Anything defined with def is a method, not a function
def triple1(x: Double) = 3 * x



// ======== 12.3 Functions with Function Params
def valueAtOneQuarter(f:(Double) => Double) = f(0.25)

valueAtOneQuarter(ceil _) // 1.0
valueAtOneQuarter(sqrt _) // 0.5 cause .5 x .5 = .25

// what is the type of valueAtOneQuarter?
// it is a function with one param so it is written as:
// (paramType) => resultType
// the result type is Doubl and the param type is (Double) => Double
//
// so: ((Double) => Double) => Double

// a higher order funct is a function that receives a function
// a higher order funct can produce a function

def mulBy(factor: Double) = (x: Double) => factor * x
// mulBy(3) returns the function (x: DOuble) => 3 * x

val quintuple = mulBy(5) // 5 is the factor
quintuple(20) // 20 is the x


// ======== 12.4 Parameter Inference
// when you pass an anon function or method, scala helps you by deducing types when possible

// no need to write valueAtOneQuarter((x: Double) => 3 * x)

// for a function with only one param you can omit the () around the param
valueAtOneQuarter(x => 3 * x)
// ( 1 * 2 * 3 * 4 ... * 8 * 9)

// each _ denotes a seperate param

// %%%%% you need a binary function for sorting:
"Mary had a little lamb".split(" ").sortWith(_.length < _.length)

// yields Array("a", "had", "Mary", "Lamb", "little").


// if the parma only occurs once on the right side of the => you can replace it with an underscore:
valueAtOneQuarter(3 * _)

// val fun = 3 * _ // error cant infer types
val fun1 = 3* (_: Double)
val fun2: (Double) => Double = 3 * _

// ***** Note: specifying the type of _ is useful for turning methods into functions
// for example:
//(_:String).length is a function
//String => Int, and (_:String).substring(_:Int, _: Int) is a function


// ======== 12.5 Useful Higher-Order Functions
//  %%%map applies a function to all elements of a collection and returns the result

(1 to 9).map(0.1 * _)

// print a triangle
(1 to 9).map("*" * _).foreach(println _)
// for each is like map excpet it doesnt return a val

// %%% filter method yields all elements that match a particular cond
// how to get only even numbers in a sequence:
(1 to 9).filter(_ % 2 == 0) // 2, 4, 6, 8


// %%% reduceLeft method takes a binary function, a function with two parsms
// applies it to all elemns of a sequence, going from left to right
(1 to 9).reduceLeft(_ * _)

// ======== 12.6 Closures
// in scala you can define a function inside any scope in a package,
// in a class, or even inside another function or method

// in the body of a function you can access any vars from an enclosing scope

// your function may be called when the var is no longer in scope

val triple3 = mulBy(3)
val half = mulBy(0.5)

println(s"${triple3(14)}") // print 42 7

// first call mulBy sets the parm var factor to 3,
// that var is referenced in the body of the function which is then stored in triple
// then the parm var is popped off the runtime stack

// next mulby is called again now sets factor to 0.5, that var is ref in the body of the func and is stored in half

// each of the returned functions has its own setting for factor
// a closure consists of code together with the definitions of any nonlocal vars that the code uses


// ======== 12.7 SAM Conversions
// in scala you pass a function as a param whenever you want to tell another function what action to take

// as of scala 2.12 you can pass scala functions to java ode expecting a SAM interface
// SAM - single abstrac method( such interfaces are called functional interfaces in java)

// the conversion from scala funct to JAVA SAM interface only works for funct literals, not for vars holding functions

// ======= 12.8 Currying
// Currrying is the process of turning a function that takes two args into a funct that takes one arg
// that funct returns a funct that consumes the 2nd arg

val mul = (x: Int, y: Int) => x * y

val mulOneAtATime = (x: Int) => {(y:Int) => x * y}

// to mult 2 numbers
mulOneAtATime(6)(7)

// the result of mulOneAtATime(6) is the func (y:Int) => 6 * y

// when you use def there is a shortcut
def mulOneAtATime2(x: Int) (y: Int) = x * y

// ****** NOTE: anything that is defined with def is a method, not a funct

/// one practical use in Scala: sometimes, you can use curying for a method param so that the type inferencer has more info:
val a = Array("Hello", "World")
val b = Array("hello", "world")
a.corresponds(b) (_.equalsIgnoreCase(_))
//(String, String) => boolean, with that info the compiler can accept _.equalsIgnoreCase(_) as a shortcut for
// (a: String, b: String) => a.equalsIgnoreCase(b)

// ======== 12.9 Control Abstractions
// in scala one can model a seq of statements as a funct with no params or return value

def runInThread(block: ()=> Unit): Unit = {
  new Thread{
    override def run() { block ()}
  }.start()
}

// when you call the function you need to supply an unsightly () =>:
runInThread{() => println("Hi"); Thread.sleep(10000); println("Bye") }

// to avoid the () => in the call do:
def runInThread2(block: => Unit): Unit = {
  new Thread{
    override def run() {block}
  }.start()
}

// new call is
runInThread2 { println("Hi"); Thread.sleep(10000); println("Bye") }

// scala programmers can build control abstractions: functions that look like lang keywords
// we can implement a funct that is used exactly as a while statement

def until(condition: => Boolean) (block: => Unit): Unit = {
  if(!condition) {
    block
    until(condition) (block)
  }
}
// call by name parameter
// the param expression is not evaluated when the funct is called
// we dont want x == 0 to evaluate to false un the call to until
// intead the expresion becomes the body of a function with no args
// the funct is passed as param

// note it is curried: the first consumes the condtion, then the block as a 2nd param
var x = 10
until(x == 0){
  x -=1
  println(x)
}

// ======== 12.10 The Return Expression
// in scala you dont use a return statement to return funct values
// the return value is simply the value of the funct body

// you can use the return statement to return a val from an anon funct to an enclosed named funct
// use ful in control abstrctions:

def indexOf(str: String, ch: Char): Int = {
  var i = 0
  until ( i == str.length) {
    if(str(i) == ch) return i
    i += 1
  }
  return -1
}

// the anon function is passed to until, when the return expression is executed
// the enclosing named funct indexOf terminates and returns the given val

// if you use return inside a named funct, you need to specify the return type
// the control flow achieved with a special exxception that is thrown by the return expression
// in the anon funct, passed out of the until funct, and caught in the indexOf func


// *** Caution: if the exception is caught in a try block before it is delivered to the named func
// then the value will not be returned
