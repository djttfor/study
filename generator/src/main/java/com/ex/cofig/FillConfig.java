package com.ex.cofig;

import java.util.List;
import java.util.Map;

public class FillConfig {
    private List<String> classNames;

    private Map<String,Map<String,Object>> configs;

    public FillConfig() {
    }

    public FillConfig(List<String> classNames, Map<String, Map<String,Object>> configs) {
        this.classNames = classNames;
        this.configs = configs;
    }

    @Override
    public String toString() {
        return "Fill{" +
                "classNames=" + classNames +
                ", configs=" + configs +
                '}';
    }

    public List<String> getClassNames() {
        return classNames;
    }

    public void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }

    public Map<String, Map<String,Object>> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, Map<String,Object>> configs) {
        this.configs = configs;
    }
}
