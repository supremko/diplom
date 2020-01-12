# Дипломный проект - тестирование сервиса по продаже заграничных туров

На данном сайте вы можете приобрести туры в различные страны, оплатив сразу с карты или выбрать вариант оплаты в кредит.

## Инструкция по запуску

1. Необходимо предварительно установить и настроить Intellij Idea, Docker (в зависимости от вашей системы настройка может отличаться) и плагин Docker к Intellij Idea

2. Скопировать данный репозиторий `git clone https://github.com/supremko/diplom.git`

3. Запустить контейнеры командой `docker-compose up -d`

4. Выбрать на какой БД будет проходить тестирование: в PurchaseTest.java изменить значение переменной database на mysql или postgres, соответственно.

5. Запустить SUT контейнер в отдельном окне терминала. При тестировании на mysql командой `java -jar aqa-shop.jar --spring.profiles.active=mysql`
При тестировании на postgres командой `java -jar aqa-shop.jar --spring.profiles.active=postgres`

6. В третьем окне терминала запустить тесты командой gradlew test (для windows) или ./gradlew test (для линукс)

## Документы
[План тестирования](/Documents/PLAN.md)

[Gradle отчет](/build/reports/tests/test/index.html)

*В процессе заполнения*