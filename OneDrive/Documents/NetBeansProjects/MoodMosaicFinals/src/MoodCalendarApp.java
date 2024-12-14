/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Lance
 * 
 */
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

    private void setDefaultCloseOperation(int EXIT_ON_CLOSE) {        
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Container getContentPane() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void pack() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void setLocationRelativeTo(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
public class MoodCalendarApp extends JFrame {
    private JTable calendarTable;
    private JLabel monthLabel;
    private YearMonth currentMonth;
    private Map<LocalDate, String> moodHistory = new HashMap<>();
    private final Map<String, Color> moodColors = Map.of(
            "Happy", Color.YELLOW,
            "Sad", Color.BLUE,
            "Anxious", Color.ORANGE,
            "Stressed", Color.RED,
            "Uncertain", Color.BLACK
    );

    /**
     * Creates new form MoodCalendarApp
     */
    public MoodCalendarApp() {
initializeUI();

    }

     private void initializeUI() {
        setTitle("Mood Mosaic");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        JPanel panel = new JPanel(new BorderLayout());

        // Header panel for navigation
        JPanel headerPanel = new JPanel(new BorderLayout());    
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        monthLabel = new JLabel("", SwingConstants.CENTER);
        currentMonth = YearMonth.now();
        updateMonthLabel();

        prevButton.addActionListener(e -> changeMonth(-1));
        nextButton.addActionListener(e -> changeMonth(1));

        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);

        // Calendar table
        calendarTable = new JTable();
        calendarTable.setRowHeight(80);
        calendarTable.setDefaultRenderer(Object.class, new CalendarCellRenderer());
        updateCalendar();

        // Mood selection buttons
        JPanel moodPanel = new JPanel(new GridLayout(1, 5));
        for (String mood : moodColors.keySet()) {
            JButton moodButton = new JButton(mood);
            moodButton.setBackground(moodColors.get(mood));
            moodButton.setOpaque(true);
            moodButton.setForeground(Color.WHITE);
            moodButton.addActionListener(new MoodButtonListener(mood));
            moodPanel.add(moodButton);
        }

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(calendarTable), BorderLayout.CENTER);
        panel.add(moodPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void updateMonthLabel() {
        monthLabel.setText(currentMonth.getMonth() + " " + currentMonth.getYear());
    }

    private void changeMonth(int offset) {
        currentMonth = currentMonth.plusMonths(offset);
        updateMonthLabel();
        updateCalendar();
    }

    private void updateCalendar() {
        DefaultTableModel model = new DefaultTableModel();
        model.setRowCount(0);

        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        model.setColumnIdentifiers(daysOfWeek);

        LocalDate firstOfMonth = currentMonth.atDay(1);
        int startDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = currentMonth.lengthOfMonth();

        Object[] week = new Object[7];
        int day = 1;

        for (int i = 0; i < startDayOfWeek; i++) {
            week[i] = null;
        }

        for (int i = startDayOfWeek; i < 7; i++) {
            week[i] = day++;
        }
        model.addRow(week);

        while (day <= daysInMonth) {
            week = new Object[7];
            for (int i = 0; i < 7 && day <= daysInMonth; i++) {
                week[i] = day++;
            }
            model.addRow(week);
        }

        calendarTable.setModel(model);
    }

    private class MoodButtonListener implements ActionListener {
        private String mood;

        public MoodButtonListener(String mood) {
            this.mood = mood;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = calendarTable.getSelectedRow();
            int selectedColumn = calendarTable.getSelectedColumn();

            if (selectedRow != -1 && selectedColumn != -1) {
                Object value = calendarTable.getValueAt(selectedRow, selectedColumn);
                if (value != null) {
                    LocalDate selectedDate = currentMonth.atDay((int) value);
                    moodHistory.put(selectedDate, mood);
                    calendarTable.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(MoodCalendarApp.this, "Please select a day on the calendar.");
            }
        }
    }

    private class CalendarCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cell.setHorizontalAlignment(SwingConstants.CENTER);
            cell.setVerticalAlignment(SwingConstants.CENTER);

            if (value != null) {
                LocalDate date = currentMonth.atDay((int) value);
                String mood = moodHistory.get(date);
                if (mood != null) {
                    cell.setBackground(moodColors.get(mood));
                } else {
                    cell.setBackground(Color.WHITE);
                }
            } else {
                cell.setBackground(Color.LIGHT_GRAY);
            }

            return cell;
        }
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // </editor-fold>
@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MoodCalendarApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MoodCalendarApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MoodCalendarApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MoodCalendarApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {
            // The constructor will handle the visibility of the JFrame
            
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

