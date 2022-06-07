import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            String nextLine = scanner.nextLine();

            if (nextLine.equals("")) {
                graph.add(new ArrayList<>());
            } else {
                List<Integer> adjacentNodes = Arrays.stream(nextLine.split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                graph.add(adjacentNodes);

            }

        }
        List<Deque<Integer>> connectedComponents =
                getConnectedComponents(graph);

        for (Deque<Integer> connectedComponent : connectedComponents) {
            System.out.print("Connected component: ");
            for (Integer integer : connectedComponent) {
                System.out.print(integer + " ");
            }
            System.out.println();

        }
    }

    public static List<Deque<Integer>> getConnectedComponents(List<List<Integer>> graph) {
        List<Deque<Integer>> components = new ArrayList<>();

        boolean[] visited = new boolean[graph.size()];

        for (int start = 0; start < graph.size(); start++) {
            if (!visited[start]) {
                components.add(new ArrayDeque<>());
                bfs(start, components, graph, visited);
                System.out.println();
            }

        }
        return components;
    }

    private static void bfs(int start, List<Deque<Integer>> components, List<List<Integer>> graph, boolean[] visited) {
        Deque<Integer> stack = new ArrayDeque<>();
        visited[start] = true;
        stack.push(start);

        while (!stack.isEmpty()) {
            int node = stack.poll();
            components.get(components.size() - 1).offer(node);

            for (int child : graph.get(node)) {
                if (!visited[child]) {
                    visited[child] = true;
                    stack.offer(child);
                }
            }
        }
    }

    private static void dfs(int node, List<Deque<Integer>> components, List<List<Integer>> graph, boolean[] visited) {
        if (!visited[node]) {
            visited[node] = true;
            for (int child : graph.get(node)) {
                dfs(child, components, graph, visited);
            }
            components.get(components.size() - 1).offer(node);
        }
    }

//    public static Collection<String> topSort(Map<String, List<String>> graph) {
//
//        Map<String, Integer> dependenciesCount = getDependenciesCount(graph);
//        List<String> sorted = new ArrayList<>();
//
//        while (!graph.isEmpty()) {
//            String key = graph.keySet()
//                    .stream()
//                    .filter(k -> dependenciesCount.get(k) == 0)
//                    .findFirst()
//                    .orElse(null);
//
//            if (key == null) {
//                break;
//            }
//
//            for (String child : graph.get(key)) {
//                dependenciesCount.put(child, dependenciesCount.get(child) - 1);
//            }
//
//            sorted.add(key);
//            graph.remove(key);
//        }
//
//        if(!graph.isEmpty()) {
//            throw new IllegalArgumentException();
//        }
//
//        return sorted;
//    }

//    private static Map<String, Integer> getDependenciesCount(Map<String, List<String>> graph) {
//        Map<String, Integer> dependenciesCount = new LinkedHashMap<>();
//        for (Map.Entry<String, List<String>> node : graph.entrySet()) {
//            dependenciesCount.putIfAbsent(node.getKey(), 0);
//            for (String child : node.getValue()) {
//                dependenciesCount.putIfAbsent(child, 0);
//                dependenciesCount.put(child, dependenciesCount.get(child) + 1);
//            }
//
//        }
//        return dependenciesCount;
//
//    }


    public static Collection<String> topSort(Map<String, List<String>> graph) {
        List<String> sorted = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Set<String> detectCycles = new HashSet<>();

        for (Map.Entry<String, List<String>> node : graph.entrySet()) {

            dfs(node.getKey(), visited, graph, sorted, detectCycles);
        }
        Collections.reverse(sorted);


        return sorted;
    }

    private static void dfs(String key, Set<String> visited, Map<String, List<String>> graph, List<String> sorted, Set<String> detectCycles) {


        if (detectCycles.contains(key)) {
            throw new IllegalArgumentException();
        }

        if (!visited.contains(key)) {
            visited.add(key);
            detectCycles.add(key);
            for (String child : graph.get(key)) {
                dfs(child, visited, graph, sorted, detectCycles);
            }
            detectCycles.remove(key);
            sorted.add(key);
        }


    }

}
