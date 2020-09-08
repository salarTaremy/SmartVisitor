package Entities;

public class OrderCellInfoViewModel extends MyCellInfo {

    public int ID_order ;
    public OrderCellInfoViewModel(String OperatorName, boolean IsRegistered, int mcc, int mnc, int cellid, int lac) {
        super(OperatorName, IsRegistered, mcc, mnc, cellid, lac);
    }
    public OrderCellInfoViewModel(int ID_order ,MyCellInfo cellInfo) {
        super(cellInfo.OperatorName, cellInfo.IsRegistered, cellInfo.mcc, cellInfo.mnc, cellInfo.cellid, cellInfo.lac);
        this.ID_order = ID_order;
    }
}