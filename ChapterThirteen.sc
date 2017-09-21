import scala.collection.mutable.ArrayBuffer
// ======== Chapter 13:  Collections
/**
The key points of this chapter are:
• All collections extend the Iterable trait.
• The three major categories of collections are sequences, sets, and maps.
• Scala has mutable and immutable versions of most collections.
• A Scala list is either empty, or it has a head and a tail which is again a list.
• Sets are unordered collections.
• Use a LinkedHashSet to retain the insertion order or a SortedSet to iterate in sorted order.
• + adds an element to an unordered collection; +: and :+ prepend or append to a sequence; ++ concatenates two collections; - and -- remove elements.
• The Iterable and Seq traits have dozens of useful methods for common operations. Check them out before writing tedious loops.
• Mapping, folding, and zipping are useful techniques for applying a function or operation to the elements of a collection.
  **/


// ======== 13.1 The Main Collection Traits
// an iterable is any collection that can yield an iterator with which you can
// access all elements in the collection

// indexed seq --> seq --> iterable
// sorted seq --> set --> iterable
// sorted map --> map --> iterable

// a seq is an ordered seq of values, such as an array or list
// a set is an unrdered collection of values
// n sortedSet the elems are always in sorted order

// a map is a set of k,v pairs
// sorted map visits the entries as sorted by the keys

// ***** NOTE: in java, both arrayList and linkedList implement common list interface
//Iterable(0xFF, 0xFF00, 0xFF0000)
//Set(Color.RED, Color.GREEN, Color.BLUE)
//Map(Color.RED -> 0xFF0000, Color.GREEN -> 0xFF00, Color.BLUE -> 0xFF)
//SortedSet("Hello", "World")

val coll = Seq{1, 3, 5, 8, 13}
val set = coll.toSet
val buffer = coll.to[ArrayBuffer]

// ***** Note: you can use the == operator to compare any seq, set, or map with another collection of the same kind


// ======== 13.2 Mutable and Immutable Collections
// scala supports both mutable and immutable collections

// immutable collection cna never change, but you can safely share a ref to it

// ***** NOTE: when you have a ref to a scala.immutable.Map, you know nobody can change the map
// if you have scaa.collection.Map then you cant change it but someone else might


// scala gives preference to immutable collections
// the compaion objects in the scala.collection package produce immutable collections
scala.collection.Map("Hello" -> 42) // produces an immutable map

// scala package and the prefef object have aliases List, Set, and Map that refer to the immutable traits

// with import scala.collection.map you can get immutable map as Map
// and mutable one as mutable.Map

// immutable sets are useful cause you can create new collections out of old ones

// if numbers is immutable then
// numbers + 9 , is a new set containing the numbers together with 9
// if 9 was already a number in the set, you get a ref to the old set


// ======== 13.3 Sequences
// a vector is the immutable equiv of an array buffer: an indexed seq with fast random access
// vectors are implemented as trees where each node has up to 32 children
/// for a vector of 1 million elements, you need 4 layers of nodes
// since 10 ^ 3 >> 2 ^ 10, 10 ^ 6, 32 ^ 4

// accessing an eleement in such a list takes 4 hops, where as in a LL it takes 500k

// a range represents an integer sequence(0, 1, 2, 3, 5.....9) or 10 20 30
// a range obj doesnt store all seq values, oly start, end and increment
// construct range objs with to and until methods



// ======== 13.4 Lists
// in scala a list is either Nil( that is empty) or an obj with a head elem with a tail that is again a list
val digits = List(4, 2)
// head is 4, tail os List(2)

// :: operator makes a new list from a given head and tail
9::List(4,2) // list 9,4,2
9:: 4 :: 2:: Nil

//:: is right-associative, lists are constructed form the end:

// in java one uses an iterator to traverse a LL, use recursion in scala
def sum(lst: List[Int]): Int =
  if(lst == Nil) 0 else lst.head + sum(lst.tail)

// or you can use pattern matching:
def sum2(lst: List[Int]): Int = lst match {
  case Nil => 0
  case h:: t => h + sum2(t) // h is lst.head , t is t.tail
    // note the :: in the operator in the 2nd pattern it destructures the list into head and tail
}

// ***** Note: Recursion works so naturally cause the tail of a list is again a list

List(9, 4, 2).sum // yields 15

// if you want to mutate list elems in place, use ListBuffer( backed by LL)
// List buffer has a tail refernce
// makes it easy to add or remove elems at either end
// dont use LL and DoubleLL and internal utableList class cause they are deprecated


//======== 13.5 Sets
// a set is a collection of distinct elements
// trying to add an existing elem has no effect
Set(2, 0, 1) + 2 // is the same as Set(2,0,1)

// unlike lists, sets do not retain the order in which the elemns are inserted
// sets are implemented as hash sets which elems are organized by the value of the hashCode
 Set(1, 2, 3, 4, 5, 6)
// if you iter over this the elems are visited in
// 5 1 6 2 3 4

// you can find elems much faster if you allow sets to reorder the elems
// finding an elm in a hash set is MUCH faster than LL or array

