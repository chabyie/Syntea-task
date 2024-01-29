# TridicKnih - Program na řazení knih

TridicKnih je program napsaný v jazyce Java, který slouží k řazení knih na základě roku vydání z XML souboru.
Program načítá XML soubor obsahující informace o knihách a třídí je do dvou CSV souborů: "knihy_stare.csv"
pro knihy vydané před stanoveným rokem zlomu a "knihy_nove.csv" pro knihy vydané v daném roce zlomu nebo později.

## Použití

1. **Kompilace Java kódu:**
   ```bash
   javac TridicKnih.java
   ```

2. **Spuštění programu**
   ```bash
   java TridicKnih <cesta_k_knihy.xml> <rok_zlomu>
   ```
   Příklad:
   ```bash
   java TridicKnih cesta/k/tvemu/knihy.xml 2010
   ```
## Poznámky
"knihy_stare.csv" a "knihy_nove.csv" se generují do stejné složky, ve které je TridicKnih.java.
