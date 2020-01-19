# Дипломный проект - тестирование сервиса по продаже заграничных туров

С помощью данного сервиса вы можете приобрести туры в различные страны, оплатив сразу с карты или выбрать вариант оплаты в кредит.

## Документы
[План тестирования](/Documents/PLAN.md)

*В процессе заполнения*


## Инструкция по запуску

1. Необходимо предварительно установить и настроить Intellij Idea, Docker (в зависимости от вашей системы настройка может отличаться) и плагин Docker к Intellij Idea

2. Скопировать данный репозиторий `git clone https://github.com/supremko/diplom.git`

3. Перейти в папку, в которую вы склонировали репозиторий, командой `cd PATH`.

4. Запустить контейнеры командой `docker-compose up -d`

5. Запустить SUT контейнер в отдельном окне терминала. При тестировании на mysql командой `java -jar aqa-shop.jar --spring.profiles.active=mysql`
При тестировании на postgres командой `java -jar aqa-shop.jar --spring.profiles.active=postgres`

6. В третьем окне терминала запустить тесты. 
Для запуска на mysql командой **gradlew test** (для windows) или **./gradlew test** (для линукс). Запуск на mysql установлен как дефолтный. 
Для запуска на postgres командой **gradlew test -Ddb_url=jdbc:postgresql://localhost:5432/app
** (для windows) или **./gradlew test -Ddb_url=jdbc:postgresql://localhost:5432/app
** (для линукс). 

