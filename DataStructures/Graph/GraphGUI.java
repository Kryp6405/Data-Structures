package DataStructures.Graph;

import java.awt.*;
import javax.swing.*;

/* Incomplete */
public class GraphGUI extends JFrame {
    private JRadioButton directedButton;
    private JRadioButton undirectedButton;
    private JButton createGraphButton;
    private Graph<String> graph;

    public GraphGUI() {
        createUI();
    }

    private void createUI() {
        setTitle("Graph Type Selector");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        ButtonGroup group = new ButtonGroup();
        directedButton = new JRadioButton("Directed", true);
        undirectedButton = new JRadioButton("Undirected", false);
        group.add(directedButton);
        group.add(undirectedButton);

        createGraphButton = new JButton("Create Graph");
        createGraphButton.addActionListener(e -> createGraph());

        add(directedButton);
        add(undirectedButton);
        add(createGraphButton);
    }

    private void createGraph() {
        GraphType type = directedButton.isSelected() ? GraphType.DIRECTED : GraphType.UNDIRECTED;
        graph = new Graph<>(type);
        
    }

    public static void main(String[] args) {
        /*SwingUtilities.invokeLater(() -> {
            new GraphGUI().setVisible(true);
        });*/

        Graph<String> graph = new Graph<>(GraphType.DIRECTED);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 1);
        graph.addEdge("B", "C", 1);
        graph.addEdge("B", "D", 2);
        graph.addEdge("C", "E", 1);
        graph.addEdge("D", "C", 1);
        graph.addEdge("D", "E", 2);
        graph.addEdge("D", "F", 3);
        graph.addEdge("F", "E", 1);

        System.out.println(graph.DFS("A"));

        graph.printTraversal(graph.BFS("A"));
    }
}
