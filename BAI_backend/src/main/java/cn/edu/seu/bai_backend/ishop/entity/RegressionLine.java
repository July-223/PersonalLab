package cn.edu.seu.bai_backend.ishop.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RegressionLine {
    private float sumX = 0;//训练集x的和
    private float sumY = 0;//训练集y的和
    private float sumXX = 0;//x*x的和
    private float sumYY = 0;//y*y的和
    private float sumXY = 0;//x*y的和
    private float sumDeltaY;//y与yi的差
    private float sumDeltaY2; // sumDeltaY的平方和
    //误差
    private float sse;//残差平方和
    private float sst;//总平方和
    private float E;
    private float[] xy;
    private ArrayList<String> listX;//x的链表
    private ArrayList<String> listY;//y的链表
    private double XMin,XMax,YMin,YMax;
    private float a0;//线性系数a0
    private float a1;//线性系数a1
    private int pn;  //训练集数据个数
    private boolean coefsValid;
    //类RegressionLine的构造函数
    public RegressionLine(){
        XMax = 0;
        YMax = 0;
        pn = 0;
        xy = new float[2];
        listX = new ArrayList<>();
        listY = new ArrayList<>();
    }
    //类RegressionLine的有参构造函数
    public RegressionLine(DataPoint data[]){
        pn = 0;
        xy = new float[2];
        listX = new ArrayList();
        listY = new ArrayList();
        for(int i = 0;i<data.length;i++){
            addDatapoint(data[i]);//添加数据集的方法addDatapoint
        }
    }

    public RegressionLine(List<DataPoint> dataPointList){
        pn = 0;
        xy = new float[2];
        listX = new ArrayList();
        listY = new ArrayList();
        for(int i = 0;i<dataPointList.size();i++){
            addDatapoint(dataPointList.get(i));//添加数据集的方法addDatapoint
        }
    }


    public int getDataPointCount(){
        return pn;
    }
    public float getA0(){
        validateCoefficients();
        return a0;
    }
    public float getA1(){
        validateCoefficients();
        return a1;
    }
    public double getSumX(){
        return sumX;
    }
    public double getSumY() {
        return sumY;
    }
    public double getSumXX() {
        return sumXX;
    }
    public double getSumYY() {
        return sumYY;
    }
    public double getSumXY() {
        return sumXY;
    }
    public double getXMin() {
        return XMin;
    }
    public double getXMax() {
        return XMax;
    }

    public double getYMax() {
        return YMax;
    }
    public double getYMin() {
        return YMin;
    }
    //添加训练集数据的方法
    public void addDatapoint(DataPoint dataPoint){
        sumX += dataPoint.x;
        sumY += dataPoint.y;
        sumXX += dataPoint.x*dataPoint.x;
        sumYY += dataPoint.y*dataPoint.y;
        sumXY += dataPoint.x*dataPoint.y;

        if(dataPoint.x > XMax){
            XMax = dataPoint.x;
        }
        if (dataPoint.y > YMax){
            YMax = dataPoint.y;
        }
        xy[0] = dataPoint.x ;//?
        xy[1] = dataPoint.y ;//?
        if(dataPoint.x !=0 && dataPoint.y != 0){
            System.out.print("("+xy[0]+",");
            System.out.println(xy[1]+")");
            try{
                listX.add(pn,String.valueOf(xy[0]));
                listY.add(pn,String.valueOf(xy[1]));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        ++pn;
        coefsValid = false;
    }
    //计算预测值y的方法
    public float at(float x){
        if(pn < 2)
            return Float.NaN;
        validateCoefficients();
        return a0 + a1 * x;
    }
    //重置此类的方法
    public void reset(){
        pn = 0;
        sumX = sumY = sumXX = sumXY = 0;
        coefsValid = false;
    }
    //计算系数a0，a1的方法
    private void validateCoefficients(){
        if (coefsValid)
            return;
        if (pn >= 2){
            float xBar = (float)sumX/pn;
            float yBar = (float)sumY/pn;
            a1 = (float)((pn*sumXY - sumX*sumY)/(pn
                    *sumXX - sumX*sumX));
            a0 = (yBar - a1*xBar);
        }
        else {
            a0 = a1 = Float.NaN;
        }
        coefsValid = true;
    }
    //计算判定系数R^2的方法
    public double getR(){
        for (int i = 0;i < pn;i++){
            float Yi = Float.parseFloat(listY.get(i).toString());
            float Y = at(Float.parseFloat(
                    listX.get(i).toString()));
            float deltaY = Yi - Y;
            float deltaY2 = deltaY*deltaY;
            sumDeltaY2 += deltaY2;
            float deltaY1 = (Yi - (float) (sumY/pn))*(Yi - (float) (sumY/pn)) ;
            sst += deltaY1;
        }
        //sst = sumYY - (sumY*sumY)/pn;
        E = 1 - sumDeltaY2/sst;
        return round(E,4);
    }
    //返回经处理过的判定系数的方法
    public double round(double v,int scale){
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public void setSumX(float sumX) {
        this.sumX = sumX;
    }

    public void setSumY(float sumY) {
        this.sumY = sumY;
    }

    public void setSumXX(float sumXX) {
        this.sumXX = sumXX;
    }

    public void setSumYY(float sumYY) {
        this.sumYY = sumYY;
    }

    public void setSumXY(float sumXY) {
        this.sumXY = sumXY;
    }

    public float getSumDeltaY() {
        return sumDeltaY;
    }

    public void setSumDeltaY(float sumDeltaY) {
        this.sumDeltaY = sumDeltaY;
    }

    public float getSumDeltaY2() {
        return sumDeltaY2;
    }

    public void setSumDeltaY2(float sumDeltaY2) {
        this.sumDeltaY2 = sumDeltaY2;
    }

    public float getSse() {
        return sse;
    }

    public void setSse(float sse) {
        this.sse = sse;
    }

    public float getSst() {
        return sst;
    }

    public void setSst(float sst) {
        this.sst = sst;
    }

    public float getE() {
        return E;
    }

    public void setE(float e) {
        E = e;
    }

    public float[] getXy() {
        return xy;
    }

    public void setXy(float[] xy) {
        this.xy = xy;
    }

    public ArrayList<String> getListX() {
        return listX;
    }

    public void setListX(ArrayList<String> listX) {
        this.listX = listX;
    }

    public ArrayList<String> getListY() {
        return listY;
    }

    public void setListY(ArrayList<String> listY) {
        this.listY = listY;
    }

    public void setXMin(double XMin) {
        this.XMin = XMin;
    }

    public void setXMax(double XMax) {
        this.XMax = XMax;
    }

    public void setYMin(double YMin) {
        this.YMin = YMin;
    }

    public void setYMax(double YMax) {
        this.YMax = YMax;
    }

    public void setA0(float a0) {
        this.a0 = a0;
    }

    public void setA1(float a1) {
        this.a1 = a1;
    }

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public boolean isCoefsValid() {
        return coefsValid;
    }

    public void setCoefsValid(boolean coefsValid) {
        this.coefsValid = coefsValid;
    }
}
