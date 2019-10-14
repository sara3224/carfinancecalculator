package edu.stthomas.carfinance;

import edu.stthomas.enums.Attributes;
import edu.stthomas.model.Record;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * loanamount,  numberOfPayments    annualInterestRate  paymentAmount
 * double,         int,                float,              double
 *
 * double, int, float
 * double, int, double
 * double, float, double
 * int, float, double
 *
 * Ref: https://www.vertex42.com/ExcelArticles/amortization-calculation.html
 */
public class FinanceCalculator implements FinanceCalculatorIfc {
    private static List<Record>  records = new ArrayList<>();

    /**
     * calculate payment amount
     * @param loanAmount
     * @param numberOfPayments
     * @param annualInterestRate
     * @return
     */
    @Override
    public double calculate(double loanAmount, int numberOfPayments, float annualInterestRate){
        annualInterestRate = annualInterestRate / (12 * 100);
        double paymentAmount = (loanAmount) * (annualInterestRate * Math.pow(1 + annualInterestRate, numberOfPayments))
                /(Math.pow(1 + annualInterestRate, numberOfPayments) - 1);
        Record record = new Record(Attributes.MONTHLY_AMT, loanAmount, paymentAmount,annualInterestRate, numberOfPayments );
        records.add(record);
        return paymentAmount;
    }

    /**
     * calculate annual interest rate
     * @param loanAmount
     * @param numberOfPayments
     * @param paymentAmount
     * @return
     * https://www.calculatorsoup.com/calculators/financial/compound-interest-calculator.php
     */
    @Override
    public float calculate(double loanAmount, int numberOfPayments, double paymentAmount){
        float annualInterestRate = (float) ((loanAmount / paymentAmount) * (12.00/numberOfPayments));
        Record record = new Record(Attributes.ANNUAL_RATE, loanAmount, paymentAmount,annualInterestRate, numberOfPayments );
        records.add(record);
        return annualInterestRate;
    }

    /**
     * calculate number of payments
     * @param loanAmount
     * @param annualInterestRate
     * @param paymentAmount
     * @return
     */
    @Override
    public int calculate(double loanAmount, float annualInterestRate, double paymentAmount){
        int numberOfPayments  =  (int) ((annualInterestRate / paymentAmount ) * loanAmount);
        Record record = new Record(Attributes.NUM_OF_PAYS, loanAmount, paymentAmount,annualInterestRate, numberOfPayments );
        records.add(record);
        return numberOfPayments;
    }

    /**
     * calculate loan amount.
     * Ref: https://www.vertex42.com/ExcelArticles/amortization-calculation.html
     * @param numberOfPayments
     * @param annualInterestRate
     * @param paymentAmount
     * @return
     */
    @Override
    public double calculate(int numberOfPayments, float annualInterestRate, double paymentAmount){
        annualInterestRate = annualInterestRate / (12 * 100);
        double loanAmount = paymentAmount * ((Math.pow(1 + annualInterestRate, numberOfPayments) - 1))
                / (annualInterestRate * Math.pow(1 + annualInterestRate, numberOfPayments));
        Record record = new Record(Attributes.LOAN_AMOUNT, loanAmount, paymentAmount, annualInterestRate, numberOfPayments );
        records.add(record);
        return loanAmount;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###.##");
        StringBuilder builder = new StringBuilder();
        builder.append("Calculate\t\t\tPrincipal\t\tAPR\t\tMonths\tPayment\n");
        for(Record record: records) {
            builder.append(record.getCalculatedAttribute()+ "\t\t\t"+df.format(record.getLoanAmount())+"\t\t\t"
            +df.format(record.getAnnualInterestRate())+"\t"+record.getNumberOfPayments()+"\t\t"+df.format(record.getPaymentAmount())+"\n");
        }
        builder.append("\n");
        return builder.toString();
    }
}
