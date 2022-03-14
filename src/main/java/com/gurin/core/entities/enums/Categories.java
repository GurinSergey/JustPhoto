package com.gurin.core.entities.enums;

/**
 * Created by SGurin on 05.05.2016.
 */
public enum Categories {

    Пейзаж(0), Портрет(1), Макро(2), Путешествие(3), Разное(4), Реклама(5), Репортаж(6), Свадьба(7), Спорт(8), Животные(9), Город(10);
    private int index;
    private String name;

    private Categories(int index) {
        this.index = index;
        this.name = this.name();
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return this.name;
    }
}
