package com.weshare.umg.system;

/**
 * @Author: finleyli
 * @Date: Created in 2019/2/15
 * @Describe:
 **/
public enum Operation {

    /**
     * 一发一
     */
    SINGLE_SEND(10),
    /**
     * 一发多
     */
    BATCH_SEND(11),

    /**
     * 业务自定
     */
    SYSTEM_LOGIC(30),
    ;



    private int operCode;

    Operation(int operCode) {
        this.operCode = operCode;
    }

    public static Operation getByValue(Integer operCode) {
        Operation[] operations = Operation.values();
        for (Operation operation : operations) {
            if (operation.operCode == operCode) {
                return operation;
            }
        }
        return null;
    }

    public int getOperCode() {
        return operCode;
    }
}
