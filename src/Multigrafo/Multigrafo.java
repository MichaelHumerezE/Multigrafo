/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multigrafo;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JTextArea; //Importar JTextArea
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Multigrafo {

    private static final int MAXVERTEX = 49;    //Máximo índice de V[]
    private Lista V[];      // Vector de vertices
    private String S[];     // Vector de nombres de los vertices
    private int n;       //"Dimensión" de V[]
    private int m;      //"Dimensión de S[]"

    Component rootPane = null; //let acomoda

    public Multigrafo() {
        V = new Lista[MAXVERTEX + 1];      //V[0..MAXVERTEX]
        S = new String[MAXVERTEX + 1];
        n = -1;
        m = -1;
        marca = new boolean[MAXVERTEX + 1];    //Iniciar la ED para el marcado de los vértices.
    }

    public void addVertice(String nombre) {
        if (n == MAXVERTEX) { //validacion
            JOptionPane.showMessageDialog(rootPane, "Demasiados vértices (solo se permiten " + (MAXVERTEX + 1) + ")");
            return;
        }
        n++;
        m++;
        V[n] = new Lista();     //Crear un nuevo vértice sin adyacentes (o sea con su Lista de adyacencia vacía)
        S[m] = new String();
        S[m] = nombre;
    }

    public int cantVertices() {
        return n + 1;
    }

    public boolean isVerticeValido(int v) {
        return (0 <= v && v <= n);
    }

    public void addArista(int u, double peso, int v) {  //Crea la arista u-->v
        if (peso <= 0) {
            JOptionPane.showMessageDialog(rootPane, "El peso debe ser mayor que cero.");
            return;
        }
        String metodo = "addArista";
        if (!isVerticeValido(u, metodo) || !isVerticeValido(v, metodo)) { //valida
            return;     //No existe el vertice u o el vertice v.
        }

        //   if (u != v) { // esto es para que no exista un camino a si mismo  caso contrario // eliminar si se puede permitir caminos a si mismo
        V[u].add(v, peso);      //Adicionar (data, peso) a la lista V[u]    
        // }
    }

    public void delArista(int u, double peso, int v) {  //Elimina la arista u-->v
        String metodo = "delArista";
        if (!isVerticeValido(u, metodo) || !isVerticeValido(v, metodo)) {
            return;     //No existe el vertice u o el vertice v.
        }
        V[u].del(v, peso);
    }

    public double costo(int u, int k, int v) {  //Devuelve el costo cuando un elemento (v) es duplicado de acuerdo a su posición k
        if (!isVerticeValido(u) || !isVerticeValido(v)) {
            return 0;
        }
        return V[u].getPeso(v, k);
    }

    public double costo(int u, int v) {  //Devuelve el peso de la arista u-->v.  Si esa arista no existe, devuelve 0
        if (!isVerticeValido(u) || !isVerticeValido(v)) {
            return 0;
        }
        return V[u].getPeso(v);
    }

    public void dfs(int v, JTextArea jta) {  //Recorrido Depth-First Search (en profundidad).
        if (!isVerticeValido(v, "dfs")) {
            return;  //Validación. v no existe en el Grafo.
        }
        desmarcarTodos();
        jta.append("DFS(" + Integer.toString(v) + ") :");
        dfs1(v, jta);
        jta.append("\n");
    }

    private void dfs1(int v, JTextArea jta) {  //mask-function de void dfs(int)
        jta.append(" " + Integer.toString(v));
        marcar(v);
        for (int i = 0; i < V[v].length(); i++) {   //for (cada w adyacente a v)
            int w = V[v].get(i);
            if (!isMarcado(w)) {
                dfs1(w, jta);
            }
        }
    }

    public void bfs(int u, JTextArea jta) {  //Recorrido Breadth-First Search (en anchura).
        if (!isVerticeValido(u, "bfs")) {
            return;  //Validación. u no existe en el Grafo. 
        }
        desmarcarTodos();
        LinkedList<Integer> cola = new LinkedList<>();  //"cola" = (vacía) = (empty)
        cola.add(u);     //Insertar u a la "cola" (u se inserta al final de la lista).
        marcar(u);
        jta.append("BFS(" + Integer.toString(u) + ") :");
        do {
            int v = cola.pop();         //Obtener el 1er elemento de la "cola".
            jta.append(" " + Integer.toString(v));
            for (int i = 0; i < V[v].length(); i++) {   //for (cada w adyacente a v)
                int w = V[v].get(i);
                if (!isMarcado(w)) {
                    cola.add(w);
                    marcar(w);
                }
            }
        } while (!cola.isEmpty());
        jta.append("\n");
    }

    public void printListas(JTextArea jta) {  //Muestra las listas del Grafo.  Util para el programador de esta class
        if (cantVertices() == 0) {
            jta.append("Multigrafo Vacío.");
        } else {
            for (int i = 0; i <= n; i++) {
                jta.append("V[" + i + " / " + S[i] + "]-->" + V[i]);
                jta.append("\n");
            }
        }
    }

    private boolean isVerticeValido(int v, String metodo) {
        boolean b = isVerticeValido(v);
        if (!b) { // b==false
            JOptionPane.showMessageDialog(rootPane, v + " no es un vértice válido del Multigrafo.");
        }
        return b;
    }

//********* Para el marcado de los vértices
    private boolean marca[];

    private void desmarcarTodos() {
        for (int i = 0; i <= n; i++) {
            marca[i] = false;
        }
    }

    private void marcar(int u) {
        if (isVerticeValido(u)) {
            marca[u] = true;
        }
    }

    private void desmarcar(int u) {
        if (isVerticeValido(u)) {
            marca[u] = false;
        }
    }

    private boolean isMarcado(int u) {   //Devuelve true sii el vertice u está marcado.
        return marca[u];
    }

    public void createFile(String archivo) {        //Método para crear un archivo vacío
        File f1 = new File(archivo);
        try {
            PrintWriter pw = new PrintWriter(f1);
            pw.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void writeFile(String archivo, String texto) { //Método para pasar el contenido del grafo a
        File f1 = new File(archivo);                      //a un archivo de texto
        try {                                               //El contenido del grafo se encuentra en el String texto
            PrintWriter pw = new PrintWriter(new FileWriter(f1, true));
            pw.println(texto);
            pw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String save(String texto, Multigrafo G) {    //Método save contenido graph into txto
        int anterior = 0;
        for (int z = 0; z < G.cantVertices(); z++) {    // en una varable string
            texto += G.S[z] + "\n";                     //Guarda los nombres de los vertices
        }
        for (int i = 0; i < G.cantVertices(); i++) { //i recorr verti
            int k = 0;
            for (int j = 0; j < G.V[i].length(); j++) {  // j arist
                int data = G.V[i].get(j);
                if (anterior != data) {  //cond. si en el vertice existe mas de 2 elem duplicados
                    k = 0;
                }
                texto += i + "\n";
                if (G.V[i].duplicado(data)) {
                    texto += G.costo(i, k, data) + "\n";        //Guarda el costo de dos vertices donde en la lista
                    texto += data + "\n";                      //concat data q es vertice destino
                    k++;
                    anterior = data;
                } else {
                    texto += G.costo(i, data) + "\n";           //Guarda el costo de dos vertices donde en la lista
                    texto += data + "\n";
                }
            }
        }
        texto = texto.trim();                       //Elimina el último espacio vacio
        return texto;
    }

    public Multigrafo readFile(String archivo, Multigrafo A) {  //Método load multigrafo
        File f1 = new File(archivo);                            //Carga a un nuevo grafo el contenido del archivo
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String read = br.readLine();
            int cantidad = Integer.parseInt(read);
            while (cantidad != 0) {                         //while para adicionar los vertices con su respectivo nombre 
                read = br.readLine();
                A.addVertice(read);
                cantidad--;
            }
            read = br.readLine();
            while (read != null && read != "") {            // while para adicionar las aristas
                int orig = Integer.parseInt(read);
                read = br.readLine();
                double peso = Double.parseDouble(read);
                read = br.readLine();
                int dest = Integer.parseInt(read);
                read = br.readLine();
                A.addArista(orig, peso, dest);
            }
            br.close();
            return A;
        } catch (FileNotFoundException ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, "Archivo no encontrado!!"); //caso de no encontrar el archivo
        } catch (IOException ex) {
            ex.printStackTrace();
            //JOptionPane.showMessageDialog(rootPane, "Error!!");
        }
        return A;
    }

    public String getNombre(int i) {                // Método para encontrar el nombre de un vértice dado su índice 
        if (i >= cantVertices() || i < 0) {
            return "Vértice no valido.";
        }
        return S[i];
    }

    public boolean existeNombre(String name, Multigrafo G) {            //Método para validar que no exista duplicados en
        for (int i = 0; i < G.cantVertices(); i++) {                    //los nombres de los vertices
            if (G.S[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    public int cantAristas() {
        int c = 0;
        for (int i = 0; i < cantVertices(); i++) {
            c += V[i].length();
        }
        return c;
    }

    public void acme(int a, int b, JTextArea jta) {  //Recorrido Depth-First Search (en profundidad).
        desmarcarTodos();
        if (existeCamino(a, b)) {
            desmarcarTodos();
            jta.append("Recorrido(" + S[a] + ") :");
            acme1(a, b, jta);
            jta.append("\n");
        } else {
            jta.append("No existe camino entre " + a + " y " + b);
        }
    }

    private int acme1(int a, int b, JTextArea jta) {  //mask-function de void dfs(int)
        marcar(a);
        int k = 0;
        int anterior = 0;
        for (int i = 0; i < V[a].length(); i++) {   //for (cada w adyacente a v)
            int w = V[a].get(i);
            if (anterior != w) {
                k = 0;
            }
            if (!isMarcado(w)) {
                if (V[a].duplicado(w)) {
                    jta.append(" " + V[a].getPeso(w, k));
                    k++;
                } else {
                    jta.append(" " + V[a].getPeso(w));
                }
                if (w != b) {
                    return acme1(w, b, jta);
                } else {
                    return 0;
                }
            }
            anterior = w;
        }
        return 0;
    }

    public boolean existeCamino(int a, int b) { //Funciona, pero no lo usé
        boolean anterior[] = new boolean[MAXVERTEX + 1];
        for (int i = 0; i < marca.length; i++) {
            anterior[i] = marca[i];
        }
        if (a == b) {
            return true;
        } else {
            desmarcarTodos();
            if (existeCamino1(a, b)) {
                for (int i = 0; i < marca.length; i++) {
                    marca[i] = anterior[i];
                }
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean existeCamino1(int a, int b) {
        marcar(a);
        for (int i = 0; i < V[a].length(); i++) {
            int w = V[a].get(i);
            if (!isMarcado(w)) {
                if (w == b) {
                    return true;
                } else {
                    if (existeCamino1(w, b) == true) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void CeminoMenorCost(int a, int b, JTextArea jta) {
        desmarcarTodos();
        if (existeCamino(a, b)) {
            desmarcarTodos();
            jta.append("Recorrido(" + S[a] + " - " + S[b] + ") :");
            CeminoMenorCost1(a, b, jta);
            jta.append("\n");
        } else {
            jta.append("No existe camino entre " + a + " y " + b);
        }
    }

    private int CeminoMenorCost1(int a, int b, JTextArea jta) {
        marcar(a);
        double pos = 1000000;
        int k = 0;
        int anterior = 0;
        for (int i = 0; i < V[a].length(); i++) { // for para encontrar el menor costo
            int w = V[a].get(i);
            if (anterior != w) {
                k = 0;
            }
            if (!isMarcado(w) && existeCamino(w, b)) {
                if (V[a].duplicado(w)) {
                    if (costo(a, k, w) < pos) {
                        pos = costo(a, k, w); //se asigna a pos(valor) el menor costo
                    }
                    k++;
                } else {
                    if (costo(a, w) < pos) {
                        pos = costo(a, w); //se asigna a pos(valor) el menor costo
                    }
                }
            }
            anterior = w;
        }
        k = 0;
        anterior = 0; //probrar
        for (int i = 0; i < V[a].length(); i++) {
            int w = V[a].get(i);
            if (anterior != w) {
                k = 0;
            }
            if (V[a].duplicado(w)) {
                if (!isMarcado(w) && costo(a, k, w) == pos && existeCamino(w, b)) { //condicionamos que el costo sea igual a la pos(camino con menor costo))
                    jta.append(" " + V[a].getPeso(w, k));
                    if (w != b) {
                        return CeminoMenorCost1(w, b, jta);
                    } else {
                        return 0;
                    }
                }
                k++;
            } else {
                if (!isMarcado(w) && costo(a, w) == pos && existeCamino(w, b)) { //condicionamos que el costo sea igual a la pos(camino con menor costo))
                    jta.append(" " + V[a].getPeso(w));
                    if (w != b) {
                        return CeminoMenorCost1(w, b, jta);
                    } else {
                        return 0;
                    }
                }
            }
            anterior = w;
        }
        return 0;
    }
}
