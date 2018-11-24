package pl.sdacademy.vending.model;

public class VendingMachineSnapshot {
    private final TraySnapshot[][] trays;

    private VendingMachineSnapshot(Builder builder){
        this.trays = builder.trays;
    }

    public static class Builder{
        private TraySnapshot[][] trays;


        private Builder(int rows, int cols){
            trays = new TraySnapshot[rows][cols];
        }

        public Builder tray(int rowNo, int colNo, TraySnapshot tray){

            trays[rowNo][colNo] = tray;
            return this;
        }

        public VendingMachineSnapshot build(){
            return new VendingMachineSnapshot(this);
        }
    }

}
