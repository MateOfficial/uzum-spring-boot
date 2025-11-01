package com.example.demo;

// Ответ со статистикой - просто поля с числами
public class StatisticsResponse {
    private Long personcount;
    private Long carcount;
    private Long uniquevendorcount;

    public StatisticsResponse() {}
    public Long getPersoncount() { return personcount; }
    public void setPersoncount(Long personcount) { this.personcount = personcount; }
    public Long getCarcount() { return carcount; }
    public void setCarcount(Long carcount) { this.carcount = carcount; }
    public Long getUniquevendorcount() { return uniquevendorcount; }
    public void setUniquevendorcount(Long uniquevendorcount) { this.uniquevendorcount = uniquevendorcount; }
}
