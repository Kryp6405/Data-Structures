package DataStructures.Tree;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.Timer;

public class BinaryTreeGUI<V> extends JFrame {
    private BinarySearchTree<Integer, V> bst = new BinarySearchTree<>();
    private JTextField insertField1, insertField2, rotateField, deleteField;
    private JButton insertButton, rotateRightButton, rotateLeftButton, deleteButton, preOrderBtn, inOrderBtn, postOrderBtn, levelOrderBtn;
    private TreePanel treePanel = new TreePanel();
    private JTextArea outputArea;


    public BinaryTreeGUI() {
        super("BST Operations GUI");
        setLayout(new BorderLayout());
        setSize(1050, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Setup control panel
        JPanel controlPanel = new JPanel(new GridLayout(1, 3));

        JPanel insertPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        insertField1 = new JTextField(5);
        insertField2 = new JTextField(5);
        insertButton = new JButton("Insert");
        insertPanel.add(new JLabel("Key:"));
        insertPanel.add(insertField1);
        insertPanel.add(new JLabel("Val:"));
        insertPanel.add(insertField2);
        insertPanel.add(insertButton);

        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        deleteField = new JTextField(5);
        deleteButton = new JButton("Delete");
        deletePanel.add(new JLabel("Key:"));
        deletePanel.add(deleteField);
        deletePanel.add(deleteButton);

        JPanel rotatePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rotateField = new JTextField(5);
        rotateRightButton = new JButton("Rotate Right");
        rotateLeftButton = new JButton("Rotate Left");
        rotatePanel.add(new JLabel("Key:"));
        rotatePanel.add(rotateField);
        rotatePanel.add(rotateRightButton);
        rotatePanel.add(rotateLeftButton);

        controlPanel.add(insertPanel);
        controlPanel.add(deletePanel);
        controlPanel.add(rotatePanel);

        
        JPanel traversalPanel = new JPanel();
        preOrderBtn = new JButton("Pre-Order");
        inOrderBtn = new JButton("In-Order");
        postOrderBtn = new JButton("Post-Order");
        levelOrderBtn = new JButton("Level-Order");
        setupTraversalActions();
        traversalPanel.add(preOrderBtn);
        traversalPanel.add(inOrderBtn);
        traversalPanel.add(postOrderBtn);
        traversalPanel.add(levelOrderBtn);

        outputArea = new JTextArea(3, 40);
        outputArea.setEditable(false);
        JScrollPane outputPane = new JScrollPane(outputArea);

        JPanel traversalControlPanel = new JPanel();
        traversalControlPanel.setLayout(new BoxLayout(traversalControlPanel, BoxLayout.Y_AXIS)); 
        traversalControlPanel.add(outputPane);
        traversalControlPanel.add(traversalPanel);
        
        // Action listeners
        insertButton.addActionListener(this::insertNode);
        deleteButton.addActionListener(this::deleteNode);
        rotateRightButton.addActionListener(this::rotateRightNode);
        rotateLeftButton.addActionListener(this::rotateLeftNode);

        // Adding components to frame
        add(controlPanel, BorderLayout.NORTH);
        add(treePanel, BorderLayout.CENTER);
        add(traversalControlPanel, BorderLayout.SOUTH); 
    }

    private void insertNode(ActionEvent e) {
        try {
            String key = insertField1.getText();
            String val = insertField2.getText();

            bst.insert(Integer.parseInt(key), val.isEmpty() ? (V) ("Node " + key) : (V) val); 
            insertField1.setText("");
            insertField2.setText("");
            treePanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter an integer.");
        }
    }

    private void deleteNode(ActionEvent e) {
        try {
            String key = deleteField.getText();

            bst.delete(Integer.parseInt(key)); 
            deleteField.setText("");
            treePanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter an integer.");
        }
    }

    private void rotateRightNode(ActionEvent e){
        try {
            String key = rotateField.getText();
            
            bst.rotateRight(Integer.parseInt(key)); 
            rotateField.setText("");
            treePanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter an integer.");
        }
    }

    private void rotateLeftNode(ActionEvent e){
        try {
            String key = rotateField.getText();
            
            bst.rotateLeft(Integer.parseInt(key)); 
            rotateField.setText("");
            treePanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter an integer.");
        }
    }

    private void setupTraversalActions() {
        preOrderBtn.addActionListener(e -> {
            ArrayList<BinarySearchTree<Integer,V>.Node> order = new ArrayList<>();
            bst.preOrderList(bst.root, order);
            highlightAndDisplayOrder(order);
        });
    
        inOrderBtn.addActionListener(e -> {
            ArrayList<BinarySearchTree<Integer,V>.Node> order = new ArrayList<>();
            bst.inOrderList(bst.root, order);
            highlightAndDisplayOrder(order);
        });

        postOrderBtn.addActionListener(e -> {
            ArrayList<BinarySearchTree<Integer,V>.Node> order = new ArrayList<>();
            bst.postOrderList(bst.root, order);
            highlightAndDisplayOrder(order);
        });

        levelOrderBtn.addActionListener(e -> {
            ArrayList<BinarySearchTree<Integer,V>.Node> order = new ArrayList<>();
            bst.levelOrderList(bst.root, order);
            highlightAndDisplayOrder(order);
        });
    }
    
    private void highlightAndDisplayOrder(ArrayList<BinarySearchTree<Integer,V>.Node> order) {
        Iterator<BinarySearchTree<Integer,V>.Node> iterator = order.iterator();
        StringBuilder traversalResult = new StringBuilder();

        Timer timer = new Timer(1000, null);
        timer.addActionListener(e -> {
            if (iterator.hasNext()) {
                BinarySearchTree<Integer,V>.Node currentNode = iterator.next();
                treePanel.setHighlightedNode(currentNode);
                traversalResult.append("[").append(currentNode.key).append(", ").append(currentNode.val).append("] --> ");
                outputArea.setText(traversalResult.toString()); 
            } else {
                outputArea.setText(traversalResult.append("Finished").toString());
                timer.stop();
                treePanel.setHighlightedNode(null);
            }
        });
        timer.start(); 
    }

    class TreePanel extends JPanel {
        private final int NODE_SIZE = 40;
        private final int LEVEL_GAP = 40;
        private BinarySearchTree<Integer,V>.Node highlightedNode = null;

        public void setHighlightedNode(BinarySearchTree<Integer,V>.Node node) {
            this.highlightedNode = node;
            repaint();  
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bst.root != null) {
                drawTree(g, getWidth() / 2, 40, bst.root, getWidth() / 6);
            }
        }

        private void drawTree(Graphics g, int x, int y, BinarySearchTree<Integer,V>.Node node, int xGap) {
            if (node == null) 
                return;

            if (node.equals(highlightedNode))
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.YELLOW);
            g.fillOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
            
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(node.key), x - 5, y + 5);

            g.setColor(Color.BLACK);
            if (node.left != null) {
                g.drawLine(x, y, x - xGap, y + LEVEL_GAP);
                drawTree(g, x - xGap, y + LEVEL_GAP, node.left, xGap * 7 / 10);
            }
            if (node.right != null) {
                g.drawLine(x, y, x + xGap, y + LEVEL_GAP);
                drawTree(g, x + xGap, y + LEVEL_GAP, node.right, xGap * 7 / 10);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BinaryTreeGUI<String> frame = new BinaryTreeGUI<>();
            frame.setVisible(true);
        });
    }
}