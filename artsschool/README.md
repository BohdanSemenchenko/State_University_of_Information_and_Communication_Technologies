# 🎨 Школа Мистецтв — Система Управління

Повноцінне графічне застосування для управління школою мистецтв:
- **Backend**: Java 21 + Spring Boot 3 + REST API + Swagger (OpenAPI)
- **Frontend**: HTML/CSS/JS — інтерактивний dark-theme GUI
- **База даних**: H2 In-Memory (не потребує налаштування)

---

## 📁 Структура проєкту

```
arts-school-full/
├── backend/                          ← Spring Boot REST API
│   ├── pom.xml
│   └── src/main/java/ua/artsschool/
│       ├── ArtsSchoolApplication.java
│       ├── model/         (Student, Teacher, Group, Enrollment, ArtDiscipline)
│       ├── dto/           (Request/Response DTO класи)
│       ├── repository/    (Spring Data JPA репозиторії)
│       ├── service/       (StudentService, TeacherService, GroupService, MapperService)
│       ├── controller/    (StudentController, TeacherController, GroupController, DashboardController)
│       ├── exception/     (GlobalExceptionHandler, власні винятки)
│       └── config/        (OpenApiConfig, DataInitializer)
└── frontend/
    └── index.html                    ← Повний SPA-застосунок
```

---

## 🚀 Запуск

### Варіант 1 — IntelliJ IDEA (рекомендований)

1. `File → Open` → папка `arts-school-full/backend`
2. IDEA автоматично завантажить Maven залежності
3. Відкрити `ArtsSchoolApplication.java`
4. Натиснути **Run ▶** (або Shift+F10)
5. Відкрити `frontend/index.html` у браузері

### Варіант 2 — Командний рядок

```bash
cd backend
mvn spring-boot:run

# Потім відкрити frontend/index.html у браузері
```

### Варіант 3 — JAR-файл

```bash
cd backend
mvn package -DskipTests
java -jar target/arts-school-backend-1.0.0.jar
```

---

## 🌐 URL після запуску

| URL | Опис |
|-----|------|
| `frontend/index.html` | Графічний інтерфейс |
| `http://localhost:8080/swagger-ui.html` | Swagger UI — документація API |
| `http://localhost:8080/api-docs` | OpenAPI JSON |
| `http://localhost:8080/h2-console` | H2 Database Console |

**H2 Console:** JDBC URL = `jdbc:h2:mem:artsschool`, User = `sa`, Password = (порожньо)

---

## 📖 REST API Ендпоінти

### 👨‍🎓 Учні — `/api/students`
| Метод | URL | Опис |
|-------|-----|------|
| GET | `/api/students` | Всі учні |
| GET | `/api/students/{id}` | Учень за ID |
| GET | `/api/students/search?q=...` | Пошук за ПІБ |
| POST | `/api/students` | Реєстрація учня |
| PUT | `/api/students/{id}` | Оновлення даних |
| PATCH | `/api/students/{id}/deactivate` | Деактивація |
| DELETE | `/api/students/{id}` | Видалення |

### 👨‍🏫 Викладачі — `/api/teachers`
| Метод | URL | Опис |
|-------|-----|------|
| GET | `/api/teachers` | Всі викладачі |
| GET | `/api/teachers/active` | Активні |
| POST | `/api/teachers` | Додати викладача |
| PUT | `/api/teachers/{id}` | Оновити |
| PATCH | `/api/teachers/{id}/deactivate` | Деактивувати |

### 📚 Групи — `/api/groups`
| Метод | URL | Опис |
|-------|-----|------|
| GET | `/api/groups` | Всі групи |
| GET | `/api/groups/available` | Доступні для запису |
| GET | `/api/groups/{id}` | Деталі + список учнів |
| GET | `/api/groups/discipline/{key}` | Групи за дисципліною |
| POST | `/api/groups` | Створити групу |
| PUT | `/api/groups/{id}` | Оновити |
| PATCH | `/api/groups/{id}/capacity?min=&max=` | Змінити ліміти |
| PATCH | `/api/groups/{id}/status` | Змінити статус |
| POST | `/api/groups/enroll` | Зарахувати учня |
| DELETE | `/api/groups/{groupId}/students/{studentId}` | Відрахувати |
| POST | `/api/groups/transfer` | Перевести між групами |
| GET | `/api/groups/{id}/enrollments` | Журнал зарахувань |

### 📊 Моніторинг — `/api/dashboard`
| Метод | URL | Опис |
|-------|-----|------|
| GET | `/api/dashboard` | Повна статистика + наповненість |
| GET | `/api/dashboard/disciplines` | Список дисциплін |

---

## ⭐ Ключові функції

### Автоматичний контроль наповненості
При кожному зарахуванні/відрахуванні система автоматично:
- Оновлює статус групи: **Набір → Активна → Майже повна (>80%) → Заповнена**
- Визначає недоукомплектовані групи
- Блокує зарахування до закритих/заповнених груп

### Статуси груп
| Статус | Умова |
|--------|-------|
| 🔵 Набір | 0 учнів |
| ✅ Активна | Мін ≤ учні < Макс |
| 🟡 Недоукомплект. | 0 < учні < Мін |
| 🔴 Заповнена | учні = Макс |
| ⏸️ Призупинена | Вручну |
| ❌ Закрита | Вручну |

### Дисципліни
14 дисциплін: Живопис, Малюнок, Скульптура, Фортепіано, Скрипка, Гітара, Вокал, Балет, Сучасний танець, Народний танець, Театр, Хореографія, Кераміка, Фотографія

---

## 🛠 Технології

| Компонент | Технологія |
|-----------|-----------|
| Backend   | Java 21, Spring Boot 3.2, Spring Data JPA |
| Database  | H2 In-Memory |
| API Docs  | SpringDoc OpenAPI 2 (Swagger UI) |
| Validation| Jakarta Bean Validation |
| Build     | Maven |
| Frontend  | HTML5, CSS3, Vanilla JS |
| Fonts     | Cormorant Garamond + DM Sans |
