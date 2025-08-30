package com.worch.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractPermissionChecker<T> {

    private final List<Function<T, Boolean>> checks = new ArrayList<>();

    public void addCheck(Function<T, Boolean> check) {
        checks.add(check);
    }

    public void preProcess(T request) {}

    public boolean checkPermissions(T request) {
        preProcess(request);
        for (Function<T, Boolean> check : checks) {
            if (!check.apply(request)) {
                return false;
            }
        }
        return true;
    }
}
