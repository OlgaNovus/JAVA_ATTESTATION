import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ToyManager toyManager = new ToyManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nГлавное меню:");
            System.out.println("1. Список игрушек");
            System.out.println("2. Остатки игрушек на складе");
            System.out.println("3. Редактировать шанс выпадения");
            System.out.println("4. Редактировать остатки");
            System.out.println("5. Добавить игрушку");
            System.out.println("6. Удалить игрушку");
            System.out.println("7. Провести лотерею");
            System.out.println("8. Считать игрушки и остатки из файла");
            System.out.println("9. Сохранить в файл");
            System.out.println("10. Выход");
            System.out.println("\nВведите номер действия: ");

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    toyManager.listToys();
                    break;
                case 2:
                    toyManager.checkInventory();
                    break;
                case 3:
                    toyManager.editChance();
                    break;
                case 4:
                    toyManager.editInventory();
                    break;
                case 5:
                    toyManager.addToy();
                    break;
                case 6:
                    toyManager.removeToy();
                    break;
                case 7:
                    toyManager.conductLottery();
                    break;
                case 8:
                    toyManager.loadFromFile();
                    break;
                case 9:
                    toyManager.saveToFile();
                    break;
                case 10:
                    System.out.println("Выход из программы.");
                    System.exit(0);
                default:
                    System.out.println("Неизвестное действие. Пожалуйста, попробуйте еще раз.");
            }
        }
    }
}