package com.example.inz2;

        import org.junit.Test;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.List;

        import static org.junit.Assert.assertEquals;


public class FilterLoadedPackagesTest {

    @Test
    public void testFilterUnloadedPackages() {

        List<Package> dataPackages = new ArrayList<>();
        dataPackages.add(new Package(false, 1));
        dataPackages.add(new Package(true, 2));
        dataPackages.add(new Package(false, 3));

        List<Package> result = filterUnloadedPackages(dataPackages);

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getPoint_number_start().intValue());
        assertEquals(3, result.get(1).getPoint_number_start().intValue());
    }

    private static class Package {
        private Boolean is_loaded;
        private Integer point_number_start;

        public Package(Boolean is_loaded, Integer point_number_start) {
            this.is_loaded = is_loaded;
            this.point_number_start = point_number_start;
        }

        public Boolean getIs_loaded() {
            return is_loaded;
        }

        public Integer getPoint_number_start() {
            return point_number_start;
        }
    }

    private List<Package> filterUnloadedPackages(List<Package> dataPackages) {
        List<Package> unloadedPackages = new ArrayList<>();

        for (Package dataPackageItem : dataPackages) {
            Boolean isLoaded = dataPackageItem.getIs_loaded();
            if (isLoaded != null && !isLoaded) {
                unloadedPackages.add(dataPackageItem);
            }
        }
        Collections.sort(unloadedPackages, new Comparator<Package>() {
            @Override
            public int compare(Package package1, Package package2) {
                return package1.getPoint_number_start().compareTo(package2.getPoint_number_start());
            }
        });

        return unloadedPackages;
    }
}