// a Linked hash set remembers the order in which elemns are inserted
// it keeps a LL for this purpose

val weekdays = scala.collection.mutable.LinkedHashSet("Mo", "Tu", "We", "Th", "Fr")

// if you want to iterate over elems in sorted order use sortedSet
val numbers = scala.collection.mutable.SortedSet(1,2,3,4,5)

// a bit set is a implementation of a set of non-negative integers as a sequence of bits
// the ith bit is 1 if i is present in the set
// this is efficient implementation as long as the max elem is not too large

// contains method checks whether a set contains a given value:
// the subset method checks whether all elems of a set are contained in anothe set
val digits1 = Set(1, 7, 2, 9)
digits1 contains 0 // false
Set(1,2) subsetOf digits1 // true

// the union intersect and diff methods carry out the usual set operations
// if you prefer, you can write them as |, &, &~
// you can also write union as ++ and difference as --
val primes = Set(2, 3, 5, 7)
val test = Set(1, 2, 3, 5, 7, 9)

test & primes  // Set(2,7)
test -- primes // Set(1,0


var elem = "hi"
var coll2 = Seq(1, 5, 9, 10)
// ======== 13.6 Operators for Adding or Removing Elements
//coll(k) gets the kth sequence elem/map the value for k
coll :+ elem // append elem to coll
coll + elem // append
//coll - elem // remove
coll ++ coll2 // collection that contains elemes in both collections
//elem :: lst // a list with the element prepended to lst
/// list ::: list2 // returns collection containing elem in both collections
// | is  union
// & removes elements that are the same from both


//Generally, + is used for adding an element to an unordered collection, while +: and :+ add an element to the beginning or end of an ordered collection.
var vector1 = Vector(1, 2, 3) :+ 5 // yields Vector(1,2,3,5)
// +: is right associative,

// they all return a new collectionw ithout modfying original

/**

As you can see, Scala provides many operators for adding and removing elements. Here is a summary:

1. Append (:+) or prepend (+:) to a sequence.
2. Add (+) to an unordered collection.
3. Remove with the - operator.
4. Use ++ and -- for bulk add and remove.
5. Mutations are += ++= -= --=.
6. For lists, many Scala programmers prefer the :: and ::: operators.
7. Stay away from ++: +=: ++=:.
  **/

//  Note :For lists, you can use +: instead of :: for consistency, with one exception: Pattern matching (case h::t) does not work with the +: operator.


// ======== 13.7 Common Methods
// head, last, headOption, lastOption
// tail, init first or last
// length isEmpty
// reduceLeft(op), reduceRight
// foldLeft(init)(op) foldRight(init)(op)
// reduce(op)
// fold(init)(op)
// aggregate(unit)(op,combinOP)
// sum, product, max, min
// count(predc), forall(predc), exists(predc)
// filter(predc), filterNot(predc), partition(predc)
// take(n) returns first n elems
// drop(n) everything but first n elems
// splitAt(n) the pair at both
// takeRight(n) takes last n elems
// dropRight(n) everythin but last n elemens
// slice(from, to) returns elem  in the range until to
// view(from to)
// zip(coll2), returns pairs of elems from this collection and another
// grouped(n) returns iterators of subcollections of length n
// sliding(n)
// groupBy(k) yields a map whos keys are k(x) for all elems x, the value for each key is the collection of elems with tha tkey

//mkString(before, between, after) makes a string of all elems adding the given strings before the 1st
//     between each and after last elem
//addString appends that string to string builder
// toIterable, toSeq, toIndexSeq, toArray, toBuffer, toList, toStream, toSet, toVector, toMap

// iterable stuff
// contains, containsSLice, startsWith, endsWith
// indexOf, lastIndexOf, indexofSlice, lastIndexOfSlice
// indexWhere(pre)
// prefixLength
//segement length
// padTo(n, fill)
// intersect(Seq, diff(Seq))
// reverse -- reverse seq
// sorted
// sortWith, SortBy
// permutations, combinations


// 13.8 Mapping a Function
val names = List("Peter", "Paul", "Bob")
names.map(_.toUpperCase)

// if the funct yields a collection instead of single vals, you may want to concat the results
// use flatMap
def ulcase(s: String) = Vector(s.toUpperCase(), s.toLowerCase())
names.flatMap(ulcase) // List("PETER", "peter", "PAUL", "paul", "MARY", "mary")

// The transform method is the in-place equivalent of map. It applies to mutable collections, and replaces each element with the result of a function. For example, the following code changes all buffer elements to
val buffer1 = ArrayBuffer("Peter", "Paul", "Mary")
buffer1.transform(_.toUpperCase)

// use foreach to apply for sideeffect
// The groupBy method yields a map whose keys are the function values, and whose values are the collections of elements whose function value is the given key. For example,

// ***** NOTE: It is possible to replace any while loop with a fold. Build a data structure that combines all variables updated in the loop,
// scanLeft scanRight combines folding and mapping
(1 to 10).scanLeft(0)(_+_)