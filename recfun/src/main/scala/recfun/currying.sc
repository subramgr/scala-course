def product1(f: Int => Int): (Int, Int) => Int = {
  def productF(a: Int, b:Int): Int = {
    if (a > b)
      1
    else
      f(a) * productF(a+1, b)
  }
  productF
}

def product(f: Int => Int)(a: Int, b:Int) : Int = {
  if (a > b)
    1
  else
    f(a) * product(f)(a+1, b)
}

def factorial(a: Int): Int ={
  if (a == 0) 1 else a * factorial(a - 1)
}

def fact(a: Int) = product(x => x)(1, a)

fact(4)

product(x => x * x)(3, 7)

def iter(f: (Int, Int) => Int, initial: Int)(g: Int => Int)(a: Int, b: Int): Int = {
  if (a > b)
    initial
  else
    f(g(a), iter(f, initial)(g)(a+1, b))
}


iter((a, b) => a * b, 1)(x => x * x)(3, 7)