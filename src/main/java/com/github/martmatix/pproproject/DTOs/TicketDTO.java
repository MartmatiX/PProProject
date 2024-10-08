package com.github.martmatix.pproproject.DTOs;

public class TicketDTO {

    private String name;
    private String description;

    public TicketDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
