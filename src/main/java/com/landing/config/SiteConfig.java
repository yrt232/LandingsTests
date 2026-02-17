package com.landing.config;

/**
 * Объект-конфигурация для одного сайта.
 * Неизменяемый (immutable) — создаём один раз, используем везде.
 * Thread-safe — можно передавать между потоками безопасно.
 */
public class SiteConfig {

    // Технические настройки
    private final String name;              // Имя сайта: profiteco, lumix...
    private final String baseUrl;           // URL сайта
    private final String testUserEmail;     // Email для входа
    private final String testUserPassword;  // Пароль для входа
    private final String testUserName;      // Имя пользователя для регистрации
    private final boolean headless;         // Режим без головы
    private final String browserSize;       // Размер окна: 1920x1080
    private final int timeout;              // Таймаут в миллисекундах
    private final String language;          // Язык по умолчанию: de, en...

    // Тексты для проверок (локализация)
    private final String loginButtonText;
    private final String loginRegisterText;
    private final String loginBackText;
    private final String loginTitle;
    private final String loginSubtitle;
    private final String registerButtonText;
    private final String registerLoginText;
    private final String dashboardTitle;
    private final String errorEmailFormat;
    private final String errorPasswordLength;

    // Конструктор — все поля задаём при создании
    public SiteConfig(String name, String baseUrl, String testUserEmail,
                      String testUserPassword, String testUserName,
                      boolean headless, String browserSize, int timeout,
                      String language, String loginButtonText,
                      String loginRegisterText, String loginBackText,
                      String loginTitle, String loginSubtitle,
                      String registerButtonText, String registerLoginText,
                      String dashboardTitle, String errorEmailFormat,
                      String errorPasswordLength) {
        this.name = name;
        this.baseUrl = baseUrl;
        this.testUserEmail = testUserEmail;
        this.testUserPassword = testUserPassword;
        this.testUserName = testUserName;
        this.headless = headless;
        this.browserSize = browserSize;
        this.timeout = timeout;
        this.language = language;
        this.loginButtonText = loginButtonText;
        this.loginRegisterText = loginRegisterText;
        this.loginBackText = loginBackText;
        this.loginTitle = loginTitle;
        this.loginSubtitle = loginSubtitle;
        this.registerButtonText = registerButtonText;
        this.registerLoginText = registerLoginText;
        this.dashboardTitle = dashboardTitle;
        this.errorEmailFormat = errorEmailFormat;
        this.errorPasswordLength = errorPasswordLength;
    }

    // Геттеры — только чтение, без сеттеров
    public String getName() { return name; }
    public String getBaseUrl() { return baseUrl; }
    public String getTestUserEmail() { return testUserEmail; }
    public String getTestUserPassword() { return testUserPassword; }
    public String getTestUserName() { return testUserName; }
    public boolean isHeadless() { return headless; }
    public String getBrowserSize() { return browserSize; }
    public int getTimeout() { return timeout; }
    public String getLanguage() { return language; }

    // Геттеры для текстов
    public String getLoginButtonText() { return loginButtonText; }
    public String getLoginRegisterText() { return loginRegisterText; }
    public String getLoginBackText() { return loginBackText; }
    public String getLoginTitle() { return loginTitle; }
    public String getLoginSubtitle() { return loginSubtitle; }
    public String getRegisterButtonText() { return registerButtonText; }
    public String getRegisterLoginText() { return registerLoginText; }
    public String getDashboardTitle() { return dashboardTitle; }
    public String getErrorEmailFormat() { return errorEmailFormat; }
    public String getErrorPasswordLength() { return errorPasswordLength; }
}