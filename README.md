# SafePass

**Mobile Password Manager**

## Реалізовані функції

* **Реєстрація / Авторизація**: дефолтний акаунт admin/1234 або реєстрація нового користувача через **AuthManager**.
* **Налаштування PIN-коду**: збереження 4-значного PIN у `SharedPreferences` через **PinManager**.
* **Вхід за допомогою PIN**: перевірка PIN-коду перед доступом до списку паролів.
* **Перелік паролів**: відображення назв з можливістю додавання нового або переходу до деталей.
* **Детальний перегляд**: показ назви, username та прихованого/видимого пароля з копіюванням у буфер.
* **CRUD-операції над записами**: додавання, редагування, видалення зашифрованих паролів через **Room** та **PasswordRepository**.
* **Генератор паролів**: створення випадкового 12-символьного пароля.
* **Модульне збереження даних**: локальна база даних SQLite (Room) для персистентного зберігання.

## Інструкція по запуску

### Передумови

* **JDK 17**
* **Android SDK** (рівень API 26–34)
* **Gradle** (вбудований у Android Studio)
* **Android Studio Arctic Fox** або новіше

### Кроки

1. **Клонувати репозиторій**

   ```bash
   git clone git@github.com:akrvch/SafePass.git SafePass
   cd SafePass
   ```
2. **Імпортувати проект**

    * Відкрийте Android Studio → **File → Open** → виберіть папку `SafePass`.
3. **Синхронізувати Gradle**

    * Після відкриття натисніть **Sync Now**, якщо з’явиться пропозиція.
4. **Запустити на емуляторі або реальному пристрої**

    * Підключіть Android-пристрій або запустіть AVD із API ≥ 26.
    * Натисніть ▶️ **Run 'app'** (або **./gradlew installDebug** у терміналі).

### Використання

1. **Login / Register**

    * Увійдіть через `admin`/`1234` або натисніть Register для створення нового акаунту.
2. **Set PIN**

    * Встановіть 4-значний PIN-код або натисніть **Later**, щоб зробити це пізніше.
3. **Enter PIN**

    * Використовуйте встановлений PIN для доступу до основного екрану.
4. **Manage Passwords**

    * Додайте новий запис через кнопку **Add password**, введіть Title та Password (або згенеруйте).
    * Клікніть на запис зі списку для перегляду, копіювання, редагування або видалення.

---

Проєкт створено для демонстрації архітектури MVVM із використанням Jetpack Compose та Room.
