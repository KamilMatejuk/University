public class WierszTrojkataPascala {
    long[] wiersz;

    public WierszTrojkataPascala(int n){
        this.wiersz = new long[n+1];
        //wypełnij tablice wierszem z trójkąta Pascala
        for(int i=0; i<=n/2; i++){
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
}