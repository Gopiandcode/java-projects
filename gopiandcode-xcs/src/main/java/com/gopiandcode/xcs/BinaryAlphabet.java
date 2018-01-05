package com.gopiandcode.xcs;
/**
 * XCS Implementation - Based on An Algorithmic Description of XCS - Martin V. Butz and Stewart W.Wilson
 */

public enum BinaryAlphabet {
    ONE, ZERO;
    public String toString() {
        switch(this) {
            case ONE:
                return "1";
            case ZERO:
                return "0";
        }
        return null;
    }

}
