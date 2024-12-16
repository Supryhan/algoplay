package problemslab.taglessfinal

object PlayWithTagless extends App {
  sealed trait IExp
  final case class Lit(i: Int) extends IExp
  final case class Neg(e: IExp) extends IExp
  final case class Add(r: IExp, l: IExp) extends IExp

  sealed trait Tree
  final case class Leaf(s: String) extends Tree
  final case class Node(s: String, ts: List[Tree]) extends Tree

  sealed trait NCtx
  final case object PosCtx extends NCtx
  final case object NegCtx extends NCtx

  sealed trait FCtx[T]
  final case class LCA[T](t: T) extends FCtx[T]
  final case class NonLCA[T]() extends FCtx[T]

  trait Exp[T] {
    def lit(i: Int): T
    def neg(t: T): T
    def add(l: T, r: T): T
  }

  object Exp {
    implicit val expInt: Exp[Int] = new Exp[Int] {
      def lit(i: Int): Int = i
      def neg(t: Int): Int = -t
      def add(l: Int, r: Int): Int = l + r
    }

    implicit val expString: Exp[String] = new Exp[String] {
      def lit(i: Int): String = i.toString
      def neg(t: String): String = s"(-$t)"
      def add(l: String, r: String): String = s"($l + $r)"
    }

    implicit val expTree: Exp[Tree] = new Exp[Tree] {
      def lit(i: Int): Tree = Node("Lit", List(Leaf(i.toString)))
      def neg(t: Tree): Tree = Node("NegCtx", List(t))
      def add(l: Tree, r: Tree): Tree = Node("Add", List(l , r))
    }

    implicit def negDownExp[T](implicit e: Exp[T]): Exp[NCtx => T] = new Exp[NCtx => T] {
      def lit(i: Int): NCtx => T = {
        case PosCtx => e.lit(i)
        case NegCtx => e.neg(e.lit(i))
      }

      def neg(x: NCtx => T): NCtx => T = {
        case PosCtx => x(NegCtx)
        case NegCtx => x(PosCtx)
      }

      def add(l: NCtx => T, r: NCtx => T): NCtx => T =
        c => e.add(l(c), r(c))
    }

    implicit def flattenExp[T: Exp]: Exp[FCtx[T] => T] = new Exp[FCtx[T] => T] {
      val e = implicitly[Exp[T]]

      def lit(i: Int): FCtx[T] => T = {
        case LCA(t) => e.add(t, e.lit(i))
        case NonLCA() => e.lit(i)
      }

      def neg(x: FCtx[T] => T): FCtx[T] => T = {
        case NonLCA() => e.neg(x(NonLCA()))
        case LCA(l) => e.neg(e.add(l, x(NonLCA())))
      }

      def add(l: FCtx[T] => T, r: FCtx[T] => T): FCtx[T] => T =
        c => l(LCA(r(c)))
    }

    implicit val wrapping: Exp[Wrapped] = new Exp[Wrapped] {
      def lit(i: Int) = new Wrapped {
        def value[T](implicit e: Exp[T]): T = e.lit(i)
      }
      def neg(t: Wrapped) = new Wrapped {
        def value[T](implicit e: Exp[T]): T = e.neg(t.value[T])
      }
      def add(l: Wrapped, r: Wrapped) = new Wrapped {
        def value[T](implicit e: Exp[T]): T = e.add(l.value[T], r.value[T])
      }
    }
  }

  object ExpSyntax {
    def lit[T](i: Int)    (implicit e: Exp[T]): T = e.lit(i)
    def neg[T](t: T)      (implicit e: Exp[T]): T = e.neg(t)
    def add[T](l: T, r: T)(implicit e: Exp[T]): T = e.add(l, r)
  }
  import ExpSyntax._

  object ExpFancySyntax {
    def lit[T](i: Int)(implicit e: Exp[T]): T = e.lit(i)

    implicit class ops[T](t: T)(implicit e: Exp[T]) {
      def unary_- : T = e.neg(t)
      def +(r: T): T = e.add(t, r)
    }

    def tf1[T: Exp]: T =
      lit(8) + (-(lit(1) + lit(2)))
  }

  def lf1[T: Exp]: T = add(lit(8), neg(add(lit(1), lit(2))))

  println(lf1[String])
  println(lf1[Int])
  println

  trait Mult[T] {
    def mul(l: T, r: T): T
  }

  object Mult {
    implicit val multInt: Mult[Int] = new Mult[Int] {
      def mul(l: Int, r: Int): Int = l * r
    }

    implicit val multString: Mult[String] = new Mult[String] {
      def mul(l: String, r: String): String = s"$l * $r"
    }

    implicit val multTree: Mult[Tree] = new Mult[Tree] {
      def mul(l: Tree, r: Tree): Tree = Node("Mult", List(l , r))
    }

    implicit def negDownMult[T](implicit e: Mult[T]): Mult[NCtx => T] = new Mult[NCtx => T] {
      def mul(l: NCtx => T, r: NCtx => T): NCtx => T = {
        case PosCtx => e.mul(l(PosCtx), r(PosCtx))
        case NegCtx => e.mul(l(PosCtx), r(NegCtx))
      }
    }
  }

