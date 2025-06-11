1 задача - класс LoginTest
2 задача - класс EchoClientTest
3 задача - pdf файл Задание 3. Чек лист и 2 бага.pdf в корне проекта.

Написано на java 17.
Аллур отчер генерирует, для просмотра требуется установленный allure
например, отсюда. https://github.com/allure-framework/allure2/releases
Скачать зип файл allure-2.34.0.zip распаковать 
и прописать глобальную переменную к bin директории

после запуска тестов, открыть командную строку в корне проекта
запустить генерацию отчета
allure generate allure-results --clean -o target/allure-report
запустить отрисовку отчета
allure open target/allure-report

В корне проекта лежит файл AllurePrintScreen.jpg - результат исполнения на 
локальной машине.
