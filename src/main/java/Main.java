import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        List<Callable<Integer>> tasks = new ArrayList<>();
        tasks.add(getThread(2200, 4));
        tasks.add(getThread(2000, 5));
        tasks.add(getThread(1500, 6));
        tasks.add(getThread(1000, 7));

        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        int resultTask = threadPool.invokeAny(tasks);

        System.out.println("Завершаем работу потоков.");
        threadPool.shutdown();
        System.out.println("Результат выполнения самой быстрой задачи = " + resultTask);

    }
    /**
     * Возвращает реализацию run() для работы потока
     * @param sleepTime частота работы потока
     * @return Runnable
     */
    public static Callable<Integer> getThread(long sleepTime, int numOfIterations) {
        return () -> {
            int consoleViewerCount = 0;
            String threadName = Thread.currentThread().getName();
            try {
                while (consoleViewerCount < numOfIterations) {
                    Thread.sleep(sleepTime);
                    System.out.println("Я " + threadName + ". Всем привет!");
                    consoleViewerCount++;
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(threadName + " завершил свою работу.");
            }
            return consoleViewerCount;
        };
    }
}
