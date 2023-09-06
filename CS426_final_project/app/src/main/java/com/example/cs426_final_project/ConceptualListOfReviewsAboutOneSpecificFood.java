package com.example.cs426_final_project;

import java.security.SecureRandom;

public class ConceptualListOfReviewsAboutOneSpecificFood {

    public ConceptualListOfReviewsAboutOneSpecificFood() {
    }

    public String getUserName(final int position) {
        if (position % 2 == 0)
            return "Azizbek Karimova";
        return "Arman Omarov";
    }

    public String getFullReview(final int position) {
        return "This is the best dessert that I have ever eaten in my life. You will absolutely regret if you do not try this!!!";
    }

    public int getColorPoint(final int position) {
        return 1 + position % 5;
    }

    public int getGreyPoint(final int position) {
        return 10;
    }

    public int getSize() {
        return 2;
    }

}
