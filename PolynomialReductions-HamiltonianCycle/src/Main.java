
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lupu Andreea
 */
public class Main {
    static void conditieA(PrintWriter out, int N){
        int i, j;
        for(j = 2; j <= N; j++) {
            out.print("(");
            for (i = 1; i <= (N/2 + 1); i ++){
                out.print("a" + i + "-" + j);
                if(i != (N/2 + 1)) 
                    out.print("|");
            }
            out.print(")");
            if(j != N)
                out.print("&");
        }
    }
    
    static void conditieCiclu(PrintWriter out, int N, int [][] matrice) {
        int i;
        for(i = 1; i <= N; i++) {
            ArrayList <Integer> noduriAdiacente = new ArrayList<>();
            for(int j = 1; j <= N; j++)
                if (matrice [i][j] == 1)
                    noduriAdiacente.add(j);
            out.print("(");
            for(int k = 0; k < noduriAdiacente.size() - 1; k++){
                
                for(int l = k + 1; l < noduriAdiacente.size(); l++) {
                    out.print("(");
                    out.print("x" + i + "-" + noduriAdiacente.get(k) + "&" + 
                            "x" + i + "-" + noduriAdiacente.get(l));
                    for(int inainte = 0; inainte < k; inainte ++) {
                        out.print("&~" + "x" + i + "-" + noduriAdiacente.get(inainte));
                    }
                    for(int intre = k + 1; intre < l; intre ++) {
                        out.print("&~" + "x" + i + "-" + noduriAdiacente.get(intre));
                    }
                    for(int dupa = l + 1; dupa < noduriAdiacente.size(); dupa ++) {
                        out.print("&~" + "x" + i + "-" + noduriAdiacente.get(dupa));
                    }
                    out.print(")");
                    if (l != noduriAdiacente.size() - 1)
                        out.print("|");
                }
                if (k != noduriAdiacente.size() - 2)
                    out.print("|");
            }
            out.print(")");
            if (i != N)
                out.print("&");
        }
    }
    
    static void conditieOrientare(PrintWriter out, int N, int [][] matrice){
        int i, j;
        int ok = 0;
        for(i = 1; i <= N; i++) {
           
            for(j = i+1; j <= N ; j++) {
                if(matrice[i][j] == 1) {
                    if(ok == 1)
                        out.print("&");
                    out.print("((");
                    out.print("x" + i + "-" + j + "|~x" + j + "-" + i + ")");
                    out.print("&");
                    out.print("(~x" + i + "-" + j + "|x" + j + "-" + i);
                    out.print("))");
                    ok = 1;
                }
            }
        }        
    }
    
    static void conditieAPrimNod(PrintWriter out, int N, int[][] matrice){
        for(int j = 1; j <= N; j++) {
            if(matrice[1][j] == 0)
                out.print("~a1-" + j );
            if(matrice[1][j] == 1) {
                out.print("((a1-" + j + "|~x1-" + j + ")&(~a1-" + j + "|x1-" + j + "))");
            }
            if(j != N)
                out.print("&");
        }
    }
    
    static void conditieAuri(PrintWriter out, int N, int [][] matrice){
        int i, j, m, coloana, ok;
        for(i =2; i <= N/2 + 1; i++) {
            for(j = 2; j <= N; j++) {
                out.print("((a" + i + "-" + j + "|~((");
                ok = 0;
                for(coloana = 2; coloana <= N; coloana ++) {
                    
                    if(matrice[j][coloana] == 1) {
                        if(ok == 1)
                            out.print("|");
                        out.print("(a" + (i-1) + "-" + coloana + "&x" + coloana + "-" + j + ")");
                        ok = 1;
                    }
                }
                out.print(")&~(");
                for(m = 1; m < i; m++) {
                    out.print("a" + m + "-" + j);
                    if(m != i-1)
                        out.print("|");
                }
                out.print(")))&");
                
                out.print("(~a" + i + "-" + j + "|((");
                ok = 0;
                for(coloana = 2; coloana <= N; coloana ++) {
                    
                    if(matrice[j][coloana] == 1) {
                        if(ok == 1)
                            out.print("|");
                        out.print("(a" + (i-1) + "-" + coloana + "&x" + coloana + "-" + j + ")");
                        ok = 1;
                    }
                }
                out.print(")&~(");
                for(m = 1; m < i; m++) {
                    out.print("a" + m + "-" + j);
                    if(m != i-1)
                        out.print("|");
                }
                out.print("))))");
                if (j != N)
                    out.print("&");
            }
            if(i != N/2 + 1)
                out.print("&");
        }
    }
    
    public static void main(String [] args) {
        try{
            Scanner scanner = new Scanner(new File("graph.in"));
            FileWriter fileWriter = new FileWriter("bexpr.out");
            PrintWriter outWriter = new PrintWriter(fileWriter);
            
            int N = scanner.nextInt();
            
            
            int [][] matrice = new int[N + 1][N + 1];
            int i = scanner.nextInt();
            int j;
            while(scanner.hasNextInt()){
                j = scanner.nextInt();
                matrice[i][j] = 1;
                matrice[j][i] = 1;
                i = scanner.nextInt();
            }
            int ok = 0;
            for(i = 1; i <= N; i++) {
                ok = 0; 
                for(j = 1; j <= N; j++) 
                    if (matrice[i][j] == 1) ok ++;
                if(ok < 2) i = N + 1;
            } 
            if (ok >= 2) {
            conditieA(outWriter, N);    
                
            outWriter.print("&");
            conditieCiclu(outWriter, N, matrice);
            
            outWriter.print("&");
            conditieOrientare(outWriter, N, matrice);
            
            outWriter.print("&");
            conditieAPrimNod(outWriter, N, matrice);
            
            outWriter.print("&");
            conditieAuri(outWriter, N, matrice);
            }
            else {
                outWriter.print("x1-1&~x1-1");
            }
            scanner.close();
            outWriter.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
