# Вычислитель отличий (Java)

[![hexlet-check](https://github.com/kicha3007-boop/java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/kicha3007-boop/java-project-71/actions)
[![Java CI](https://github.com/kicha3007-boop/java-project-71/actions/workflows/main.yml/badge.svg)](https://github.com/kicha3007-boop/java-project-71/actions/workflows/main.yml)

В этом проекте отрабатывается работа с коллекциями и структурами данных. Изучаются способы построения и обхода деревьев. Вы познакомитесь с разными форматами данных (json, yml), научитесь их парсить и формировать. Начнете писать тесты (JUnit) и освоите разработку через них. Познакомитесь с непрерывной интеграцией (CI) и элементами экстремального программирования (XP). Прокачаете ООП мышление.

Учебный проект Хекслета: https://ru.hexlet.io/programs/java
Как это должно работать: https://asciinema.org/a/NFIQgLVMu1ymFsqg4ESeOQeXi

## Стек

- Java 21, Gradle 8.14 (Kotlin DSL)
- picocli — разбор аргументов командной строки
- Jackson — чтение json/yaml и вывод json
- JUnit 5, JaCoCo (порог покрытия 80% в `check`), Spotless (google-java-format)

## Установка

Нужны JDK 21 и make.

```bash
git clone https://github.com/kicha3007-boop/java-project-71.git
cd java-project-71
make install   # сборка в app/build/install/app
```

Проверки: `make test`, `make lint`, `make build` (тесты + линтер + порог покрытия).

## Использование

```text
$ ./app/build/install/app/bin/app -h
Usage: gendiff [-hV] [-f=format] filepath1 filepath2
Compares two configuration files and shows a difference.
      filepath1         path to first file
      filepath2         path to second file
  -f, --format=format   output format [default: stylish]
  -h, --help            Show this help message and exit.
  -V, --version         Print version information and exit.
```

Поддерживаются файлы `.json`, `.yml`/`.yaml` (в том числе в смеси). Ключи сравниваются на первом
уровне, вложенные значения выводятся как есть.

### Формат stylish (по умолчанию)

```text
$ ./app/build/install/app/bin/app file1.json file2.yml
{
    chars1: [a, b, c]
  - chars2: [d, e, f]
  + chars2: false
  - checked: false
  + checked: true
  - default: null
  + default: [value1, value2]
  - id: 45
  + id: null
  - key1: value1
  + key2: value2
    numbers1: [1, 2, 3, 4]
  - numbers2: [2, 3, 4, 5]
  + numbers2: [22, 33, 44, 55]
  - numbers3: [3, 4, 5]
  + numbers4: [4, 5, 6]
  + obj1: {nestedKey=value, isNested=true}
  - setting1: Some value
  + setting1: Another value
  - setting2: 200
  + setting2: 300
  - setting3: true
  + setting3: none
}
```

### Формат plain

```text
$ ./app/build/install/app/bin/app -f plain file1.json file2.json
Property 'chars2' was updated. From [complex value] to false
Property 'checked' was updated. From false to true
Property 'default' was updated. From null to [complex value]
Property 'id' was updated. From 45 to null
Property 'key1' was removed
Property 'key2' was added with value: 'value2'
Property 'numbers2' was updated. From [complex value] to [complex value]
Property 'numbers3' was removed
Property 'numbers4' was added with value: [complex value]
Property 'obj1' was added with value: [complex value]
Property 'setting1' was updated. From 'Some value' to 'Another value'
Property 'setting2' was updated. From 200 to 300
Property 'setting3' was updated. From true to 'none'
```

### Формат json

```text
$ ./app/build/install/app/bin/app -f json flat1.json flat2.json
[ {
  "key" : "follow",
  "status" : "removed",
  "value" : false
}, {
  "key" : "host",
  "status" : "unchanged",
  "value" : "hexlet.io"
}, {
  "key" : "proxy",
  "status" : "removed",
  "value" : "123.234.53.22"
}, {
  "key" : "timeout",
  "status" : "changed",
  "oldValue" : 50,
  "newValue" : 20
}, {
  "key" : "verbose",
  "status" : "added",
  "value" : true
} ]
```

### Как библиотека

```java
import hexlet.code.Differ;

String diff = Differ.generate(filePath1, filePath2);           // stylish
String plain = Differ.generate(filePath1, filePath2, "plain"); // stylish | plain | json
```

Устройство: `Parser` читает json/yaml в `Map`, `DiffBuilder` строит внутреннее представление —
список `DiffNode` (ключ, статус `added/removed/unchanged/changed`, старое и новое значение),
`Formatter` выбирает форматер из пакета `formatters`.

---

<details>
<summary>Автоматические тесты Хекслета</summary>

Тесты запускаются на каждый коммит. За запуск отвечает файл `.github/workflows/hexlet-check.yml` — не удаляйте и не переименовывайте ни его, ни репозиторий.

</details>

## О Хекслете

[Хекслет](https://ru.hexlet.io/) — школа программирования: авторские программы обучения с практикой, поддержкой наставников и реальными проектами, которые остаются в резюме. Этот репозиторий — один из таких проектов.
