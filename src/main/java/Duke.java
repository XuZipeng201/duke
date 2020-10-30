import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    public static void main(String[] args) {
        /*String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";*/

        /* A greeting message */

        String logo = " ____  _      ,-.___,-.     \n"
                     + "|  _ \\|_|     \\_/_ _\\_/ \n"
                     + "| |_| | |       )O_O(      \n"
                     + "|  __/| |      { (_) }     \n"
                     + "|_|   |_|       `-^-'      \n";

        System.out.println("Hello,\n" + logo + "I'm Pi,\n" + "What can I do for you?");


        String input;
        Scanner in = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<Task>();
        input = in.nextLine();

        while (!input.equals("bye")) {
            String[] inputs = input.split(" ");

            if (input.equals("list")) {
                printList(tasks);
            } else if (input.contains("done")) {
                int finishedTask = Integer.valueOf(inputs[1]);
                tasks.get(finishedTask - 1).markAsDone();

                System.out.println("Nice! I've marked this task as done:");
                System.out.println(" " + tasks.get(finishedTask - 1).getTaskListInfo());

            } else if (input.contains("delete")) {
                int removedTask = Integer.valueOf(inputs[1]);
                System.out.println("Noted. I've removed this task:");
                System.out.println(" " + tasks.get(removedTask - 1).getTaskListInfo());

                tasks.remove(removedTask - 1);

                System.out.println("Now you have " + tasks.size() + " task(s) in the list.");

            } else {
                try {
                    addTask(input, inputs, tasks);

                    System.out.println("Now you have " + tasks.size() + " task(s) in the list.");

                } catch (DukeException exception) {
                    System.out.println(exception);
                }
            }
              input = in.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");

    }


    /*Add new Task*/
    public static void addTask(String userInput,String[] inputs, ArrayList<Task> tasks) throws DukeException{

        if (inputs[0].startsWith("deadline")) {
            if (inputs.length == 1) {
                throw new DukeException(userInput);
            } else if (userInput.endsWith("/by")) {
                throw new DukeException(userInput);
            } else if ((inputs.length > 1) && (!userInput.contains("/by"))) {
                throw new DukeException(userInput);
            }

            int findPosition = userInput.indexOf("/by");
            String taskName = userInput.substring(9, findPosition);
            String taskTime = userInput.substring(findPosition + 4, userInput.length());
//            if (taskTime.equals(" ")) {
//                throw new DukeException("  ☹ OOPS!!! Please input a timing for this task");
//            }
            tasks.add(new Deadline(taskName, taskTime));

        } else if (inputs[0].startsWith("event")) {
            if (inputs.length == 1){
                throw new DukeException(userInput);
            } else if (userInput.endsWith("/at")) {
                throw new DukeException(userInput);
            } else if ((inputs.length > 1) && (!userInput.contains("/at"))) {
                throw new DukeException(userInput);
            }

            int findPosition = userInput.indexOf("/at");

            String taskName = userInput.substring(6, findPosition);
            String taskTime = userInput.substring(findPosition + 4, userInput.length());

            tasks.add(new Event(taskName, taskTime));

        } else if (inputs[0].startsWith("todo")) {
            if (inputs.length == 1) {
                throw new DukeException(userInput);
            }
            String taskName = userInput.substring(5);
            tasks.add(new ToDo(taskName));
        } else {
            throw new DukeException(userInput);
        }

        System.out.println("Got it. I've added this task:");
        System.out.println("  " + tasks.get(tasks.size() - 1).getTaskListInfo());

    }


    /*print out the user input from the list*/
    public static void printList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        if (tasks.size() == 0){
            System.out.println("Currently your list is empty.");
        }

        for (int i = 0; i < tasks.size(); i++ ) {
            System.out.println((i + 1) + ". " + tasks.get(i).getTaskListInfo());
        }
    }


}
