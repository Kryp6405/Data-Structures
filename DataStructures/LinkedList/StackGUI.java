package DataStructures.LinkedList;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.ActionEvent;

public class StackGUI<T> extends JFrame {
    private Stack<T> stack = new Stack<>();
    private JTextField inputField;
    private JLabel sizeLabel;
    private StackPanel stackPanel;

    public StackGUI() {
        setTitle("Stack Visualization");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);
        setupUI();
    }

    private void setupUI() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);
        sizeLabel = new JLabel("Size: 0");
        inputField = new JTextField(10);
        sizeLabel.setForeground(Color.WHITE);
        JButton pushButton = new JButton("Push");
        JButton popButton = new JButton("Pop");
        JButton peekButton = new JButton("Peek");
        topPanel.add(sizeLabel);
        topPanel.add(inputField);
        topPanel.add(pushButton);
        topPanel.add(popButton);
        topPanel.add(peekButton);

        // Custom stack panel for drawing
        stackPanel = new StackPanel(this);
        JScrollPane scrollPane = new JScrollPane(stackPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(stackPanel, BorderLayout.CENTER);

        // Button actions
        pushButton.addActionListener(this::pushAction);
        popButton.addActionListener(this::popAction);
        peekButton.addActionListener(this::peekAction);
    }

    private void pushAction(ActionEvent e) {
        String value = inputField.getText();
        stack.push((T) value); 
        inputField.setText("");
        stackPanel.repaintStack(stack, sizeLabel);
    }

    private void popAction(ActionEvent e) {
        if (!stack.isEmpty()) {
            stack.pop();
            stackPanel.repaintStack(stack, sizeLabel);
        }
    }

    private void peekAction(ActionEvent e) {
        if (!stack.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Top: " + stack.peek());
        }
    }

    class StackPanel extends JPanel {
        private final StackGUI<T> parentFrame;
        
        public StackPanel(StackGUI<T> parentFrame) {
            this.parentFrame = parentFrame;
            setBackground(Color.BLACK);
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawStack(g);
        }
    
        private void drawStack(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
            int centerX = getWidth() / 2;
            int baseY = getHeight() - 35;
            int stackWidth = 100;
            int stackHeight = parentFrame.stack.size() * 35 + 20;
            g2d.setStroke(new BasicStroke(3.6f));
            g2d.setColor(
                Color.getHSBColor(
                    Color.RGBtoHSB(169,169,169, new float[]{0,0,0})[0], 
                    Color.RGBtoHSB(169,169,169, new float[]{0,0,0})[1], 
                    Color.RGBtoHSB(169,169,169, new float[]{0,0,0})[2]
                )
            );
            g2d.drawOval(centerX - stackWidth / 2, baseY - stackHeight, stackWidth, 20);
            if(!stack.isEmpty()){
                g2d.drawLine(centerX - stackWidth / 2, baseY - stackHeight + 10, centerX - stackWidth / 2, baseY - 10);
                g2d.drawLine(centerX + stackWidth / 2, baseY - stackHeight + 10, centerX + stackWidth / 2, baseY - 10); 
            }
            

            g2d.drawArc(centerX - stackWidth / 2, baseY - 20, stackWidth, 20, 0, -180); // Bottom of cylinder

            for (int i = 0; i < parentFrame.stack.size(); i++) {
                int discYOld = baseY - i * 35 - 20;
                int discY = baseY - (i + 1) * 35 - 20;

                /*Arc2D outerArc = new Arc2D.Double(centerX - stackWidth / 2, discYOld, stackWidth, 20, 0, -180, Arc2D.OPEN);
                Arc2D innerArc = new Arc2D.Double(centerX - stackWidth / 2, discY, stackWidth, 20, 0, -180, Arc2D.OPEN);

                Area outerArea = new Area(outerArc);
                Area innerArea = new Area(innerArc);
                outerArea.subtract(innerArea);

                g2d.setColor(
                    Color.getHSBColor(
                        Color.RGBtoHSB(211,211,211, new float[]{0,0,0})[0], 
                        Color.RGBtoHSB(211,211,211, new float[]{0,0,0})[1], 
                        Color.RGBtoHSB(211,211,211, new float[]{0,0,0})[2]
                    )
                );
                g2d.fill(outerArea);
                */
                g2d.setColor(
                    Color.getHSBColor(
                        Color.RGBtoHSB(169,169,169, new float[]{0,0,0})[0], 
                        Color.RGBtoHSB(169,169,169, new float[]{0,0,0})[1], 
                        Color.RGBtoHSB(169,169,169, new float[]{0,0,0})[2]
                    )
                );
                g2d.drawArc(centerX - stackWidth / 2, discYOld, stackWidth, 20, 0, -180);
                g2d.drawArc(centerX - stackWidth / 2, discY, stackWidth, 20, 0, -180);

                int discHeight = 35;
                int textY = discY + discHeight / 2 + 20;

                FontMetrics fm = g2d.getFontMetrics();
                String text = stack.toList().get(i).toString();
                int textWidth = fm.stringWidth(text);

                g2d.setColor(Color.WHITE);
                g2d.drawString(text, centerX - textWidth / 2, textY + fm.getAscent() / 2 - fm.getDescent() / 2);
            }
            
            if(!stack.isEmpty()){
                g2d.drawString("Top", centerX + stackWidth / 2 + 10, baseY - stackHeight + 10);
                g2d.drawString("Bottom", centerX + stackWidth / 2 + 10, baseY - 5);
            } else{
                g2d.setColor(Color.WHITE);
                g2d.drawString("Empty\n(Top = Bottom)", centerX + stackWidth / 2 + 10, baseY - 5);
            }
        }

        public void repaintStack(Stack<T> stack, JLabel sizeLabel) {
            sizeLabel.setText("Size: " + stack.size());
            repaint(); 
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StackGUI<String>().setVisible(true));
    }
}