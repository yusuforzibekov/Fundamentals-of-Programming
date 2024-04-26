package com.epam.rd.autotasks;

import java.lang.Math;
import java.util.List;

public class Battleship8x8 {
    private final long ships;
    private long shots = 0L;

    public Battleship8x8(final long ships) {
        this.ships = ships;
    }

    private long getShots() {
        return shots;
    }

    public boolean shoot(String shot) {
        int column = (int) shot.charAt(0) - (int) 'A';
        int row = (int) shot.charAt(1) - (int) '1';
        char[][] map = convertLongToArray(ships);
        final double pew = Math.pow(2, 8 * (7 - row) + 7 - column);
        if (pew - 1 == Long.MAX_VALUE) {
            shots -= pew;
        } else {
            shots += (long) pew;
        }
        return map[row][column] != '.';
    }

    private static char[][] convertLongToArray(final long map) {
        char[][] listMap = new char[8][8];
        String binNum = Long.toBinaryString(map);
        int binLen = binNum.length();
        int k = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (64 - binLen > 0) {
                    listMap[i][j] = '.';
                    binLen += 1;
                } else {
                    listMap[i][j] = (binNum.charAt(k) == '0') ? '.' : '☐';
                    k++;
                }
            }
        }
        return listMap;
    }

    public String state() {
        String field = "";
        char[][] map = convertLongToArray(ships);
        char[][] hits = convertLongToArray(shots);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (map[i][j] == '.' && hits[i][j] == '☐') {
                    field += "×";
                } else if (map[i][j] == '☐' && hits[i][j] == '☐') {
                    field += "☒";
                } else {
                    field += map[i][j];
                }
            }
            field += "\n";
        }
        return field;
    }

    public static void main(String[] g) {
        long map = -1150950973573693440L;
        List<String> shotss = List.of("A1", "B2", "C3", "D4");
        Battleship8x8 battle = new Battleship8x8(map);
        shotss.forEach(battle::shoot);
        System.out.println(Long.toBinaryString((long) -Math.pow(2, 63) + 4));
    }

}