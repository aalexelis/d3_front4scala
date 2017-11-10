package com.example

final case class PhoneNumber(value: String) extends AnyVal with ValueClass[String]
object PhoneNumber extends (String => PhoneNumber)