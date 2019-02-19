package writer.objects;

import lombok.Data;

import java.util.*;

@Data
public class CollectionObject {
    public List<Integer> integerList = new ArrayList<>();
    public Set<String> stringSet = new HashSet<>();
    public Map<Integer, String> testMap = new HashMap<>();
}
