package com.swirldslabs.voting.voting;

public enum Proposal {
// "Python", "C", "C++", "Java", "C#", "JavaScript", "Visual Basic", "SQL", "PHP", "MATLAB"
    PYTHON("Python", 0),
    C("C", 1),
    C_PLUS_PLUS("C++", 2),
    JAVA("Java", 3),
    C_SHARP("C#", 4),
    JAVA_SCRIPT("JavaScript", 5),
    VB("Visual Basic", 6),
    SQL("SQL", 7),
    PHP("PHP", 8),
    MATLAB("MATLAB", 9);

    private final String name;

    private final int id;

    Proposal(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
