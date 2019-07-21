package graphics.Handlers;


/**
 * @author Ben Azoulay
 */
public class Counter {

    private int aCounter;

    /**
     * Constructor.
     * @param counterBlock the number of block
     */
    public Counter(int counterBlock) {
        this.aCounter = counterBlock;

    }

    /**
     * add number to current count.
     * @param number the number of block to increase
     */
    public void increase(int number) {
        this.aCounter += number;
    }

    /**
     * subtract number from current count.
     * @param number the number of block to decrease
     */
    public void decrease(int number) {
        this.aCounter -= number;

    }

    /**
     * get current count.
     * @return the number of blocks remaining
     */
    public int getValue() {
        return this.aCounter;
    }
    /**
     * set current count.
     * @param nb the number to set
     */
    public void setValue(int nb) {
        this.aCounter = nb;
    }

}
