package DataStructures.Graph;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Set;

public class GraphGUI<T> extends JFrame {

    private Graph<T> graph;
    private JTextField vertexField;
    private JTextField sourceField;
    private JTextField destinationField;
    private JTextField weightField;
    private JPanel graphPanel;

    public GraphGUI(Graph<T> graph) {
        super("Graph Visualizer");
        this.graph = graph;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Initialize UI components
        initializeUI();
    }

    private void initializeUI() {
        // Layout for the entire frame
        setLayout(new BorderLayout());

        // Control panel for adding vertices and edges
        JPanel controlPanel = new JPanel();
        vertexField = new JTextField(5);
        JButton addVertexButton = new JButton("Add Vertex");
        sourceField = new JTextField(5);
        destinationField = new JTextField(5);
        weightField = new JTextField(5);
        JButton addEdgeButton = new JButton("Add Edge");

        addVertexButton.addActionListener(e -> addVertex());
        addEdgeButton.addActionListener(e -> addEdge());

        controlPanel.add(new JLabel("Vertex:"));
        controlPanel.add(vertexField);
        controlPanel.add(addVertexButton);
        controlPanel.add(new JLabel("Source:"));
        controlPanel.add(sourceField);
        controlPanel.add(new JLabel("Dest:"));
        controlPanel.add(destinationField);
        controlPanel.add(new JLabel("Weight:"));
        controlPanel.add(weightField);
        controlPanel.add(addEdgeButton);

        // Graph panel where the graph will be displayed
        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g);
            }
        };
        graphPanel.setPreferredSize(new Dimension(800, 500));
        graphPanel.setBackground(Color.WHITE);

        add(controlPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);
    }

    private void addVertex() {
        // Example conversion, adjust based on T's type
        T vertex = (T) vertexField.getText();
        graph.addVertex(vertex);
        repaint();
    }

    private void addEdge() {
        // Example conversion, adjust based on T's type
        T source = (T) sourceField.getText();
        T destination = (T) destinationField.getText();
        int weight = Integer.parseInt(weightField.getText());
        graph.addEdge(source, destination, weight);
        repaint();
    }

    private void drawGraph(Graphics g) {
        Set<T> vertices = graph.getVerticies();
        // Draw vertices
        // This is a placeholder, you need to map your vertices to coordinates

        // Draw edges
        for (T vertex : vertices) {
            List<Graph<T>.Edge> edges = graph.getEdges(vertex);
            for (Graph<T>.Edge edge : edges) {
                // Draw edge from vertex to edge.destination
                // Again, this requires mapping to screen coordinates
            }
        }
    }

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>(GraphType.DIRECTED);
        SwingUtilities.invokeLater(() -> {
            GraphGUI<String> visualizer = new GraphGUI<>(graph);
            visualizer.setVisible(true);
        });
    }
}