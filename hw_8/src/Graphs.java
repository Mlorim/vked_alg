import java.util.*;

public class Graphs {
    static List<List<Integer>> findConnectedComponents(Map<Integer, List<Integer>> graph) {
            Map<Integer, Boolean> visited = new HashMap<>();
            for (int i = 1; i <= graph.size(); i++) {
                visited.put(i, false);
            }
            List<List<Integer>> connectedComponents = new ArrayList<>();
            for (int i = 1; i <= graph.size(); i++) {
                int currentNode = i;
                if (!visited.get(currentNode)) {
                    List<Integer> component = new ArrayList<>();
                    dfs(graph, currentNode, visited, component);
                    connectedComponents.add(component);
                }
            }
            return connectedComponents;
        }

    static void dfs(Map<Integer, List<Integer>> graph, int v, Map<Integer, Boolean> visited, List<Integer> component) {
        visited.put(v, true);
        component.add(v);
        for (int i : graph.get(v)) {
            if (!visited.get(i)) {
                dfs(graph, i, visited, component);
            }
        }
    }

    static void dfs_color(Map<Integer, List<Integer>> graph, int v, Map<Integer, Integer> visited, int color) {
        visited.put(v, color);
        for (int i : graph.get(v)) {
            if (visited.get(i) == 0) {
                dfs_color(graph, i, visited, color);
            }
        }
    }

    static Map<Integer, Integer> findConnectedComponents_color(Map<Integer, List<Integer>> graph) {
        Map<Integer, Integer> visited = new HashMap<>();
        for (int i = 1; i <= graph.size(); i++) {
            visited.put(i, 0);
        }
        int color = 0;
        for (int node : graph.keySet()) {
            if (visited.get(node) == 0) {
                color++;
                dfs_color(graph, node, visited, color);
            }
        }
        return visited;
    }

