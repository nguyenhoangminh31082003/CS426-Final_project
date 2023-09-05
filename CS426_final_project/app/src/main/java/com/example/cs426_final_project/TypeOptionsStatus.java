package com.example.cs426_final_project;

import java.util.ArrayList;
import java.util.List;

public class TypeOptionsStatus {

    private List<String> values;
    private List<Boolean> chosen;

    public int getSize() {
        return this.values.size();
    }

    public TypeOptionsStatus(final List<String> values) {
        this.values = values;
        this.chosen = new ArrayList<Boolean>();
        for (int i = values.size() - 1; i >= 0; --i)
            this.chosen.add(false);
    }

    public boolean toggle(final int position) {
        if ((position < 0) || (position >= this.values.size()))
            return false;
        this.chosen.set(position, !(this.chosen.get(position)));
        return true;
    }

    public boolean checkChosen(final int position) {
        return this.chosen.get(position);
    }

    public String getTypeOption(final int position) {
        if ((position < 0) || (position >= this.values.size()))
            return null;
        return this.values.get(position);
    }
}
