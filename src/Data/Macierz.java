package Data;

/**
 * Klasa służąca do wykonywania podstawowych operacji  na macierzach:
 * dodawanie macierzy, odejmowanie, mnożenie, potęgowanie, transpozycja,
 * wyznaczanie śladu macierzy, klasa pozwala również na wyliczanie wyznacznika macierzy
 * i jej macierzy odwrotnej. Posiada także szereg metod określających typ macierzy:
 * zerowa, kwadratowa, diagonalna, jednostkowa, symetryczna, skośnosymetryczna, binarna,
 * górnotrojkątna, dolnotrójkątna, trójkątna, osobliwa, nieosobliwa, skalarna, kolumnowa,
 * wierszowa czy antydiagonlna.
 *
 * @author http://www.darmoweskrypty.linuxpl.info
 * @version 1.0
 */
//http://www.darmoweskrypty.linuxpl.info/macierz/java
public class Macierz {
 
    private double[][] tablica;
 
    Macierz(double[][] m1) {
        this.tablica = m1;
    }
 
 
    /**
     * Metoda wyliczająca wyznacznik dla podanej tablicy.
     * Gdy podana tablica jest tablica 1x1 to jej wyznacznik jet równy elementowi
     * tablica[0][0], jeśli jest to tablica 2x2 wyznacznik wyliczany jest ze wzoru:
     * tablica[0][0] * tablica[1][1] - tablica[0][1] * tablica[1][0], gdy podana
     * tablica nie jest kwadratowa zwracany jest wyjątek RuntimeException.
     *
     * @param tablica Tablica dla której wyznaczamy wyznacznik
     * @return wyznacznik macierzy
     * @throws RuntimeException jeśli dana macierz nie jest kwadratowa
     * @see #wyznaczWyznacznik() 
     */
    private double wyznaczWyznacznikMacierzy(double[][] tablica) {
        double wyznacznik = 0;
 
        if (tablica.length == 1 && tablica[0].length == 1) {
            wyznacznik = tablica[0][0];
        } else if (tablica.length != tablica[0].length) {
            throw new RuntimeException("Nie można wyznaczyć wyznacznika dla macierzy która nie jest kwadratowa");
        } else if (tablica.length == 2 && tablica[0].length == 2) {
            wyznacznik = (tablica[0][0] * tablica[1][1] - tablica[0][1] * tablica[1][0]);
        } else {
            double[][] nTab = new double[tablica.length + (tablica.length - 1)][tablica[0].length];
            for (int i = 0, _i = 0; i < nTab.length; i++, _i++) {
                for (int j = 0; j < tablica[0].length; j++) {
                    if (_i < tablica.length && j < tablica[0].length) {
                        nTab[i][j] = tablica[_i][j];
                    } else {
                        _i = 0;
                        nTab[i][j] = tablica[_i][j];
                    }
                }
            }
 
            double iloczyn = 1;
            int _i;
 
            for (int i = 0; i < tablica.length; i++) {
                _i = i;
                for (int j = 0; j < tablica[0].length; j++) {
                    iloczyn *= nTab[_i][j];
                    _i++;
                }
                wyznacznik += iloczyn;
                iloczyn = 1;
            }
 
            iloczyn = 1;
            for (int i = 0; i < tablica.length; i++) {
                _i = i;
                for (int j = tablica[0].length - 1; j >= 0; j--) {
                    iloczyn *= nTab[_i][j];
                    _i++;
                }
                wyznacznik -= iloczyn;
                iloczyn = 1;
            }
        }
        return wyznacznik;
    }
 
 
    /**
     * Metoda wyznaczająca wyznacznik dla danej macierzy.
     *
     * @return wyznacznik macierzy
     * @see #wyznaczWyznacznikMacierzy(double[][])
     */
    public double wyznaczWyznacznik() {
        return this.wyznaczWyznacznikMacierzy(this.tablica);
    }
 
 
    /**
     * Metoda służąca do dodania do siebie dwóch macierzy.
     * Macierze są dodawane do siebie tylko wtedy gdy mają takie same wymiary tzn.:
     * mają taką samą ilość kolumn i wierszy, gdy macierze nie spełniają tego warunku
     * wyrzucany jest wyjątek: RuntimeException.
     *
     * @param macierz Macierz dodawana
     * @throws RuntimeException jeśli dodawane macierze mają inne wymiary
     */
    public void dodajMacierz(Macierz macierz) {
        double[][] tablicaDoDodania = macierz.getTablice();
        double[][] macierzDodana = null;
        if (this.tablica.length == tablicaDoDodania.length && this.tablica[0].length == tablicaDoDodania[0].length) {
            macierzDodana = new double[this.tablica.length][this.tablica[0].length];
            for (int i = 0; i < this.tablica.length; i++) {
                for (int j = 0; j < this.tablica[0].length; j++) {
                    macierzDodana[i][j] = (this.tablica[i][j] + tablicaDoDodania[i][j]);
                }
            }
        } else {
            throw new RuntimeException("Nie można dodać do siebie macierzy o różnych wymiarach");
        }
        this.tablica = macierzDodana;
    }
 
 
    /**
     * Metoda służąca do odjęcia od siebie dwóch macierzy.
     * Macierze mogą być odjęte do siebie tylko wtedy gdy mają takie same wymiary tzn.:
     * mają taką samą ilość kolumn i wierszy, gdy macierze nie spełniają tego warunku
     * wyrzucany jest wyjątek: RuntimeException.
     *
     * @param macierz Macierz odejmowana
     * @exception RuntimeException gdy macierze mają inne wymiary
     */
    public void odejmijMacierz(Macierz macierz) {
        double[][] tablicaDoOdjecia = macierz.getTablice();
        double[][] macierzOdjeta = null;
        if (this.tablica.length == tablicaDoOdjecia.length && this.tablica[0].length == tablicaDoOdjecia[0].length) {
            macierzOdjeta = new double[this.tablica.length][this.tablica[0].length];
            for (int i = 0; i < this.tablica.length; i++) {
                for (int j = 0; j < this.tablica[0].length; j++) {
                    macierzOdjeta[i][j] = (this.tablica[i][j] - tablicaDoOdjecia[i][j]);
                }
            }
        } else {
            throw new RuntimeException("Nie można odjąć od siebie macierzy o różnych wymiarach");
        }
        this.tablica = macierzOdjeta;
    }
 
 
    /**
     * Metoda służąca do pomnożenia macierzy przez skalar.
     *
     * @param skalar liczba przez którą wymnażana jest macierz
     * @see #pomnozPrzezSkalar(double,double[][])
     */
    public void pomnozPrzezSkalar(double skalar) {
        this.tablica = this.pomnozPrzezSkalarTablice(skalar, this.tablica);
    }
 
 
    /**
     * Prywatna metoda służąca do pomnożenia podanej tablicy przez skalar.
     * 
     * @param skalar liczba przez która mnożona jest tablica
     * @param tablica przez którą mnożony jest skalar
     * @return wymnożona tablica
     */
    private double[][] pomnozPrzezSkalarTablice(double skalar, double[][] tablica) {
        double[][] macierzPomnozona = new double[tablica.length][tablica[0].length];
        for (int i = 0; i < tablica.length; i++) {
            for (int j = 0; j < tablica[0].length; j++) {
                macierzPomnozona[i][j] = (tablica[i][j] * skalar);
            }
        }
        return macierzPomnozona;
    }
 
 
    /**
     * Metoda zwracająca macierz w postaci tablicy wielowymiarowej.
     *
     * @return macierz w formie tablicy wielowymiarowej
     */
    public double[][] getTablice() {
        return tablica;
    }
 
 
    /**
     * Sprawdza czy macierz jest macierzą zerową, czyli taka która składa się
     * tylko z samych zer.
     *
     * @return true jeśli tablica jest zerowa, false jeśli nie
     */
    public boolean isZerowa() {
        for (int i = 0; i < this.tablica.length; i++) {
            for (int j = 0; j < this.tablica[0].length; j++) {
                if (this.tablica[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
 
 
    /**
     * Metoda sprawdzająca czy dana macierz jest macierzą kwadratową czyli taką
     * która ma tyle samo kolumn co wierszy.
     *
     * @return boolean True jest kwadratowa, false jeśli nie jest kwadratowa
     */
    public boolean isKwadratowa() {
        if (this.tablica[0].length == this.tablica.length) {
            return true;
        } else {
            return false;
        }
    }
 
 
    /**
     * Metoda służąca do pomnożenia aktualnej macierzy przez macierz podana jako
     * parametr metody.
     *
     * @param macierz przez która mnożymy aktualną macierz
     */
    public void pomnoz(Macierz macierz) {
        double[][] macierzMnozona = macierz.getTablice();
        double[][] macierzPomnozona = new double[this.tablica.length][macierzMnozona[0].length];
        macierzPomnozona = this.wymnozTablice(this.tablica, macierzMnozona);
        this.tablica = macierzPomnozona;
    }
 
 
    /**
     * Sprawdza czy podana tablica jest diagonalna, czyli taka w której wszystkie
     * elementy leżące poza główna przekątna są zerami.
     *
     * @return true jeśli tablica jest diagonalna, false jeśli nie
     */
    public boolean isDiagonalna() {
        if (!this.isKwadratowa()) {
            return false;
        }
        for (int i = 0; i < this.tablica.length; i++) {
            for (int j = 0; j < this.tablica[0].length; j++) {
                if (i != j && this.tablica[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
 
 
    /**
     * Metoda sprawdzająca czy dana macierz jest jednostkowa czyli taka w
     * której elementy leżące na głównej przekątnej sa jedynkami a pozostałe
     * elementy są zerami.
     *
     * @return true jeśli tablica jest jednostkowa, false jeśli nie jest
     */
    public boolean isJednostkowa() {
        if (!this.isDiagonalna()) {
            return false;//tablice nie diagonalne nie mogą być jednostkowe
        }
        for (int i = 0, j = 0; i < this.tablica.length; i++, j++) {
            if (this.tablica[i][j] != 1) {
                return false;
            }
        }
        return true;
    }
 
 
    /**
     * Sprawdza czy macierz jest symetryczna to znaczy taka dla której
     * elementy leżące na pozycjach [i][j] i [j][i] są sobie równe.
     *
     * @return true jeśli tablica jest symetryczna, false jeśli nie
     */
    public boolean isSymetryczna() {
        if (!this.isKwadratowa()) {
            return false; //tablica nie kwadratowe nie mogą być symetryczne
        } else {
            for (int i = 0; i < this.tablica.length; i++) {
                for (int j = 0; j < this.tablica[0].length; j++) {
                    if (this.tablica[i][j] != this.tablica[j][i]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
 
 
    /**
     * Sprawdza czy tablica jest skośnosymetryczna to znaczy że dla każdego
     * elementu [i][j]=-[j][i]
     *
     * @return  true jeśli tablica jest skośnosymetryczna false jeśli nie jest
     */
    public boolean isSkosnosymetryczna() {
        if (!this.isKwadratowa()) {
            return false; //tablice nie kwadratowe nie mogą być skosnosymetryczne
        } else {
            for (int i = 0; i < this.tablica.length; i++) {
                for (int j = 0; j < this.tablica[0].length; j++) {
                    if (this.tablica[i][j] != (-this.tablica[j][i])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
 
 
    /**
     * Metoda sprawdzająca czy podana macierz jest macierzą binarna czyli taką
     * której elementy są albo jedynkami albo zerami.
     * 
     * @return true jeśli macierz jest binarna, false jeśli nie jest
     */
    public boolean isBinarna() {
        for (int i = 0; i < this.tablica.length; i++) {
            for (int j = 0; j < this.tablica[0].length; j++) {
                if (this.tablica[i][j] == 0 || this.tablica[i][j] == 1) {
                } else {
                    return false;
                }
            }
        }
        return true;
    }
 
 
    /**
     * Metoda przekształcająca macierz na typ String, do wygenerowanego stringa
     * dodawane są dodatkowe formatowania aby macierz była czytelniejsza.
     *
     * @return tablica w formie obiektu String
     */
    @Override
    public String toString() {
        String temp = "";
        for (int i = 0; i < this.tablica.length; i++) {
            temp += "|";
            for (int j = 0; j < this.tablica[0].length; j++) {
                temp += " " + this.tablica[i][j];
            }
            temp += " |\n";
 
        }
        return temp;
    }
 
 
    /**
     * Metoda służąca do transponowania macierzy.
     *
     * @see #transponujTablice(double[][])
     */
    public void transponuj() {
        this.tablica = this.transponujTablice(this.tablica);
    }
 
 
    /**
     * Prywatna metoda służąca do zamieniania pozycjami wierszy tablicy z jej
     * kolumnami.
     *
     * @param tablica do transpozycji
     * @return tablica z zamienionymi kolumnami i wierszami
     * @see #transponuj() 
     */
    private double[][] transponujTablice(double[][] tablica) {
        double[][] macierzTransponowana = new double[tablica[0].length][tablica.length];
        for (int i = 0; i < tablica.length; i++) {
            for (int j = 0; j < tablica[0].length; j++) {
                macierzTransponowana[j][i] = tablica[i][j];
            }
        }
        return macierzTransponowana;
    }
 
 
    /**
     * Metoda sprawdzająca czy dana macierz jest górnotrójkątna czyli taka macierz
     * kwadratowa w której wszystkie elementy poniżej głównej przekątnej są zerami.
     * 
     * @return true jeśli macierz jest górnotrójkątna, false jeśli nie jest
     */
    public boolean isGornoTrojkatna() {
        if (!this.isKwadratowa()) {
            return false;
        }
        for (int i = 0; i < this.tablica.length; i++) {
            for (int j = 0; j < this.tablica[0].length; j++) {
                if (i > j && this.tablica[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
 
 
    /**
     * Metoda sprawdzająca czy dana macierz jest dolnotrójkątna czyli taka macierz
     * kwadratowa w której wszystkie elementy powyżej głównej przekątnej są zerami.
     *
     * @return true jeśli macierz jest dolnotrójkątna, false jeśli nie jest
     */
    public boolean isDolnoTrojkatna() {
        if (!this.isKwadratowa()) {
            return false;
        }
        for (int i = 0; i < this.tablica.length; i++) {
            for (int j = 0; j < this.tablica[0].length; j++) {
                if (j > i && this.tablica[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
 
 
    /**
     * Sprawdza czy dana macierz jest trójkątna czyli taka dla której elementy
     * leżące poniżej lub powyżej głównej przekątnej są zerami.
     *
     * @return true jeśli macierz jest trójkątna, false jeśli nie jest
     */
    public boolean isTrojkatna() {
        if (!this.isDolnoTrojkatna() && !this.isGornoTrojkatna()) {
            return false;
        }
        return true;
    }
 
 
    /**
     * Sprawdza czy dana macierz jest osobliwa czyli taka dla której wyznacznik
     * jest równy 0.
     * 
     * @return true jeśli macierz jest osobliwa, false jeśli nie jest
     */
    public boolean isOsobliwa() {
        if (this.wyznaczWyznacznik() != 0) {
            return false;
        }
        return true;
    }
 
 
    /**
     * Sprawdza czy dana macierz jest nieosobliwa czyli taka dla której wyznacznik
     * jest różny od 0.
     *
     * @return true jeśli macierz jest nieosobliwa, false jeśli nie jest
     */
    public boolean isNieosobliwa() {
        if (this.wyznaczWyznacznik() == 0) {
            return false;
        }
        return true;
    }
 
 
    /**
     * Metodą sprawdzająca czy dana macierz jest skalarna czyli taka macierz
     * która jest macierzą diagonalną i równocześnie wszystkie jej elementy leżące
     * na głównej przekątnej są sobie równe.
     * 
     * @return true jeśli macierz jest skalarna, false jeśli nie jest
     * @see #isDiagonalna() 
     */
    public boolean isSkalarna() {
        if (!this.isDiagonalna()) {
            return false;
        }
        double element = this.tablica[0][0];
        for (int i = 0, j = 0; i < this.tablica.length; i++, j++) {
            if (this.tablica[i][j] != element) {
                return false;
            }
        }
        return true;
    }
 
 
    /**
     * Sprawdza czy dana macierz jest idempotentna czyli taką macierz kwadratowa
     * dla której jej kwadrat jest równy jej samej A=A^2.
     * 
     * @return true jeśli macierz jest idempotentna, false jeśli nie jest
     */
    public boolean isIdempotentna() {
        if (!this.isKwadratowa()) {
            return false;
        }
        double[][] macierzKwadratowa = new double[this.tablica.length][this.tablica[0].length];
        for (int i = 0; i < this.tablica.length; i++) {
            for (int j = 0; j < this.tablica[0].length; j++) {
                double temp = 0;
                for (int w = 0; w < this.tablica.length; w++) {
                    temp += this.tablica[i][w] * this.tablica[w][j];
                }
                macierzKwadratowa[i][j] = temp;
            }
        }
        //Porównywanie macierzy A i A^2
        for (int i = 0; i < this.tablica.length; i++) {
            for (int j = 0; j < this.tablica[0].length; j++) {
                if (this.tablica[i][j] != macierzKwadratowa[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
 
 
    /**
     * Prywatna metoda jej zadaniem jest podnoszenie do potęgi podanej tablicy,
     * metoda wykorzystuje przy potęgowaniu algorytm szybkiego potęgowania.
     *
     * @param tab tablica która jest potęgowana
     * @param wykladnik wartość do której podnoszona jest tablica
     * @return tablica podniesiona do podanej potęgi
     * @see #podniesDoPotegi(int) 
     */
    private double[][] potegoj(double[][] tab, int wykladnik) {
        if (wykladnik == 0) {
            double[][] temp = new double[tab.length][tab[0].length];
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[0].length; j++) {
                    if (i == j) {
                        temp[i][j] = 1;
                    } else {
                        temp[i][j] = 0;
                    }
                }
            }
            return temp;
        } else if (wykladnik % 2 != 0) {
            return this.wymnozTablice(tab, this.potegoj(tab, wykladnik - 1));
        } else {
            double[][] temp = this.potegoj(tab, wykladnik / 2);
            return this.wymnozTablice(temp, temp);
        }
    }
 
 
    /**
     * Metoda służąca do podnoszenia macierzy do podanej potęgi, w przypadku
     * gdy macierz nie jest kwadratowa lub podany wykładnik jest ujemny wyrzucany
     * jest wyjątek RuntimeException.
     *
     * @param wykladnik wartość potęgi do jakiej podnoszona jest macierz
     * @exception RuntimeException macierz nie jest kwadratowa lub wykładnik ujemny
     * @see #isKwadratowa() 
     */
    public void podniesDoPotegi(int wykladnik) {
        if (!this.isKwadratowa()) {
            throw new RuntimeException("Nie można potęgować macierzy które nie są kwadratowe");
        } else {
            if (wykladnik >= 2) {
                if (wykladnik % 2 == 0) {
                    this.tablica = this.potegoj(this.tablica, wykladnik);
                }
            } else if (wykladnik == 1) {// A^1=A
            } else if (wykladnik == 0) {//tablica jednostkowa A^0=I
                for (int i = 0; i < this.tablica.length; i++) {
                    for (int j = 0; j < this.tablica[0].length; j++) {
                        this.tablica[i][j] = 1;
                    }
                }
            } else {
                throw new RuntimeException("Wykładnik nie może być ujemny");
            }
        }
    }
 
 
    /**
     * Metoda służąca do pomnożenia dwóch tablic: tab1 i tab2, tablice te można
     * pomnożyć przez siebie pod warunkiem ze ilość kolumn tablicy tab1 jest taka
     * sama jak ilość wierszy tablicy tab2, w przeciwnym wypadku wyrzucany jest
     * wyjątek RuntimeException.
     * 
     * @param tab1 pierwsza tablica
     * @param tab2 druga tablica
     * @return tablica będąca iloczynem tablic tab1 i tab2
     */
    private double[][] wymnozTablice(double[][] tab1, double[][] tab2) {
        double[][] macierzPomnozona = new double[tab1.length][tab2[0].length];
        if (tab1[0].length == tab2.length) {
            for (int i = 0; i < tab1.length; i++) {//ilosc wierszy tab1
                for (int j = 0; j < tab2[0].length; j++) { //ilosc kolumn tab2
                    double temp = 0;
                    for (int w = 0; w < tab2.length; w++) { //ilosc wierszy tab2
                        temp += tab1[i][w] * tab2[w][j];
                    }
                    macierzPomnozona[i][j] = temp;
                }
            }
        } else {
            throw new RuntimeException("Podane tablice mają niewłasciwe wymiary");
        }
        return macierzPomnozona;
    }
 
 
    /**
     * Sprawdza czy macierz jest kolumnowa czyli taka która posiada jedną kolumnę.
     *
     * @return true dla macierzy kolumnowej, false dla macierzy nie kolumnowej
     */
    public boolean isKolumnowa() {
        if (this.tablica[0].length == 0) {
            return true;
        } else {
            return false;
        }
    }
 
 
    /**
     * Sprawdza czy tablica jest wierszowa czyli taka która posiada jeden wiersz.
     *
     * @return boolean true jeśli jest wierszowa, false jeśli nie jest
     */
    public boolean isWierszowa() {
        if (this.tablica.length == 0) {
            return true;
        } else {
            return false;
        }
    }
 
 
    /**
     * Metoda wyliczająca ślad macierzy czyli sumę elementów na głównej przekątnej
     * macierzy, macierz dla której wyznaczany jest ślad musi być macierzą
     * kwadratowa.
     *
     * @return ślad macierzy
     */
    public double wyznaczSladMacierzy() {
        double suma = 0;
        if (!this.isKwadratowa()) {
            throw new RuntimeException("Nie można wyznaczyć śladu dla macierzy nie kwadratowej");
        } else {
            for (int i = 0; i < this.tablica.length; i++) {
                suma += this.tablica[i][i];
            }
        }
        return suma;
    }
 
 
    /**
     * Metoda sprawdzająca czy dana macierz jest antydiagonalna, czyli taka
     * która zawiera same zera oprócz elementów na przekątnej biegnącej od prawego
     * górnego wierzchołka do lewego dolnego.
     * Macierz może być antydiagonalna pod warunkiem ze jest kwadratowa.
     *
     * @return true jesli jest antydiagonalna, false jesli nie jest antydiagonalna
     */
    public boolean isAntydiagonalna() {
        if (!this.isKwadratowa()) {
            return false;
        }
        int temp = this.tablica[0].length - 1;
        for (int i = 0; i < this.tablica.length; i++) {
            for (int j = 0; j < this.tablica[0].length; j++) {
                if (j != temp && this.tablica[i][j] != 0) {
                    return false;
                }
            }
            temp--;
        }
        return true;
    }
 
     
    /**
     * Metoda służąca do wyznaczania macierzy odwrotnej do podanej w konstruktorze
     * klasy tablicy. W pierwszej kolejności sprawdzane jest czy dana tablica jest
     * kwadratowa, tylko dla takich tablic można wyznaczyć macierz odwrotna, jeśli
     * nie jest kwadratowa wyrzucany jest wyjątek RuntimeException.
     * Dla macierzy kwadratowych składających się tylko z jednego elementu macierz
     * odwrotna jest równa 1/(wyznacznik tablicy), dla macierzy o większej ilości
     * elementów wyznaczana jest macierz dołączona a samą macierz odwrotna wyznacza
     * się jako 1/(wyznacznik tablicy)*(macierz dołączona).
     * 
     * @return macierz odwrotna 
     */
    public Macierz wyznaczMacierzOdwrotna() {
        if (!this.isKwadratowa()) {
            throw new RuntimeException("Nie można wyznaczyć macierzy odwrotnej dla macierzy która nie jest kwadratowa");
        } else {
            double[][] macierzOdwrotna;
 
            if (this.tablica.length == 1) {
                macierzOdwrotna = new double[1][1];
                macierzOdwrotna[0][0] = 1 / this.wyznaczWyznacznik();
                return new Macierz(macierzOdwrotna);
            } else {
                macierzOdwrotna = new double[this.tablica.length][this.tablica[0].length];
                double[][] macierzDolaczona = new double[this.tablica.length][this.tablica[0].length];
                for (int i = 0; i < this.tablica.length; i++) {
                    for (int j = 0; j < this.tablica[0].length; j++) {
                        double[][] temp = new double[this.tablica.length - 1][this.tablica[0].length - 1];
                        int a = 0, b = 0;
                        for (int w = 0; w < this.tablica.length; w++) {
                            for (int z = 0; z < this.tablica[0].length; z++) {
                                if (w != i && z != j) {
                                    if (b >= temp.length) {
                                        b = 0;
                                        a++;
                                    }
                                    temp[a][b] = this.tablica[w][z];
                                    b++;
                                }
                            }
                        }
 
                        double wyznacznikTemp = this.wyznaczWyznacznikMacierzy(temp);
 
                        if ((i + j) % 2 != 0) {//Niparzyste czyli zmiana znaku wyznacznika
                            if (wyznacznikTemp > 0) {
                                wyznacznikTemp -= 2 * wyznacznikTemp;
                            } else {
                                wyznacznikTemp -= 2 * wyznacznikTemp;
                            }
                        } else {
                        }
                        macierzDolaczona[i][j] = wyznacznikTemp;
                    }
                }
                macierzDolaczona = this.transponujTablice(macierzDolaczona);
                macierzOdwrotna = this.pomnozPrzezSkalarTablice(1 / this.wyznaczWyznacznik(), macierzDolaczona);
                return new Macierz(macierzOdwrotna);
            }
        }
    }
}