    static boolean hasCycle(Map<Integer, List<Integer>> graph) {
        List<Integer> visited = new ArrayList<>();
        for (int vertex : graph.keySet()) {
            if (!visited.contains(vertex)) {
                if (dfs_parent(graph, vertex, -1, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean dfs_parent(Map<Integer, List<Integer>> graph, int vertex, Integer parent, List<Integer> visited) {
        visited.add(vertex);
        for (int neighbor : graph.get(vertex)) {
            if (neighbor != parent) {
                if (visited.contains(neighbor) || dfs_parent(graph, neighbor, vertex, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean isTree(Map<Integer, List<Integer>> graph, int start) {
        List<Integer> visited = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        Map<Integer, Integer> parent = new HashMap<>();
        parent.put(start, null);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            visited.add(vertex);
            for (int neighbor : graph.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    parent.put(neighbor, vertex);
                } else {
                    if (parent.get(vertex) != neighbor) {
                        return false;
                    }
                }
            }
        }
        return visited.size() == graph.size();
    }

    static Map<Integer, Integer> dijkstra(Map<Integer, Map<Integer, Integer>> graph, int start) {
        Map<Integer, Integer> distances = new HashMap<>();
        for (int vertex : graph.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        priorityQueue.add(new int[]{0, start});

        while (!priorityQueue.isEmpty()) {
            int[] current = priorityQueue.poll();
            int currentDistance = current[0];
            int currentVertex = current[1];

            if (currentDistance > distances.get(currentVertex)) {
                continue;
            }

            for (Map.Entry<Integer, Integer> entry : graph.get(currentVertex).entrySet()) {
                int neighbor = entry.getKey();
                int weight = entry.getValue();
                int distance = currentDistance + weight;

                if (distance < distances.get(neighbor)) {
                    distances.put(neighbor, distance);
                    priorityQueue.add(new int[]{distance, neighbor});
                }
            }
        }
        return distances;
    }

    static boolean isBipartite(List<List<Integer>> graph) {
        int n = graph.size();
        int[] colors = new int[n];

        java.util.function.Function<Integer, Boolean> bfs = (start) -> {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(start);
            colors[start] = 1;

            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (int neighbor : graph.get(node)) {
                    if (colors[neighbor] == 0) {
                        colors[neighbor] = -colors[node];
                        queue.add(neighbor);
                    } else if (colors[neighbor] == colors[node]) {
                        return false;
                    }
                }
            }
            return true;
        };

        for (int i = 0; i < n; i++) {
            if (colors[i] == 0) {
                if (!bfs.apply(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean isBipartiteDFS(List<List<Integer>> graph) {
        int n = graph.size();
        int[] colors = new int[n];

        java.util.function.BiFunction<Integer, Integer, Boolean> dfs = new java.util.function.BiFunction<Integer, Integer, Boolean>() {
            public Boolean apply(Integer node, Integer c) {
                colors[node] = c;
                for (int neighbor : graph.get(node)) {
                    if (colors[neighbor] == 0) {
                        if (!apply(neighbor, -c)) {
                            return false;
                        }
                    } else if (colors[neighbor] == colors[node]) {
                        return false;
                    }
                }
                return true;
            }
        };

        for (int i = 0; i < n; i++) {
            if (colors[i] == 0) {
                if (!dfs.apply(i, 1)) {
                    return false;
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        // Тест 1: Поиск компонент связности
        System.out.println("Тест 1: Поиск компонент связности");
        Map<Integer, List<Integer>> graph1 = new HashMap<>();
        graph1.put(1, Arrays.asList(2, 3));
        graph1.put(2, Arrays.asList(1));
        graph1.put(3, Arrays.asList(1));
        graph1.put(4, Arrays.asList(5));
        graph1.put(5, Arrays.asList(4));
        graph1.put(6, Arrays.asList());

        List<List<Integer>> components = findConnectedComponents(graph1);
        System.out.println("Компоненты связности: " + components);

        Map<Integer, Integer> colorComponents = findConnectedComponents_color(graph1);
        System.out.println("Цветовая раскраска компонент: " + colorComponents);

        // Тест 2: Проверка на наличие цикла
        System.out.println("\nТест 2: Проверка на наличие цикла");
        Map<Integer, List<Integer>> graph2 = new HashMap<>();
        graph2.put(1, Arrays.asList(2, 3));
        graph2.put(2, Arrays.asList(1, 3));
        graph2.put(3, Arrays.asList(1, 2));

        boolean hasCycle = hasCycle(graph2);
        System.out.println("Граф содержит цикл: " + hasCycle);

        Map<Integer, List<Integer>> treeGraph = new HashMap<>();
        treeGraph.put(1, Arrays.asList(2, 3));
        treeGraph.put(2, Arrays.asList(1));
        treeGraph.put(3, Arrays.asList(1));

        boolean hasCycleTree = hasCycle(treeGraph);
        System.out.println("Дерево содержит цикл: " + hasCycleTree);

        // Тест 3: Проверка является ли граф деревом
        System.out.println("\nТест 3: Проверка является ли граф деревом");
        boolean isTree = isTree(treeGraph, 1);
        System.out.println("Граф является деревом: " + isTree);

        boolean isTreeGraph2 = isTree(graph2, 1);
        System.out.println("Граф с циклом является деревом: " + isTreeGraph2);

        // Тест 4: Алгоритм Дейкстры
        System.out.println("\nТест 4: Алгоритм Дейкстры");
        Map<Integer, Map<Integer, Integer>> weightedGraph = new HashMap<>();
        weightedGraph.put(1, new HashMap<>());
        weightedGraph.put(2, new HashMap<>());
        weightedGraph.put(3, new HashMap<>());
        weightedGraph.get(1).put(2, 1);
        weightedGraph.get(1).put(3, 4);
        weightedGraph.get(2).put(3, 2);
        weightedGraph.get(3).put(1, 4);

        Map<Integer, Integer> distances = dijkstra(weightedGraph, 1);
        System.out.println("Кратчайшие расстояния от вершины 1: " + distances);

        // Тест 5: Проверка на двудольность
        System.out.println("\nТест 5: Проверка на двудольность");
        List<List<Integer>> bipartiteGraph = new ArrayList<>();
        bipartiteGraph.add(Arrays.asList(1, 3)); // 0
        bipartiteGraph.add(Arrays.asList(0, 2)); // 1
        bipartiteGraph.add(Arrays.asList(1, 3)); // 2
        bipartiteGraph.add(Arrays.asList(0, 2)); // 3

        boolean isBipartite = isBipartite(bipartiteGraph);
        System.out.println("Граф является двудольным (BFS): " + isBipartite);

        boolean isBipartiteDFS = isBipartiteDFS(bipartiteGraph);
        System.out.println("Граф является двудольным (DFS): " + isBipartiteDFS);

        List<List<Integer>> nonBipartiteGraph = new ArrayList<>();
        nonBipartiteGraph.add(Arrays.asList(1, 2)); // 0
        nonBipartiteGraph.add(Arrays.asList(0, 2)); // 1
        nonBipartiteGraph.add(Arrays.asList(0, 1)); // 2

        boolean nonBipartite = isBipartite(nonBipartiteGraph);
        System.out.println("Не двудольный граф (BFS): " + nonBipartite);
    }
}