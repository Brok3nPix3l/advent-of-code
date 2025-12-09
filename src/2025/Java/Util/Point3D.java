package Util;

public record Point3D(int x, int y, int z) {
    public static Point3D fromArray(String[] tokens) {
        int x = Integer.parseInt(tokens[0]);
        int y = Integer.parseInt(tokens[1]);
        int z = Integer.parseInt(tokens[2]);
        return new Point3D(x, y, z);
    }
}