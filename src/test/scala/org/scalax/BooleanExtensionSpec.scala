package org.scalax

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}


class BooleanExtensionSpec extends FlatSpec with Matchers {

  import BooleanExtension._

  "BooleanExtension" should "evaluate ifTrue" in {
    2 > 1 ifTrue "greater" orElse "lower" should be("greater")
  }

  it should "evaluate ifFalse" in {
    2 > 1 ifFalse "greater" orElse "lower" should be("lower")
  }

  it should "evaluate ifNull" in {
    val someValue: Any = null

    someValue ifNull "isNull" orElse "isNotNull" should be("isNull")
  }

  it should "evaluate ifNotNull" in {
    val someValue: Int = 23

    someValue ifNotNull (_ + " isNotNull") orElse "isNull" should be("23 isNotNull")
  }
}


