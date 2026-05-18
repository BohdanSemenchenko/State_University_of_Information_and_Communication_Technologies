package ua.artsschool.model;

public enum ArtDiscipline {
    PAINTING("Живопис"),
    DRAWING("Малюнок"),
    SCULPTURE("Скульптура"),
    MUSIC_PIANO("Фортепіано"),
    MUSIC_VIOLIN("Скрипка"),
    MUSIC_GUITAR("Гітара"),
    MUSIC_VOCAL("Вокал"),
    DANCE_BALLET("Балет"),
    DANCE_CONTEMPORARY("Сучасний танець"),
    DANCE_FOLK("Народний танець"),
    THEATER("Театральне мистецтво"),
    CHOREOGRAPHY("Хореографія"),
    CERAMICS("Кераміка"),
    PHOTOGRAPHY("Фотографія");

    private final String displayName;
    ArtDiscipline(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}
