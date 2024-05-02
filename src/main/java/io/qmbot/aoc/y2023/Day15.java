package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import java.util.HashMap;
import java.util.Map;


public class Day15 implements Puzzle {
    @Override
    public Object part1(String input) {
        String[] strings = parse(input);
        long result = 0;
        for(String s : strings){
            result += HASH(s.trim());
        }
        return result;
    }

    @Override
    public Object part2(String input) {
        String[] strings = parse(input);
        Box[] boxes = boxes(strings);
        return focusingPower(boxes);
    }

    String[] parse(String input){
        return input.split(",");
    }

    long focusingPower(Box[] boxes) {
        long result = 0L;
        for (int i = 0; i < boxes.length; i++) {
            Box b = boxes[i];
            if (b.lensMap != null) {
                for (Lens l : b.lensMap.values()) {
                    int slot = l.getSlot();
                    int fl = l.getFocalLength();
                    int box = i+1;
                    int fp = box  * slot * fl;
                    result += fp;
                }
            }
        }
        return result;
     }

    Box[] boxes(String[] strings) {
        Box[] boxes = new Box[256];
        for (int i = 0; i < 256; i++) {
            boxes[i] = new Box();
        }
        for(String s : strings){
            s = s.trim();
            if (s.contains("-")){
                String label = s.substring(0, s.length() - 1);
                deleteLens(boxes, label);
            } else if (s.contains("=")){
                String[] lens = s.split("=");
                String label = lens[0];
                int focalLength = Integer.parseInt(lens[1]);
                int boxNumber = HASH(label);
                if (boxes[boxNumber].lensMap.containsKey(label)) {
                    boxes[boxNumber].lensMap.get(label).setFocalLength(focalLength);
                } else {
                    boxes[boxNumber].lensMap.put(label, new Lens(boxes[boxNumber].lensMap.size() + 1, focalLength));
                }
            }
        }
        return boxes;
    }
    void deleteLens(Box[] boxes, String label) {
        Box b = boxes[HASH(label)];
        if (b.lensMap.containsKey(label)) {
            int freeSlot = b.lensMap.get(label).getSlot();
            b.lensMap.remove(label);
            for(Lens l : b.lensMap.values()){
                if (l.slot > freeSlot) {
                    l.setSlot(l.slot - 1);
                }
            }
        }
    }

    int HASH(String string){
        int result = 0;
        for (int i = 0; i < string.length(); i++) {
            int ascii = (int) string.charAt(i) + result;
            int step2 = ascii * 17;
            result = step2 % 256;
        }
        return result;
    }

    static class Box{
        Map<String, Lens> lensMap = new HashMap<>();

        @Override
        public String toString() {
            return  lensMap.toString();
        }
    }
    static class Lens{
        int slot;
        int focalLength;

        @Override
        public String toString() {
            return  slot +
                    ", " + focalLength;
        }

        public Lens(int slot, int focalLength) {
            this.slot = slot;
            this.focalLength = focalLength;
        }

        public int getSlot() {
            return slot;
        }

        public void setSlot(int slot) {
            this.slot = slot;
        }

        public int getFocalLength() {
            return focalLength;
        }

        public void setFocalLength(int focalLength) {
            this.focalLength = focalLength;
        }
    }
}
