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

Note
---
- [rules] meansrules are optional
-
