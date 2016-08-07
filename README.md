Для генериации таблиц в базе данных необходимо использовать файл: 
- deploy_db.bat (для Windows) и deploy_db.sh (*nix платформ). Находящиеся в папке test\src\main\db.
в файле deploy_db.bat необходимо перепроверить корректность пути к psql.exe, "C:\Program Files\PostgreSQL\9.4\bin\psql.exe", при несоответствии - необходимо подкорректировать путь к psql.exe.
При запуске deploy_db.bat необходимо ввести ваш пароль к PostgreSQL.

После генерации таблиц в базе данных должны появиться 3 таблицы: TableA, TableB, TableC.
.bat-файл проверялся, а вот .sh, к сожалению, нет было на чем.
Поэтому:
В случае каких либо проблем создания таблиц в базе данных можно воспользоваться опцией "create" в Hibernate.
Для этого необходимо открыть файл hibernate.cfg.xml (test\src\main\resources) и изменить конфигурацию property name="hibernate.hbm2ddl.auto" на "create". Таким образом таблицы будут созданы в базе данных автомотически при первом

запуске проекта, основываясь на мапинге из папки domain.

Структура проекта:

папка for_read - из этой папки осуществляется чтение xml-файлов. Если файл не имеет расширения .xml - ,то такой файл обрабатываться не будет. В папке уже находятся 3 файла для демонстрации работы. Файл с именем doc1.xml - должен спарситься в TableA, файл с именем doc2.xml в TableB, doc3.xml в TableC, и файл failed_file.xml - содержит некорректный формат.

папка for_parsed - в эту папку попадают файлы, которые были успешно спарсены. После выполнения программы, в эту папку должны попасть файлы doc1.xml, doc2.xml и doc3.xml.

папка for_failed - в эту папку попадут те файлы, которые не прошли парсинг. В нашем случае - файл failed_file.xml.

папка logs - содержит файл log.txt с логами.

папка config - содержит файл config.txt, в котором указывается интервал чтения файлов из папки for_read, в секундах. Если параметр не задан, по умолчанию время = 1 минута.

Теперь о структуре классов:

domain - описание таблиц. В нашем случае три таблицы: TableA, TableB, TableC.
exception - содержит ParserException, это свой эксепшн, который выбрасывается в случае невозможности спарсить xml в объект или если нет ни одной соответствующей таблицы.
thread - содержит класс Threader. Главный класс по парсингу из хмл в таблицы.
timer - осуществляется циклическое чтение из папки for_read.
в корне проекта класс Main - Для запуска проекта.
xml.entities - для конвертации из хмл в объекты используется JAXB аннотации.
xml - класс DateAdapter парсит из строки в объект Date, это необходимо для поля creationDate в таблице TableA.

resources:

hibernate.cfg.xml - содержит параметры подключение к базе данных. Этот файл следует подкорректировать:
На что обязательно следует обратить внимание:
параметр property name="hibernate.connection.url" указывает, что будет использоваться база данных postgres (по умолчанию),
и следует обратить внимание на порт, у меня он 5433, по умолчанию 5432, порт нужно исправить в соответствии с тем, какой порт используется у вас.
параметр property name="hibernate.connection.password" - пароль подключения к базе данных. У меня пароль = 11. У вас, скорей всего, другой, нужно изменить пароль.

log4j.properties - конфигурация лог-файла.
  
