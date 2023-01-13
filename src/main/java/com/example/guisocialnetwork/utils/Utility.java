package com.example.guisocialnetwork.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility {
    /**
     * parcurgere in adancime
     * @param graph graful retelei, tip Map
     * @param visited dictionar in care verific daca un nod a fost vizitat, tipul HashMap
     * @param x nodul din care incep cautarea, tipul Long
     */
    public void DFS(Map<Long, ArrayList<Long>> graph, HashMap<Long, Integer> visited, Long x){
        visited.replace(x, 1);
        if(graph.get(x) == null) {
            return;
        }
        for(long nod : graph.get(x)) {
            if(visited.get (nod) == null)
                continue;
            if(visited.get(nod) == 0)
                DFS(graph, visited, nod);
        }
    }
    /**
     * parcurgere in adanciume si adauga in lista "useri" userii decoperiti
     * @param graph graful retelei, tipul Map
     * @param vizited dictionar in care verific daca un nod a fost vizited, tipul HashMap
     * @param x nodul din care incep cautarea, tipul Long
     * @param useri lista de utilizatori din comunitate, tipul List
     */
    public void DFSComunitate(Map<Long, ArrayList<Long>> graph, HashMap<Long, Integer> vizited, Long x, List<Long> useri){
        useri.add(x);
        vizited.replace(x, 1);
        if(graph.get(x) == null) {
            return;
        }
        for(long nod : graph.get(x)) {
            if(vizited.get(nod) == null)
                continue;
            if(vizited.get(nod) == 0)
                DFSComunitate(graph, vizited, nod, useri);
        }
    }


}
