package utils

object Validator {
  def isValideEmail(s: String): Boolean = {
    val emailRegex = """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r
    s match {
      case s if !minLength(s, 1) => false
      case s if emailRegex.findFirstMatchIn(s).isDefined => true
      case _ => false
    }
  }

  def isValidPhone(s: String): Boolean = {

    /*
     See https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
     */
    val phoneRegex =
      """^(\+(\d{1,3}))(\d{10})(?: *x(\d+))?\s*$""".r

    def sanitizedString(s: String) = s.replace("(0)", "").replace(" ", "").replace("(", "").replace(")", "").replace("-", "").replace("/", "")

    s match {
      case s if !minLength(s, 1) => false
      case s if phoneRegex.findFirstMatchIn(sanitizedString(s)).isDefined => true
      case _ => false
    }
  }

  def isValidDate(s: String): Boolean = {
    !minLength(s, 1)
  }

  def maxLength(s: String, length: Int): Boolean = s match {
    case null => false
    case s if s.trim.isEmpty => false
    case s if s.size <= length => true
    case _ => false
  }

  def minLength(s: String, length: Int): Boolean = s match {
    case null => false
    case s if s.trim.isEmpty => false
    case s if s.size >= length => true
    case _ => false
  }
}
