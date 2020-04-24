/* Kamil Matejuk */
#include <iostream>
using namespace std;

/* Stwórz publiczną klasę WierszTrojkataPascala posiadającą jeden konstruktor
WierszTrojkataPascala(int n), który tworzy tablicę liczb odpowiedniego rozmiaru i oblicza 
w niej n-ty wiersz trójkąta Pascala. 
Następnie zaimplementuj publiczną metodę wspolczynnik(int m) zwracającą wartość m-tego 
elementu wiersza trójkąta Pascala. Funkcja ta powinna prawidaowo dziaaa¢ dla liczb od 0 do n.

Dodaj odpowiednie własne wyjątki dla tej klasy, np. w przypadku użycia liczb ujemnych czy
większych od argumentu konstruktora. */

class WierszTrojkataPascala {
    public: int max;
    public: long wiersz[100];

    public: WierszTrojkataPascala(int n){
        max = n;
        for(int i=0; i<=n/2; i++){
            //wypełnij tablice wierszem z trójkąta Pascala
            int a = n;
            wiersz[i] = 1;
            while(a > n-i){
                wiersz[i] = wiersz[i]*a;
                a--;
            }
            a = i;
            while(a > 1){
                wiersz[i] = wiersz[i]/a;
                a--;
            }
        }
        for(int i=n; i>=n/2; i--){
            wiersz[i] = wiersz[n-i];
        }
    }

    public: void wspolczynnik(int m){
        //sprawdzenie czy m się mieści w zakresie
        if((m < 0) || (m > max)){
            cout << m << " - liczba spoza zakresu\n";
            return;
        }
        //wypisanie wartosci z tablicy
        long wartosc = wiersz[m];
        cout << wartosc << "\n";
    }

};

int main(int argc, char *argv[]){
    WierszTrojkataPascala *W;
    //sprawdzenie czy nr wiersza jest prawidłowy (liczba naturalna)
    try{
        int n = stoi(argv[1]);
        if(n >= 0){
            W = new WierszTrojkataPascala(n);
        } else {
            cout << n << " - nieprawidłowy nr wiersza\n";
            return 0;
        }
    }
    catch(const exception& e){
        cout << argv[1] << " - nieprawidłowy nr wiersza\n";
    }
    //sprawdzenie czy dane są liczbami
    for(int i=2; i<argc; i++){
        try{
            int n = stoi(argv[i]);
            //wyznacz wspolczynnik
            W->wspolczynnik(n);
        }
        catch(const exception& e){
            cout << argv[i] << " - nieprawidłowa dana\n";
        }
        
    }

    return 0;
}