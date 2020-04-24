import java.util.*;

public class WierszTrojkataPascala {
    long[] wiersz;
    int max;

    public WierszTrojkataPascala(int n){
        this.max = n;
        this.wiersz = new long[n+1];
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

    public void wspolczynnik(int m){
        //sprawdzenie czy m się mieści w zakresie
        if((m < 0) || (m > max)){
            System.out.println(m + " - liczba spoza zakresu");
            return;
        }
        //wypisanie wartosci z tablicy
        long wartosc = wiersz[m];
        System.out.println(m + " - " + wartosc);
    }
}