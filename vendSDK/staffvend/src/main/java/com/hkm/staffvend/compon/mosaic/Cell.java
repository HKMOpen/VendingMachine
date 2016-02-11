package com.hkm.staffvend.compon.mosaic;

/**
 * Created by zJJ on 1/23/2016.
 */
public class Cell {

    int id;
    int coorX;
    int coorY;
    public double x1; // left
    public double y1; // top

    public double x2; // right
    public double y2; // bottom

    private CellView mCellView;

    public boolean isOccupied = false;

    public double getHeight() {
        return y2 - y1;
    }

    public CellView getCellView() {
        return mCellView;
    }

    public void setCellView(CellView mCellView) {
        this.mCellView = mCellView;
    }
}
