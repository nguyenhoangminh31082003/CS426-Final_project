package com.example.cs426_final_project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MutableTypeOptionsData {

    /*
    private static final String FILE_NAME = "type_options_data.json";

    private JSONObject values;

    private void loadValues(Activity activity) {
        FileInputStream fileInputStream = null;
        this.values = new JSONObject();

        System.out.println("Read file " + TypeOptionsData.FILE_NAME);

        try {
            fileInputStream = activity.openFileInput(TypeOptionsData.FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String text;

            while ((text = bufferedReader.readLine()) != null)
                stringBuilder.append(text);

            try {
                this.values = new JSONObject(stringBuilder.toString());
            } catch (JSONException e) {
                this.values = new JSONObject();
            }

        } catch (FileNotFoundException e) {
            this.values = new JSONObject();
            e.printStackTrace();
        } catch (IOException e) {
            this.values = new JSONObject();
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeValues(Activity activity) {
        FileOutputStream fileOutputStream = null;
        final String text = this.values.toString();

        try {
            fileOutputStream = activity.openFileOutput(TypeOptionsData.FILE_NAME, activity.MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("File " + "'" + TypeOptionsData.FILE_NAME + "'"  + "is successfully written!!! ");
    }

    public void setDefaultValues() {
        final String[] values = {
                "Cơm",
                "Cơm gà",
                "Cơm tấm",
                "Bún",
                "Bún đậu",
                "Mì",
                "Pizza",
                "Bánh mì",
                "Bánh tráng",
                "Cà phê",
                "Trà sữa",
                "Chè",
                "Gà rán",
                "Cháo",
                "Xôi",
                "Bánh",
                "Lẩu"
        };
        this.values = new JSONObject();
        for (int i = 0; i < values.length; ++i) {
            try {
                this.values.put(String.valueOf(i), values[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSize() {
        return this.values.length();
    }

    */

    Set<String> values;

    public void setDefaultValues() {
        final String[] values = {
                "Cơm",
                "Cơm gà",
                "Cơm tấm",
                "Bún",
                "Bún đậu",
                "Mì",
                "Pizza",
                "Bánh mì",
                "Bánh tráng",
                "Cà phê",
                "Trà sữa",
                "Chè",
                "Gà rán",
                "Cháo",
                "Xôi",
                "Bánh",
                "Lẩu"
        };
        this.values = new HashSet<String>() {
        };
        for (int i = 0; i < values.length; ++i)
            this.values.add(values[i]);
    }

    public MutableTypeOptionsData() {
        this.setDefaultValues();
    }

    public List<String> toList() {
        List<String> result = new ArrayList<String>(); ;

        for (String value : this.values)
            result.add(value);

        return result;
    }

}
