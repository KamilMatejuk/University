/* Kamil Matejuk */
#include <iostream>
using namespace std;

int main()
{
    int n,k;
    cin >> n >> k;
    int nsilnia=1,ksilnia=1,nksilnia=1;
    for(int i=1; i<=n; i++){
        nsilnia = nsilnia*i;
    }
    for(int i=1; i<=k; i++){
        ksilnia =  ksilnia*i;
    }
    for(int i=1; i<=(n-k); i++){
        nksilnia = nksilnia*i;
    }
    int wynik = nsilnia/(ksilnia*nksilnia);
    cout << wynik;
    return 0;
}
