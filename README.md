    Описание:

  Это пример реализации технологии блокчейн.
  Здесь реализована структура через клиент и сервер. Наше общение, пополнение, перевод производится за счет запросов через localhost.
  
  
    Запуск:
  docker-compose up
  
    Команды пользователя:
  1. http://localhost:8082/status - проверка того, что клиент смог подняться  
  2. http://localhost:8082/account/new/{name} - создание нового пользователя. Вместо "{name}" необходимо указать имя вашего аккаунта. 
  3. http://localhost:8082/accounts/names - Вывести имена всех пользователей
  4. http://localhost:8082/accounts/number - Вывести кол-во пользователей
  5. http://localhost:8082/account/balance/{name} - Вывести баланс пользователя
  6. http://localhost:8082/transaction/money_transfer/{from}/{to}/{amount} - Сделать перевод от пользователя {from} пользователю {to} на сумму {amount}