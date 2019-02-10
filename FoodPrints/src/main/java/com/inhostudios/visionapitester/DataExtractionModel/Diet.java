package com.inhostudios.visionapitester.DataExtractionModel;

public enum Diet {
    BALANCED("balanced"),         // TESTED
    HIGH_PROTEIN("high-protein"), // TESTED
    LOW_FAT("low-fat"),           // TESTED
    LOW_CARB("low-carb");         // TESTED

    // NOT WORKING
//    HIGH_FIBER("high-fiber"),
//    LOW_SODIUM("low-sodium");

    private final String text;

    Diet(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}