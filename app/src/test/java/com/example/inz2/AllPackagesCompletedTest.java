package com.example.inz2;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AllPackagesCompletedTest {

    @Test
    public void testCheckAllPackagesCompleted_AllPackagesCompleted() {
        List<DataPackage> dataPackages = new ArrayList<>();
        dataPackages.add(new DataPackage(true, true));
        dataPackages.add(new DataPackage(true, true));
        dataPackages.add(new DataPackage(true, true));

        assertTrue(checkAllPackagesCompleted(dataPackages));
    }

    @Test
    public void testCheckAllPackagesCompleted_SomePackagesNotCompleted() {
        List<DataPackage> dataPackages = new ArrayList<>();
        dataPackages.add(new DataPackage(true, true));
        dataPackages.add(new DataPackage(false, true));
        dataPackages.add(new DataPackage(true, false));

        assertFalse(checkAllPackagesCompleted(dataPackages));
    }

    @Test
    public void testCheckAllPackagesCompleted_EmptyPackageList() {
        List<DataPackage> dataPackages = new ArrayList<>();

        assertTrue(checkAllPackagesCompleted(dataPackages));
    }

    private static class DataPackage {
        private Boolean is_loaded;
        private Boolean is_unloaded;

        public DataPackage(Boolean is_loaded, Boolean is_unloaded) {
            this.is_loaded = is_loaded;
            this.is_unloaded = is_unloaded;
        }

        public Boolean getIs_loaded() {
            return is_loaded;
        }

        public Boolean getIs_unloaded() {
            return is_unloaded;
        }
    }

    private boolean checkAllPackagesCompleted(List<DataPackage> packages) {
        for (DataPackage dataPackage : packages) {
            Boolean isLoaded = dataPackage.getIs_loaded();
            Boolean isUnloaded = dataPackage.getIs_unloaded();
            if (isLoaded == null || isUnloaded == null || !isLoaded || !isUnloaded) {
                return false;
            }
        }
        return true;
    }
}