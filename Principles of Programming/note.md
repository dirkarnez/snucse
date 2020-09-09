Week 1, Tue
========
> 2020-09-01

1시간 50분 수업인데, 실습 진행이 어려워서 가능하면 1시간 안에 끝내고, 늦어도 1.5시간 수업으로 끝내겠다. 다음부턴 수업자료를 미리 올리겠다. 조교 메일은 <pp@sf.snu.ac.kr>. 수업시간에는 노트북 지참 필수. 필요하면 학과에서 노트북을 빌려줘요. 질문은 채팅이나 마이크로 언제든지 편하게 해주세용.

출석 5%, 과제 25%, 중간 30%, 기말 40%. 모든 과제 채점은 자동채점기다. 과제는 보통 총 4~5개에 Term project 한번. 시험은 프로그래밍으로 치룬다. 컴파일이 되는지 안되는지는 실시간으로 알려주지만, 점수는 공개하지 않는다. 시험 포맷은 숙제랑 비슷하고, 시간제한은 없다. 기본적으로 4시간 시험인데, 학생이 더 하고싶다고 하면 조교가 설득은 하겠지만, 시간은 무한히 준다. 여러번 제출이 가능하다.

조교 추천도서: [스칼라로 배우는 함수형 프로그래밍](https://www.yes24.com/Product/Goods/16969986), [Functional Programming in Scala (ISBN: 9781617290657)](https://books.google.co.kr/books/about/Functional_Programming_in_Scala.html?id=bmTRlwEACAAJ)

후 엠 아이: 카이스트에서 학부하고 병특한다음 영국으로 유학가서 박사했다. 고딩때엔 1994 IMO 동메달. 소프트웨어 검증, 로레벨 언어 시맨틱 (C, C++, LLVM, Rust) Relaxed-memory 컨시스턴시 관련 연구.

요즘은 구글에서 Protected KVM같은 연구를 하고있다. OS도 접근 못하는 메모리영역을 만들기. 몇년 내로 안드로이드에 모두 들어갈듯. 요즘은 삼성에서 커널 패치를 직접 안하고, 구글에서 제네릭 커널 이미지를 배포하는데, 이거덕분에 구글에서 신기술을 적용하면 여러 안드로이드에서 쓰기 쉬워짐. 요즘은 정적검증으로 Protected KVM의 안정성을 증명하는 일을 하고있음.

대부분의 컴파일러 최적화는 컨커런시를 고려하지 않고 싱글코어 기준으로 개발되었음. 90년대 들어서 멀티코어가 나오니까 그런 최적화들중 올바르게 동작하지 않는것이 많았음. 이 잘못된 최적화를 바로잡고, 컨커런시를 위한 최적화를 바로잡는것이 오래 연구되었었는데, 여기서 나온 연구중 하나가 Relaxed-Memory 컨커런시.

POPL이랑 PLDI가 PL에선 탑 2 컨퍼런스. <https://csrankings.org> 참고하세용. Software Foundation 랩에서 2011년부터 2020년까지 7개의 PLDI 논문, 7개의 POPL 논문 퍼블리시 (전세계 8등), 10년동안 매해 POPL이나 PLDI중 한곳에 계속 논문을 냄 (전세계 3등), distinguished paper award 두번받음 (PLDI 2017, POPL 2020)

프원 수업에서 가르친는건 제가 연구하는 분야는 아니에용. 그리고 제가 controversial한걸 가르칠수도 있어용. 여러분들도 제가 가르쳤다고 제가 한 말을 모두 믿지 마시고, 본인이 직접 경험하고 판단하세용.

수업에서 크게 세개를 합니다.

1. Functional Programming with Function Applications
2. OOP
3. Type Classes for Interface

교수님 피셜: 제가 해보니까 타입 클래스가 모든 면에서 OOP보다 나아요.

교수님 피셜: 이론적으로 핵심만 놓고 보면 OOP는 장점이 없는것같아요.

제 생각엔 타입클래스가 더 좋다고 주장을 할거고.

### Imperative vs Functional programming
##### 명령적
- 메모리 읽기쓰기에 의한 계산
- 컴퓨터 수준에서 How to do를 구체적으로 기술
- 효율적인 코드를 쓰기 쉽다
```c
sum = 0;
i = n;
while (i > 0) {
  sum = sum + i;
  i = i - 1;
}
```

##### 뻥셔널
- 함수 apply, 함수 조합에 의한 계산
- 계산 결과 관점에서 what to do만 기술
- 안전한 코드를 쓰기 쉽다, 훨씬 빠르게 짤 수 있음
- 컴파일러가 똑똑하고 최적화를 잘 해줘야함
```scala
def sum(n) = 
  if (n <= 0)
    0
  else
    n + sum(n-1)
```

알고리즘에 따라 Imperative로 기술하는게 훨씬 효율적이고 간단할때에도 많다. 그런데 여러분 Imperative 맨날 하잖아요? 그러니까 이번 수업에선 안할게용.

Rust가 굉장히 Fancy한 언어이니 여러분 알아보세요. C와 같은 성능을 내면서 쉽게 프로그래밍을 할 수 있어요. Rust로 OS도 짤 수 있고, 브라우저도 만들어요.

많은 언어들이 imperative 스타일과 functional 스타일을 모두 지원한다.

- 주로 명령형: Java, js, C++, Python, Rust, ...
- 주로 함수형: OCaml, SML, Lisp, Scheme
- 중간: Scala
- Purely function하지만 모나드로 명령형 지원: Haskell

교수님 피셜: Rust랑 잘 맞는 사람이 프로그램을 잘 짜는 사람일 가능성이 크죠. 미래에 면접을 프로그램을 러스트로 짜보세요 이렇게 할수도 있어요.

수업에서 스칼라 쓰는 이유: 명령형 & 함수형 스타일 모두 지원, OOP와 타입클래스 지원, 자바 호환됨

### OOP
내가 잘 아는 그것,,

요즘은 Interface와 구현을 나누는것이 중요한 대세에요.

오늘 썰이 좀 길어지는데 첫수업이니 썰을 좀더 풀겠습니다. Interface는 디테일은 숨기고 what to do만 기술한것이라고 볼 수 있잖아요? Type level에서 what to do만 기술한 interface만 가지고도 구현을 할 수 있는데 이 추상이 아주 중요해요. 여러분이 자동차의 동작원리를 다 알아야만 차 운전 할 수 있는거 아니잖아요? 현대차 포르셰 테슬라 다 내부는 전혀 다르지만 인터페이스가 같으니 여러분이 다 운전할 수 있는거잖아요? 이것이 코드 재활용성을 굉장히 키워요.

요즘 큰 회사에서 하는 SOA(서비스중심설계)같은걸 보면, 팀끼리 인터페이스만 공유하고 내부 코드를 절대 공유 못하게 막아요. 이렇게 인터페이스만 공유하며 프로그래밍하는것이, 큰 스케일인 서비스 설계에서도 일어나고, 작은 스케일인 코드 수준에서도 일어나요.

혹자는 컴퓨터과학이 머냐 했을때 "Abstraction of mechanization"라고 대답하기도 해요. 컴퓨터는 점점 복잡해지고 할수있는 일이 많아지는데, 이걸 어떻게 추상화하고 쉽게 짤 수 있게 만드느냐 라고 이야기하는데 맞는말같아요.

파트3에는 OOP를 매번 까면서 타입클래스에 대해 배울겁니다. 왜 OOP가 안좋은지. 제 개인적인 의견이 조금 들어가있을 수 있습니다.

Q&A

- Q: 저기 스칼라 예제코드에 있는 Unit이 먼가요?
- A: 가능한 값이 하나뿐인 타입

출석은 본인이 알아서 하세용. 온라인인 학생이 너무 적으면 할수도 있긴한데, 온라인 수업이라 어차피 접속해놓고 놀수도 있는거고.. 알아서 하세용

&nbsp;

Week 1, Thu
========
> 2020-09-03

## Part 1. Functional Programming with Function Applications
### Values, Expressions, Names
- Value: 값
- Type: 값의 집합
- Expressions: Values, names, primitive operations 등 수식의 조합
- Name Binding: Expression을 Name에 결합하는것

```scala
def a = 1 + (2 + 3)
def b = 3 + a * 4

// 모든 expression에 타입을 붙일 수 있음
def a = 1 + ((2 + 3): Int)
```

### Evaluation
실행은 아래의 순서로 이뤄짐

1. 가장 바깥쪽부터 안쪽으로, 수식에 있는 연산자와 name들에 대해 아래를 적용시킴
2. (name) name들을 모두 바인딩된 expression으로 바꿔줌
3. (name) expression을 실행함
4. (operator) operator들의 operand들을 evaluate함
5. (operator) operands들을 operator에 적용시킴

### Functions and Substitution
```
def f(x: Int): Int = x + a
```

- Function: 파라미터가 있는 expression, 함수도 name에 바인딩할 수 있음
- Evaluation by substitution: operand들을 맨 왼쪽부터 차례대로 evaluate 한 뒤, 

Polymorphic function이 나중에 나옴

### Simple Recursion
함수 X의 정의 안에서 X를 호출할 수 있음

```scala
def sum(n: Int): Int =
  if (n <= 0)
    0
  else
    n + sum(n-1)
```

&nbsp;

Week 2, Tue
========
> 2020-09-08

### Termination/Divergence
끝나지 않는 연산

- Termination: 하나의 값으로 reduce되는 expression
- Divergence: 영원히 reduce하는 expression

```scala
def loop: Int = loop
```

### Evaluation strategy: Call-by-value, Call-by-name
```scala
def foo(value: Int, name: =>Int) = value

foo(1+2, loop) // 계산이 끝남
foo(loop, 1+2) // 무한루프에 들어감
```

### Name binding strategy
```scala
// Call-by-value
val x = 1 + 2 + 3
val foo = loop

// Call-by-name
// Bind to the name without evaluating it, mostly used to define functions
def y = 1 + 2 + 3
def bar = loop
```

### Conditional expressions
```scala
if (cond) exp1 else exp2

// Rewrite rules:
if (true) exp1 else exp2 //=> exp1
if (false) exp1 else exp2 //=> exp1
```

### Boolean expression
```scala
true, false, !exp

// e1은 call by value, e2는 call by name
e1 && e2, e1 || e2

e1 <= e2, ...
```

### Practice
```scala
def sqrt(x: Double) = {
  def sqrtIter(guess: Double, x: Double): Double = {
    if (isGoodEnough(guess, x)) guess
    else sqrtIter(improve(guess, x), x)
  }

  def isGoodEnough(guess: Double, x: Double) = {
    val ratio = guess * guess / x
    0.999 < ratio && ratio < 1.001
  }

  def improve(guess: Double, x: Double) =
    (guess + x/guess) / 2

  sqrtIter(1, x)
}

sqrt(2)
```

스칼라는 재귀함수의 경우 반환형을 명시하도록 강제하고있다. 프로그래머가 알아보기 쉬우라고 강제하는것이다. ML이나 하스켈 등 반환형 안써도 타입추론 잘 되는 언어들은 많음.