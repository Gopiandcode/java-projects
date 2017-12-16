import java.util.ArrayList;

/**
 * Created by gopia on 27/01/2017.
 */
public class TemperatureTableGenerator {
    private float minTemp, maxTemp;
    private boolean generated = false;
    private ArrayList<ArrayList<String>> table;
    private char initTempHeader, endTempHeader;
    private int columnnCount;
    private int columnSpaces;

    private float celciusToFarenheit(float celcius) {
        return (float) (celcius * (9.0/5.0) + 32);
    }


    public TemperatureTableGenerator(float min, float max) {
        this.minTemp = min;
        this.maxTemp = max;
    }

    public void setColumnHeaders(char init, char to) {
        this.initTempHeader = init;
        this.endTempHeader = to;
    }

    public void setColumnNumber(int count) {
        this.columnnCount = count;
    }

    public void setColumnSpaces(int spaces) {
        this.columnSpaces = spaces;
    }


    public void formatTable() {
        if(this.generated) return;
        this.table = new ArrayList<ArrayList<String>>();
        float min = this.minTemp;

        ArrayList<String> line = new ArrayList<String>();
        for(int i = 0; i<this.columnnCount; i++) {
            line.add(String.format("%-3c%-3c ", this.initTempHeader, this.endTempHeader));
            for(int j = 0; j<this.columnSpaces; j++) {line.add(" ");}
        }

        table.add(line);

        while(min < this.maxTemp) {
            line = new ArrayList<String>();
            for(float i = min; i<min+columnnCount; i++) {
                line.add(String.format("%-3d",(int)i));
                line.add(String.format("%-3d ",(int)celciusToFarenheit(i)));
                for(int j = 0; j<columnSpaces; j++) {line.add(" ");}
            }
            table.add(line);
            min = min + columnnCount;
        }

        this.generated = true;
        return;
    }

    public ArrayList<ArrayList<String>> getTable() {
        if(!generated) formatTable();
        return new ArrayList<ArrayList<String>>(table);
    }


}
