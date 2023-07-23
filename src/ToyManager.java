import model.Toy;
import model.ToyWithCount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class ToyManager {

    private final List<ToyWithCount> toys;
    private final Scanner scanner;

    public ToyManager() {
        toys = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void listToys() {
        if (toys.isEmpty()) {
            System.out.println("Список игрушек пуст.");
            return;
        }
        for (ToyWithCount toyWithCount : toys) {
            System.out.println(toyWithCount.getToy().getToyName() + " (ID: " + toyWithCount.getToy().getId() + ")");
        }
    }

    public void checkInventory() {
        if (toys.isEmpty()) {
            System.out.println("Список игрушек пуст.");
            return;
        }
        for (ToyWithCount toyWithCount : toys) {
            System.out.println(toyWithCount.getToy().getToyName() + " - Остаток: " + toyWithCount.getCount());
        }
    }

    public void editChance() {
        System.out.println("Введите ID игрушки для изменения шанса выпадения:");
        Integer id = scanner.nextInt();
        for (ToyWithCount toyWithCount : toys) {
            if (toyWithCount.getToy().getId().equals(id)) {
                System.out.println("Введите новый шанс выпадения:");
                Integer newWinRate = scanner.nextInt();
                toyWithCount.getToy().setWeight(newWinRate);
                System.out.println("Шанс выпадения изменен успешно.");
                return;
            }
        }
        System.out.println("Игрушка с указанным ID не найдена.");
    }

    public void editInventory() {
        System.out.println("Введите ID игрушки для изменения остатка:");
        Integer id = scanner.nextInt();
        for (ToyWithCount toyWithCount : toys) {
            if (toyWithCount.getToy().getId().equals(id)) {
                System.out.println("Введите новый остаток:");
                Integer newCount = scanner.nextInt();
                toyWithCount.setCount(newCount);
                System.out.println("Остаток изменен успешно.");
                return;
            }
        }
        System.out.println("Игрушка с указанным ID не найдена.");
    }

    public void addToy() {
        System.out.println("Введите ID для новой игрушки:");
        Integer id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите название новой игрушки:");
        String name = scanner.nextLine();
        System.out.println("Введите шанс выпадения новой игрушки:");
        Integer winRate = scanner.nextInt();
        System.out.println("Введите остаток новой игрушки:");
        Integer count = scanner.nextInt();
        Toy toy = new Toy(id, name, winRate);
        ToyWithCount toyWithCount = new ToyWithCount(toy);
        toyWithCount.setCount(count);
        toys.add(toyWithCount);
        System.out.println("Игрушка добавлена успешно.");
    }

    public void removeToy() {
        System.out.println("Введите ID игрушки для удаления:");
        Integer id = scanner.nextInt();
        for (ToyWithCount toyWithCount : toys) {
            if (toyWithCount.getToy().getId().equals(id)) {
                toys.remove(toyWithCount);
                System.out.println("Игрушка удалена успешно.");
                return;
            }
        }
        System.out.println("Игрушка с указанным ID не найдена.");
    }

    public void conductLottery() {
        if (toys.isEmpty()) {
            System.out.println("Нет игрушек для розыгрыша.");
            return;
        }

        // считаем полный вес всех наименований игрушек, которые в наличии
        int totalWeight = 0;

        for (ToyWithCount toy : toys) {
            if (toy.getCount() > 0) {
                totalWeight += toy.getToy().getWeight();
            }
        }

        // генерируем случайное число в диапазоне от 0 до полного веса всех игрушек
        int randomNumber = new Random().nextInt(totalWeight);

        int sum = 0;
        for (ToyWithCount toyWithCount : toys) {

            // начинаем суммировать веса игрушек
            sum += toyWithCount.getToy().getWeight();

            // до тех пор, пока общий вес не будет больше сгенерированного числа
            if (randomNumber < sum) {
                System.out.println("Поздравляем! Вы выиграли " + toyWithCount.getToy().getToyName());

                // уменьшаем количество оставшихся игрушек
                toyWithCount.setCount(toyWithCount.getCount() - 1);
                return;
            }
        }

        System.out.println("Произошла ошибка при проведении розыгрыша.");
    }

    public void loadFromFile() {
        try {
            FileReader toyReader = new FileReader("toys.csv");
            BufferedReader toyBufferedReader = new BufferedReader(toyReader);

            FileReader countReader = new FileReader("toys_count.csv");
            BufferedReader countBufferedReader = new BufferedReader(countReader);

            // Используем Map для временного хранения остатков игрушек по ID
            Map<Integer, Integer> countMap = new HashMap<>();
            String line;
            while ((line = countBufferedReader.readLine()) != null) {
                String[] countData = line.split(",");
                Integer id = Integer.parseInt(countData[0]);
                Integer count = Integer.parseInt(countData[1]);
                countMap.put(id, count);
            }

            toys.clear();  // Очищаем текущий список игрушек
            while ((line = toyBufferedReader.readLine()) != null) {
                String[] toyData = line.split(",");
                Integer id = Integer.parseInt(toyData[0]);
                String name = toyData[1];
                Integer winRate = Integer.parseInt(toyData[2]);

                // Извлекаем количество из Map по ID
                Integer count = countMap.get(id);
                if (count == null) {
                    System.out.println("Ошибка: для игрушки с ID " + id + " не найдено количество.");
                    return;
                }

                Toy toy = new Toy(id, name, winRate);
                ToyWithCount toyWithCount = new ToyWithCount(toy);
                toyWithCount.setCount(count);
                toys.add(toyWithCount);
            }
            toyReader.close();
            countReader.close();
            System.out.println("Данные загружены из файлов toys.csv и toys_count.csv.");
        } catch (IOException e) {
            System.out.println("Ошибка при чтении из файла: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try {
            FileWriter toyWriter = new FileWriter("toys.csv");
            FileWriter countWriter = new FileWriter("toys_count.csv");

            for (ToyWithCount toyWithCount : toys) {
                Toy toy = toyWithCount.getToy();

                // Записываем информацию об игрушке в файл toys.csv
                toyWriter.write(toy.getId() + "," + toy.getToyName() + "," + toy.getWeight() + "\n");

                // Записываем количество игрушек в файл toys_count.csv
                countWriter.write(toy.getId() + "," + toyWithCount.getCount() + "\n");
            }
            toyWriter.close();
            countWriter.close();
            System.out.println("Данные сохранены в файлы toys.csv и toys_count.csv.");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении в файл: " + e.getMessage());
        }
    }
}
