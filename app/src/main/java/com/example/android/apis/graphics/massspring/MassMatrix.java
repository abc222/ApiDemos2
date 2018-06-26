package com.example.android.apis.graphics.massspring;

import java.util.ArrayList;
import java.util.List;

public class MassMatrix {
    public static final int M = 1;
    List<Spring> springs = new ArrayList();
    List<Mass> masses = new ArrayList<Mass>();

    public void addFroce(float xForce, float yForce) {
        for (Mass mass : masses) {
            mass.addFroce(xForce, yForce);
        }
    }

    public void setVerts(float[] mVerts) {

        float[] result = getVerts();

        for (int i = 0; i < mVerts.length; i++) {
            mVerts[i] = result[i];
        }
    }

    public float[] getVerts() {
        float[] result = new float[masses.size() * 2];
        for (int i = 0; i < masses.size(); i++) {
            result[i * 2] = masses.get(i).getX();
            result[i * 2 + 1] = masses.get(i).getY();
        }
        return result;
    }

    public MassMatrix() {
        init();
    }

    public void init() {
//        Mass mass1 = new Mass(false, M, 0, 0);
//        Mass mass2 = new Mass(false, M, 400, 0);
//        Mass mass3 = new Mass(false, M, 0, 400);
//        Mass mass4 = new Mass(false, M, 400, 400);
//        Mass mass5 = new Mass(true, M, 200, 200);

//        Spring spring1 = new Spring(283, 1, mass1, mass5);
//        Spring spring2 = new Spring(283, 1, mass2, mass5);
//        Spring spring3 = new Spring(283, 1, mass3, mass5);
//        Spring spring4 = new Spring(283, 1, mass4, mass5);
//        springs.add(spring1);
//        springs.add(spring2);
//        springs.add(spring3);
//        springs.add(spring4);

//        mass5.add(spring1);
//        mass5.add(spring2);
//        mass5.add(spring3);
//        mass5.add(spring4);

        //添加点
        for(int i = 0;i <= 400;i += 100){
            for(int j = 0;j <= 400;j += 100){
                if(i == 0 || j == 0 || i == 400 || j == 400)
                    masses.add(new Mass(false,M,(float)i,(float)j));
                else
                    masses.add(new Mass(true,M,(float)i,(float)j));
            }
        }
        //添加弹簧
        for(int i = 0;i < 4;i ++){
            springs.add(new Spring(142,1,masses.get(i),masses.get(i+1)));
            springs.add(new Spring(142,1,masses.get(i+5),masses.get(i+6)));
            springs.add(new Spring(142,1,masses.get(i+10),masses.get(i+11)));
            springs.add(new Spring(142,1,masses.get(i+15),masses.get(i+16)));
            springs.add(new Spring(142,1,masses.get(i+20),masses.get(i+21)));
        }
        for(int i = 0;i < 16;i += 5){
            springs.add(new Spring(142,1,masses.get(i),masses.get(i+5)));
            springs.add(new Spring(142,1,masses.get(i+1),masses.get(i+6)));
            springs.add(new Spring(142,1,masses.get(i+2),masses.get(i+7)));
            springs.add(new Spring(142,1,masses.get(i+3),masses.get(i+8)));
            springs.add(new Spring(142,1,masses.get(i+4),masses.get(i+9)));
        }
        //给点加弹簧
        masses.get(6).add(springs.get(1));
        masses.get(6).add(springs.get(6));
        masses.get(6).add(springs.get(21));
        masses.get(6).add(springs.get(26));

        masses.get(7).add(springs.get(6));
        masses.get(7).add(springs.get(11));
        masses.get(7).add(springs.get(22));
        masses.get(7).add(springs.get(27));

        masses.get(8).add(springs.get(11));
        masses.get(8).add(springs.get(16));
        masses.get(8).add(springs.get(23));
        masses.get(8).add(springs.get(28));

        masses.get(11).add(springs.get(2));
        masses.get(11).add(springs.get(7));
        masses.get(11).add(springs.get(26));
        masses.get(11).add(springs.get(31));

        masses.get(12).add(springs.get(7));
        masses.get(12).add(springs.get(12));
        masses.get(12).add(springs.get(27));
        masses.get(12).add(springs.get(32));

        masses.get(13).add(springs.get(12));
        masses.get(13).add(springs.get(17));
        masses.get(13).add(springs.get(28));
        masses.get(13).add(springs.get(33));

        masses.get(16).add(springs.get(3));
        masses.get(16).add(springs.get(8));
        masses.get(16).add(springs.get(31));
        masses.get(16).add(springs.get(36));

        masses.get(17).add(springs.get(8));
        masses.get(17).add(springs.get(13));
        masses.get(17).add(springs.get(32));
        masses.get(17).add(springs.get(37));

        masses.get(18).add(springs.get(13));
        masses.get(18).add(springs.get(18));
        masses.get(18).add(springs.get(33));
        masses.get(18).add(springs.get(38));

    }

    public void simulate(float t) {
        for (Spring spring : springs) {
            spring.calculate();
        }
        for (Mass mass : masses) {
            mass.move(t);
        }
    }
}
