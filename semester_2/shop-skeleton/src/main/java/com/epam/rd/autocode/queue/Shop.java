package com.epam.rd.autocode.queue;

import com.epam.rd.autocode.queue.CashBox.State;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;


public class Shop {

    private final int cashBoxCount;

    private final List<CashBox> cashBoxes;

    int[] minMaxSizes;

    public Shop(int count) {
        this.cashBoxCount = count;
        this.cashBoxes = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            cashBoxes.add(new CashBox(i));
        }
    }

    private static int getTotalBuyersCount(List<CashBox> cashBoxes) {
        int sum = 0;
        for (CashBox cashBox : cashBoxes) {
            int size = cashBox.getQueue().size();
            sum += size;
        }
        return sum;
    }

    public static int[] getMinMaxSize(List<CashBox> cashBoxes) {
        int enableCashBox = 0;

        for (CashBox cashBox : cashBoxes) {
            if (cashBox.notInState(State.DISABLED)) {
                enableCashBox++;
            }
        }
        int total = getTotalBuyersCount(cashBoxes) - 1;
        int maxSize = total / enableCashBox;
        int minSize = total % enableCashBox;

        return new int[]{minSize, maxSize};
    }

    public void addBuyer(Buyer buyer) {
        boolean seen = false;
        CashBox best = null;
        Comparator<CashBox> comparator = Comparator.comparingInt(new ToIntFunction<>() {
            @Override
            public int applyAsInt(CashBox cashBox) {
                return cashBox.getQueue().size();
            }
        });

        for (CashBox box : cashBoxes) {
            if (box.inState(State.ENABLED)) {
                if (! seen || comparator.compare(box, best) < 0) {
                    seen = true;
                    best = box;
                }
            }
        }
        CashBox minQueueCashBox = seen ? best : null;

        if (minQueueCashBox != null) {
            minQueueCashBox.addLast(buyer);
        }
    }

    public int getCashBoxCount() {
        return cashBoxCount;
    }

    public void tact() {
        minMaxSizes = getMinMaxSize(cashBoxes);
        for (CashBox cashBox : cashBoxes) {
            if (cashBox.getQueue().size() != 0 && cashBox.inState(State.ENABLED) || cashBox.inState(State.IS_CLOSING)) {
                cashBox.serveBuyer();
            }
        }
        for (CashBox cashBox : cashBoxes) {
            if (cashBox.inState(State.IS_CLOSING) && cashBox.getQueue().isEmpty()) {
                cashBox.setState(State.DISABLED);
            }

            if (cashBox.inState(State.ENABLED) && cashBox.getQueue().size() < minMaxSizes[1]) {
                balanceQueues();
            }
        }
    }

    private void balanceQueues() {
        List<Buyer> defector_Buyers = new ArrayList<>();

        for (CashBox cashBox : cashBoxes) {
            int required_Buyers = minMaxSizes[1] - cashBox.getQueue().size();

            while (required_Buyers >= 0 && ! defector_Buyers.isEmpty() && cashBox.inState(State.ENABLED)) {
                Buyer defector = defector_Buyers.remove(0);
                cashBox.addLast(defector);
                required_Buyers--;
            }

            int excess_Buyers = cashBox.getQueue().size() - minMaxSizes[1];
            while (excess_Buyers > 0) {
                defector_Buyers.add(cashBox.removeLast());
                excess_Buyers--;
            }
        }
    }

    public void setCashBoxState(int cashBoxNumber, State state) {
        cashBoxes.get(cashBoxNumber).setState(state);
    }

    public CashBox getCashBox(int cashBoxNumber) {
        return cashBoxes.get(cashBoxNumber);
    }

    public void print() {
        for (CashBox cashBox : cashBoxes) {
            System.out.println(cashBox.toString());
        }
    }

}