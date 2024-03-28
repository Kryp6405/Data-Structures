package DataStructures.Graph;
import javax.swing.*;
import java.util.*;
import java.util.Queue;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GraphGUI<T> extends JFrame {

    private Graph<T> graph;
    private JTextField vertexField;
    private JTextField sourceField;
    private JTextField destinationField;
    private JTextField weightField;
    private JPanel graphPanel;
    private final int vertexDiameter = 20;
    private final int startHeight = 30; // Start drawing from the top
    private final int levelHeight = 50; // Space between levels vertically
    private final int vertexWidth = 50; // Space between nodes horizontally
    private Map<T, Point> vertexPositions;

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
        vertexPositions = new HashMap<>();
        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g, graph.getAdjacencyList());
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

    /*private void drawGraph(Graphics g) {
    // Calculate spacing
    int margin = 50; // Margin from the edge of the panel
    int totalVertices = graph.getVerticies().size();
    int spacing = (graphPanel.getWidth() - 2 * margin) / Math.max(1, totalVertices - 1);

    // Temporary mapping of vertex to screen coordinates
    Map<T, Point> vertexPositions = new HashMap<>();
    int x = margin;
    int y = graphPanel.getHeight() / 2; // Draw all vertices on the same vertical level for simplicity

    // Assign positions and draw vertices
    for (T vertex : graph.getVerticies()) {
        vertexPositions.put(vertex, new Point(x, y));
        g.fillOval(x - 5, y - 5, 10, 10); // Draw the vertex
        x += spacing;
    }

    // Draw edges as directed arrows
    for (T vertex : graph.getVerticies()) {
        List<Graph<T>.Edge> edges = graph.getEdges(vertex);
        if (edges != null) {
            for (Graph<T>.Edge edge : edges) {
                Point source = vertexPositions.get(vertex);
                Point destination = vertexPositions.get(edge.getDestination());
                if (source != null && destination != null) {
                    drawArrow(g, source.x, source.y, destination.x, destination.y);
                }
            }
        }
    }
}*/

    private void drawGraph(Graphics g, Map<T, java.util.List<Graph<T>.Edge>> adjacencyList) {
        // Starting positions
        int y = startHeight;
        
        // Keep track of vertices that need to be processed
        Queue<T> queue = new LinkedList<>(graph.getVerticies());
        Set<T> processed = new HashSet<>();

        while (!queue.isEmpty()) {
            int size = queue.size();
            int x = (getWidth() - (size * vertexWidth)) / 2; // Center the nodes horizontally

            for (int i = 0; i < size; i++) {
                T vertex = queue.poll();
                if (!processed.contains(vertex)) {
                    processed.add(vertex);
                    if (!vertexPositions.containsKey(vertex)) {
                        vertexPositions.put(vertex, new Point(x, y));
                        drawVertex(g, vertex, x, y);
                    }

                    // Process edges
                    java.util.List<Graph<T>.Edge> edges = graph.getEdges(vertex);
                    if (edges != null) {
                        for (Graph<T>.Edge e : edges) {
                            if (!vertexPositions.containsKey(e.getDestination())) {
                                // If destination not processed yet, add it for processing
                                queue.offer(e.getDestination());
                            } else {
                                // Draw edge if destination already exists
                                drawEdge(g, vertex, e.getDestination());
                            }
                        }
                    }
                    x += vertexWidth;
                }
            }
            y += levelHeight; // Move down to the next level
        }
        
        // Draw edges after all vertices have been placed to avoid drawing over vertices
        for (Map.Entry<T, java.util.List<Graph<T>.Edge>> entry : adjacencyList.entrySet()) {
            T source = entry.getKey();
            for (Graph<T>.Edge e : entry.getValue()) {
                drawEdge(g, source, e.getDestination());
            }
        }
    }

    private void drawVertex(Graphics g, T label, int x, int y) {
        g.fillOval(x - vertexDiameter / 2, y - vertexDiameter / 2, vertexDiameter, vertexDiameter);
        g.drawString(label.toString(), x, y - vertexDiameter / 2);
    }

    private void drawEdge(Graphics g, T source, T destination) {
        Point start = vertexPositions.get(source);
        Point end = vertexPositions.get(destination);
        if (start != null && end != null) {
            drawArrow(g, start.x, start.y, end.x, end.y);
        }
    }

    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int arrowSize = 10; // Size of the arrow head
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - arrowSize, xn = xm, ym = arrowSize, yn = -arrowSize, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>(GraphType.DIRECTED);
        SwingUtilities.invokeLater(() -> {
            GraphGUI<String> visualizer = new GraphGUI<>(graph);
            visualizer.setVisible(true);
        });
    }
}