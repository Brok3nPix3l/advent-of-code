package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 implements DailyChallenge {
    File inputFile;

    public Day5(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public long Part1(boolean debug) {
        List<Long[]> ranges = new ArrayList<>();
        boolean readingRanges = true;
        long freshIngredientIdCount = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (debug) {
                    System.out.println(line);
                }
                if (line.isEmpty()) {
                    readingRanges = false;
                    continue;
                }
                if (readingRanges) {
                    ranges.add(Arrays.stream(line.split("-")).map(Long::parseLong).toArray(Long[]::new));
                    continue;
                }
                long ingredientId = Long.parseLong(line);
                for (Long[] range : ranges) {
                    if (range[0] <= ingredientId && ingredientId <= range[1]) {
                        freshIngredientIdCount++;
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return freshIngredientIdCount;
    }

    public long Part2(boolean debug) {
        TreeMap<Long, Long> freshIngredientIdRangeMap = new TreeMap<>();
        long freshIngredientIdCount = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (debug) {
                    System.out.println(line);
                }
                if (line.isEmpty()) {
                    if (debug) {
                        System.out.println(freshIngredientIdRangeMap);
                    }
//                    for (long i = smallestValue; i <= largestValue; i++) {
//                        if (debug) {
//                            double percent = Math.round(1.0 * i / (largestValue - smallestValue));
//                            if (percent > prevPercent) {
//                                System.out.println(percent);
//                                prevPercent = percent;
//                            }
//                        }
//                        for (Long[] range : ranges) {
//                            if (range[0] <= i && i <= range[1]) {
//                                freshIngredientIdCount++;
//                                break;
//                            }
//                        }
//                    }
                    break;
                }
                Long[] range = Arrays.stream(line.split("-")).map(Long::parseLong).toArray(Long[]::new);
                if (debug && range[0] < 0 || range[1] < 0) {
                    System.out.println(Arrays.toString(range));
                }
                // five scenarios:
                // 1. current range doesn't overlap with any existing ranges
                // add current range to map
                // 2. small element of current range is smaller than the large element of an established range,
                // and large element of current range is larger than large element of an established range
                // remove the existing range, and insert (small element from existing range, large element from
                // current range)
                // 3. small element of current range is smaller than the small element of an established range,
                // and large element of current range is smaller than large element of an established range
                // remove the existing range, and insert (small element from current range, large element from
                // existing range)
                // 4. current range totally encapsulates an existing range
                // remove the existing range, and insert the current range
                // 5. current range is entirely encapsulated within an existing range
                // do nothing
                Map.Entry<Long, Long> smallerThanSmallElement = freshIngredientIdRangeMap.floorEntry(range[0]);
//                if (smallerThanSmallElement != null && smallerThanSmallElement.getKey() >
//                smallerThanSmallElement.getValue()) {
//                    smallerThanSmallElement = freshIngredientIdRangeMap.floorEntry(smallerThanSmallElement
//                    .getValue());
//                }
                Map.Entry<Long, Long> largerThanSmallElement = freshIngredientIdRangeMap.ceilingEntry(range[0]);
//                if (largerThanSmallElement != null && largerThanSmallElement.getKey() <
//                largerThanSmallElement.getValue()) {
//                    largerThanSmallElement = freshIngredientIdRangeMap.ceilingEntry(largerThanSmallElement
//                    .getKey());
//                }
                Map.Entry<Long, Long> smallerThanLargeElement = freshIngredientIdRangeMap.floorEntry(range[1]);
//                if (smallerThanLargeElement != null && smallerThanLargeElement.getKey() >
//                smallerThanLargeElement.getValue()) {
//                    smallerThanLargeElement = freshIngredientIdRangeMap.floorEntry(smallerThanLargeElement
//                    .getValue());
//                }
                Map.Entry<Long, Long> largerThanLargeElement = freshIngredientIdRangeMap.ceilingEntry(range[1]);
//                if (largerThanLargeElement != null && largerThanLargeElement.getKey() <
//                largerThanLargeElement.getValue()) {
//                    largerThanLargeElement = freshIngredientIdRangeMap.ceilingEntry(largerThanLargeElement
//                    .getKey());
//                }

                if (freshIngredientIdRangeMap.isEmpty() ||
                        /*smallerThanSmallElement != null && */smallerThanLargeElement != null && max(smallerThanLargeElement) < range[0]/* && min(smallerThanSmallElement).equals(min(smallerThanLargeElement)) && max(smallerThanSmallElement).equals(max(smallerThanLargeElement))*/ &&
                        largerThanSmallElement != null/* && largerThanLargeElement != null*/ && min(largerThanSmallElement) > range[1]/* && min(largerThanSmallElement).equals(min(largerThanLargeElement)) && max(largerThanSmallElement).equals(max(largerThanLargeElement))*/) {
                    // scenario 1
                    freshIngredientIdRangeMap.put(range[0], range[1]);
                    freshIngredientIdRangeMap.put(range[1], range[0]);
                    continue;
                }
                // don't think I need to check largerThanLargeElement here, since both checks should be the same
                if (smallerThanSmallElement != null /*&& min(smallerThanSmallElement) <= range[0] */&& max(smallerThanSmallElement) >= range[1]) {
                    // scenario 5
                    continue;
                }
                // scenario 4
                while (largerThanSmallElement != null &&
                        min(largerThanSmallElement) >= range[0] &&
                        max(largerThanSmallElement) <= range[1]) {
                    freshIngredientIdRangeMap.remove(largerThanSmallElement.getKey());
                    freshIngredientIdRangeMap.remove(largerThanSmallElement.getValue());
                    largerThanSmallElement = freshIngredientIdRangeMap.ceilingEntry(range[0]);
                }
                while (smallerThanLargeElement != null &&
                        min(smallerThanLargeElement) >= range[0] &&
                        max(smallerThanLargeElement) <= range[1]) {
                    freshIngredientIdRangeMap.remove(smallerThanLargeElement.getKey());
                    freshIngredientIdRangeMap.remove(smallerThanLargeElement.getValue());
                    smallerThanLargeElement = freshIngredientIdRangeMap.floorEntry(range[1]);
                }
                while (smallerThanSmallElement != null && min(smallerThanSmallElement) <= range[0] && max(smallerThanSmallElement) >= range[0] && max(smallerThanSmallElement) <= range[1]) {
                    // scenario 2
                    freshIngredientIdRangeMap.remove(smallerThanSmallElement.getKey());
                    freshIngredientIdRangeMap.remove(smallerThanSmallElement.getValue());
                    range[0] = smallerThanSmallElement.getKey();
                    smallerThanSmallElement = freshIngredientIdRangeMap.floorEntry(range[0]);
                }
                while (largerThanLargeElement != null && min(largerThanLargeElement) >= range[0] && min(largerThanLargeElement) <= range[1] && max(largerThanLargeElement) >= range[1]) {
                    // scenario 3
                    freshIngredientIdRangeMap.remove(largerThanLargeElement.getKey());
                    freshIngredientIdRangeMap.remove(largerThanLargeElement.getValue());
                    range[1] = largerThanLargeElement.getKey();
                    largerThanLargeElement = freshIngredientIdRangeMap.ceilingEntry(range[1]);
                }
                freshIngredientIdRangeMap.put(range[0], range[1]);
                freshIngredientIdRangeMap.put(range[1], range[0]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Set<Long> values = new HashSet<>();
        long prevValue = -1;
        List<Map.Entry<Long,Long>> ascendingEntries = freshIngredientIdRangeMap.entrySet().stream().filter(e -> e.getKey() < e.getValue()).toList();
        if (debug) {
            System.out.println("ascendingEntries: " + ascendingEntries);
        }
        for (Map.Entry<Long, Long> range : ascendingEntries) {
            if (debug && values.contains(range.getValue())) {
                System.out.println("value " + range.getValue() + " duplicated");
            }
            values.add(range.getValue());
//            if (range.getKey() > range.getValue()) {
//                continue;
//            }
            freshIngredientIdCount += range.getValue() - range.getKey() + 1;
            if (debug && prevValue != -1 && range.getKey() < prevValue) {
                System.out.println("prevValue=" + prevValue + " range key=" + range.getKey());
            }
            prevValue = range.getValue();
        }
        return freshIngredientIdCount;
    }

    private Long max(Map.Entry<Long, Long> entry) {
        return Math.max(entry.getKey(), entry.getValue());
    }

    private Long min(Map.Entry<Long, Long> entry) {
        return Math.min(entry.getKey(), entry.getValue());
    }
}
