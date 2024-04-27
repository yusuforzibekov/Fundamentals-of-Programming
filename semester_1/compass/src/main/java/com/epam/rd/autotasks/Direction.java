package com.epam.rd.autotasks;

public enum Direction {
    N(0), NE(45), E(90), SE(135), S(180), SW(225), W(270), NW(315);

    Direction(final int degrees) {
        this.degrees = degrees;
    }

    private final int degrees;

    public int getDegrees() {
        return degrees;
    }

    public static Direction ofDegrees(int degrees) {
        degrees = (degrees % 360 + 360) % 360;

        for (Direction d : Direction.values()) {
            if (d.getDegrees() == degrees)
                return d;
        }
        return null;
    }

    public static Direction closestToDegrees(int degrees) {
        if (degrees > 360 || degrees < -360) {
            int ost = degrees / 360;
            degrees = degrees - (360 * ost);

            innerClosestToDegrees(degrees);
        }
        return innerClosestToDegrees(degrees);
    }

    private static Direction innerClosestToDegrees(int degrees) {
        for (Direction d : Direction.values()) {
            if (degrees < d.getDegrees() && degrees >= d.getDegrees() - 22 ||
                    (degrees > d.getDegrees() && degrees <= d.getDegrees() + 22))  {
                return d;
            }
        }
        return ofDegrees(degrees);
    }

    public Direction opposite() {
        for (Direction d : Direction.values()) {
            if (d.getDegrees() == this.getDegrees() - 180 || d.getDegrees() == this.getDegrees() + 180) {
                return d;
            }
        }
        return null;
    }

    public int differenceDegreesTo(Direction direction) {
        int diff = Math.abs(this.getDegrees() - direction.getDegrees());
        return Math.min(diff, 360 - diff);
    }
}
