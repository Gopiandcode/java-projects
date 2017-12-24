package Main;

import Language.*;

/**
 * Created by gopia on 05/09/2017.
 */
public class Main {
    public static void main(String[] args) {
        Stm prog = new CompoundStm(
                new AssignStm("a", new OpExp(new NumExp(5), OpExp.PLUS, new NumExp(3))),
                new CompoundStm(new AssignStm(
                        "b",
                        new EseqExp(new PrintStm(new PairExpList(new IdExp("a"),
                                new LastExpList(new OpExp(new IdExp("a"), OpExp.MINUS, new NumExp(1))))),
                                new OpExp(new NumExp(10), OpExp.TIMES, new IdExp("a")))),
                        new PrintStm(new LastExpList(new IdExp("b"))
                )));

        System.out.println(prog.maxargs());

    }
}
