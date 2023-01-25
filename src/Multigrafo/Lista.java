/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multigrafo;

public class Lista {

    public Nodo L;
    private int n;

    public Lista() {
        L = null;
        n = 0;
    }

    public boolean isVacia() {  //Devuelve true sii esta lista está vacía.
        return (L == null);
    }

    public void add(int x, double y) { //x=data y=peso
        if (!exist(x, y)) {         //comentar este if para adicionar datas con pesos iguales
            Nodo Ant = null;
            Nodo p = L;
            while (p != null && x > p.getData()) { //recorre
                Ant = p;
                p = p.getLink();
            }
            Nodo nuevo;
            if (Ant == null) {  //en caso de que este al comienzo
                if (p == null) {    //Lista vacia
                    nuevo = new Nodo(x, y);
                    nuevo.setLink(L);
                    L = nuevo;
                    n++;
                } else {
                    if (x != p.getData()) {     //x sea unico
                        nuevo = new Nodo(x, y);
                        nuevo.setLink(L);
                        L = nuevo;
                        n++;
                    } else { // caso contrario si existe duplicado se recorre por los pesos
                        while (p != null && y >= p.getPeso() && p.getData() == x) { // x repe
                            Ant = p;
                            p = p.getLink();
                        }
                        if (Ant == null) {  //en caso que este al principio (repe)
                            nuevo = new Nodo(x, y);
                            nuevo.setLink(L);
                            L = nuevo;
                            n++;
                        } else {
                            //if (Ant.getPeso() != y) {   //med o fin de los repes
                            nuevo = new Nodo(x, y);
                            Ant.setLink(nuevo);
                            nuevo.setLink(p);
                            n++;
                            //}
                        }
                    }
                }
            } else {  //Medio
                if (p != null) {        //Medio
                    if (p.getData() != x) { //cuando el dato es unico
                        nuevo = new Nodo(x, y);
                        Ant.setLink(nuevo);
                        nuevo.setLink(p);
                        n++;
                    } else {
                        while (p != null && y > p.getPeso() && p.getData() == x) { // recorre x peso si x es repe
                            Ant = p;
                            p = p.getLink();
                        }
                        if (Ant.getPeso() != y) { // verifico si el peso es el mismo al del parametro //si es igual no lo mete
                            nuevo = new Nodo(x, y);
                            Ant.setLink(nuevo);
                            nuevo.setLink(p);
                            n++;
                        }
                    }
                } else { // caso contrario p==null
                    if (Ant.getData() != x) {    //x no está en la lista.  Insertarlo entre Ant y p
                        nuevo = new Nodo(x, y);
                        Ant.setLink(nuevo);
                        nuevo.setLink(p);
                        n++;
                    }
                }
            }
        }
    }

    public void del(int x, double y) { //elimina un nodo con su peso
        Nodo Ant = null;
        Nodo p = L;

        while (p != null && x > p.getData()) { //hasta encontrar el elemento x
            Ant = p;
            p = p.getLink();
        }

        while (p != null && y > p.getPeso() && x == p.getData()) {  // hasta encontrar el costo y
            Ant = p;
            p = p.getLink();
        }

        if (p != null && p.getData() == x && p.getPeso() == y) {  //x y y existe en la Lista 
            if (Ant == null) {
                L = L.getLink();    //x era el primero de la Lista
            } else {
                Ant.setLink(p.getLink());
            }

            p.setLink(null);
            n--;
        }
    }

    
    public int get(int k) {
        if (k < 0 || k > length() - 1) //Diverge con la PRE
        {
            throw new RuntimeException("Lista.get: Índice fuera de rango");
        }
        Nodo p = L;
        for (int i = 1; i <= k; i++) {
            p = p.getLink();
        }
        return p.getData();
    }

    public int length() {
        return n;
    }

    @Override
    public String toString() {
        String S = "[";
        String coma = "";

        Nodo p = L;
        while (p != null) {
            S += coma + p.getData() + "/" + p.getPeso();
            coma = ", ";
            p = p.getLink();
        }

        return S + "]";
    }

    private boolean exist(int x, double y, Nodo p) {    //Método que devuelve true si existe el vertice x
        if (p == null) {                                //con el peso y especificado
            return false;
        } else {
            if (p.getData() == x && p.getPeso() == y) {
                return true;
            } else {
                return (exist(x, y, p.getLink()));
            }
        }
    }

    public boolean exist(int x, double y) {
        return (exist(x, y, L));
    }

    public double getPeso(int data, int k) {    //Devuelve el Peso que acompaña al data.  Si data no existe, devuelve 0.
        Nodo p = existe(data, k);               //cuando data es repetido de la lista
        if (p != null) {
            return p.getPeso();
        }
        return 0;
    }
    public double getPeso(int data) {    //Devuelve el Peso que acompaña al data.  Si data no existe, devuelve 0.
        Nodo p = existe1(data);         //Cuando el data es único de la lista
        if (p != null) {
            return p.getPeso();
        }

        return 0;
    }
    
    private Nodo existe(int data, int k) {  //Devuelve el puntero al Nodo donde se encuentra data. 
        Nodo p = L;                         //cuando data es repetido de la lista
        while (p != null && data > p.getData()) {
            p = p.getLink();
        }
        for (int i = 0; i < k; i++) {
            p = p.getLink();
        }
        if (p != null && p.getData() == data) {
            return p;
        }
        return null;    //Devolver null, si data no existe en la lista.
    }

   

    private Nodo existe1(int data) {  //Devuelve el puntero al Nodo donde se encuentra data. 
        Nodo p = L;                 //Cuando el data es único de la lista
        while (p != null && data > p.getData()) {
            p = p.getLink();
        }
        if (p != null && p.getData() == data) {
            return p;
        }
        return null;    //Devolver null, si data no existe en la lista.
    }

    public boolean duplicado(int data) {     //Método que devuelve true si existe datas
        Nodo p = L;                         //repetidos en la lsita
        int c = 0;
        while (p != null && data > p.getData()) { 
            p = p.getLink();
        }
        while (p != null && data == p.getData()) { //cuenta los dup
            c++;
            p = p.getLink();
        }

        return (c > 1);
    }
}
