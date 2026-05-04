import java.util.*;

public class Scheduler {

    // Priority Scheduling
    public static List<Task> prioritySchedule(List<Task> tasks) {
        PriorityQueue<Task> pq = new PriorityQueue<>(
            (a, b) -> b.priority - a.priority
        );
        pq.addAll(tasks);

        List<Task> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(pq.poll());
        }
        return result;
    }

    // FCFS Scheduling
    public static List<Task> fcfsSchedule(List<Task> tasks) {
        return new ArrayList<>(tasks);
    }

    // Round Robin
    public static List<String> roundRobin(List<Task> tasks, int quantum) {
        Queue<Task> q = new LinkedList<>(tasks);
        List<String> result = new ArrayList<>();

        while (!q.isEmpty()) {
            Task t = q.poll();

            if (t.burstTime > quantum) {
                result.add(t.name + " (" + quantum + ")");
                t.burstTime -= quantum;
                q.add(t);
            } else {
                result.add(t.name + " (" + t.burstTime + ")");
            }
        }
        return result;
    }
}