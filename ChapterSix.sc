// ======== 6.1 Singletons
// scala has no static methods or fields
// instead use object construct
// object defines a single instance of a class

object Accounts{
  private var lastNumber = 0
  def newUniqueNumber() = { lastNumber += 1; lastNumber}
}

// when you need a new unique account number in your app:
Accounts.newUniqueNumber()

// the constructor of the object is executed when the object is first used
// if the obj is never used its never constructed

// an obj can have essentially all the features of a class
// --it can even extend other classes or traits
// ***** just one exception, you cant provide constructor params


// use an obj in scala when
// -as a home for utility functions or constants
// when as ingle immutable instance can be sahred efficiently
// when a single instance is req to cord some service



// ======== 6.2 Companion Objects
// in java you can write a class with both instance methods and static methods
// in scala you can achieve thisby having a class and a compaion object of the same name

class Account {
  val id = Account.newUniqueNumber()
  private var balance = 0.0
  def deposit(amount: Double) { balance += amount}
}

object Account { // companion object
  private var lastNumber = 0
  private def newUniqueNumber() = { lastNumber += 1; lastNumber}
}

// the class and the companion can access each other's private features
// they must be located in the same src file
// companion object's features are not the scope of the class

// ** TIP: in REPL, you must define the class and object together in paste mode.
// type:
// :paste, paste in both then Ctrl+D

// ***** Note: a companion object contains features that accompany a class


// ======== 6.3 Objects Extending a Class or Trait
// an obj can extend a class/ and or one or more traits
// result is an obj of a class that extends the given class/traits
// also has all the features of the object too

// create a default obj that can be shared:

abstract class UnDoableAction(val description: String){
  def undo(): Unit
  def redo(): Unit
}

object DoNothingAction extends UnDoableAction("Do Nothing") {
  override def undo() {}
  override def redo() {}
}

// do nothing cna be sahred across all places that need this default
val actions = Map("open"-> DoNothingAction, "save" -> DoNothingAction)

// ======== 6.4 The Apply Method
// its common to have obj with the apply method
// the apply method is called for the expressions of the form
// object(arg1,...,argN)

// typically the apply method returns an obj of the companion class

// array obj defines apply methods that allow array creation with expressions like
Array("Mary", "had","a", "little", "lamb")

// Caution:
// its easy to confuse Array(100) and new Array(100)
// the first one calls apply(100) which yields an array[int] with a single element 100
// the 2nd invokes the constructor this(100), the result is an array with 100 null elements

class Account1 private (val id: Int, initialBalance: Double) {
  private var balance = initialBalance
}

object Account1 {
  //def apply(initialBalance: Double) = {
    //new Account1(uniqieNumber(), initialBalance)
}

// now you can construct an account as
//val acct = Account(1000.00)

//======== 6.5 Application Objects
// instead of providing a main method do:
// object Hello extends App { println("Hello, World")


// if you need command line argsm get them from args property
object Hello extends App {
  if (args.length > 0)
    println(f"Hello ${args(0)}")
  else
    println("Hello, World!")
}

// Note: older versions of scala had an app trait for the same purpose
// the trait carried out the programs action in the static initailizer
// just use app trait instead


// ====== 6.6 Enumerations
// scala does not have enumerated types
// std library provides enumeration helper class to produce enumeratins

object TrafficLightColor extends Enumeration {
  val Red, Yellow, Green = Value
}

// each three fields are initialized to a call to value

// each cal to the value method returns a new instance of an inner class also called value

// you can also pass IDS, names or both to the Value Method:
//val Red1 = Value(0, "Stop")
// val Yellow = Value(10)
// val Green = Value("Go)

// if not specified the ID is one more than the previously assigned starting with zero
// the default name is field name

// you can now refer to the enumeration values as TrafficLightColor.Red

// the enumeration type is TrafficLightColor.Value
// TrafficLightColor is the object holding the values
object TrafficLightColor1 extends Enumeration {
  type TrafficLightColor1 = Value
  val Red, Yellow, Green = Value
}


import TrafficLightColor1._
def doWhat(color: TrafficLightColor1) = {
  if(color == Red) "Stop"
  else if(color == Yellow) "hurry up"
  else "go"
}

TrafficLightColor1.values // yuelds a set of all values:

// you can look up enumeration value by its ID or name.
TrafficLightColor1(0)
TrafficLightColor1.withName("Red")

