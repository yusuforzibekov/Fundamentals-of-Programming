package com.epam.autotasks;

import com.google.common.collect.ImmutableTable;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

public class CatDataProcessor {

    public ImmutableTable<String, Cat.Breed, Integer> createCatTable(List<Cat> cats) {
        ImmutableTable.Builder<String, Cat.Breed, Integer> immutableTable = ImmutableTable.builder();
        for (Cat cat : cats) {
            if (!cat.getName().isEmpty() && cat.getBreed() != null) {
                if (! immutableTable.build().contains(cat.getName(), cat.getBreed()))
                    immutableTable.put(cat.getName(), cat.getBreed(), cat.getContestResult().getSum());
            }
        }
        return immutableTable.build();
    }

    public JSONArray createCatJson(List<Cat> cats) {
        JSONArray jsonArray = new JSONArray();

        cats.forEach(cat -> {
            JSONObject catJson = new JSONObject();
            catJson.put("awards", cat.getAwards());
            catJson.put("name", cat.getName());
            catJson.put("weight", cat.getWeight());
            JSONObject contestResult = new JSONObject();
            contestResult.put("running", cat.getContestResult().getRunning());
            contestResult.put("purring", cat.getContestResult().getPurring());
            contestResult.put("jumping", cat.getContestResult().getJumping());
            contestResult.put("sum", cat.getContestResult().getSum());
            catJson.put("contestResult", contestResult);
            catJson.put("age", cat.getAge());
            catJson.put("breed", cat.getBreed() != null ? cat.getBreed().toString() : null);
            jsonArray.put(catJson);
        });

        return jsonArray;
    }
}