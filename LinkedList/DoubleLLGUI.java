package DataStructures.LinkedList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoubleLLGUI<T> extends JFrame {
    private DoubleLL<T> list;
    private JPanel drawingPanel;
    private JLabel sizeLabel; 
    private int isNewNode = 0, isGetNode = 0, isSetNode = 0, isRev = 0;  


    public DoubleLLGUI() {
        this.list = new DoubleLL<>();
        initUI();
    }

    private void initUI() {
        setTitle("Double Linked List Visualizer");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        sizeLabel = new JLabel("Size: 0");
        sizeLabel.setForeground(Color.WHITE);
        JPanel sizePanel = new JPanel();
        sizePanel.setBackground(Color.BLACK);
        sizePanel.add(sizeLabel);

        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.BLACK);
                drawList(g);
            }
        };
        drawingPanel.setPreferredSize(new Dimension(600, 200));
        
        //Add
        JLabel locationLabel = new JLabel("Loc:");
        JTextField locationField = new JTextField(5);
        JLabel valueLabel = new JLabel("Val:");
        JTextField valueField = new JTextField(5);
        locationLabel.setForeground(Color.WHITE); 
        valueLabel.setForeground(Color.WHITE);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = valueField.getText();
                String loc = locationField.getText();
                
                try{
                    if (!value.isEmpty()) {
                        if(!loc.isEmpty()){
                            list.add(Integer.parseInt(loc), (T) value);
                            locationField.setText("");
                            valueField.setText("");
                            drawingPanel.repaint();
                            isNewNode = Integer.parseInt(loc)+1;
                        } else {
                            list.add((T) value); 
                            valueField.setText("");
                            drawingPanel.repaint();
                            isNewNode = list.size();
                        }

                        updateSizeLabel();
                    }
                } catch(IndexOutOfBoundsException error){
                    JOptionPane.showMessageDialog(DoubleLLGUI.this, "Invalid Add", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Delete
        JLabel startLabel = new JLabel("Start:");
        JTextField startField = new JTextField(5);
        JLabel endLabel = new JLabel("End:");
        JTextField endField = new JTextField(5);
        JLabel atLabel = new JLabel("At:");
        JTextField atField = new JTextField(5);
        startLabel.setForeground(Color.WHITE); 
        endLabel.setForeground(Color.WHITE);
        atLabel.setForeground(Color.WHITE);
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = startField.getText();
                String end = endField.getText();
                String at = atField.getText();
                
                try{
                    if (!at.isEmpty() && start.isEmpty() && end.isEmpty()) {
                        list.remove(Integer.parseInt(at));
                        startField.setText("");
                        endField.setText("");
                        atField.setText("");
                        drawingPanel.repaint();
                    } else if(!(!at.isEmpty() && start.isEmpty() && end.isEmpty())){
                        list.removeAll(Integer.parseInt(start), Integer.parseInt(end));
                        startField.setText("");
                        endField.setText("");
                        atField.setText("");
                        drawingPanel.repaint();
                    } else {
                        throw new Exception();
                    }
                    updateSizeLabel();
                } catch(Exception error){
                    JOptionPane.showMessageDialog(DoubleLLGUI.this, "Invalid Delete", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Get
        JLabel getLocLabel = new JLabel("Loc:");
        JTextField getLocField = new JTextField(5);
        getLocLabel.setForeground(Color.WHITE); 
        JButton getButton = new JButton("Get");
        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String get = getLocField.getText();
                
                try{
                    if (!get.isEmpty()) {
                        list.get(Integer.parseInt(get));
                        getLocField.setText("");
                        drawingPanel.repaint();
                        isGetNode = Integer.parseInt(get)+1;
                    } else{
                        throw new Exception();
                    }
                } catch(Exception error){
                    JOptionPane.showMessageDialog(DoubleLLGUI.this, "Invalid Get", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Set
        JLabel setLocLabel = new JLabel("Loc:");
        JTextField setLocField = new JTextField(5);
        JLabel setValLabel = new JLabel("Val:");
        JTextField setValField = new JTextField(5);
        setLocLabel.setForeground(Color.WHITE); 
        setValLabel.setForeground(Color.WHITE); 
        JButton setButton = new JButton("Set");
        setButton.addActionListener(new ActionListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent e) {
                String loc = setLocField.getText();
                String val = setValField.getText();

                try{
                    if (!loc.isEmpty() && !val.isEmpty()) {
                        list.set(Integer.parseInt(loc), (T) val);
                        setLocField.setText("");
                        setValField.setText("");
                        drawingPanel.repaint();
                        isSetNode = Integer.parseInt(loc)+1;
                    } else{
                        throw new Exception();
                    }
                } catch(Exception error){
                    JOptionPane.showMessageDialog(DoubleLLGUI.this, "Invalid Set", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Reverse
        JButton revButton = new JButton("Reverse");
        revButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.reverse();
                isRev = 1;
                drawingPanel.repaint();
            }
        });

        //Reset
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.deleteAll();
                updateSizeLabel();
                drawingPanel.repaint();
            }
        });

        
        JPanel leftControlPanel = new JPanel();
        leftControlPanel.setLayout(new BoxLayout(leftControlPanel, BoxLayout.Y_AXIS)); // Set layout
        leftControlPanel.setBackground(Color.BLACK);

        // Add Panel
        JPanel addSetPanel = new JPanel();
        addSetPanel.setBackground(Color.BLACK);
        addSetPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addSetPanel.add(locationLabel);
        addSetPanel.add(locationField);
        addSetPanel.add(valueLabel);
        addSetPanel.add(valueField);
        addSetPanel.add(addButton);

        // Delete Panel
        JPanel deleteSetPanel = new JPanel();
        deleteSetPanel.setBackground(Color.BLACK);
        deleteSetPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        deleteSetPanel.add(startLabel);
        deleteSetPanel.add(startField);
        deleteSetPanel.add(endLabel);
        deleteSetPanel.add(endField);
        deleteSetPanel.add(atLabel);
        deleteSetPanel.add(atField);
        deleteSetPanel.add(deleteButton);

        leftControlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftControlPanel.add(addSetPanel);
        leftControlPanel.add(deleteSetPanel);
        
        JPanel middleControlPanel = new JPanel();
        middleControlPanel.setLayout(new BoxLayout(middleControlPanel, BoxLayout.Y_AXIS)); 
        middleControlPanel.setBackground(Color.BLACK);

        // Get Panel
        JPanel getSetPanel = new JPanel();
        getSetPanel.setBackground(Color.BLACK);
        getSetPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        getSetPanel.add(getLocLabel);
        getSetPanel.add(getLocField);
        getSetPanel.add(getButton);

        // Set Panel
        JPanel setSetPanel = new JPanel();
        setSetPanel.setBackground(Color.BLACK);
        setSetPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        setSetPanel.add(setLocLabel);
        setSetPanel.add(setLocField);
        setSetPanel.add(setValLabel);
        setSetPanel.add(setValField);
        setSetPanel.add(setButton);

        middleControlPanel.add(getSetPanel);
        middleControlPanel.add(setSetPanel);

        JPanel rightControlPanel = new JPanel();
        rightControlPanel.setLayout(new BoxLayout(rightControlPanel, BoxLayout.Y_AXIS)); 
        rightControlPanel.setBackground(Color.BLACK);

        // Reverse Panel
        JPanel revSetPanel = new JPanel();
        revSetPanel.setBackground(Color.BLACK);
        revSetPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        revSetPanel.add(revButton);

        JPanel resetSetPanel = new JPanel();
        resetSetPanel.setBackground(Color.BLACK);
        resetSetPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        resetSetPanel.add(resetButton);

        rightControlPanel.add(revSetPanel);
        rightControlPanel.add(resetSetPanel);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS)); 
        controlPanel.setBackground(Color.BLACK);

        controlPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        controlPanel.add(leftControlPanel);
        controlPanel.add(middleControlPanel);
        controlPanel.add(rightControlPanel);

        add(sizePanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);
        add(drawingPanel, BorderLayout.CENTER);
    }

    private void updateSizeLabel() {
        sizeLabel.setText("Size: " + list.size());
    }

    private void drawList(Graphics g) {
        DoubleLL<T>.Node<T> current = list.head;
        int x = 40, y = 80; // Starting position
        int counter = 1;
        while (current != null) {
            g.setColor(Color.WHITE);
            if(counter == isNewNode)
                g.setColor(Color.GREEN);
            else if(counter == isGetNode) 
                g.setColor(Color.YELLOW);
            else if(counter == isSetNode)
                g.setColor(Color.PINK);
            else if(counter == isRev++)
                g.setColor(Color.BLUE);
            else
                g.setColor(Color.WHITE);
            counter++;
            g.fillOval(x, y, 50, 50);
            g.setColor(Color.BLACK);
            FontMetrics fm = g.getFontMetrics();
            String text = current.val.toString();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            int textX = x + (50 - textWidth) / 2;
            int textY = y + (50 - textHeight) / 2 + fm.getAscent();
            g.setColor(Color.BLACK);
            g.drawString(text, textX, textY);
            if (current != null) {
                g.setColor(Color.WHITE);
                g.drawLine(x + 50, y + 10, x + 80, y + 10);
                g.fillPolygon(new int[]{x + 80, x + 90, x + 80},
                              new int[]{y + 15, y + 10, y + 5}, 3);
                g.drawLine(x, y + 40, x - 30, y + 40);
                g.fillPolygon(new int[]{x - 30, x - 40, x - 30},
                              new int[]{y + 45, y + 40, y + 35}, 3);
            }
            current = current.next;
            x += 90;
        }
        isNewNode = isGetNode = isSetNode = isRev = 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DoubleLLGUI<String> frame = new DoubleLLGUI<>();
                frame.setVisible(true);
            }
        });
    }
}