  object MultSyntax {
    def mul[T](l: T, r: T)(implicit e: Mult[T]): T = e.mul(l, r)
  }
  import MultSyntax._

  def lfm1[T: Exp : Mult] = add(lit(7), neg(mul(lit(1), lit(2))))
  def lfm2[T: Exp : Mult] = mul(lit(7), lf1)

  println(lfm1[String])
  println(lfm1[Int])
  println

  println(lfm2[String])
  println(lfm2[Int])
  println

  val lf1Tree = lf1[Tree]
  println(lf1Tree)
  println

  type ErrMsg = String

  def readInt(s: String): Either[ErrMsg, Int] = {
    import scala.util.{Try, Success, Failure}
    Try(s.toInt) match {
      case Success(i) => Right(i)
      case Failure(f) => Left(f.toString)
    }
  }

  trait Wrapped {
    def value[T](implicit e: Exp[T]): T
  }

  def fromTree0[T: Exp](t: Tree): Either[ErrMsg, T] = ???

  def fromTree[T: Exp](t: Tree): Either[ErrMsg, T] = {
    val e = implicitly[Exp[T]]
    t match {
      case Node("Lit", List(Leaf(n))) =>
        readInt(n).right.map(e.lit)

      case Node("NegCtx", List(t)) =>
        fromTree(t).right.map(e.neg)

      case Node("Add", List(l , r)) =>
        for(lt <- fromTree(l).right; rt <- fromTree(r).right)
          yield e.add(lt, rt)

      case _ => Left(s"Invalid tree $t")
    }
  }

  // def fromTree1(t: Tree): Either[ErrMsg, Wrapped] = t match {
  //   case Node("Lit", List(Leaf(n))) =>
  //     readInt(n).right.map(x => new Wrapped { def value[T: Exp]: T = lit(x) })

  //   case Node("NegCtx", List(t)) =>
  //     fromTree1(t).right.map(x => new Wrapped { def value[T: Exp]: T = neg(x.value) })

  //   case Node("Add", List(l , r)) =>
  //     for(lt <- fromTree1(l).right; rt <- fromTree1(r).right)
  //     yield new Wrapped { def value[T: Exp]: T = add(lt.value, rt.value) }

  //   case _ => Left(s"Invalid tree $t")
  // }

  fromTree[Wrapped](lf1Tree) match {
    case Left(err) =>
      println(err)

    case Right(t) =>
      println(t.value[Int])
      println(t.value[String])
  }

  def fromTreeExt[T]
  (recur: => (Tree => Either[ErrMsg, T]))
  (implicit e: Exp[T])
  : Tree => Either[ErrMsg, T] = {
    tree => tree match {
      case Node("Lit", List(Leaf(n))) =>
        readInt(n).right.map(e.lit)

      case Node("NegCtx", List(t)) =>
        recur(t).right.map(e.neg)

      case Node("Add", List(l , r)) =>
        for(lt <- recur(l).right; rt <- recur(r).right)
          yield e.add(lt, rt)

      case t => Left(s"Invalid tree $t")
    }
  }

  def fix[A](f: (=> A) => A): A = f(fix(f))
  def fromTree2[T: Exp](t: Tree): Either[ErrMsg, T] = fix(fromTreeExt[T] _)(t)

  println(fromTree2[Int](lf1Tree))
  println(fromTree2[String](lf1Tree))

  def fromTreeExt2[T]
  (recur: => (Tree => Either[ErrMsg, T]))
  (implicit e: Exp[T], m: Mult[T])
  : Tree => Either[ErrMsg, T] = {
    case Node("Mult", List(l , r)) =>
      for(lt <- recur(l).right; rt <- recur(r).right)
        yield m.mul(lt, rt)

    case t => fromTreeExt(recur).apply(t)
  }

  def fromTree3[T: Exp : Mult](t: Tree): Either[ErrMsg, T] = fix(fromTreeExt2[T] _)(t)

  val lfm2Tree = lfm2[Tree]
  println(lfm2Tree)
  println

  println(fromTree3[Int](lf1Tree))
  println(fromTree3[String](lf1Tree))
  println(fromTree3[Int](lfm2Tree))
  println(fromTree3[String](lfm2Tree))
  println

  def pushNeg[T](e: NCtx => T): T = e(PosCtx)

  pushNeg(lf1[NCtx => Int])
  println(lf1[NCtx => String].apply(PosCtx))
  println(lfm1[NCtx => String].apply(PosCtx))
  println(lfm2[NCtx => String].apply(PosCtx))
  println

  println(lf1[FCtx[NCtx => String] => NCtx => String].apply(NonLCA()).apply(PosCtx))
  println

  object Bijection {
    implicit def initialize: Exp[IExp] = new Exp[IExp] {
      def lit(i: Int): IExp = Lit(i)
      def neg(t: IExp): IExp = Neg(t)
      def add(l: IExp, r: IExp): IExp = Add(l, r)
    }

    def finalize[T](i: IExp)(implicit e: Exp[T]): T = i match {
      case Lit(l) => e.lit(l)
      case Neg(n) => e.neg(finalize[T](n))
      case Add(l, r) => e.add(finalize[T](l), finalize[T](r))
    }
  }
}