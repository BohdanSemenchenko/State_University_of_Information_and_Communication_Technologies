package ua.artsschool.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ua.artsschool.dto.*;
import ua.artsschool.model.ArtDiscipline;
import ua.artsschool.service.*;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final GroupService groupService;

    @Override
    public void run(String... args) {
        log.info("🎨 Ініціалізація демонстраційних даних...");

        // Teachers
        TeacherResponse t1 = teacherService.create(TeacherRequest.builder()
                .firstName("Олена").lastName("Коваленко")
                .phone("+380501112233").email("kovalenko@art.ua")
                .disciplines(List.of(ArtDiscipline.PAINTING, ArtDiscipline.DRAWING)).build());

        TeacherResponse t2 = teacherService.create(TeacherRequest.builder()
                .firstName("Ігор").lastName("Петренко")
                .phone("+380502223344").email("petrenko@art.ua")
                .disciplines(List.of(ArtDiscipline.MUSIC_PIANO, ArtDiscipline.MUSIC_VIOLIN)).build());

        TeacherResponse t3 = teacherService.create(TeacherRequest.builder()
                .firstName("Марія").lastName("Сидоренко")
                .phone("+380503334455").email("sydorenko@art.ua")
                .disciplines(List.of(ArtDiscipline.DANCE_BALLET, ArtDiscipline.CHOREOGRAPHY)).build());

        TeacherResponse t4 = teacherService.create(TeacherRequest.builder()
                .firstName("Василь").lastName("Мороз")
                .phone("+380504445566").email("moroz@art.ua")
                .disciplines(List.of(ArtDiscipline.CERAMICS, ArtDiscipline.SCULPTURE)).build());

        // Groups
        GroupResponse g1 = groupService.create(GroupRequest.builder().name("Живопис молодший")
                .discipline(ArtDiscipline.PAINTING).teacherId(t1.getId())
                .minCapacity(4).maxCapacity(8).schedule("Пн,Ср 15:00-16:30")
                .ageRange("7-10 років").classroom("Кімната 101").build());

        GroupResponse g2 = groupService.create(GroupRequest.builder().name("Живопис старший")
                .discipline(ArtDiscipline.PAINTING).teacherId(t1.getId())
                .minCapacity(5).maxCapacity(10).schedule("Вт,Чт 16:00-17:30")
                .ageRange("11-16 років").classroom("Кімната 101").build());

        GroupResponse g3 = groupService.create(GroupRequest.builder().name("Фортепіано початковий")
                .discipline(ArtDiscipline.MUSIC_PIANO).teacherId(t2.getId())
                .minCapacity(3).maxCapacity(6).schedule("Пн,Ср,Пт 14:00-15:00")
                .ageRange("6-9 років").classroom("Зал 201").build());

        GroupResponse g4 = groupService.create(GroupRequest.builder().name("Скрипка")
                .discipline(ArtDiscipline.MUSIC_VIOLIN).teacherId(t2.getId())
                .minCapacity(4).maxCapacity(8).schedule("Вт,Чт,Сб 15:00-16:00")
                .ageRange("8-14 років").classroom("Зал 202").build());

        GroupResponse g5 = groupService.create(GroupRequest.builder().name("Балет початковий")
                .discipline(ArtDiscipline.DANCE_BALLET).teacherId(t3.getId())
                .minCapacity(6).maxCapacity(12).schedule("Пн,Ср,Пт 17:00-18:30")
                .ageRange("6-10 років").classroom("Балетний зал").build());

        GroupResponse g6 = groupService.create(GroupRequest.builder().name("Кераміка")
                .discipline(ArtDiscipline.CERAMICS).teacherId(t4.getId())
                .minCapacity(4).maxCapacity(8).schedule("Сб,Нд 10:00-12:00")
                .ageRange("10-18 років").classroom("Майстерня").build());

        GroupResponse g7 = groupService.create(GroupRequest.builder().name("Вокал")
                .discipline(ArtDiscipline.MUSIC_VOCAL).teacherId(t2.getId())
                .minCapacity(5).maxCapacity(15).schedule("Пт 16:00-17:30")
                .ageRange("8-18 років").classroom("Зал 203").build());

        // Students
        Long[] studentIds = new Long[18];
        String[][] students = {
            {"Анна","Іванова","2016-03-15","+380671111111","ivanova@gmail.com"},
            {"Богдан","Кравченко","2015-07-22","+380672222222",""},
            {"Вікторія","Лисенко","2014-11-05","+380673333333","lysenko@gmail.com"},
            {"Денис","Шевченко","2013-04-18","+380674444444",""},
            {"Єва","Бойко","2016-09-30","+380675555555","boyko@gmail.com"},
            {"Захар","Ткаченко","2012-02-14","+380676666666",""},
            {"Ірина","Мельник","2015-06-25","+380677777777","melnyk@gmail.com"},
            {"Костянтин","Олексієнко","2014-08-10","+380678888888",""},
            {"Лариса","Поліщук","2013-12-03","+380679999999","polishchuk@gmail.com"},
            {"Максим","Гриценко","2016-05-17","+380670000000",""},
            {"Надія","Романенко","2011-01-28","+380661111111","romanenko@gmail.com"},
            {"Олег","Бондаренко","2012-10-09","+380662222222",""},
            {"Поліна","Зінченко","2015-03-21","+380663333333","zinchenko@gmail.com"},
            {"Роман","Харченко","2014-07-06","+380664444444",""},
            {"Світлана","Дяченко","2013-11-15","+380665555555","diachenko@gmail.com"},
            {"Тарас","Кузьменко","2016-04-28","+380666666666",""},
            {"Уляна","Павленко","2012-08-13","+380667777777","pavlenko@gmail.com"},
            {"Федір","Гончаренко","2011-02-20","+380668888888",""},
        };

        for (int i = 0; i < students.length; i++) {
            String[] s = students[i];
            StudentResponse sr = studentService.create(StudentRequest.builder()
                    .firstName(s[0]).lastName(s[1])
                    .birthDate(LocalDate.parse(s[2]))
                    .phone(s[3]).email(s[4].isEmpty() ? null : s[4]).build());
            studentIds[i] = sr.getId();
        }

        // Enroll: g1 — FULL (8/8)
        long[] g1ids = {0,1,2,3,4,5,6,7};
        for (long idx : g1ids) groupService.enroll(studentIds[(int)idx], g1.getId(), null);

        // g2 — underfilled (3/10)
        groupService.enroll(studentIds[8], g2.getId(), null);
        groupService.enroll(studentIds[9], g2.getId(), null);
        groupService.enroll(studentIds[10], g2.getId(), null);

        // g3 — normal (4/6)
        groupService.enroll(studentIds[0], g3.getId(), null);
        groupService.enroll(studentIds[11], g3.getId(), null);
        groupService.enroll(studentIds[12], g3.getId(), null);
        groupService.enroll(studentIds[13], g3.getId(), null);

        // g4 — underfilled (2/8)
        groupService.enroll(studentIds[14], g4.getId(), null);
        groupService.enroll(studentIds[15], g4.getId(), null);

        // g5 — active (7/12)
        groupService.enroll(studentIds[0], g5.getId(), null);
        groupService.enroll(studentIds[2], g5.getId(), null);
        groupService.enroll(studentIds[4], g5.getId(), null);
        groupService.enroll(studentIds[6], g5.getId(), null);
        groupService.enroll(studentIds[8], g5.getId(), null);
        groupService.enroll(studentIds[10], g5.getId(), null);
        groupService.enroll(studentIds[16], g5.getId(), null);

        // g6 — forming (0/8)
        // g7 — almost full (13/15)
        for (int i = 0; i < 13; i++) groupService.enroll(studentIds[i % 18], g7.getId(), null);

        log.info("✅ Завантажено: {} учнів, {} викладачів, {} груп", 18, 4, 7);
    }
}
