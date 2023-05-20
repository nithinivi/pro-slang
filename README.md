# pro-slang

```
Program    ::= [rules] query
rule       ::= atom. | atom <= literals.
rules      ::= rule [rules]
query      ::= ? literals.
literal    ::= atom | not atom
literals   ::= literal [ and literal ]
atom       ::= ident | ident(terms)                     eg. diff(Y, X, 0)
term       ::= ident | numeral | ident(terms) | IDENT
terms      ::= term [, terms]
```
