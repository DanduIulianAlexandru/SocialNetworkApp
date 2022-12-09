package com.example.guisocialnetwork.domain;

import com.example.guisocialnetwork.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<Long, ArrayList<Long>> graphMap;
    /**
     * constructor
     * @param friends, lista de prieteni, tipul Iterable
     */
    public Graph(Iterable<Friendship> friends) {
        graphMap = new HashMap<>();
        for(Friendship friendship : friends){
            addEdge(friendship.getFirstFriend(), friendship.getSecondFriend());
            addEdge(friendship.getSecondFriend(), friendship.getFirstFriend());
        }
    }
    /**
     * adaugam o muchie in graful retelei
     * @param user, id-ul primului prieten, tipul Long
     * @param friend, id-ul celui de-al doilea prieten, tipul Long
     */
    public void addEdge(long user, long friend){
        ArrayList<Long> list = graphMap.get(user);
        if(list == null) {
            list = new ArrayList<>();
            list.add(friend);
            graphMap.put(user, list);
        }
        else{
            list.add(friend);
            graphMap.replace(user, list);
        }
    }
    /**
     * stergem o muchie din graful retelei
     * @param user, id-ul primului prieten, tipul Long
     * @param friend, id-ul celui de-al doilea prieten, tipul Long
     */
    public void deleteEdge(long user, long friend){
        ArrayList<Long> list = graphMap.get(user);
        if(list != null){
            list.remove(friend);
            graphMap.replace(user, list);
        }

    }

    /**
     * numarul de comunitati din  graful retelei = numarul de componente conexe
     *
     * @param users, lista de useri, tipul Iterable
     * @return numarul de comunitati, tipul Integer
     */
    public int theNumberOfComunities(Iterable<User>users){
        HashMap<Long, Integer> visited = new HashMap<>();
        for(User nod : users)
            visited.put(nod.getId(), 0);
        Utility utility = new Utility();
        utility.DFS(graphMap, visited, users.iterator().next().getId());
        int contor = 1;
        boolean ok = true;
        long x = 0;
        while(ok){
            for(User user : users) {
                if (visited.get(user.getId()) == 0) {
                    contor++;
                    ok = true;
                    x = user.getId();
                    break;
                }
                ok = false;
            }
            utility.DFS(graphMap, visited,x);
        }
        return contor;
    }

    /**
     * cea mai sociabila comunitate din graful retelei
     * @param users, lista de useri, tipul Iterable
     * @return lista cu id-urile userilor, tipul List
     */
    public List<Long> theMostSociableComunity(Iterable<User>users){
        HashMap<Long,Integer> visited = new HashMap<>();
        for(User user : users)
            visited.put(user.getId(), 0);
        Utility utility = new Utility();
        List<Long> comunity = new ArrayList<>();
        List<Long> finalComunity = new ArrayList<>();
        utility.DFSComunitate(graphMap, visited, users.iterator().next().getId(), comunity);
        int max = 0;
        boolean ok = true;
        long x = 0;
        while(ok){
            for(User user : users) {
                if (visited.get(user.getId()) == 0) {
                    ok = true;
                    x = user.getId();
                    break;
                }
                ok = false;
            }
            if(max < comunity.size())
            {
                max = comunity.size();
                finalComunity.removeIf(l -> l > 0); //clean up la lista
                finalComunity.addAll(comunity);
            }
            comunity.removeIf(l -> l > 0);
            utility.DFSComunitate(graphMap, visited, x, comunity);
        }
        return finalComunity;
    }
}
