import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class TaskSchedulerGUI extends JFrame {

    ArrayList<Task> tasks = new ArrayList<>();
    JTextArea output;
public TaskSchedulerGUI() {

    setTitle("Task Scheduler");
    setSize(700, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // MAIN PANEL
    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    add(mainPanel);

    // INPUT PANEL
    JPanel inputPanel = new JPanel(new GridLayout(2, 3, 10, 10));
    inputPanel.setBorder(BorderFactory.createTitledBorder("Add Task"));

    JTextField name = new JTextField();
    JTextField priority = new JTextField();
    JTextField time = new JTextField();

    inputPanel.add(new JLabel("Name"));
    inputPanel.add(new JLabel("Priority"));
    inputPanel.add(new JLabel("Time"));

    inputPanel.add(name);
    inputPanel.add(priority);
    inputPanel.add(time);

    // BUTTON PANEL
    JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
    buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

    JButton add = new JButton("Add");
    JButton priorityBtn = new JButton("Priority");
    JButton fcfsBtn = new JButton("FCFS");
    JButton rrBtn = new JButton("Round Robin");
    JButton clearBtn = new JButton("Clear");
    JButton stopBtn = new JButton("Stop");

    buttonPanel.add(add);
    buttonPanel.add(priorityBtn);
    buttonPanel.add(fcfsBtn);
    buttonPanel.add(rrBtn);
    buttonPanel.add(clearBtn);
    buttonPanel.add(stopBtn);

    // OUTPUT
    output = new JTextArea();
    output.setEditable(false);
    output.setFont(new Font("Consolas", Font.PLAIN, 14));
    output.setBackground(new Color(40, 44, 52));   
    output.setForeground(new Color(220, 220, 220)); 

    JScrollPane scroll = new JScrollPane(output);
    scroll.setBorder(BorderFactory.createTitledBorder("Execution Output"));

    // ADD PANELS
    mainPanel.add(inputPanel, BorderLayout.NORTH);
    mainPanel.add(scroll, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    // BUTTON COLORS
    add.setBackground(new Color(76, 175, 80));   
    clearBtn.setBackground(new Color(244, 67, 54)); 
    stopBtn.setBackground(new Color(255, 152, 0));  

    add.setForeground(Color.WHITE);
    clearBtn.setForeground(Color.WHITE);
    stopBtn.setForeground(Color.BLACK);

        // 🔹 ADD TASK
        add.addActionListener(e -> {
            try {
                String taskName = name.getText().trim();
                int p = Integer.parseInt(priority.getText().trim());
                int t = Integer.parseInt(time.getText().trim());

                tasks.add(new Task(taskName, p, t));
                output.append("✔ Task Added: " + taskName + "\n");

                name.setText("");
                priority.setText("");
                time.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter valid inputs!");
            }
        });

        // 🔹 PRIORITY
        priorityBtn.addActionListener(e -> {
            if (tasks.isEmpty()) return;

            List<Task> res = Scheduler.prioritySchedule(tasks);

            int waitingTime = 0, totalWaiting = 0;

            output.append("Priority Scheduling:\n");

            for (Task t : res) {
                output.append(t.name + " (WT: " + waitingTime + ")\n");
                totalWaiting += waitingTime;
                waitingTime += t.burstTime;
            }

            output.append("Avg WT: " + (totalWaiting / res.size()) + "\n");
        });

        // 🔹 FCFS
        fcfsBtn.addActionListener(e -> {
            if (tasks.isEmpty()) return;

            List<Task> res = Scheduler.fcfsSchedule(tasks);

            int waitingTime = 0, totalWaiting = 0;

            output.append("FCFS Scheduling:\n");
            for (Task t : res) {
                output.append(t.name + " (WT: " + waitingTime + ")\n");
                totalWaiting += waitingTime;
                waitingTime += t.burstTime;
            }

            output.append("Avg WT: " + (totalWaiting / res.size()) + "\n");
        });

        // 🔹 ROUND ROBIN
        rrBtn.addActionListener(e -> {
            if (tasks.isEmpty()) return;

            List<String> res = Scheduler.roundRobin(tasks, 2);

            output.append("Round Robin:\n");
            for (String s : res) {
                output.append(s + "\n");
            }
        });

        // 🔹 CLEAR
        clearBtn.addActionListener(e -> {
            tasks.clear();
            output.setText("");
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new TaskSchedulerGUI();
    }
}