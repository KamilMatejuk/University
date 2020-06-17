/* Kamil Matejuk */
#include <stdio.h>
#include <math.h>
#include <string.h>
#include <string>
#include <vector>
using namespace std;


class Figura {
    public: virtual void obwod()=0;
    public: virtual void pole()=0;

    public: virtual ~Figura(){};
};

class Kolo: public Figura{
    private: int promien;
    public: Kolo(int r){
        promien = r;
    }

    void pole(){
        printf("Pole Koła: ............ %.5f\n", promien*promien*3.14159);
    }
    void obwod(){
        printf("Obwód Koła: ........... %.5f\n", 2*promien*3.14159);
    }
    public: ~Kolo(){};
};
class Pieciokat: public Figura{
    private: int bok;
    public: Pieciokat(int b){
        bok = b;
    }

    void pole(){
        printf("Pole Pięciokąta: ...... %.5f\n", bok*bok*1.72);
    }
    void obwod(){
        printf("Obwód Pięciokąta: ..... %d\n", bok*5);
    }
    public: ~Pieciokat(){};
};
class Szesciokat: public Figura{
    private: int bok;
    public: Szesciokat(int b){
        bok = b;
    }

    void pole(){
        printf("Pole Sześciokata: ..... %.5f\n", bok*bok*2.59805);
    }
    void obwod(){
        printf("Obwód Sześciokata: .... %d\n", bok*6);
    }
    public: ~Szesciokat(){};
};
class Czworokat: public Figura{};

class Kwadrat: public Czworokat{
    private: int bok;
    public: Kwadrat(int b){
        bok  = b;
    }

    void pole(){
        printf("Pole Kwadratu: ........ %d\n", bok*bok);
    }
    void obwod(){
        printf("Obwód Kwadratu: ....... %d\n", bok*4);
    }
    public: ~Kwadrat(){};
};
class Prostokat: public Czworokat{
    private: int bok1,bok2;
    public: Prostokat(int b1, int b2){
        bok1 =  b1;
        bok2 = b2;
    }

    void pole(){
        printf("Pole Prostokąta: ...... %d\n", bok1*bok2);
    }
    void obwod(){
        printf("Obwód Prostokąta: ..... %d\n", 2*(bok1+bok2));
    }
    public: ~Prostokat(){};
};
class Romb: public Czworokat{
    private: int bok,kat;
    public: Romb(int b, int k){
        bok = b;
        kat = k;
    }

    void pole(){
        printf("Pole Rombu: ........... %.5f\n", bok*bok*sin(kat*3.14159/180));
    }
    void obwod(){
        printf("Obwód Rombu:........... %d\n", 4*bok);
    }
    public: ~Romb(){};
};

int main(int argc, char *argv[]){
    
    vector <Figura*> tabela_figur;
    //sprawdzić czy istnieje argv[1] - argv[0] to nazwa programu
    if(argc == 1){
        printf("Musisz podać jakieś figury\n");
        return 0;
    }
    //sprawdzenie czy wszystkie argumenty to liczby naturalne i czy jest ich dobra ilosc
    int sum = 1;
    for(int i=0; i<strlen(argv[1]); i++){
        switch (argv[1][i]) {
            case 'o': sum++; break;
            case 'c': sum += 5; break;
            case 'p': sum++; break;
            case 's': sum++; break;
            default: printf("Błędny format - nie ma takiej figury jak: %c\n", argv[1][i]); return 0;
        }
    }
    if(argc-1 == sum){
        for(int i=2; i<argc; i++){
            try {
                int n = stoi(argv[i]);
                if(n<=0){
                    printf("Argumenty nie są liczbami naturalnymi\n");
                    return 0;
                }
            } catch (exception) {
                printf("Argumenty nie są liczbami naturalnymi\n");
                return 0;
            }
        }
    } else { 
        printf("Nieprawidłowa ilość argumentów\n");
        return 0;
    }

    int j=2; //polozenie argumentu
    int k=0; //ilosc obiektow w tabeli
    for(int i=0; i<strlen(argv[1]); i++){
        switch (argv[1][i]) {
            case 'o':{
                int r = stoi(argv[j]);
                tabela_figur.push_back(new Kolo(r));
                j++;
                k++;
                break;}
            case 'c':{
                int b1 = stoi(argv[j]);
                int b2 = stoi(argv[j+1]);
                int b3 = stoi(argv[j+2]);
                int b4 = stoi(argv[j+3]);
                int kat = stoi(argv[j+4]);
                if(kat == 90){
                    if((b1==b2) && (b1==b3) && (b1==b4)){
                        tabela_figur.push_back(new Kwadrat(b1));
                    } else {
                        if(b1 != b2){
                            tabela_figur.push_back(new Prostokat(b1,b2));
                        } else {
                            tabela_figur.push_back(new Prostokat(b1,b3));
                        }
                    }
                } else if((b1==b2) && (b1==b3) && (b1==b4)){
                    tabela_figur.push_back(new Romb(b1,kat));
                }
                j += 5;
                k++;
                break;}
            case 'p':{
                int bok = stoi(argv[j]);
                tabela_figur.push_back(new Pieciokat(bok));
                j++;
                k++;
                break;}
            case 's':{
                int bok = stoi(argv[j]);
                tabela_figur.push_back(new Szesciokat(bok));
                j++;
                k++;
                break;}
        }
    }
    //wypisanie pola i obwodu obiektow
    for(int a=0; a<k; a++){
        tabela_figur.at(a)->pole();
        tabela_figur.at(a)->obwod();
    }
    tabela_figur.~vector<Figura*>();
    return 0;
}