# Pro-slang

Pro-Slang is a prolog interpreter written in java. It is a port of prolog interpreter written by L. Allison of Monash University. Full article about the same is available at https://users.monash.edu/~lloyd/tildeLogic/Prolog.toy/


## BNF
```
Program    ::= [rules] query

rules      ::= rule [rules]
rule       ::= atom. | atom <= literals.

terms      ::= term [, terms]
term       ::= ident | numeral | ident(terms) | IDENT

query      ::= ? literals.
literals   ::= literal [ and literal ]
literal    ::= atom | not atom

atom       ::= ident | ident(terms)

```

## Examples

```
parent(chacko, wilson).
parent(wilson, kuttan).

grandfather(X, Y) <= parent(X, Z) and parent(Z,Y).

?grandfather(chacko, kuttan).
```

```
yes
```
---
```
witch(X)  <= burns(X) and female(X).
burns(X)  <= wooden(X).
wooden(X) <= floats(X).
floats(X) <= sameweight(duck, X).

female(girl).
sameweight(duck,girl).

? witch(girl).
```

```
yes
```

 Knight Sir Bedevere provides complete reasoning for above logic in this [video](https://www.youtube.com/watch?v=iGx1hiSJbCo)


# Acknowledgement

- This was motivated and influenced by the guidance of my guru [Praseed Pai](https://github.com/praseedpai)
- Knowledge to port from pascal to java where largely from  https://github.com/sinsinan/fslang by [Sinsinan](https://github.com/sinsinan)
