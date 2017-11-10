package com.example

final case class Email(value: String) extends AnyVal with ValueClass[String]
object Email extends (String => Email)