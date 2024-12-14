import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class MoodCalendarApp extends JFrame {

    private JTable calendarTable;
    private JLabel monthLabel;
    private YearMonth currentMonth;
    private final Map<LocalDate, String> moodHistory;
    private final Map<String, Color> moodColors = Map.of(
            "Happy", Color.YELLOW,
            "Sad", Color.BLUE,
            "Anxious", Color.ORANGE,
            "Stressed", Color.RED,
            "Uncertain", Color.BLACK
    );

    public MoodCalendarApp() {
        this.moodHistory = new HashMap<>();
        // Initialize UI components
        initializeUI();
    }

    private void initializeUI() {
        // Set JFrame properties
        setTitle("Mood Mosaic");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null); // Center the frame on the screen

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
        private final String mood;

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
            
