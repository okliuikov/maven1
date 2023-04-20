package com.code;



/* Modify the WaterBuilder class implementation, so it produces valid H₂O molecules
 in the system output (combinations of 3 chars containing 2xH and 1xO atoms).
 Note: The order of H/O does not matter, the quantity does (i.e. HHO is the same fine as HOH). */

public class WaterBuilder {

    public WaterBuilder() {
        this.numberHydrogen = 0;
        this.numberOxygen = 0;

    }

    private int numberHydrogen;
    private int numberOxygen;

    public synchronized void addH() {
        try {
            while (numberOxygen == 0 && numberHydrogen == 2) {
                wait();
            }

            System.out.print("H");
            numberHydrogen++;
            if (numberHydrogen == 2 && numberOxygen == 1) {
                numberHydrogen = 0;
                numberOxygen = 0;
                System.out.println("\n");
            }
            notifyAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public synchronized void addO() {
        try {
            while (numberHydrogen < 2 && numberOxygen == 1) {
                wait();
            }
            System.out.print("O");
            numberOxygen++;
            if (numberOxygen == 1 && numberHydrogen == 2) {
                numberOxygen = 0;
                numberHydrogen = 0;
                System.out.println("\n");
            }
            notifyAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String testSeq = "HOHHHHHHHOHHHHHHHHHHHOOOOOOOOH"; // 10 H₂O's
        WaterBuilder myWater = new WaterBuilder();
        for (char c : testSeq.toCharArray()) {
            switch (c) {
                case 'H':
                    new Thread(myWater::addH).start();
                    break;
                case 'O':
                    new Thread(myWater::addO).start();
                    break;
            }
        }
    }
}