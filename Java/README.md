## Contents
* `Data Structures` [Binary_Search_Tree](#Binary_Search_Tree)
* `Data Structures` [Data_Structures](#Data_Structures)
* `Graphs` [Djikstra_Algorithm](#Djikstra_Algorithm)
* `GUI` [Graphic_Editor](#Graphic_Editor)
* `Data Structures` [Kth_Minimal_Element_Algoryth](#Kth_Minimal_Element_Algoryth)
* `Data Structures` [Master_Theorem](#Master_Theorem)
* `Data Structures` [Priority_Queue_using_Heap](#Priority_Queue_using_Heap)
* `Data Structures` [Sorting_Algorythms_Basics](#Sorting_Algorythms_Basics)
* `Data Structures` [Sorting_Algorythms_Intermediate](#Sorting_Algorythms_Intermediate)
* `Game` [Symulacja_Wilka_i_Zajecy](#Symulacja_Wilka_i_Zajecy)
* `Math` [Wiersz_Trojkata_Pascala_GUI](#Wiersz_Trojkata_Pascala_GUI)
* `Inheritance` [Pole_i_Obwod_Figur](#Pole_i_Obwod_Figur)
* `Inheritance` [Pole_i_Obwod_Figur_Enum](#Pole_i_Obwod_Figur_Enum)

## Topics
### Binary_Search_Tree
More info in [folder](Binary_Search_Tree).
### Data_Structures
More info in [folder](Data_Structures).
### Djikstra_Algorithm
More info in [folder](Djikstra_Algorithm).
### Graphic_Editor
More info in [folder](Graphic_Editor).
### Kth_Minimal_Element_Algoryth
More info in [folder](Kth_Minimal_Element_Algoryth).
### Master_Theorem
More info in [folder](Master_Theorem).
### Priority_Queue_using_Heap
More info in [folder](Priority_Queue_using_Heap).
### Sorting_Algorythms_Basics
More info in [folder](Sorting_Algorythms_Basics).
### Sorting_Algorythms_Intermediate
More info in [folder](Sorting_Algorythms_Intermediate).
### Symulacja_Wilka_i_Zajecy
More info in [folder](Symulacja_Wilka_i_Zajecy).
### Wiersz_Trojkata_Pascala_GUI
More info in [folder](Wiersz_Trojkata_Pascala_GUI).

### Pole_i_Obwod_Figur
Program powinien liczyć pole i obwód następujących figur geometrycznych: okrąg, kwadrat, prostokąt, romb, pięciokąt foremny, sześciokąt foremny.<br/>
Do tego celu należy stworzyć hierarchię obsaugujących poszczególne rodzaje figur. Korzeniem tej hierarchii powinna być abstrakcyjna klasa Figura, zawierająca abstrakcyjne metody do obliczania obwodu oraz pola danej figury. Po klasie `Figura` powinna dziedziczyć abstrakcyjna klasa `Czworokat` oraz klasy: `Okrag`, `Pieciokat`, `Szesciokat`. Po klasie `Czworokat` klasy: `Kwadrat`, `Prostokat`, `Romb`. Stwórz odpowiednie metody w klasach potomnych, które będą obliczały obwód i pole w sposób specyczny dla danej figury. W lini polece« można podać następujące rodzaje figur geometrycznych (o - okrąg, c-czworokąt, p-pięciokąt, s-sześciokąt) oraz ich parametry, przy czym: okrąg posiada jeden parametr: promień, czworokąt posiada pięć parametrów: bok1, bok2, bok3, bok4, kąt, pięciokąt i sze±ciokąt foremny: bok. Program powinien stworzyć obiekty dla tych figur, zapisać te obiekty w jednej tablicy, a następnie wypisać dla poszczególnych obiektów pole i obwód figury.<br/>
Należy zadbać o odpowiednią obsługę błędów uwzględniając brakujące parametry, nieprawidłowe wartości kątów, itd.

### Pole_i_Obwod_Figur_Enum
Przerobić `Pole_i_Obwod_Figur.java` w następujący sposób:
* Należy stworzyć klasę Figury zawierającą dwie wewnętrzne klasy typu enum
* Pierwsza klasa powinna zawierać elementy obsługujące figury z jednym parametrem (okrąg, kwadrat, pięciokąt, sze±ciokąt)
* Druga klasa powinna zawiera¢ elementy obsługujące figury z dwoma parametrami (prostokąt, romb)
* Z każdym elementem enum powinny być związane metody `ObliczPole` oraz `ObliczObwod`. Metody te powinny być specyczne dla  poszczególnych typów figur.
