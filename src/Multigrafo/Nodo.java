/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multigrafo;

public class Nodo {
    public int Data;
    public Nodo Link;
    public double Peso;

    public Nodo(){
        this(0,0);
    }
    
    public Nodo(int Data, double Peso) {
        this.Data = Data;
        this.Link = null;
        this.Peso = Peso;
    }

    public int getData() {
        return Data;
    }

    public void setData(int Data) {
        this.Data = Data;
    }

    public Nodo getLink() {
        return Link;
    }

    public void setLink(Nodo Link) {
        this.Link = Link;
    }
    
    public double getPeso(){
        return Peso;
    }
    
    public void setPeso(double Peso){
        this.Peso = Peso;
    }
}
