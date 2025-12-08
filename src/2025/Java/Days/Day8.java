package Days;

import Util.Position3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day8 implements DailyChallenge {
    File inputFile;

    public Day8(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public long Part1(boolean debug) {
        int connectionsToMake = inputFile.getName().equals("day8.txt") ? 1000 : 10;
        long ans = 1L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            // each position initially points to itself
            // when a position A is connected to another position B, each position points to each other i.e. A -> B and B -> A
                // to avoid loops, if A and B each point to themselves, just point B to A and leave A pointing to itself
                    // A is now a circuit of size 2
                // if either position(s) A or B point to a different position, this position points to that position instead
                    // e.g. if B -> C and we connect A and B, then A -> C and B -> A -> C
                // if any other position(s) D and E point to either position A or B, they now point to the downstream position
                    // e.g. D -> A and A -> C, D -> C

            List<Position3D> positions = new ArrayList<>();
            Map<Integer,Map<Integer, Double>> connections = new HashMap<>();
            PriorityQueue<List<Integer>> potentialConnections = new PriorityQueue<>(Comparator.comparingDouble(l -> connections.get(l.get(0)).get(l.get(1))));
            List<Integer> connectedTo = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");
                Position3D pos = Position3D.fromArray(tokens);
                positions.add(pos);
                for (int i = 0; i < positions.size() - 1; i++) {
                    connections.computeIfAbsent(i, a -> new HashMap<>()).put(positions.size() - 1, euclideanDistance(positions.get(i), pos));
                    connections.computeIfAbsent(positions.size() - 1, a -> new HashMap<>()).put(i, euclideanDistance(positions.get(i), pos));
                    potentialConnections.add(Arrays.asList(i, positions.size() - 1));
                }
                connectedTo.add(positions.size() - 1);
            }
            if (debug) {
//                System.out.println("positions: " + positions);
//                System.out.println("connections: " + connections);
                System.out.println("potentialConnections: " + potentialConnections.size());
            }
            for (int i = 0; i < connectionsToMake && !potentialConnections.isEmpty(); i++) {
                List<Integer> connectionToAdd = potentialConnections.poll();
                final int positionA = connectionToAdd.get(0);
                final int positionB = connectionToAdd.get(1);
                if (debug) {
                    System.out.println("connectionToAdd: " + connectionToAdd);
                }
                int aIsConnectedTo = connectedTo.get(positionA);
                int bIsConnectedTo = connectedTo.get(positionB);
                if (aIsConnectedTo == positionA && bIsConnectedTo == positionB) {
                    connectedTo.set(positionB, positionA);
                    continue;
                }
                int rootPositionAIsConnectedTo = positionA;
                while (aIsConnectedTo != rootPositionAIsConnectedTo) {
                    rootPositionAIsConnectedTo = aIsConnectedTo;
                    aIsConnectedTo = connectedTo.get(rootPositionAIsConnectedTo);
                }
                int rootPositionBIsConnectedTo = positionB;
                while (bIsConnectedTo != rootPositionBIsConnectedTo) {
                    rootPositionBIsConnectedTo = bIsConnectedTo;
                    bIsConnectedTo = connectedTo.get(rootPositionBIsConnectedTo);
                }
                connectedTo.set(rootPositionBIsConnectedTo, rootPositionAIsConnectedTo);
            }
            if (debug) {
                System.out.println("connectedTo: " + connectedTo);
            }
            Map<Integer,Integer> circuitSizes = determineCircuitSizes(positions, connectedTo);
            if (debug) {
                System.out.println("circuitSizes: " + circuitSizes);
            }
            List<Integer> sizes = circuitSizes.values().stream()
                    .sorted(Comparator.reverseOrder()).toList();
            if (debug) {
                System.out.println("sizes: " + sizes);
            }
            for (int i = 0; i < 3; i++) {
                ans *= sizes.get(i);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }

    private Map<Integer, Integer> determineCircuitSizes(List<Position3D> positions, List<Integer> connectedTo) {
        Map<Integer, Integer> circuitSizes = new HashMap<>();
        for (int i = 0; i < positions.size(); i++) {
            int local = i;
            int curConnectedTo = connectedTo.get(i);
            while (curConnectedTo != local) {
                local = curConnectedTo;
                curConnectedTo = connectedTo.get(local);
            }
            circuitSizes.put(curConnectedTo, circuitSizes.getOrDefault(curConnectedTo, 0) + 1);
        }
        return circuitSizes;
    }

    private double euclideanDistance(Position3D a, Position3D b) {
        return Math.abs(Math.sqrt(Math.pow(a.x() - b.x(), 2) + Math.pow(a.y() - b.y(), 2) + Math.pow(a.z() - b.z(), 2)));
    }

    public long Part2(boolean debug) {
        throw new RuntimeException("Not implemented yet");
//        try (Scanner scanner = new Scanner(this.inputFile)) {
//            while (scanner.hasNextLine()) {
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }
}
