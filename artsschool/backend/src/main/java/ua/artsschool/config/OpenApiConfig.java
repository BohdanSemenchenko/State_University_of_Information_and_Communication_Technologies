package ua.artsschool.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI artsSchoolOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("🎨 Arts School Management API")
                        .description("""
                                REST API для системи управління школою мистецтв.
                                
                                **Основні можливості:**
                                - 👨‍🎓 Реєстрація та управління учнями
                                - 👨‍🏫 Управління викладачами  
                                - 📚 Формування та управління групами
                                - ⚡ Автоматичний контроль наповненості груп
                                - 📊 Панель моніторингу та статистика
                                - 🔄 Зарахування, відрахування та переведення учнів
                                
                                **База даних:** H2 In-Memory (консоль: `/h2-console`)
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Arts School Management")
                                .email("admin@artsschool.ua")));
    }
}
