Запросы curl


метод getAll. Выдает всю еду пользователя обратная сортировка по дате
curl -v http://localhost:8080/topjava/rest/meals

метод get Выдает одну зарегистрированного пользователя еду по id (100006)
curl -v http://localhost:8080/topjava/rest/meals/100006

метод getBetween Фильтрация еды по дате
curl -v http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=00:00&endDate=2020-01-31&endTime=23:55

метод getAllUsers Вывод всех пользователей
curl -v http://localhost:8080/topjava/rest/admin/users

метод get Вывод пользователя по id (100000)
curl -v http://localhost:8080/topjava/rest/admin/users/100000

