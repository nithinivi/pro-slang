# pro-slang

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

## Example

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


Note
---
- [rules] meansrules are optional
-
