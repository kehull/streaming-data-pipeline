package com.labs1904.hwe.exercises

object StretchProblems {

  /*
  Checks if a string is palindrome.
 */
  def isPalindrome(s: String): Boolean = {
    val len = s.length
    val half = len / 2
    if (len % 2 != 0) {
      false
    }
    else if (s.substring(0, half) != s.takeRight(half).reverse) {
      false
    }
    else {
      true
    }
  }

  /*
For a given number, return the next largest number that can be created by rearranging that number's digits.
If no larger number can be created, return -1
 */
  def getNextBiggestNumber(i: Integer): Any = {
    val digits = i.toString.toList
    val intDigits = digits.map(_.toString.toInt)
    if (intDigits == intDigits.sortWith(_ > _)) {
      -1
    }
    else {
      val perm = intDigits.permutations.toList
      var compiledPermutations = List[Int]()
      for (p<-perm){compiledPermutations = compiledPermutations :+ p.mkString.toInt}
      val sortedPermutations = compiledPermutations.sortWith(_ > _)
      sortedPermutations(sortedPermutations.indexOf(intDigits.mkString.toInt)-1)
//      println(sortedPermutations)
//      for (p <- intDigits.permutations) {
      }
    }



}