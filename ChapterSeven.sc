// ======== 7.1 Packages

// packages help manage names in a larger program
// use the fully qualified names such as
// scala.collection.immutable.Map or scala.collection.mutable.Map
// use import statement to provide a shorter alias

// to add items to a package, include them in package statements
package com {
  package horstmann {
    package impatient {
      class Employee {
      }
    }
  }
}

// then the Name employee can be access anywhere as
// com.horstmann.impatient.Employee

// unlike an object or a class, a package can be defined in multiple files

// ***** note:there is no enforced relationship between the directory of the src file
// and the package

// you can also contribute to more than one package in a single file:
// the Employee.scala file may contain:
package net {
  package bigjava {
    class Counter {
    }
  }
}

// ======== 7.2 Scope Rules
// scala packages nest just like all other scopes
// you can access names from enclosing scopes

package com {
  package horstmann {
    object Utils {
      def percentOf(value: Double, rate: Double) = value * rate /100
    }
  }
}
// utils was defined in the parent package
// everything in the parent package is in scope
// no need to do com.horstmann.Utils.percentOf

// package names are relative in scala
// packages are open ended, anyone can contribute to packages at any time

// use absolute names
// ***** Note: most programmers use complete paths for package names
// without the _root_ prefix, safe as long as you avoid scala, java, com, net names
//val subordinates = new _root_.scala.collection.mutable.ArrayBuffer[Employee]

// ======== 7.3 Chained Package Clauses
// a package can contain a chan or path segment:

package com.horstmann.impatient {
  //members of com and com.horstmann are NOT visible here
  package people {
    class Person {

    }
  }
}
// such clause limits the visible members, now com.horstmann.collection.package
// would no longe rbe accessible as collection


// ======== 7.4 Top-of-File Notation
// instead of nested notation, you can have package clauses at the top of the file
// without braces:
//package com.horstmann.impatient
//package people1

class Person {

}

// preferred notation if all the code in the file belongs to the same package
// all of the file belongs to package com.horstmann.impatient.people
// but the package com.horstmann.impatient has also been opened so you can refer to it too


// ======== 7.5 Package Objects
// a package can contain classes, obj, traits, but not the definitions of func or vars
// limitation of JVM
// pakage objects address this limitation
// every package can have one package object
// define it in the parent package, and it has the same name as the child package

//package com.horstmann.impatient

/**package object people = {
  val defaultName = "John Public"
}

package people {
  class Person {
    var name = defaultName // a constant from the package
  }
}

  **/
// note that defaultName didnt need to be qualified cause its in the same package
// elsewhere you access it via:
// com.hostmann.impatient.people.defaultName

// behind the scenes the package gets compiled into a JVM class with static methods and fields
// called package.class, inside the package

// in our ex it would be a class com.horstmann.impatient.people.package
// with static field defaultName

// its a good idea to use the same naming schem for src files
// put the package object into a file
// com/hostmann/impatient/people/package.scala

// that way anyono who wants ot add func or var to a package can find the pack obj easily



// ======== 7.6 Package Visibility
// in java a class that isnt declared as public, private, or protected is visible
// in the package containing the class

// in scala you can use qualifiers to achieve the same effect
// the following is visible in its own package:

//package com.horstmann.impatient.people

/**
class Person2 {
  private[people] def description = "A person with the name $name"
}
  **/

// you can extend visibility to an enclosing package
//private[impatient] def description = s"A person with name $name"

// ======== 7.7 Imports
// imports let you use short names instead of long ones
import java.awt.Color
// you can write color instead of java.awt.color

//in scala * is a valid character for an identifier

// you can also import ll members of a package as
import java.awt._
//val c1 = RED
//val c2 = decode("#ff0000")

// this is like the import static in java


//======== 7.8 Imports can be anywhere
// in scala imports can be anywhere not just top of a file
// scope of import statement extends until the end of the enclosing block

// ======== 7.9 Renaming and hiding members
// if you want to import more than one member from a package use a selector like:
import java.awt.{Color,Font}

// the selector syntax lets you rename members:
import java.util.{HashMap => JavaHashMap}
import scala.collection.mutable._

// now JavaHashMap is a jav.util.HashMap and HashMap is a scala.collection.mutable

// The selector HashMap -> _ hides aa member instead of renaming it,
// only useful if you import others:

import java.util.{HashMap => _, _}
import scala.collection.mutable._
// now HashMap unambigiously refers to scala.collection.mutable.HashMap
// since java Hashmap is hidden



// ======== 7.10 Implicit Imports
// every scala program implicitly starts with
import java.lang._
import scala._
import Predef._

// scala import allowed to override the preceding import
// predef contains commonly used types, implicit conversions, and utility methods




